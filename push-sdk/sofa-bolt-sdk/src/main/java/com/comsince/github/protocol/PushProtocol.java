package com.comsince.github.protocol;

import com.alipay.remoting.*;
import com.comsince.github.PushCommandFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午5:57
 **/
public class PushProtocol implements Protocol{
    /* because the design defect, the version is neglected in RpcProtocol, so we design RpcProtocolV2 and add protocol version. */
    public static final byte PROTOCOL_CODE       = (byte) 1;
    /** version 1, is the same with RpcProtocol */
    public static final byte PROTOCOL_VERSION_1  = (byte) 1;
    /** version 2, is the protocol version for RpcProtocolV2 */
    public static final byte PROTOCOL_VERSION_2  = (byte) 2;


    private CommandEncoder   encoder;
    private CommandDecoder   decoder;
    private HeartbeatTrigger heartbeatTrigger;
    private CommandHandler   commandHandler;
    private CommandFactory   commandFactory;

    public PushProtocol() {
        this.encoder = new PushCommandEncoder();
        this.decoder = new PushCommandDecoder();
        this.commandFactory = new PushCommandFactory();
        this.commandHandler = new PushCommandHandler();
        this.heartbeatTrigger = new PushHeartBeatTrigger();
    }

    @Override
    public CommandEncoder getEncoder() {
        return encoder;
    }

    @Override
    public CommandDecoder getDecoder() {
        return decoder;
    }

    @Override
    public HeartbeatTrigger getHeartbeatTrigger() {
        return heartbeatTrigger;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    public CommandFactory getCommandFactory() {
        return commandFactory;
    }
}
