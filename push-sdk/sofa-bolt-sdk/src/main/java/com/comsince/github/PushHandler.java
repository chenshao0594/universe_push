package com.comsince.github;

import com.alipay.remoting.*;
import com.alipay.remoting.rpc.protocol.UserProcessor;
import com.comsince.github.protocol.PushProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理器，接收到服务端的消息，经过pipline最后交由相应的handler处理
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:39
 **/
@ChannelHandler.Sharable
public class PushHandler extends ChannelInboundHandlerAdapter {

    static Logger logger = LoggerFactory.getLogger(PushHandler.class);
    private boolean                                     serverSide;

    private ConcurrentHashMap<String, UserProcessor<?>> userProcessors;

    public PushHandler() {
        this.serverSide = false;
    }

    public PushHandler(ConcurrentHashMap<String, UserProcessor<?>> userProcessors) {
        this.serverSide = false;
        this.userProcessors = userProcessors;
    }

    public PushHandler(boolean serverSide, ConcurrentHashMap<String, UserProcessor<?>> userProcessors) {
        this.serverSide = serverSide;
        this.userProcessors = userProcessors;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive message "+msg);
        ProtocolCode protocolCode = ProtocolCode.fromBytes(PushProtocol.PROTOCOL_VERSION_1);
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        protocol.getCommandHandler().handleCommand(
                new RemotingContext(ctx, new InvokeContext(), serverSide, userProcessors), msg);
    }
}
