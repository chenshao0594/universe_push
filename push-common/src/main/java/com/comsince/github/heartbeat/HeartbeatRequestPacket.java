package com.comsince.github.heartbeat;

import com.comsince.github.Header;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;

import java.nio.ByteBuffer;

public class HeartbeatRequestPacket extends PushPacket {

    @Override
    public ByteBuffer encode() {
        int bodyLength = 0;
        if(getBody() != null){
            bodyLength = getBody().length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH + bodyLength);
        Header header = new Header();
        header.setSignal(Signal.PING);
        header.setLength(bodyLength);
        buffer.put(header.getContents());
        if(getBody() != null){
            buffer.put(getBody());
        }
        return buffer;
    }
}
