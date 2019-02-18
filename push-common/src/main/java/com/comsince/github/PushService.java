package com.comsince.github;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:38
 **/
public interface PushService {
    void sendSingleDeviceMessage(String ip, String message) throws Exception;

    /**
     * 单推接口
     * */
    void pushByToken(String token, String message);
}
