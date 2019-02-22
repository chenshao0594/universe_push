package com.comsince.github.core.callback;


import com.comsince.github.core.AsyncSocket;

public interface ConnectCallback {
    public void onConnectCompleted(Exception ex, AsyncSocket socket);
}
