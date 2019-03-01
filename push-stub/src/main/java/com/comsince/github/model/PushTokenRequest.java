package com.comsince.github.model;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-25 下午3:52
 **/
public class PushTokenRequest {
    private String message;
    private List<String> tokens;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "PushTokenRequest{" +
                "message='" + message + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
