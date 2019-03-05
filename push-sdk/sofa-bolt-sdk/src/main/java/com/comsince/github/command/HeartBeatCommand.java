package com.comsince.github.command;

import com.alipay.remoting.CommandCode;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.ProtocolCode;
import com.alipay.remoting.config.switches.ProtocolSwitch;
import com.alipay.remoting.exception.DeserializationException;
import com.alipay.remoting.exception.SerializationException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午5:39
 **/
public class HeartBeatCommand extends PushCommand{

    private Object heartBeatRequest;

    public HeartBeatCommand(Object heartBeatRequest) {
        this.heartBeatRequest = heartBeatRequest;
    }

    @Override
    public ProtocolCode getProtocolCode() {
        return null;
    }

    @Override
    public CommandCode getCmdCode() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
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
    public void serialize() throws SerializationException {

    }

    @Override
    public void deserialize() throws DeserializationException {

    }

    @Override
    public void serializeContent(InvokeContext invokeContext) throws SerializationException {

    }

    @Override
    public void deserializeContent(InvokeContext invokeContext) throws DeserializationException {

    }
}
