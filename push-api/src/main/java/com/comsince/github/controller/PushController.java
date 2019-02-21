package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import com.comsince.github.PushService;
import org.apache.commons.lang.StringUtils;
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
        if(!StringUtils.isNotBlank(token)){
            return new PushResponse(10001,"token is empty");
        }
        if(!StringUtils.isNotBlank(message)){
            return new PushResponse(10002,"message is empty");
        }
        pushService.pushByToken(token,message);
        return new PushResponse(200,"ok");
    }

    /**
     * 发送tokens,tokens以分号隔开
     * */
    @RequestMapping("sendByTokens")
    public PushResponse sendByTokens(String message,String tokens){
        if(!StringUtils.isNotBlank(message)){
            return new PushResponse(10002,"message is empty");
        }
        if(!StringUtils.isNotBlank(tokens)){
            return new PushResponse(10001,"tokens is empty");
        }
        String[] tokenArr = tokens.split(",");
        if(tokenArr == null){
            return new PushResponse(10003,"tokens is invalid");
        }
        for (int i = 0; i<tokenArr.length; i++){
           pushService.pushByToken(tokenArr[i],message);
        }
        return new PushResponse(200,"push success");
    }

    @RequestMapping("sendToAll")
    public PushResponse sendToAll(String message){
        logger.info("push message "+message);
        if(!StringUtils.isNotBlank(message)){
            return new PushResponse(10002,"message is empty");
        }
        pushService.pushAll(message);
        return new PushResponse(200,"push success");
    }

    @RequestMapping(value = "sendToAll",method = RequestMethod.POST)
    public PushResponse sendToAll0(@RequestBody String message){
        logger.info("push message "+message);
        if(!StringUtils.isNotBlank(message)){
            return new PushResponse(10002,"message is empty");
        }
        pushService.pushAll(message);
        return new PushResponse(200,"push success");
    }

}
