package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
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


}
