package com.comsince.github;

import com.comsince.github.callback.CompletedCallback;
import com.comsince.github.callback.ConnectCallback;
import com.comsince.github.callback.DataCallback;
import com.comsince.github.future.Cancellable;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;
import com.comsince.github.test.NIOClient;
import com.comsince.github.utils.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 下午2:48
 **/
public class PushNIOClientStarter {
    static Log log = LoggerFactory.getLogger(PushNIOClientStarter.class);



    public static void main(String[] args){
        for(int i=0;i<1;i++){
            sendConnect("172.16.177.107",6789);
            //("172.16.176.23",6789);
            //sendConnect("172.16.176.25",6789);
        }

    }




    public static void sendConnect(String host, int port){
        NIOClient nioClient = new NIOClient(host,port);
        nioClient.connect();
    }
}
