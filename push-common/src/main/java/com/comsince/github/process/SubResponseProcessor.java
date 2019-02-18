package com.comsince.github.process;

import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.sub.SubResponse;
import com.comsince.github.sub.SubResponsePacket;
import com.comsince.github.sub.SubService;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

public class SubResponseProcessor implements MessageProcessor {
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        SubResponse subResponse = new SubResponse();
        subResponse.setStatus(200);
        SubService subService = (SubService) SpringApplicationContext.getBean("subService");
        String token = subService.generateToken();
        subResponse.setToken(token);

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
