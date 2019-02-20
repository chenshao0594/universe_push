package com.comsince.github;

import com.comsince.github.sub.SubRequestPacket;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:00
 **/
public class PushClientStarter {
    //服务器节点
    public static Node serverNode2 = new Node("172.16.177.107", Const.PORT);
    public static Node serverNode1 = new Node("172.16.176.23", Const.PORT);
    public static Node serverNode0 = new Node("172.16.176.25", Const.PORT);
    public static Node serverNode = new Node(Const.SERVER, Const.PORT);

    //handler, 包括编码、解码、消息处理
    public static ClientAioHandler tioClientHandler = new PushClientHandler();

    //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
    public static ClientAioListener aioListener = null;

    //断链后自动连接的，不想自动连接请设为null
    private static ReconnConf reconnConf = new ReconnConf(5000L);

    //一组连接共用的上下文对象
    public static ClientGroupContext clientGroupContext = new ClientGroupContext(tioClientHandler, aioListener, reconnConf);

    public static TioClient tioClient = null;
    public static ClientChannelContext clientChannelContext = null;

    /**
     * 启动程序入口
     */
    public static void main(String[] args) throws Exception {
        clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        send(serverNode0);
        send(serverNode1);
        send(serverNode2);
    }

    public static void send(Node node) throws Exception{
        tioClient = new TioClient(clientGroupContext);
        clientChannelContext = tioClient.connect(node);
        send();
    }

    private static void send() throws Exception {
        SubRequestPacket subRequestPacket = new SubRequestPacket();
        Tio.send(clientChannelContext, subRequestPacket);
    }
}
