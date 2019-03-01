package com.comsince.github;
import com.comsince.github.logger.Log;
import com.comsince.github.client.NIOClient;
import com.comsince.github.logger.LoggerFactory;

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
            sendConnect("172.16.176.23",6789);
            sendConnect("172.16.176.25",6789);
           // sendConnect("127.0.0.1",6789);
        }

    }

    public static void sendConnect(String host, int port){
        NIOClient nioClient = new NIOClient(host,port);
        nioClient.connect();
    }
}
