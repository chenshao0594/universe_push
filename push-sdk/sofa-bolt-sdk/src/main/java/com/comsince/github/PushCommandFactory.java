package com.comsince.github;

import com.alipay.remoting.CommandFactory;
import com.alipay.remoting.RemotingCommand;
import com.alipay.remoting.ResponseStatus;

import java.net.InetSocketAddress;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午4:01
 **/
public class PushCommandFactory implements CommandFactory{
    @Override
    public <T extends RemotingCommand> T createRequestCommand(Object requestObject) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createResponse(Object responseObject, RemotingCommand requestCmd) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createExceptionResponse(int id, String errMsg) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createExceptionResponse(int id, Throwable t, String errMsg) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createExceptionResponse(int id, ResponseStatus status) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createExceptionResponse(int id, ResponseStatus status, Throwable t) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createTimeoutResponse(InetSocketAddress address) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createSendFailedResponse(InetSocketAddress address, Throwable throwable) {
        return null;
    }

    @Override
    public <T extends RemotingCommand> T createConnectionClosedResponse(InetSocketAddress address, String message) {
        return null;
    }
}
