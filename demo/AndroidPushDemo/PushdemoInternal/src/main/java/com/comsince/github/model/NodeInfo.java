package com.comsince.github.model;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-27 下午5:14
 **/
public class NodeInfo {

    public NodeInfo(){}

    public NodeInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
