package com.comsince.github.process;

import com.comsince.github.PushPacket;
import org.tio.core.ChannelContext;

public interface MessageProcessor {
    /**
     * 处理客户端发送过来的消息
     * @param pushPacket
     * @param channelContext
     * */
    void process(PushPacket pushPacket, ChannelContext channelContext);

    boolean match(PushPacket pushPacket);
}
