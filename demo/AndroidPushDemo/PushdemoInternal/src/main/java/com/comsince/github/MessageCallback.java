package com.comsince.github;


import com.comsince.github.push.Signal;

public interface MessageCallback {
    public void receiveMessage(Signal signal, String message);
}
