package com.comsince.github.core.wrapper;


import com.comsince.github.core.AsyncSocket;

public interface AsyncSocketWrapper extends AsyncSocket, DataEmitterWrapper {
    public AsyncSocket getSocket();
}
