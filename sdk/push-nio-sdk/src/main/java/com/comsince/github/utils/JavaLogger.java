package com.comsince.github.utils;

import com.comsince.github.Log;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 下午2:27
 **/
public class JavaLogger implements Log {

    private Class loggerClass;

    public JavaLogger(Class loggerClass){
        this.loggerClass = loggerClass;
    }

    @Override
    public void i(String tag, String message) {
        System.out.println(loggerClass.getSimpleName()+"tag "+"message "+message);
    }

    @Override
    public void i(String message) {
        System.out.println(loggerClass.getSimpleName()+"message "+message);
    }

    @Override
    public void e(String tag, String message, Exception e) {

    }

    @Override
    public void e(String message) {
        System.err.println(loggerClass.getSimpleName()+" message "+message);
    }
}
