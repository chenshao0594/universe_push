package com.comsince.github;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

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
    public void sendByIp(){
        pushService.pushByIp("172.16.42.71","发送给指定ip的消息");
    }

    @Test
    public void sendAll(){
        pushService.pushAll("发送给全部设备的消息");
    }

    @Test
    public void sendByTokens(){
        List<String> tokens = new ArrayList<>();
        tokens.add("158275b19f0c3ed1e6aba22f9f6d3f41");
        tokens.add("0c4d24f2c37bc801fd15717b010901f5");
        pushService.pushByTokens(tokens,"发送指定tokens的消息");
    }

    @Test
    public void sendByToken(){
        pushService.pushByToken("acc415d62223749e46d5de9895946ca4","发送指定token的消息");
    }
}