package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.heartbeat.HeartbeatResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:05
 **/
public class HeartbeatResponseProcessor implements MessageProcessor {
    Logger logger = LoggerFactory.getLogger(HeartbeatResponseProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        logger.info("receiver signal "+pushPacket.getHeader().getSignal());
        HeartbeatResponsePacket heartbeatResponsePacket = new HeartbeatResponsePacket("ping back");
        Tio.send(channelContext,heartbeatResponsePacket);
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.PING;
    }
}
