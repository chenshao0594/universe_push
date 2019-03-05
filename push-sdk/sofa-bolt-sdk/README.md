# sofa-bolt-sdk版本

# 基于Netty最佳实践SofaBolt框架
## 基础通信模型

### 链接工厂
* ConnectionFactory
用户创建链接connection,默认的链接工厂提供基于Netty的链接创建的基本方法

```java
public interface ConnectionFactory {

    /**
     * Initialize the factory.
     */
    void init(ConnectionEventHandler connectionEventHandler);

    /**
     * Create a connection use #BoltUrl
     * 
     * @param url target url
     * @return connection
     */
    Connection createConnection(Url url) throws Exception;

    /**
     * Create a connection according to the IP and port.
     * Note: The default protocol is RpcProtocol.
     * 
     * @param targetIP target ip
     * @param targetPort target port
     * @param connectTimeout connect timeout in millisecond
     * @return connection
     */
    Connection createConnection(String targetIP, int targetPort, int connectTimeout)
                                                                                    throws Exception;

    /**
     * Create a connection according to the IP and port.
     *
     * Note: The default protocol is RpcProtocolV2, and you can specify the version
     *
     * @param targetIP target ip
     * @param targetPort target port
     * @param version protocol version
     * @param connectTimeout connect timeout in millisecond
     * @return connection
     */
    Connection createConnection(String targetIP, int targetPort, byte version, int connectTimeout)
                                                                                                  throws Exception;
}
```

#### 链接工厂的默认实现
* 编解码器
* 心跳处理器
* 消息处理器
* 配置管理

如下是根据这里处理器初始化Netty Bootstrap
```java
@Override
    public void init(final ConnectionEventHandler connectionEventHandler) {
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NettyEventLoopUtil.getClientSocketChannelClass())
            .option(ChannelOption.TCP_NODELAY, ConfigManager.tcp_nodelay())
            .option(ChannelOption.SO_REUSEADDR, ConfigManager.tcp_so_reuseaddr())
            .option(ChannelOption.SO_KEEPALIVE, ConfigManager.tcp_so_keepalive());

        // init netty write buffer water mark
        initWriteBufferWaterMark();

        // init byte buf allocator
        if (ConfigManager.netty_buffer_pooled()) {
            this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        } else {
            this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        }

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder", codec.newDecoder());
                pipeline.addLast("encoder", codec.newEncoder());

                boolean idleSwitch = ConfigManager.tcp_idle_switch();
                if (idleSwitch) {
                    pipeline.addLast("idleStateHandler",
                        new IdleStateHandler(ConfigManager.tcp_idle(), ConfigManager.tcp_idle(), 0,
                            TimeUnit.MILLISECONDS));
                    pipeline.addLast("heartbeatHandler", heartbeatHandler);
                }

                pipeline.addLast("connectionEventHandler", connectionEventHandler);
                pipeline.addLast("handler", handler);
            }
        });
    }
```

### ConnectionManager

```java
public interface ConnectionManager extends Scannable {
    /**
     * init
     */
    void init();

    /**
     * Add a connection to {@link ConnectionPool}.
     * If it contains multiple pool keys, this connection will be added to multiple {@link ConnectionPool} too.
     * 
     * @param connection an available connection, you should {@link #check(Connection)} this connection before add
     */
    void add(Connection connection);

    /**
     * Add a connection to {@link ConnectionPool} with the specified poolKey.
     * 
     * @param connection an available connection, you should {@link #check(Connection)} this connection before add
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    void add(Connection connection, String poolKey);

    /**
     * Get a connection from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return a {@link Connection} selected by {@link ConnectionSelectStrategy}<br>
     *   or return {@code null} if there is no {@link ConnectionPool} mapping with poolKey<br>
     *   or return {@code null} if there is no {@link Connection} in {@link ConnectionPool}.
     */
    Connection get(String poolKey);

    /**
     * Get all connections from {@link ConnectionPool} with the specified poolKey.
     * 
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return a list of {@link Connection}<br>
     *   or return an empty list if there is no {@link ConnectionPool} mapping with poolKey.
     */
    List<Connection> getAll(String poolKey);

    /**
     * Get all connections of all poolKey.
     *
     * @return a map with poolKey as key and a list of connections in ConnectionPool as value
     */
    Map<String, List<Connection>> getAll();

    /**
     * Remove a {@link Connection} from all {@link ConnectionPool} with the poolKeys in {@link Connection}, and close it.
     */
    void remove(Connection connection);

    /**
     * Remove and close a {@link Connection} from {@link ConnectionPool} with the specified poolKey.
     * 
     * @param connection target connection
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    void remove(Connection connection, String poolKey);

    /**
     * Remove and close all connections from {@link ConnectionPool} with the specified poolKey.
     * 
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    void remove(String poolKey);

    /**
     * Remove and close all connections from all {@link ConnectionPool}.
     */
    void removeAll();

    /**
     * check a connection whether available, if not, throw RemotingException
     * 
     * @param connection target connection
     */
    void check(Connection connection) throws RemotingException;

    /**
     * Get the number of {@link Connection} in {@link ConnectionPool} with the specified pool key
     * 
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return connection count
     */
    int count(String poolKey);

    /**
     * Get a connection using {@link Url}, if {@code null} then create and add into {@link ConnectionPool}.
     * The connection number of {@link ConnectionPool} is decided by {@link Url#getConnNum()}
     * 
     * @param url {@link Url} contains connect infos.
     * @return the created {@link Connection}.
     * @throws InterruptedException if interrupted
     * @throws RemotingException if create failed.
     */
    Connection getAndCreateIfAbsent(Url url) throws InterruptedException, RemotingException;

    /**
     * This method can create connection pool with connections initialized and check the number of connections.
     * The connection number of {@link ConnectionPool} is decided by {@link Url#getConnNum()}.
     * Each time call this method, will check the number of connection, if not enough, this will do the healing logic additionally.
     */
    void createConnectionAndHealIfNeed(Url url) throws InterruptedException, RemotingException;

    // ~~~ create operation

    /**
     * Create a connection using specified {@link Url}.
     * 
     * @param url {@link Url} contains connect infos.
     */
    Connection create(Url url) throws RemotingException;

    /**
     * Create a connection using specified {@link String} address.
     * 
     * @param address a {@link String} address, e.g. 127.0.0.1:1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     * @throws RemotingException if create failed
     */
    Connection create(String address, int connectTimeout) throws RemotingException;

    /**
     * Create a connection using specified ip and port.
     * 
     * @param ip connect ip, e.g. 127.0.0.1
     * @param port connect port, e.g. 1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     */
    Connection create(String ip, int port, int connectTimeout) throws RemotingException;
}
```

#### 默认实现
* ConnectionFactory
* ConnectionEventHandler 链接事件处理器
* ConnectionSelectStrategy 链接池选取策略


### 协议管理

* 编解码

```java
public interface Codec {

    /**
     * Create an encoder instance.
     *
     * @return new encoder instance
     */
    ChannelHandler newEncoder();

    /**
     * Create an decoder instance.
     *
     * @return new decoder instance
     */
    ChannelHandler newDecoder();
}
```

* 私有协议

```java
public interface Protocol {
    /**
     * Get the newEncoder for the protocol.
     * 
     * @return
     */
    CommandEncoder getEncoder();

    /**
     * Get the decoder for the protocol.
     * 
     * @return
     */
    CommandDecoder getDecoder();

    /**
     * Get the heartbeat trigger for the protocol.
     * 
     * @return
     */
    HeartbeatTrigger getHeartbeatTrigger();

    /**
     * Get the command handler for the protocol.
     * 
     * @return
     */
    CommandHandler getCommandHandler();

    /**
     * Get the command factory for the protocol.
     * @return
     */
    CommandFactory getCommandFactory();
}
```

### 消息处理器

消息读出后，消息经过解码器后，进入消息处理器，此时经过messageHandler，然后在进行消息处理

```java
public interface RemotingProcessor<T extends RemotingCommand> {

    /**
     * Process the remoting command.
     * 
     * @param ctx
     * @param msg
     * @param defaultExecutor
     * @throws Exception
     */
    void process(RemotingContext ctx, T msg, ExecutorService defaultExecutor) throws Exception;

    /**
     * Get the executor.
     * 
     * @return
     */
    ExecutorService getExecutor();

    /**
     * Set executor.
     * 
     * @param executor
     */
    void setExecutor(ExecutorService executor);

}
```

# 基于SofaBolt的消息推送客户端

## 协议解析
