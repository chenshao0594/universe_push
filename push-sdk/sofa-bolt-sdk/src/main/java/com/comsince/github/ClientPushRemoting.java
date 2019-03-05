package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.exception.RemotingException;
import com.comsince.github.command.PushCommand;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:56
 **/
public class ClientPushRemoting extends PushRemoting{

    public ClientPushRemoting(CommandFactory commandFactory, RemotingAddressParser addressParser, DefaultConnectionManager connectionManager) {
        super(commandFactory, addressParser, connectionManager);
    }

    @Override
    public void oneway(Url url, PushCommand request, InvokeContext invokeContext) throws RemotingException, InterruptedException {
        final Connection conn = getConnectionAndInitInvokeContext(url, invokeContext);
        this.connectionManager.check(conn);
        this.oneway(conn, request);
    }


    @Override
    protected InvokeFuture createInvokeFuture(RemotingCommand request, InvokeContext invokeContext) {
        return null;
    }

    @Override
    protected InvokeFuture createInvokeFuture(Connection conn, RemotingCommand request, InvokeContext invokeContext, InvokeCallback invokeCallback) {
        return null;
    }


    /**
     * Get connection and set init invokeContext if invokeContext not {@code null}
     *
     * @param url target url
     * @param invokeContext invoke context to set
     * @return connection
     */
    protected Connection getConnectionAndInitInvokeContext(Url url, InvokeContext invokeContext)
            throws RemotingException,
            InterruptedException {
        long start = System.currentTimeMillis();
        Connection conn;
        try {
            conn = this.connectionManager.getAndCreateIfAbsent(url);
        } finally {
            if (null != invokeContext) {
                invokeContext.putIfAbsent(InvokeContext.CLIENT_CONN_CREATETIME,
                        (System.currentTimeMillis() - start));
            }
        }
        return conn;
    }
}
