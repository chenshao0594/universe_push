package com.comsince.github;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class PushGateTest{
    ClassPathXmlApplicationContext classPathXmlApplicationContext;
    PushService pushService;

    @Before
    public void prepare(){
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("kiev-server.xml");
        pushService = (PushService) classPathXmlApplicationContext.getBean("pushService");
    }

    @Test
    public void sendMessageByIp() throws Exception {
        for(int i=0;i<10;i++){
            pushService.pushByToken("88c667cfc16d103ac1ead60432aaee33","这时从push-gate发送的消息"+i);

        }
    }
}