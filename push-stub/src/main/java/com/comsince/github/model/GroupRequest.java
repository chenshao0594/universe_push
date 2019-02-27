package com.comsince.github.model;

import java.io.Serializable;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:29
 * {
        "from":"source_token",
        "to":"des_token",
        "group":"comsince",
        "type":0,  //单聊消息
        "messageType":0,
        "message":"消息内容，可以自定义，方便用扩展图片，视频类消息"
    }
 **/
public class GroupRequest implements Serializable{

    public static final int SIGNAL_CONTACT_MESSAGE = 1;
    public static final int GROUP_CONTACT_MESSAGE = 0;

    String from;
    String to;
    String group;
    int type;
    int messageType;
    String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GroupRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", group='" + group + '\'' +
                ", type=" + type +
                ", messageType=" + messageType +
                ", message='" + message + '\'' +
                '}';
    }
}
