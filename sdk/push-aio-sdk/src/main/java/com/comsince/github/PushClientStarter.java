package com.comsince.github;

import com.comsince.github.heartbeat.HeartbeatRequestPacket;
import com.comsince.github.sub.SubRequestPacket;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

import java.util.Random;
import java.util.concurrent.*;

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

    private static Executor executor = Executors.newFixedThreadPool(200, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"push-client");
        }
    });

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
    /**
     * 启动程序入口
     */
    public static void main(String[] args) throws Exception {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    pressConnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //send(serverNode0);
            }
        });

    }

    public static void pressConnect()throws Exception{
        for(int i = 0; i<1500; i++){
            send(serverNode1);
            send(serverNode2);
            send(serverNode0);
        }
    }

    public static void send(Node node) throws Exception{
        clientGroupContext.setHeartbeatTimeout(0);
        tioClient = new TioClient(clientGroupContext);
        ClientChannelContext channelContext = tioClient.connect(node);
        send(channelContext);
        Random random = new Random();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Tio.send(channelContext, new HeartbeatRequestPacket());
            }
        },10*1000, (random.nextInt(30) + 30) * 1000, TimeUnit.MILLISECONDS);
    }

    private static void send(ClientChannelContext channelContext) throws Exception {
        SubRequestPacket subRequestPacket = new SubRequestPacket();
        Tio.send(channelContext, subRequestPacket);
    }
}
