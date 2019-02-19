package com.comsince.github;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 下午2:39
 **/
public class PushGateStarter {
    ClassPathXmlApplicationContext classPathXmlApplicationContext;
    PushService pushService;

    public void prepare(){
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("kiev-server.xml");
        pushService = (PushService) classPathXmlApplicationContext.getBean("pushService");
    }

    public void sendMessageByIp() {
        pushService.pushByIp("127.0.0.1","这时由测试组件发送的消息");
    }


}
