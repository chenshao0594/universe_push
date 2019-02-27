package com.comsince.github.controller;

import com.comsince.github.model.PushResponse;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-20 上午10:44
 **/
@RestController
@RequestMapping("monitor")
public class MonitorController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("alive")
    public PushResponse active(){
        return new PushResponse(200,"success");
    }


    @RequestMapping(value = "onlinenum")
    public PushResponse onLineNum(){
        RMap<String,Integer> flagMap = redissonClient.getMap("online_status");
        int onlineNum = 0;
        for (Map.Entry<String,Integer> entry : flagMap.entrySet()){
            if(entry.getValue() == 1){
                onlineNum++;
            }
        }
        return new PushResponse(200,String.valueOf(onlineNum));
    }

    @RequestMapping(value = "onlinestatus")
    public PushResponse onlineStatus(@RequestParam String token){
        return new PushResponse(200,isOnline(token) ? "online":"offline");
    }

    private boolean isOnline(String token){
        RMap<String,Integer> flagMap = redissonClient.getMap("online_status");
        int flag = 0;
        if(flagMap.containsKey(token)){
            flag = flagMap.get(token);
        }
        return 1 == flag ? true : false;
    }

}
