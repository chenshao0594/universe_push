package com.comsince.github.service;

import com.alibaba.fastjson.JSON;
import com.comsince.github.GroupContactService;
import com.comsince.github.PushGroupService;
import com.comsince.github.model.GroupRequest;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:33
 **/
@Service
public class PushGroupServiceImpl implements PushGroupService{
    Logger logger = LoggerFactory.getLogger(PushGroupServiceImpl.class);
    @Autowired
    private GroupContactService groupContactService;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public void sendGroupRequest(GroupRequest groupRequest) {
        String requestJson = JSON.toJSONString(groupRequest);
        logger.info("receive request "+requestJson);
        if(GroupRequest.SIGNAL_CONTACT_MESSAGE == groupRequest.getType()){
             groupContactService.pushByToken(groupRequest.getTo(),groupRequest.getMessage());
        } else if(GroupRequest.GROUP_CONTACT_MESSAGE == groupRequest.getType()){
            RList<String> rList = redissonClient.getList(groupRequest.getGroup());
            for(String token : rList){
                if(groupRequest.getFrom().equals(token)){
                    continue;
                }
                groupContactService.pushByToken(token,requestJson);
            }
        }
    }
}
