package com.comsince.github;

import com.comsince.github.callback.CompletedCallback;
import com.comsince.github.callback.DataCallback;

/**
 * 数据接收接口
 * */
public interface DataEmitter {
    public void setDataCallback(DataCallback callback);
    public DataCallback getDataCallback();
    public boolean isChunked();
    public void pause();
    public void resume();
    public void close();
    public boolean isPaused();
    public void setEndCallback(CompletedCallback callback);
    public CompletedCallback getEndCallback();
    public AsyncServer getServer();
    public String charset();
}
