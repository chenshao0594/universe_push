package com.comsince.github;

import java.util.List;

/** 群组消息推送
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:38
 **/
public interface GroupContactService {
    boolean pushByToken(String token, String message);

    void pushByTokens(List<String> token, String message);

}
