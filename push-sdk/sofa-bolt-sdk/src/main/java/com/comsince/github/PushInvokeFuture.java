package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.log.BoltLoggerFactory;
import com.alipay.remoting.rpc.DefaultInvokeFuture;
import com.alipay.remoting.rpc.ResponseCommand;
import com.comsince.github.command.ResponsePushCommand;
import io.netty.util.Timeout;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-7 上午10:49
 **/
public class PushInvokeFuture implements InvokeFuture{

    private static final Logger logger                  = BoltLoggerFactory
            .getLogger("PushInvokeFuture");

    private int                      invokeId;

    private InvokeCallbackListener callbackListener;

    private InvokeCallback           callback;

    private volatile ResponsePushCommand responseCommand;

    private final CountDownLatch countDownLatch          = new CountDownLatch(1);

    private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);

    private Timeout                  timeout;

    private Throwable                cause;

    private ClassLoader              classLoader;

    private byte                     protocol;

    private InvokeContext            invokeContext;

    private CommandFactory           commandFactory;


    /**
     * Constructor.
     *
     * @param invokeId invoke id
     * @param callbackListener callback listener
     * @param callback callback
     * @param protocol protocol code
     * @param commandFactory command factory
     */
    public PushInvokeFuture(int invokeId, InvokeCallbackListener callbackListener,
                               InvokeCallback callback, byte protocol, CommandFactory commandFactory) {
        this.invokeId = invokeId;
        this.callbackListener = callbackListener;
        this.callback = callback;
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.protocol = protocol;
        this.commandFactory = commandFactory;
    }

    /**
     * Constructor.
     *
     * @param invokeId invoke id
     * @param callbackListener callback listener
     * @param callback callback
     * @param protocol protocol
     * @param commandFactory command factory
     * @param invokeContext invoke context
     */
    public PushInvokeFuture(int invokeId, InvokeCallbackListener callbackListener,
                               InvokeCallback callback, byte protocol,
                               CommandFactory commandFactory, InvokeContext invokeContext) {
        this(invokeId, callbackListener, callback, protocol, commandFactory);
        this.invokeContext = invokeContext;
    }


    @Override
    public RemotingCommand waitResponse(long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }

    @Override
    public RemotingCommand waitResponse() throws InterruptedException {
        this.countDownLatch.await();
        return this.responseCommand;
    }

    @Override
    public RemotingCommand createConnectionClosedResponse(InetSocketAddress responseHost) {
        return this.commandFactory.createConnectionClosedResponse(responseHost, null);
    }

    @Override
    public void putResponse(RemotingCommand response) {
        this.responseCommand = (ResponsePushCommand) response;
        this.countDownLatch.countDown();
    }

    @Override
    public int invokeId() {
        return this.invokeId;
    }

    @Override
    public void executeInvokeCallback() {
        if (callbackListener != null) {
            if (this.executeCallbackOnlyOnce.compareAndSet(false, true)) {
                callbackListener.onResponse(this);
            }
        }
    }

    @Override
    public void tryAsyncExecuteInvokeCallbackAbnormally() {
        try {
            Protocol protocol = ProtocolManager.getProtocol(ProtocolCode.fromBytes(this.protocol));
            if (null != protocol) {
                CommandHandler commandHandler = protocol.getCommandHandler();
                if (null != commandHandler) {
                    ExecutorService executor = commandHandler.getDefaultExecutor();
                    if (null != executor) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                ClassLoader oldClassLoader = null;
                                try {
                                    if (PushInvokeFuture.this.getAppClassLoader() != null) {
                                        oldClassLoader = Thread.currentThread()
                                                .getContextClassLoader();
                                        Thread.currentThread().setContextClassLoader(
                                                PushInvokeFuture.this.getAppClassLoader());
                                    }
                                    PushInvokeFuture.this.executeInvokeCallback();
                                } finally {
                                    if (null != oldClassLoader) {
                                        Thread.currentThread()
                                                .setContextClassLoader(oldClassLoader);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    logger.error("Executor null in commandHandler of protocolCode [{}].",
                            this.protocol);
                }
            } else {
                logger.error("protocolCode [{}] not registered!", this.protocol);
            }
        } catch (Exception e) {
            logger.error("Exception caught when executing invoke callback abnormally.", e);
        }
    }

    @Override
    public void setCause(Throwable cause) {
         this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public InvokeCallback getInvokeCallback() {
        return callback;
    }

    @Override
    public void addTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    @Override
    public void cancelTimeout() {
        if (this.timeout != null) {
            this.timeout.cancel();
        }
    }

    @Override
    public boolean isDone() {
        return this.countDownLatch.getCount() <= 0;
    }

    @Override
    public ClassLoader getAppClassLoader() {
        return null;
    }

    @Override
    public byte getProtocolCode() {
        return protocol;
    }

    @Override
    public void setInvokeContext(InvokeContext invokeContext) {

    }

    @Override
    public InvokeContext getInvokeContext() {
        return null;
    }
}
