package com.comsince.github.controller;

import com.comsince.github.model.PushResponse;
import com.comsince.github.utils.Constants;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("alive")
    public PushResponse active(){
        return new PushResponse(200,"success");
    }


    @RequestMapping(value = "onlinenum")
    public PushResponse onLineNum(){
        long onlineNum = redissonClient.getAtomicLong(Constants.ONLINE_NUM).get();
        return new PushResponse(200,String.valueOf(onlineNum));
    }

    @RequestMapping(value = "onlinestatus")
    public PushResponse onlineStatus(@RequestParam String token){
        return new PushResponse(200,isOnline(token) ? "online":"offline");
    }

    private boolean isOnline(String token){
        RMap<String,Integer> flagMap = redissonClient.getMap(Constants.ONLINE_STATUS);
        int flag = 0;
        if(flagMap.containsKey(token)){
            flag = flagMap.get(token);
        }
        return 1 == flag ? true : false;
    }

}
