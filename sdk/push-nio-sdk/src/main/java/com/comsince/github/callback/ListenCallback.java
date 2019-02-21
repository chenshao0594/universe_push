package com.comsince.github.callback;


import com.comsince.github.AsyncServerSocket;
import com.comsince.github.AsyncSocket;

public interface ListenCallback extends CompletedCallback {
    public void onAccepted(AsyncSocket socket);
    public void onListening(AsyncServerSocket socket);
}
