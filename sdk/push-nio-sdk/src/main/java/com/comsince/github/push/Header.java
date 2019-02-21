package com.comsince.github.push;

import java.nio.ByteBuffer;

public class Header {
    public static final int LENGTH = 6;
    public static final int VERSION = 1;

    protected byte[] mContents;

    public Header() {
        mContents = new byte[LENGTH];
        mContents[0] = (byte)0xf8;  //flag
        mContents[1] = VERSION;   //version
    }

    public Header(ByteBuffer buffer) {
        mContents = new byte[LENGTH];
        if(buffer.limit() >= LENGTH) {
            for(int i = 0; i < LENGTH; i++) {
                mContents[i] = buffer.get();
            }
        }
    }

    public byte[] getContents() {
        return mContents;
    }

    public Header setVersion(int version) {
        mContents[1] = (byte) version;
        return this;
    }

    public int getVersion() {
        return mContents[1];
    }

    public Header setSignal(Signal signal) {
        mContents[2] = (byte)((mContents[2] & (0x01 << 7)) | signal.ordinal());
        return this;
    }

    public Signal getSignal() {
        return Signal.toEnum((~(0x01 << 7)) & mContents[2]);
    }

    public Header setLength(int length) {
        mContents[3] = (byte)((length >> 8) & 0xff);
        mContents[4] = (byte)(length & 0xff);
        return this;
    }

    public int getLength() {
        return (((mContents[3] & 0xff) << 8) | (0xff & mContents[4]));
    }


    public boolean isValid(){
        return mContents[0] == (byte)0xf8 &&
                getLength() >= 0 &&
                getSignal() != Signal.NONE;
    }

    public static void main(String[] args){
        System.out.println(~(0x01 << 7));
        System.out.println(~0x80);
    }
}
