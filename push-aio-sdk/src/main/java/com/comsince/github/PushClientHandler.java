package com.comsince.github;

import com.comsince.github.handler.PushMessageHanlder;
import com.comsince.github.heartbeat.HeartbeatRequestPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

public class PushClientHandler extends PushMessageHanlder {

    Logger logger = LoggerFactory.getLogger(PushClientHandler.class);

    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        return new HeartbeatRequestPacket();
    }


    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        PushPacket pushPacket = (PushPacket) packet;
        Signal signal = pushPacket.getHeader().getSignal();
        logger.info("push client receive signal ：" + signal.name()+" body-> "+new String(((PushPacket) packet).getBody(),"UTF-8"));
    }
}
