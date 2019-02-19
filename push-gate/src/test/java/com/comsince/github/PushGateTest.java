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
            pushService.pushByToken("e4177a965a0a88539801401b3510330e","this is from 0push-gate pushMessage"+i);
            pushService.pushByToken("d2d44c9b876c04d9cebefd284af2fdea","this is from 1push-gate pushMessage"+i);
        }
    }
}