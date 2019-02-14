package com.comsince.github.impl;

import com.comsince.github.HelloPacket;
import com.comsince.github.PushServerStarter;
import com.comsince.github.PushService;
import org.springframework.stereotype.Service;
import org.tio.core.Tio;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 上午11:39
 **/
@Service
public class PushServiceImpl implements PushService{

    @Override
    public void sendSingleDeviceMessage(String ip, String message) throws Exception {
        HelloPacket helloPacket = new HelloPacket();
        helloPacket.setBody(message.getBytes(HelloPacket.CHARSET));
        Tio.sendToIp(PushServerStarter.serverGroupContext,ip,helloPacket);
    }
}
