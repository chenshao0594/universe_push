package com.comsince.github;

import com.comsince.github.model.GroupRequest;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:29
 **/
public interface PushGroupService {
    void sendGroupRequest(GroupRequest groupRequest);
}
