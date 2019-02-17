package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.sub.SubResponse;
import com.comsince.github.sub.SubResponsePacket;
import org.tio.core.ChannelContext;
import org.tio.core.DefaultTioUuid;
import org.tio.core.Tio;

public class SubResponseProcessor implements PushMessageProcessor {
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        SubResponse subResponse = new SubResponse();
        subResponse.setStatus(200);
        subResponse.setToken(new DefaultTioUuid().uuid());

        SubResponsePacket subResponsePacket = new SubResponsePacket(subResponse);
        Tio.send(channelContext,subResponsePacket);
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return pushPacket.getHeader().getSignal() == Signal.SUB;
    }
}
