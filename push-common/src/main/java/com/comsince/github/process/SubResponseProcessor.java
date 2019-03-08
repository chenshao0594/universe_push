package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.configuration.PushCommonConfiguration;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.sub.SubRequestBody;
import com.comsince.github.sub.SubResponse;
import com.comsince.github.sub.SubResponsePacket;
import com.comsince.github.sub.SubService;
import com.comsince.github.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.DefaultTioUuid;
import org.tio.core.Tio;
import org.tio.utils.json.Json;

public class SubResponseProcessor implements MessageProcessor {
    Logger logger = LoggerFactory.getLogger(SubResponseProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {

        SubResponse subResponse = new SubResponse();
        subResponse.setStatus(200);

        if(pushPacket.getBody() != null){
            SubRequestBody subRequestBody = Json.toBean(new String(pushPacket.getBody()),SubRequestBody.class);
            logger.info("receive request body "+subRequestBody.getUid());
            if(StringUtils.isNotBlank(subRequestBody.getUid())){
                String uid = subRequestBody.getUid();
                //如何时重连，需要关闭先前的channel,有可能这个链接在不同机器上，这里不再处理，让其超时断连
                RedissonClient redissonClient = (RedissonClient) SpringApplicationContext.getBean(Constants.REDISCLIENT_NAME);
                redissonClient.getMap(Constants.UID_TOKEN_MAP).fastPut(uid,channelContext.getBsId());
            }
        }
        subResponse.setToken(channelContext.getBsId());
        logger.info("receive signal "+pushPacket.getHeader().getSignal()+" bind token "+channelContext.getBsId());
        SubResponsePacket subResponsePacket = new SubResponsePacket(subResponse);
        Tio.send(channelContext,subResponsePacket);
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.SUB;
    }
}
