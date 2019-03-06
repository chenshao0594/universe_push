package com.comsince.github.command;

import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.RemotingCommand;
import com.alipay.remoting.exception.DeserializationException;
import com.alipay.remoting.exception.SerializationException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午5:09
 **/
public abstract class PushCommand implements RemotingCommand{
    public static final int LENGTH = 6;
    public static final int VERSION = 1;

    protected byte[] header;

    protected byte[] content;

    protected Signal signal;

    /** invoke context of each rpc command. */
    private InvokeContext     invokeContext;

    public PushCommand(byte[] header) {
        this.header = header;
    }

    public PushCommand(Signal signal) {
        header = new byte[LENGTH];
        header[0] = (byte)0xf8;  //flag
        header[1] = VERSION;   //version
        setSignal(signal);
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header){
        this.header = header;
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

    public void setContent(byte[] content) {
        if (content != null) {
            this.content = content;
            setContentLength(content.length);
        } else {
            setContentLength(0);
        }
    }

    public byte[] getContent(){
        return content;
    }

    @Override
    public void serialize() throws SerializationException {
        serializeHeader(invokeContext);
        serializeContent(invokeContext);
    }

    @Override
    public void deserialize() throws DeserializationException {
       deserializeContent(invokeContext);
    }

    /**
     * Serialize the header.
     *
     * @throws Exception
     */
    public void serializeHeader(InvokeContext invokeContext) throws SerializationException {
    }

    /**
     * Serialize the content.
     *
     * @throws Exception
     */
    @Override
    public void serializeContent(InvokeContext invokeContext) throws SerializationException {
    }
}
