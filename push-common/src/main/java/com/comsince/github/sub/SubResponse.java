package com.comsince.github.sub;


import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 *
 * 订阅响应格式
 *
 * */
public class SubResponse {
    private int status;
    private String token;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
