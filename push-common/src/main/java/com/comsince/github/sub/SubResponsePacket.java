package com.comsince.github.sub;

import com.alibaba.fastjson.JSON;
import com.comsince.github.Header;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class SubResponsePacket extends PushPacket {
    byte[] subResponseByte;

    public SubResponsePacket(SubResponse subResponse){
        String responseJson = JSON.toJSONString(subResponse);
        try {
            subResponseByte = responseJson.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ByteBuffer encode() {
        int bodyLength = subResponseByte.length;
        ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH + bodyLength);
        Header header = new Header();
        header.setSignal(Signal.SUB);
        header.setLength(bodyLength);
        buffer.put(header.getContents());
        buffer.put(subResponseByte);
        return buffer;
    }
}
