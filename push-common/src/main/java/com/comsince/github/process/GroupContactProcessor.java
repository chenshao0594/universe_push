package com.comsince.github.process;

import com.comsince.github.PushGroupService;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.model.GroupRequest;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:58
 **/
public class GroupContactProcessor implements MessageProcessor{
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        PushGroupService pushGroupService = (PushGroupService) SpringApplicationContext.getBean("pushGroupService");
        pushGroupService.sendGroupRequest(Json.toBean(new String(pushPacket.getBody()), GroupRequest.class));
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return Signal.CONTACT == pushPacket.getHeader().getSignal();
    }
}
