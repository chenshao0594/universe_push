package com.comsince.github.core.callback;


import com.comsince.github.core.AsyncServerSocket;
import com.comsince.github.core.AsyncSocket;

public interface ListenCallback extends CompletedCallback {
    public void onAccepted(AsyncSocket socket);
    public void onListening(AsyncServerSocket socket);
}
