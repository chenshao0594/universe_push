package com.comsince.github.protocol;

import com.alipay.remoting.ProtocolManager;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:33
 **/
public class PushProtocolManager {
    public static final int DEFAULT_PROTOCOL_CODE_LENGTH = 1;

    public static void initProtocols() {
        ProtocolManager.registerProtocol(new PushProtocol(), PushProtocol.PROTOCOL_CODE);
    }
}
