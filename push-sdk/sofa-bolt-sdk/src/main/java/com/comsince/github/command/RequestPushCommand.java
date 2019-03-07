package com.comsince.github.command;

import com.alipay.remoting.CommandCode;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.config.switches.ProtocolSwitch;
import com.alipay.remoting.exception.DeserializationException;
import com.alipay.remoting.exception.SerializationException;
import com.alipay.remoting.util.IDGenerator;
import com.comsince.github.protocol.PushCommandCode;
import com.comsince.github.protocol.PushProtocol;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午10:11
 **/
public class RequestPushCommand extends PushCommand{

    private Object request;

    public RequestPushCommand(Object request, Signal signal) {
        super(signal);
        this.commandCode = PushCommandCode.PUSH_REQUEST;
        this.request = request;
        this.setId(signal.ordinal());
    }

    @Override
    public ProtocolCode getProtocolCode() {
        return ProtocolCode.fromBytes(PushProtocol.PROTOCOL_CODE);
    }

    @Override
    public CommandCode getCmdCode() {
        return commandCode;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    }

    @Override
    public void serializeHeader(InvokeContext invokeContext) throws SerializationException {
        if(signal != null){
            setSignal(signal);
        }
    }

    @Override
    public void serializeContent(InvokeContext invokeContext) throws SerializationException {
        if(request != null){
            setContent(request.toString().getBytes());
        }
    }
}
