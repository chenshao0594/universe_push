package com.comsince.github.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-25 下午3:42
 **/
public class PushTokensResponse extends PushResponse {
    private List<String> successTokens = new ArrayList<>();
    private List<String> failTokens = new ArrayList<>();
    public PushTokensResponse(int code, String message) {
        super(code, message);
    }

    public void setSuccessToken(String token){
        successTokens.add(token);
    }

    public void setFailTokens(String token){
        failTokens.add(token);
    }

    public List<String> getSuccessTokens() {
        return successTokens;
    }

    public void setSuccessTokens(List<String> successTokens) {
        this.successTokens = successTokens;
    }

    public List<String> getFailTokens() {
        return failTokens;
    }

    public void setFailTokens(List<String> failTokens) {
        this.failTokens = failTokens;
    }
}
