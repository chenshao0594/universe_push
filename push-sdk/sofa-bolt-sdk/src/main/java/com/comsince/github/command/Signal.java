package com.comsince.github.command;

/***
 * 基本信令定义
 **/
public enum Signal {
    NONE,
    SUB, //订阅信令
    AUTH,//鉴权信令
    PING,//心跳指令
    PUSH,//推送指令
    CONTACT;//聊天信令

    public static Signal toEnum(int ordinal) {
        byte o = (byte) ordinal;
        if (o > NONE.ordinal() &&
                o < Signal.values().length) {
            for (Signal signal : Signal.values()) {
                if (signal.ordinal() == o) {
                    return signal;
                }
            }
        }
        return NONE;
    }
}
