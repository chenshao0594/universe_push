package com.comsince.github.pushmessage;

import com.comsince.github.Header;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:43
 **/
public class PushMessagePacket extends PushPacket {
    public PushMessagePacket(String pushMessage){
        if(StringUtils.isNotBlank(pushMessage)){
            try {
                byte[] pushBytes = pushMessage.getBytes("UTF-8");
                setBody(pushBytes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ByteBuffer encode() {
        int bodyLength = 0;
        if(getBody() != null){
            bodyLength = getBody().length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH + bodyLength);
        Header header = new Header();
        header.setSignal(Signal.PUSH);
        header.setLength(bodyLength);
        buffer.put(header.getContents());
        if(getBody() != null){
            buffer.put(getBody());
        }
        return buffer;
    }
}
