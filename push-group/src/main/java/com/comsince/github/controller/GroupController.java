package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午4:32
 **/
@RequestMapping(value = "group")
@RestController
public class GroupController {

    Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加入群组
     * */
    @RequestMapping(value = "joinGroup")
    public PushResponse joinGroup(@RequestParam String token, @RequestParam String group){
        logger.info("token "+token+" join group "+group);
        RList<String> tokenList = redissonClient.getList(group);
        if(tokenList.contains(token)){
            return new PushResponse(30001,"already add group");
        }
         boolean flag = tokenList.add(token);
         return new PushResponse(200,String.valueOf(flag));
    }


    /**
     * 退出群组
     * */
    @RequestMapping(value = "quitGroup")
    public PushResponse quitGroup(@RequestParam String token,@RequestParam String group){
        boolean flag = redissonClient.getList(group).remove(token);
        return new PushResponse(200,String.valueOf(flag));
    }

    /**
     * 创建群组
     * */
    @RequestMapping(value = "createGroup")
    public void createGroup(@RequestParam String group){

    }


}
