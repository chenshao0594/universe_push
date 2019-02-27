package com.comsince.github.model;


import com.alibaba.fastjson.JSON;

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

    public static void main(String[] args){
        String tokenRequest = "\n" +
                "{\n" +
                "    \"message\":\"推送消息\",\n" +
                "    \"tokens\":[\n" +
                "        \"193de2250cd2983685c3abe1a7a809b9\",\n" +
                "        \"74c191b995bc7ea0dcaf0950065dc011\"\n" +
                "    ]\n" +
                "}\n";
        PushTokenRequest pushTokenRequest = (PushTokenRequest) JSON.parseObject(tokenRequest,PushTokenRequest.class);
        System.out.println(pushTokenRequest);
    }
}
