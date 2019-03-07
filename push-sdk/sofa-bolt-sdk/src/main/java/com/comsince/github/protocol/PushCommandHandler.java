package com.comsince.github.protocol;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.RpcCommand;
import com.alipay.remoting.rpc.RpcConfigManager;
import com.comsince.github.command.PushCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:43
 **/
public class PushCommandHandler implements CommandHandler {
    Logger logger = LoggerFactory.getLogger(PushCommandHandler.class);

    /** All processors */
    ProcessorManager processorManager;


    public PushCommandHandler() {
        this.processorManager = new ProcessorManager();
        //process request
        this.processorManager.registerProcessor(PushCommandCode.PUSH_REQUEST,
                new PushRequestProcessor());
        //process response
        this.processorManager.registerProcessor(PushCommandCode.PUSH_RESPONSE,
                new PushResponseProcessor());

        this.processorManager
                .registerDefaultProcessor(new AbstractRemotingProcessor<RemotingCommand>() {
                    @Override
                    public void doProcess(RemotingContext ctx, RemotingCommand msg) throws Exception {
                        logger.error("No processor available for command code {}, msgId {}",
                                msg.getCmdCode(), msg.getId());
                    }
                });
    }

    @Override
    public void handleCommand(RemotingContext ctx, Object msg) throws Exception {
        logger.info("receive msg "+msg);
        try {
            if (msg instanceof List) {
                final Runnable handleTask = new Runnable() {
                    @Override
                    public void run() {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Batch message! size={}", ((List<?>) msg).size());
                        }
                        for (final Object m : (List<?>) msg) {
                            PushCommandHandler.this.process(ctx, m);
                        }
                    }
                };
                if (RpcConfigManager.dispatch_msg_list_in_default_executor()) {
                    // If msg is list ,then the batch submission to biz threadpool can save io thread.
                    // See com.alipay.remoting.decoder.ProtocolDecoder
                    processorManager.getDefaultExecutor().execute(handleTask);
                } else {
                    handleTask.run();
                }
            } else {
                process(ctx, msg);
            }
        } catch (final Throwable t) {
            //processException(ctx, msg, t);
        }
    }

    private void process(RemotingContext ctx, Object msg) {
        try {
            final PushCommand cmd = (PushCommand) msg;
            final RemotingProcessor processor = processorManager.getProcessor(cmd.getCmdCode());
            processor.process(ctx, cmd, processorManager.getDefaultExecutor());
        } catch (final Throwable t) {
            logger.info("process exception ",t);
            //processException(ctx, msg, t);
        }
    }

    @Override
    public void registerProcessor(CommandCode cmd, RemotingProcessor<?> processor) {
        this.processorManager.registerProcessor(cmd, processor);
    }

    @Override
    public void registerDefaultExecutor(ExecutorService executor) {
        this.processorManager.registerDefaultExecutor(executor);
    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return this.processorManager.getDefaultExecutor();
    }
}
