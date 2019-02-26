package com.comsince.github;

import java.util.List;

/**
 * 普通消息推送,在消息体自定义格式实现消息自行解析
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:38
 **/
public interface PushService {

    void pushByIp(String ip, String message);

    /**
     * 单推接口
     * */
    boolean pushByToken(String token, String message);


    void pushByTokens(List<String> token, String message);

    void pushAll(String message);
}
