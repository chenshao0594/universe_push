package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.log.BoltLoggerFactory;
import com.comsince.github.command.PushCommand;
import org.slf4j.Logger;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:54
 **/
public abstract class PushRemoting extends BaseRemoting{

    /** logger */
    private static final Logger logger = BoltLoggerFactory.getLogger("PushRemoting");

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
        this.oneway(url, request, invokeContext);
    }

    public abstract void oneway(final Url url, final PushCommand request,
                                final InvokeContext invokeContext) throws RemotingException,
            InterruptedException;

}
