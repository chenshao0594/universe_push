package com.comsince.github.controller;

import com.alibaba.fastjson.JSON;
import com.comsince.github.model.PushResponse;
import com.comsince.github.PushService;
import com.comsince.github.model.PushTokenRequest;
import com.comsince.github.model.PushTokensResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
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

    @Reference
    private PushService pushService;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("sendByToken")
    public PushResponse sendByToken(String token,@RequestParam("message") String message){
        logger.info("push "+token+ "message "+message);
        if(!StringUtils.isNotBlank(token)){
            return new PushResponse(10001,"token is empty");
        }
        if(!StringUtils.isNotBlank(message)){
            return new PushResponse(10002,"message is empty");
        }
        boolean flag = pushService.pushByToken(token,message);
        if(flag){
            return new PushResponse(200,"ok");
        } else {
            return new PushResponse(20001,"push token fail");
        }

    }

    /**
     * 发送tokens,tokens以分号隔开
     * */
    @RequestMapping(value = "sendByTokens",method = RequestMethod.GET)
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

    /**
     *
     {
       "message":"推送消息",
       "tokens":[
        "193de2250cd2983685c3abe1a7a809b9",
        "74c191b995bc7ea0dcaf0950065dc011"
        ]
     }
     * */
    @RequestMapping(value = "sendByTokens",method = RequestMethod.POST)
    public PushTokensResponse sendByTokens(@RequestBody String pushRequest){
        if(!StringUtils.isNotBlank(pushRequest)){
            return new PushTokensResponse(10004,"pushRequest is empty");
        }

        PushTokenRequest pushTokenRequest = JSON.parseObject(pushRequest,PushTokenRequest.class);
        if(!StringUtils.isNotBlank(pushTokenRequest.getMessage())){
            return new PushTokensResponse(10002,"message is empty");
        }
        if(pushTokenRequest.getTokens() == null){
            return new PushTokensResponse(10001,"tokens is empty");
        }
        PushTokensResponse pushTokensResponse = new PushTokensResponse(200,"");
        for(String token : pushTokenRequest.getTokens()){
            boolean online = isOnline(token);
            logger.info("token "+token+" online "+online);
            if(online){
                pushService.pushByToken(token,pushTokenRequest.getMessage());
                pushTokensResponse.setSuccessToken(token);
            } else {
                pushTokensResponse.setFailTokens(token);
            }
        }
        return pushTokensResponse;
    }

    /**
     * 群推接口暂时无法返回失败推送的token，谨慎使用
     * */
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

    private boolean isOnline(String token){
        RMap<String,Integer> flagMap = redissonClient.getMap("online_status");
        int flag = 0;
        if(flagMap.containsKey(token)){
            flag = flagMap.get(token);
        }
        return 1 == flag ? true : false;
    }

}
