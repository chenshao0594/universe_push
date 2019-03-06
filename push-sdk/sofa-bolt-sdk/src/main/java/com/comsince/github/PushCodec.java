package com.comsince.github;


import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.codec.Codec;
import com.alipay.remoting.codec.ProtocolCodeBasedEncoder;
import com.alipay.remoting.rpc.protocol.RpcProtocolV2;
import com.comsince.github.protocol.PushProtocol;
import com.comsince.github.protocol.PushProtocolDecoder;
import com.comsince.github.protocol.PushProtocolEncoder;
import com.comsince.github.protocol.PushProtocolManager;
import io.netty.channel.ChannelHandler;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:36
 **/
public class PushCodec implements Codec {
    @Override
    public ChannelHandler newEncoder() {
        return new PushProtocolEncoder(ProtocolCode.fromBytes(PushProtocol.PROTOCOL_CODE));
    }

    @Override
    public ChannelHandler newDecoder() {
        return new PushProtocolDecoder();
    }
}
