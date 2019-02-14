package com.comsince.github;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-14 下午3:42
 **/
public class PushServerStarterMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("kiev-server.xml");
//        PushServerStarter pushServerStarter = (PushServerStarter) classPathXmlApplicationContext.getBean("pushServerStarter");
//        pushServerStarter.init();
    }
}
