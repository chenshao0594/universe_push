package com.comsince.github.callback;


import com.comsince.github.AsyncSocket;

public interface ConnectCallback {
    public void onConnectCompleted(Exception ex, AsyncSocket socket);
}
