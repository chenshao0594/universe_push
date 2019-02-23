package com.comsince.github.client;

import com.comsince.github.core.*;
import com.comsince.github.core.callback.CompletedCallback;
import com.comsince.github.core.callback.ConnectCallback;
import com.comsince.github.core.callback.DataCallback;
import com.comsince.github.core.future.Cancellable;
import com.comsince.github.logger.Log;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;
import com.comsince.github.logger.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NIOClient implements ConnectCallback,DataCallback,CompletedCallback {
    Log log = LoggerFactory.getLogger(NIOClient.class);
    private AsyncServer asyncServer;
    private AsyncSocket asyncSocket;
    private String host;
    private int port;
    private static final int initInterval = 30 * 1000;
    private int interval = initInterval;
    private int heartNum = 1;
    private int reconnectNum = 0;
    private Cancellable cancellable;

    volatile boolean isConnected = false;

    Header receiveHeader = null;
    ByteBufferList receiveBuffer = new ByteBufferList();

    private PushMessageCallback pushMessageCallback;

    public void setPushMessageCallback(PushMessageCallback pushMessageCallback){
        this.pushMessageCallback = pushMessageCallback;
    }

    public NIOClient(String host, int port) {
        this.host = host;
        this.port = port;
        asyncServer = new AsyncServer(host+"-"+port);
    }

    public void connect(){
        if(!isConnected){
            cancellable = asyncServer.connectSocket(host,port,this);
        }
    }

    public void close(){
        if(cancellable != null){
            cancellable.cancel();
        }
    }

    private void sub(){
        //start register
        final Header header = new Header();
        header.setSignal(Signal.SUB);
        header.setLength(0);

        Util.writeAll(asyncSocket, header.getContents(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null){
                    log.i(ex.getCause().getMessage());
                }
            }
        });
    }

    private void heart(){
        Header header = new Header();
        header.setSignal(Signal.PING);
        byte[] sendByte = header.getContents();
        Util.writeAll(asyncSocket, sendByte, new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null){
                    log.e("heart","send message",ex);
                    return;
                }
            }
        });
    }

    @Override
    public void onConnectCompleted(Exception ex, AsyncSocket socket) {
        if(pushMessageCallback != null){
            pushMessageCallback.receiveException(ex);
        }
        if(ex != null){
            log.e("connect failed",ex);
            interval = initInterval;
            reconnect();
            return;
        }

        isConnected = true;
        reconnectNum = 0;
        this.asyncSocket = socket;
        asyncSocket.setDataCallback(this);
        asyncSocket.setClosedCallback(this);

        sub();
        scheduleHeartbeat();

    }

    @Override
    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        if(receiveBuffer.remaining() == 0){
            ByteBufferList headerBuffer = bb.get(Header.LENGTH);
            receiveHeader = new Header(headerBuffer.getAll());
        }
        int bodyLength = receiveHeader.getLength();
        int read = bodyLength - receiveBuffer.remaining();
        int reallyRead = read > bb.remaining() ? bb.remaining() : read;
        bb.get(receiveBuffer,reallyRead);

        if(receiveBuffer.remaining() == bodyLength){
            String message = receiveBuffer.readString(Charset.forName("UTF-8"));

            if(receiveHeader.getSignal() == Signal.PING){
                heartNum++;
                interval = interval + 30 * 1000 * heartNum;
                if(interval > 5 * 60 * 1000){
                    interval = 5 * 60 * 1000;
                }
                message = message + " next interval "+interval/1000 +" seconds";
                scheduleHeartbeat();
            }
            String logMessage = "receive signal ["+receiveHeader.getSignal()+"] body-> "+message;
            log.i(logMessage);
            if(pushMessageCallback != null){
                pushMessageCallback.receiveMessage(receiveHeader.getSignal(),message);
            }
        }

    }

    @Override
    public void onCompleted(Exception ex) {
        if(pushMessageCallback != null){
            pushMessageCallback.receiveException(ex);
        }
        if(ex != null) {
            log.e("onCompleted ",ex);
        }
        interval = initInterval;
        heartNum = 1;
        isConnected = false;
        reconnect();
    }

    private void reconnect() {
        reconnectNum ++;
        if(reconnectNum < 5){
            asyncServer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    asyncServer.connectSocket(host,port,NIOClient.this);
                }
            },10 * 1000);
        } else {
            log.i("reconnect reach max time");
        }
    }

    private void scheduleHeartbeat(){
        asyncServer.postDelayed(new Runnable() {
            @Override
            public void run() {
                heart();
            }
        },interval);
    }
}
