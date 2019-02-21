package com.comsince.github;

import com.comsince.github.callback.CompletedCallback;
import com.comsince.github.callback.ConnectCallback;
import com.comsince.github.callback.DataCallback;
import com.comsince.github.callback.WritableCallback;
import com.comsince.github.push.Header;
import com.comsince.github.push.Signal;
import com.comsince.github.utils.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 下午2:48
 **/
public class PushClientStarter {
    static Log log = LoggerFactory.getLogger(PushClientStarter.class);
    private static final String HOST = "172.16.177.107";
    private static final int PORT = 6789;
    public static void main(String[] args){
        AsyncServer.getDefault().connectSocket(HOST, PORT, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                log.i("connect success");
                //start register
                Header header = new Header();
                header.setSignal(Signal.SUB);
                header.setLength(0);

                Util.writeAll(socket, header.getContents(), new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        log.i("send sub signal success");
                    }
                });


                socket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        log.i(bb.peekString());
                    }
                });

                socket.setWriteableCallback(new WritableCallback() {
                    @Override
                    public void onWriteable() {

                    }
                });
            }
        });
    }
}
