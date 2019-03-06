package com.comsince.github.protocol;

import com.alipay.remoting.Protocol;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.ProtocolManager;
import com.alipay.remoting.codec.AbstractBatchDecoder;
import com.alipay.remoting.exception.CodecException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:37
 **/
public class PushProtocolDecoder extends AbstractBatchDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ProtocolCode protocolCode = ProtocolCode.fromBytes(PushProtocol.PROTOCOL_VERSION_1);
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        if (null != protocol) {
            in.resetReaderIndex();
            protocol.getDecoder().decode(ctx, in, out);
        } else {
            throw new CodecException("Unknown protocol code: [" + protocolCode
                    + "] while decode in ProtocolDecoder.");
        }
    }
}
