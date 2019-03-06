package com.comsince.github;

import com.alipay.remoting.Connection;
import com.alipay.remoting.exception.RemotingException;
import com.comsince.github.command.RequestPushCommand;
import com.comsince.github.command.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-4 下午4:26
 **/
public class SofaBoltStarter {

    static Logger logger                    = LoggerFactory
            .getLogger(SofaBoltStarter.class);

    static PushClient          client;

    static String             addr                      = "172.16.177.107:6789";

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args){

        new Thread(new Runnable() {
            @Override
            public void run() {
                client = new PushClient();
                client.init();

                try {
                    //Connection connection = client.createConnection(addr);
                    logger.info("start sub");
                    //client.invokeSyncSub(addr,new RequestPushCommand(null, Signal.SUB));
                     client.onewaySub(addr, new RequestPushCommand(null, Signal.SUB));
                } catch (RemotingException e) {
                    String errMsg = "RemotingException caught in oneway!";
                    logger.error(errMsg, e);
                } catch (InterruptedException e) {
                    logger.error("interrupted!");
                }
                //client.shutdown();
            }
        }).start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
