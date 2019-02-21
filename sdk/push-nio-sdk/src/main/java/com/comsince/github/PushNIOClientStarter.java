package com.comsince.github;

import com.comsince.github.callback.CompletedCallback;
import com.comsince.github.callback.ConnectCallback;
import com.comsince.github.callback.DataCallback;
import com.comsince.github.future.Cancellable;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;
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

    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args){
        for(int i=0;i<1000;i++){
            sendConnect("172.16.177.107",6789);
            sendConnect("172.16.176.23",6789);
            sendConnect("172.16.176.25",6789);
        }

    }

    public static void sendConnect(String host,int port){

        AsyncServer.getDefault().connectSocket(host, port, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                log.i("connect success");
                //start register
                final Header header = new Header();
                header.setSignal(Signal.SUB);
                header.setLength(0);

                Util.writeAll(socket, header.getContents(), new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        log.i("send sub signal success");
                    }
                });


                socket.setDataCallback(new DataCallback() {
                    Header receiveHeader = null;
                    ByteBufferList receiveBuffer = new ByteBufferList();
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        if(receiveBuffer.remaining() == 0){
                            ByteBufferList headerBuffer = bb.get(Header.LENGTH);
                            receiveHeader = new Header(headerBuffer.getAll());
                            log.i("receive signal "+receiveHeader.getSignal());
                        }
                        int bodyLength = receiveHeader.getLength();
                        int read = bodyLength - receiveBuffer.remaining();
                        int reallyRead = read > bb.remaining() ? bb.remaining() : read;
                        bb.get(receiveBuffer,reallyRead);

                        if(receiveBuffer.remaining() == bodyLength){
                            log.i("receive body-> "+receiveBuffer.readString(Charset.forName("UTF-8")));
                        }
                    }
                });


                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        log.i("send heartbeat");
                        Header header = new Header();
                        header.setSignal(Signal.PING);
                        byte[] sendByte = header.getContents();
                        Util.writeAll(socket, sendByte, new CompletedCallback() {
                            @Override
                            public void onCompleted(Exception ex) {
                                log.i("send heartbeat onCompleted");
                            }
                        });
                    }
                },500,(new Random().nextInt(30) +30) * 1000, TimeUnit.MILLISECONDS);

            }
        });



    }
}
