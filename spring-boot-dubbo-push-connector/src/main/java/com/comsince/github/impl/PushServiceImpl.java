package com.comsince.github.impl;
import com.comsince.github.PushServer;
import com.comsince.github.PushService;
import com.comsince.github.pushmessage.PushMessagePacket;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Tio;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:39
 **/
@Service
public class PushServiceImpl implements PushService{
    Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

    @Override
    public void pushByIp(String ip, String message) {
        logger.info("push to "+ip+" message "+message);
        PushMessagePacket pushMessagePacket = new PushMessagePacket(message);
        Tio.sendToIp(PushServer.serverGroupContext,ip,pushMessagePacket);
    }

    @Override
    public boolean pushByToken(String token,String message) {
        logger.info("pushByToken-> "+token+" message->"+message);
        PushMessagePacket pushMessagePacket = new PushMessagePacket(message);
        return Tio.sendToBsId(PushServer.serverGroupContext,token,pushMessagePacket);
    }

    @Override
    public void pushByTokens(List<String> tokens, String message) {
       logger.info("pushByTokens-> "+tokens+" message->"+message);
       for(String token: tokens){
           pushByToken(token,message);
       }
    }

    @Override
    public void pushAll(String message) {
        logger.info("push to all message "+message);
        PushMessagePacket pushMessagePacket = new PushMessagePacket(message);
        Tio.sendToAll(PushServer.serverGroupContext,pushMessagePacket);
    }
}
