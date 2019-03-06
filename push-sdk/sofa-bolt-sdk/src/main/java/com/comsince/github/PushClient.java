package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.config.AbstractConfigurableInstance;
import com.alipay.remoting.config.configs.ConfigType;
import com.alipay.remoting.connection.ConnectionFactory;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.log.BoltLoggerFactory;
import com.alipay.remoting.rpc.protocol.UserProcessor;
import com.comsince.github.command.RequestPushCommand;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 这里时基于sofa bolt框架实现私有协议
 * @author comsicne
 *         Copyright (c) [2019]
 * @Time 19-3-5 下午3:32
 **/
public class PushClient extends AbstractConfigurableInstance {

    /** logger */
    private static final Logger logger                   = BoltLoggerFactory
            .getLogger("RpcRemoting");

    private ConcurrentHashMap<String, UserProcessor<?>> userProcessors           = new ConcurrentHashMap<String, UserProcessor<?>>();
    /** connection factory */
    private ConnectionFactory connectionFactory        = new PushConnectFactory(
            userProcessors,
            this);

    /** connection select strategy */
    private ConnectionSelectStrategy connectionSelectStrategy = new RandomSelectStrategy(
            switches());

    /** connection event handler */
    private ConnectionEventHandler connectionEventHandler   = new PushConnectionEventHandler(
            switches());

    /** connection event listener */
    private ConnectionEventListener connectionEventListener  = new ConnectionEventListener();

    /** connection manager */
    private DefaultConnectionManager connectionManager        = new DefaultConnectionManager(
            connectionSelectStrategy,
            connectionFactory,
            connectionEventHandler,
            connectionEventListener,
            switches());

    /** address parser to get custom args */
    private RemotingAddressParser                       addressParser;

    /** rpc remoting */
    protected PushRemoting                               pushRemoting;

    protected PushClient() {
        super(ConfigType.CLIENT_SIDE);
    }

    public void init(){
        if (this.addressParser == null) {
            this.addressParser = new PushAddressParser();
        }
        this.connectionManager.init();
        this.pushRemoting = new ClientPushRemoting(new PushCommandFactory(), this.addressParser,
                this.connectionManager);
    }

    /**
     * 订阅请求
     * */
    public void onewaySub(String address, RequestPushCommand subscribeCommand) throws RemotingException, InterruptedException {
        pushRemoting.oneway(address,subscribeCommand,null);
    }

    public void invokeSyncSub(String address, RequestPushCommand subscribeCommand) throws RemotingException, InterruptedException {
        pushRemoting.invokeSync(address,subscribeCommand,null,3000);
    }


    public Connection createConnection(String address) throws RemotingException, InterruptedException {
        Url url = this.addressParser.parse(address);
        Connection connection = pushRemoting.connectionManager.getAndCreateIfAbsent(url);
        return connection;
    }


    /**
     * Shutdown.
     * <p>
     * Notice:<br>
     *   <li>Rpc client can not be used any more after shutdown.
     *   <li>If you need, you should destroy it, and instantiate another one.
     */
    public void shutdown() {
        this.connectionManager.removeAll();
        logger.warn("Close all connections from client side!");
        logger.warn("push client shutdown!");
    }
}
