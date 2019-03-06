package com.comsince.github.protocol;

import com.alipay.remoting.Connection;
import com.alipay.remoting.Protocol;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.ProtocolManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;

import java.io.Serializable;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:50
 **/
@ChannelHandler.Sharable
public class PushProtocolEncoder extends MessageToByteEncoder<Serializable> {

    /** default protocol code */
    protected ProtocolCode defaultProtocolCode;

    public PushProtocolEncoder(ProtocolCode protocolCode){
        this.defaultProtocolCode = protocolCode;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
        Attribute<ProtocolCode> att = ctx.channel().attr(Connection.PROTOCOL);
        ProtocolCode protocolCode;
        if (att == null || att.get() == null) {
            protocolCode = this.defaultProtocolCode;
        } else {
            protocolCode = att.get();
        }
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        protocol.getEncoder().encode(ctx, msg, out);
    }
}
