package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.configuration.PushCommonConfiguration;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.sub.SubResponse;
import com.comsince.github.sub.SubResponsePacket;
import com.comsince.github.sub.SubService;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.DefaultTioUuid;
import org.tio.core.Tio;

public class SubResponseProcessor implements MessageProcessor {
    Logger logger = LoggerFactory.getLogger(SubResponseProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        SubResponse subResponse = new SubResponse();
        subResponse.setStatus(200);
        String token = channelContext.getBsId();
        if(!StringUtils.isNotBlank(token)){
            logger.info("channel connect generate token fail so regenerate token");
            PushCommonConfiguration pushServerConfiguration = (PushCommonConfiguration) SpringApplicationContext.getBean("pushServerConfiguration");
            SubService subService = pushServerConfiguration.subService();
            if(subService == null){
                token = new DefaultTioUuid().uuid();
            } else {
                token = subService.generateToken();
            }
            RedissonClient redissonClient = (RedissonClient) SpringApplicationContext.getBean("redissonClient");
            redissonClient.getMap("online_status").fastPut(token,1);
        }
        subResponse.setToken(token);
        logger.info("receive signal "+pushPacket.getHeader().getSignal()+" generate token "+token);
        SubResponsePacket subResponsePacket = new SubResponsePacket(subResponse);
        //绑定token
        Tio.bindBsId(channelContext,token);
        Tio.send(channelContext,subResponsePacket);
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.SUB;
    }
}
