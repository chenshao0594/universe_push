package com.comsince.github.protocol;

import com.alipay.remoting.CommandCode;
import com.alipay.remoting.CommandHandler;
import com.alipay.remoting.RemotingContext;
import com.alipay.remoting.RemotingProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:43
 **/
public class PushCommandHandler implements CommandHandler {
    Logger logger = LoggerFactory.getLogger(PushCommandHandler.class);
    @Override
    public void handleCommand(RemotingContext ctx, Object msg) throws Exception {
        logger.info("receive msg "+msg);
    }

    @Override
    public void registerProcessor(CommandCode cmd, RemotingProcessor<?> processor) {

    }

    @Override
    public void registerDefaultExecutor(ExecutorService executor) {

    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return null;
    }
}
