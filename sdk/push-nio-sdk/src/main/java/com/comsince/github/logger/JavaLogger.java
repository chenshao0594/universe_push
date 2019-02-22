package com.comsince.github.logger;

import com.comsince.github.logger.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 下午2:27
 **/
public class JavaLogger implements Log {

    private Class loggerClass;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JavaLogger(Class loggerClass){
        this.loggerClass = loggerClass;
    }

    @Override
    public void i(String tag, String message) {
        System.out.println(loggerClass.getSimpleName()+"tag "+"message: "+message);
    }

    @Override
    public void i(String message) {
        System.out.println("["+Thread.currentThread().getName()+"]"+" ["+dateFormat.format(new Date())+"] "+"["+loggerClass.getSimpleName()+"] "+"message: "+message);
    }

    @Override
    public void e(String tag, String message, Exception e) {
        System.err.println("["+Thread.currentThread().getName()+"]"+" ["+dateFormat.format(new Date())+"] "+"["+loggerClass.getSimpleName()+"] "+" message: "+message+" "+e.getCause().getMessage());
    }

    @Override
    public void e(String message) {
        System.err.println("["+Thread.currentThread().getName()+"]"+" ["+dateFormat.format(new Date())+"] "+"["+loggerClass.getSimpleName()+"] "+" message: "+message);
    }
}
