package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.heartbeat.HeartBeatBody;
import com.comsince.github.heartbeat.HeartbeatResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.json.Json;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:05
 **/
public class HeartbeatResponseProcessor implements MessageProcessor {
    Logger logger = LoggerFactory.getLogger(HeartbeatResponseProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        logger.info("receive signal "+pushPacket.getHeader().getSignal()+" interval "+new String(pushPacket.getBody()));
        HeartBeatBody heartBeatBody = Json.toBean(new String(pushPacket.getBody()),HeartBeatBody.class);

        HeartbeatResponsePacket heartbeatResponsePacket = new HeartbeatResponsePacket("ping back next interval "+heartBeatBody.getInterval());
        //设置链接心跳的间隔，如果超出范围自动断开链接
        channelContext.setHeartbeatTimeout(heartBeatBody.getInterval()+ 60 * 1000);
        Tio.send(channelContext,heartbeatResponsePacket);
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.PING;
    }
}
