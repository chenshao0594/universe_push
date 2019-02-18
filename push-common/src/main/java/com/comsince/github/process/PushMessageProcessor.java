package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:48
 **/
public class PushMessageProcessor implements MessageProcessor{
    Logger logger = LoggerFactory.getLogger(PushMessageProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        logger.info("send push message "+new String(pushPacket.getBody()));
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.PUSH;
    }
}
