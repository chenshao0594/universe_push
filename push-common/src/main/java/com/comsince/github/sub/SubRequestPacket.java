package com.comsince.github.sub;

import com.comsince.github.Header;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;

import java.nio.ByteBuffer;

public class SubRequestPacket extends PushPacket {

    @Override
    public ByteBuffer encode() {
        ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH);
        Header header = new Header();
        header.setSignal(Signal.SUB);
        header.setLength(0);
        buffer.put(header.getContents());
        return buffer;
    }
}
