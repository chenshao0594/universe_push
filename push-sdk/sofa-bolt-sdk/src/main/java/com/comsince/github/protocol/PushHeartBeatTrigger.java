package com.comsince.github.protocol;

import com.alipay.remoting.HeartbeatTrigger;
import com.comsince.github.command.RequestPushCommand;
import com.comsince.github.command.Signal;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 下午5:06
 **/
public class PushHeartBeatTrigger implements HeartbeatTrigger {
    @Override
    public void heartbeatTriggered(ChannelHandlerContext ctx) throws Exception {
        int interval = 15000;
        String heartInterval = "{\"interval\":"+interval+"}";
        ctx.channel().writeAndFlush(new RequestPushCommand(heartInterval, Signal.PING));
    }
}
