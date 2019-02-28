package com.comsince.github.impl;

import com.comsince.github.GroupContactService;
import com.comsince.github.PushServer;
import com.comsince.github.contactmessage.ContactMessagePacket;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Tio;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:42
 **/
@Service
public class GroupContactServiceImpl implements GroupContactService {
    Logger logger = LoggerFactory.getLogger(GroupContactServiceImpl.class);
    @Override
    public boolean pushByToken(String token,String message) {
        logger.info("pushByToken-> "+token+" message->"+message);
        ContactMessagePacket contactMessagePacket = new ContactMessagePacket(message);
        return Tio.sendToBsId(PushServer.serverGroupContext,token,contactMessagePacket);
    }

    @Override
    public void pushByTokens(List<String> tokens, String message) {
        logger.info("pushByTokens-> "+tokens+" message->"+message);
        for(String token: tokens){
            pushByToken(token,message);
        }
    }
}
