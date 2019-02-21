package com.comsince.github.wrapper;


import com.comsince.github.AsyncSocket;

public interface AsyncSocketWrapper extends AsyncSocket, DataEmitterWrapper {
    public AsyncSocket getSocket();
}
