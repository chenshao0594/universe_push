package com.comsince.github.heartbeat;
import java.io.UnsupportedEncodingException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:06
 **/
public class HeartbeatResponsePacket extends HeartbeatRequestPacket {

    public HeartbeatResponsePacket(String heartBeat) {
        try {
            this.setBody(heartBeat.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
