package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import com.comsince.github.PushService;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午4:42
 **/
@RequestMapping(value = "message")
@RestController
public class GroupMessageController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PushService pushService;

    @RequestMapping(value = "sendToGroup")
    public PushResponse sendToGroup(@RequestParam String token, @RequestParam String group,@RequestParam String message){
        RList<String> tokenList = redissonClient.getList(group);
        if(tokenList != null){
            for(String sendToken : tokenList){
                if(!sendToken.equals(token)){
                    pushService.pushByToken(token,message);
                }
            }
        }
        return new PushResponse(200,"send success");
    }

    @RequestMapping(value = "sendToSingle")
    public PushResponse sendToSingle(@RequestParam String token, @RequestParam String group,@RequestParam String message){
        RList<String> tokenList = redissonClient.getList(group);
        if(tokenList != null){
            if(!tokenList.contains(token)){
                return new PushResponse(3001,"dont send message out of group");
            }
            pushService.pushByToken(token,message);
        }
        return new PushResponse(200,"send success");
    }
}
