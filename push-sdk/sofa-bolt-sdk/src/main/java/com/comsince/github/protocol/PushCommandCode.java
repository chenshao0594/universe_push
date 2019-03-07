package com.comsince.github.protocol;

import com.alipay.remoting.CommandCode;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-7 上午11:25
 **/
public enum  PushCommandCode implements CommandCode{

    PUSH_REQUEST((short) 1), PUSH_RESPONSE((short) 2);

    private short value;

    PushCommandCode(short value) {
        this.value = value;
    }

    @Override
    public short value() {
        return this.value;
    }

    public static PushCommandCode valueOf(short value) {
        switch (value) {
            case 1:
                return PUSH_REQUEST;
            case 2:
                return PUSH_RESPONSE;
        }
        throw new IllegalArgumentException("Unknown push command code value: " + value);
    }
}
