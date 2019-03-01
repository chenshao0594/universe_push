package com.comsince.github.client;

import com.comsince.github.push.Signal;

public interface PushMessageCallback {
    void receiveMessage(Signal signal,String message);
    void receiveException(Exception e);
}
