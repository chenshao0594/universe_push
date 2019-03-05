package com.comsince.github;

import com.alipay.remoting.config.ConfigurableInstance;
import com.alipay.remoting.connection.DefaultConnectionFactory;
import com.alipay.remoting.rpc.protocol.UserProcessor;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 链接管理工厂，主要包括如下组件
 * 消息加解码器
 * 心跳处理器
 * 消息处理器
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:35
 **/
public class PushConnectFactory extends DefaultConnectionFactory {
    public PushConnectFactory(ConcurrentHashMap<String, UserProcessor<?>> userProcessors,
                              ConfigurableInstance configInstance) {
        super(new PushCodec(), null, new PushHandler(userProcessors), configInstance);
    }
}
