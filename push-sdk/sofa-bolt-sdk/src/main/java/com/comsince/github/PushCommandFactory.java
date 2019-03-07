package com.comsince.github;

import com.alipay.remoting.CommandFactory;
import com.alipay.remoting.RemotingCommand;
import com.alipay.remoting.ResponseStatus;
import com.alipay.remoting.rpc.protocol.RpcResponseCommand;
import com.comsince.github.command.RequestPushCommand;
import com.comsince.github.command.ResponsePushCommand;

import java.net.InetSocketAddress;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午4:01
 **/
public class PushCommandFactory implements CommandFactory{

    @Override
    public RequestPushCommand createRequestCommand(Object requestObject) {
        return null;
    }

    @Override
    public ResponsePushCommand createResponse(Object responseObject, RemotingCommand requestCmd) {
        return null;
    }

    @Override
    public ResponsePushCommand createExceptionResponse(int id, String errMsg) {
        return createExceptionResponse(id, null, errMsg);
    }

    @Override
    public ResponsePushCommand createExceptionResponse(int id, Throwable t, String errMsg) {
        return null;
    }

    @Override
    public ResponsePushCommand createExceptionResponse(int id, ResponseStatus status) {
        ResponsePushCommand responseCommand = new ResponsePushCommand();
        responseCommand.setId(id);
        responseCommand.setResponseStatus(status);
        return responseCommand;
    }

    @Override
    public ResponsePushCommand createExceptionResponse(int id, ResponseStatus status, Throwable t) {
        return null;
    }

    @Override
    public ResponsePushCommand createTimeoutResponse(InetSocketAddress address) {
        return null;
    }

    @Override
    public ResponsePushCommand createSendFailedResponse(InetSocketAddress address, Throwable throwable) {
        return null;
    }

    @Override
    public ResponsePushCommand createConnectionClosedResponse(InetSocketAddress address, String message) {
        return null;
    }
}
