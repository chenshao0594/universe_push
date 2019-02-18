package com.comsince.github.impl;

import com.comsince.github.HelloPacket;
import com.comsince.github.PushServerStarter;
import com.comsince.github.PushService;
import com.comsince.github.process.HeartbeatResponseProcessor;
import com.comsince.github.pushmessage.PushMessagePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tio.core.Tio;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:39
 **/
@Service
public class PushServiceImpl implements PushService{
    Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);
    @Override
    public void sendSingleDeviceMessage(String ip, String message) throws Exception {
        HelloPacket helloPacket = new HelloPacket();
        helloPacket.setBody(message.getBytes(HelloPacket.CHARSET));
        Tio.sendToIp(PushServerStarter.serverGroupContext,ip,helloPacket);
    }

    @Override
    public void pushByToken(String token,String message) {
        logger.info("pushByToken-> "+token+" message->"+message);
        PushMessagePacket pushMessagePacket = new PushMessagePacket(message);
        Tio.sendToBsId(PushServerStarter.serverGroupContext,token,pushMessagePacket);
    }
}
