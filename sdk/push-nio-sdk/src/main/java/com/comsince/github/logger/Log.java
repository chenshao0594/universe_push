package com.comsince.github.logger;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-21 上午11:15
 **/
public interface Log {
    public void i(String tag,String message);

    public void i(String message);

    public void e(String message,Exception e);

    public void e(String tag,String message,Exception e);

    public void e(String message);
}
