package com.comsince.github.command;

import com.alipay.remoting.CommandCode;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.ResponseStatus;
import com.alipay.remoting.config.switches.ProtocolSwitch;
import com.alipay.remoting.exception.DeserializationException;
import com.comsince.github.protocol.PushCommandCode;

import java.io.UnsupportedEncodingException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 下午2:25
 **/
public class ResponsePushCommand extends PushCommand{

    public String response;
    private ResponseStatus responseStatus;

    public ResponsePushCommand() {
        super();
    }

    public ResponsePushCommand(byte[] header) {
        super(header);
        this.commandCode = PushCommandCode.PUSH_RESPONSE;
        this.id = getSignal().ordinal();
    }

    public ResponsePushCommand(Signal signal) {
        super(signal);
    }

    @Override
    public ProtocolCode getProtocolCode() {
        return null;
    }

    @Override
    public CommandCode getCmdCode() {
        return commandCode;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public InvokeContext getInvokeContext() {
        return null;
    }

    @Override
    public byte getSerializer() {
        return 0;
    }

    @Override
    public ProtocolSwitch getProtocolSwitch() {
        return null;
    }

    @Override
    public void deserializeContent(InvokeContext invokeContext) throws DeserializationException {
        try {
            response = new String(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for property <tt>responseStatus</tt>.
     *
     * @return property value of responseStatus
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter method for property <tt>responseStatus</tt>.
     *
     * @param responseStatus value to be assigned to property responseStatus
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return "ResponsePushCommand{" +
                "response='" + response + '\'' +
                '}';
    }
}
