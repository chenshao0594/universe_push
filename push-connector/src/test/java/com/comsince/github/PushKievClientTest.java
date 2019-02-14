package com.comsince.github;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 下午3:54
 **/
public class PushKievClientTest {

    ClassPathXmlApplicationContext classPathXmlApplicationContext;
    PushService pushService;

    @Before
    public void prepare(){
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("kiev-client.xml");
        pushService = (PushService) classPathXmlApplicationContext.getBean("pushService");
    }

    @Test
    public void sendMessageByIp() throws Exception {
        pushService.sendSingleDeviceMessage("127.0.0.1","这时由测试组件发送的消息");
    }
}
