package com.comsince.github.handler;

import com.comsince.github.configuration.PushCommonConfiguration;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.sub.SubService;
import com.comsince.github.utils.Constants;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.DefaultTioUuid;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午10:26
 **/
public class PushConnectorListener implements ServerAioListener{
    Logger logger = LoggerFactory.getLogger(PushConnectorListener.class);

    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) throws Exception {
        String token;
        PushCommonConfiguration pushServerConfiguration = (PushCommonConfiguration) SpringApplicationContext.getBean(Constants.PUSHSERVER_CONFIGURATION);
        SubService subService = pushServerConfiguration.subService();
        if(subService == null){
            token = new DefaultTioUuid().uuid();
        } else {
            token = subService.generateToken();
        }
        Tio.bindBsId(channelContext,token);

        RedissonClient redissonClient = (RedissonClient) SpringApplicationContext.getBean(Constants.REDISCLIENT_NAME);
        long onlineNum = redissonClient.getAtomicLong(Constants.ONLINE_NUM).incrementAndGet();
        redissonClient.getMap(Constants.ONLINE_STATUS).fastPut(token,1);
        logger.info("onAfterConnected client:"+channelContext.getClientNode()+" current onlineNum "+onlineNum);
    }

    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {

    }

    /**
     * 本接口用来回调发送是否成功,用户缓存消息，便于用户上线后消息下发
     * */
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {
        logger.info("onAfterSent client:"+channelContext.getClientNode()+" bsId "+channelContext.getBsId()+" sendSuccess "+b);
    }

    public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {

    }

    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {
        //重连可能出现不同channel对应同一个token这里不要用来统计当前在线token
      RedissonClient redissonClient = (RedissonClient) SpringApplicationContext.getBean(Constants.REDISCLIENT_NAME);
      long onlineNum = redissonClient.getAtomicLong(Constants.ONLINE_NUM).decrementAndGet();
      redissonClient.getMap(Constants.ONLINE_STATUS).fastPut(channelContext.getBsId(),0);
      logger.info("onBeforeClose close client:"+channelContext.getClientNode()+" token:"+channelContext.getBsId()+" current onlineNum "+onlineNum);
    }


}
