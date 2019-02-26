package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加入群组
     * */
    @RequestMapping(value = "joinGroup")
    public PushResponse joinGroup(@RequestParam String token, @RequestParam String group){
         boolean flag = redissonClient.getList(group).add(token);
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
