package com.comsince.github.model;

/**
 * {
 "from":"source_token",
 "to":"des_token",
 "group":"comsince",
 "type":0,  //群聊消息
 "messageType":0,
 "message":"消息内容，可以自定义，方便用扩展图片，视频类消息"
 }
 */

public class PublicMessage {
    public static final int TEXT_MESSAGE_TYPE = 1;
    public static final int TYPE_PUBLIC = 0;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
