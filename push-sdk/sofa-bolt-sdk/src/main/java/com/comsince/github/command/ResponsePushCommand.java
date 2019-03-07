package com.comsince.github.command;

import com.alipay.remoting.CommandCode;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.ProtocolCode;
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

    public ResponsePushCommand(byte[] header) {
        super(header);
        this.commandCode = PushCommandCode.PUSH_RESPONSE;
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
        return getSignal().ordinal();
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

    @Override
    public String toString() {
        return "ResponsePushCommand{" +
                "response='" + response + '\'' +
                '}';
    }
}
