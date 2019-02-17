package com.comsince.github;

import com.comsince.github.handler.PushMessageHanlder;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

public class PushClientHandler extends PushMessageHanlder {

    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        return super.heartbeatPacket(channelContext);
    }
}
