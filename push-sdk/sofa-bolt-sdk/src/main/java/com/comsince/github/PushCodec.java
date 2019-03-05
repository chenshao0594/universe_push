package com.comsince.github;


import com.alipay.remoting.codec.Codec;
import io.netty.channel.ChannelHandler;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:36
 **/
public class PushCodec implements Codec {
    @Override
    public ChannelHandler newEncoder() {
        return null;
    }

    @Override
    public ChannelHandler newDecoder() {
        return null;
    }
}
