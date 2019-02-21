package com.comsince.github.push;

/***
 * 基本信令定义
 **/
public enum Signal {
    NONE,
    SUB, //订阅信令
    AUTH,//鉴权信令
    PING,
    PUSH;//心跳信令

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
