package com.comsince.github.logger;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 下午2:27
 **/
public class LoggerFactory {
    public static Log getLogger(Class loggerClass){
        return new JavaLogger(loggerClass);
    }
}
