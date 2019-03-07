package com.comsince.github.protocol;

import com.alipay.remoting.AbstractRemotingProcessor;
import com.alipay.remoting.RemotingContext;
import com.comsince.github.command.RequestPushCommand;

import java.util.concurrent.ExecutorService;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-7 上午11:05
 **/
public class PushRequestProcessor extends AbstractRemotingProcessor<RequestPushCommand> {

    public PushRequestProcessor(){

    }

    public PushRequestProcessor(ExecutorService executor){
        super(executor);
    }

    @Override
    public void doProcess(RemotingContext ctx, RequestPushCommand msg) throws Exception {

    }
}
