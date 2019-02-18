package com.comsince.github.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 上午11:07
 **/
@Component
public class SpringApplicationContext implements ApplicationContextAware{
    Logger logger = LoggerFactory.getLogger(SpringApplicationContext.class);
    public static ApplicationContext pushApplicationContext = null;

    public static Object getBean(String name){
        if(pushApplicationContext != null){
            return pushApplicationContext.getBean(name);
        } else {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("init applicationContext");
        pushApplicationContext = applicationContext;
    }
}
