package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import com.comsince.github.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-19 下午4:30
 **/
@RestController
@RequestMapping("push")
public class PushController {

    Logger logger = LoggerFactory.getLogger(PushController.class);

    @Autowired
    private PushService pushService;

    @RequestMapping("sendByToken")
    public PushResponse sendByToken(String token,@RequestParam("message") String message){
        logger.info("push "+token+ "message "+message);
        pushService.pushByToken(token,message);
        return new PushResponse(200,"ok");
    }

    /**
     * 发送tokens,tokens以分号隔开
     * */
    @RequestMapping("sendByTokens")
    public String sendByTokens(String message,String tokens){
         return null;
    }

    @RequestMapping("sendToAll")
    public String sendToAll(String message){
        logger.info("push message "+message);
        pushService.pushAll(message);
        return "push success";
    }

    @RequestMapping(value = "sendToAll",method = RequestMethod.POST)
    public PushResponse sendToAll0(@RequestBody String message){
        logger.info("push message "+message);
        pushService.pushAll(message);
        return new PushResponse(200,"push success");
    }

}
