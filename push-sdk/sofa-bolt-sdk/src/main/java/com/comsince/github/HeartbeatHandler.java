package com.comsince.github;

import com.alipay.remoting.Connection;
import com.alipay.remoting.Protocol;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.ProtocolManager;
import com.comsince.github.protocol.PushProtocol;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午11:03
 **/
@ChannelHandler.Sharable
public class HeartbeatHandler extends ChannelDuplexHandler {

    /**
     *
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ProtocolCode protocolCode = ProtocolCode.fromBytes(PushProtocol.PROTOCOL_VERSION_1);
            Protocol protocol = ProtocolManager.getProtocol(protocolCode);
            protocol.getHeartbeatTrigger().heartbeatTriggered(ctx);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
