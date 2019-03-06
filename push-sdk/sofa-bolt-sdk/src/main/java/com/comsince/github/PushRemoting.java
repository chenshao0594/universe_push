package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.exception.RemotingException;
import com.comsince.github.command.PushCommand;
import com.comsince.github.command.ResponsePushCommand;
import com.comsince.github.protocol.PushProtocolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:54
 **/
public abstract class PushRemoting extends BaseRemoting{

    static {
        PushProtocolManager.initProtocols();
    }

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger("PushRemoting");

    /** address parser to get custom args */
    protected RemotingAddressParser addressParser;

    /** connection manager */
    protected DefaultConnectionManager connectionManager;

    public PushRemoting(CommandFactory commandFactory) {
        super(commandFactory);
    }

    public PushRemoting(CommandFactory commandFactory, RemotingAddressParser addressParser, DefaultConnectionManager connectionManager) {
        super(commandFactory);
        this.addressParser = addressParser;
        this.connectionManager = connectionManager;
    }


    public void oneway(final String addr, final PushCommand request, final InvokeContext invokeContext)
            throws RemotingException,
            InterruptedException {
        Url url = this.addressParser.parse(addr);
        logger.info("parse url "+url);
        this.oneway(url, request, invokeContext);
    }

    public Object invokeSync(String address, PushCommand request, InvokeContext invokeContext,int timeoutMillis) throws RemotingException, InterruptedException {
        Url url = this.addressParser.parse(address);
        return this.invokeSync(url,request,invokeContext,timeoutMillis);
    }

    public Object invokeSync(final Connection conn, final PushCommand request,
                             final InvokeContext invokeContext, final int timeoutMillis) throws RemotingException, InterruptedException {
        ResponsePushCommand responsePushCommand = (ResponsePushCommand) super.invokeSync(conn,request,timeoutMillis);
        return responsePushCommand;
    }

    public abstract void oneway(final Url url, final PushCommand request,
                                final InvokeContext invokeContext) throws RemotingException,
            InterruptedException;


    public abstract Object invokeSync(Url url, PushCommand request, InvokeContext invokeContext,int timeoutMillis) throws RemotingException, InterruptedException;

}
