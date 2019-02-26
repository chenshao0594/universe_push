package com.comsince.github.client;

import com.comsince.github.core.*;
import com.comsince.github.core.callback.CompletedCallback;
import com.comsince.github.core.callback.ConnectCallback;
import com.comsince.github.core.callback.DataCallback;
import com.comsince.github.core.future.Cancellable;
import com.comsince.github.logger.Log;
import com.comsince.github.logger.LoggerFactory;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


/**
 *
 * 这里Android client，提供心跳回调机制，方便客户端自由定制
 * */
public class AndroidNIOClient implements ConnectCallback,DataCallback,CompletedCallback {
    Log log = LoggerFactory.getLogger(NIOClient.class);
    private AsyncServer asyncServer;
    private AsyncSocket asyncSocket;
    private String host;
    private int port;
    private Cancellable cancellable;

    volatile boolean isConnected = false;

    Header receiveHeader = null;
    ByteBufferList receiveBuffer = new ByteBufferList();

    private PushMessageCallback pushMessageCallback;

    public void setPushMessageCallback(PushMessageCallback pushMessageCallback){
        this.pushMessageCallback = pushMessageCallback;
    }

    public AndroidNIOClient(String host, int port) {
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
                log.e("write completed",ex);
            }
        });
    }

    /**
     * @param interval 距离下次心跳间隔，单位毫秒
     * */
    public void heart(long interval){
        log.i("Android client send heartbeat");
        ByteBufferList heartBuffer = new ByteBufferList();
        Header header = new Header();
        header.setSignal(Signal.PING);
        String heartInterval = "{\"interval\":"+interval+"}";
        header.setLength(heartInterval.getBytes().length);

        ByteBuffer allBuffer = ByteBufferList.obtain(Header.LENGTH + header.getLength());
        allBuffer.put(header.getContents());
        allBuffer.put(heartInterval.getBytes());
        allBuffer.flip();
        heartBuffer.add(allBuffer);

        Util.writeAll(asyncSocket, heartBuffer, new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                log.e("send heartbeat completed",ex);
            }
        });
    }

    @Override
    public void onConnectCompleted(Exception ex, AsyncSocket socket) {
        //ex为空，表示链接正常
        if(ex != null){
            if(pushMessageCallback != null){
                pushMessageCallback.receiveException(ex);
            }
            log.e("connect failed",ex);
            return;
        }

        isConnected = true;
        this.asyncSocket = socket;
        asyncSocket.setDataCallback(this);
        asyncSocket.setClosedCallback(this);
        sub();
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
        isConnected = false;
    }


}