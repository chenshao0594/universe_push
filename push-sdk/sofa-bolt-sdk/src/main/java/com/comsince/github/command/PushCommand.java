package com.comsince.github.command;

import com.alipay.remoting.RemotingCommand;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午5:09
 **/
public abstract class PushCommand implements RemotingCommand{


    protected byte[] header;


    public byte[] getContents() {
        return header;
    }

    public void setVersion(int version) {
        header[1] = (byte) version;
    }

    public int getVersion() {
        return header[1];
    }

    public void setSignal(Signal signal) {
        header[2] = (byte)((header[2] & (0x01 << 7)) | signal.ordinal());
    }

    public Signal getSignal() {
        return Signal.toEnum((~(0x01 << 7)) & header[2]);
    }

    public void setContentLength(int length) {
        header[3] = (byte)((length >> 8) & 0xff);
        header[4] = (byte)(length & 0xff);
    }

    public int getContentLength() {
        return (((header[3] & 0xff) << 8) | (0xff & header[4]));
    }


    public boolean isValid(){
        return header[0] == (byte)0xf8 &&
                getContentLength() >= 0 &&
                getSignal() != Signal.NONE;
    }

}
