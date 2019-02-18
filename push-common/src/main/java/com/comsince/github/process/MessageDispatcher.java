package com.comsince.github.process;

import com.comsince.github.PushPacket;
import org.tio.core.ChannelContext;

import java.util.ArrayList;
import java.util.List;

public class MessageDispatcher {
    private static List<MessageProcessor> pushMessageProcessors = new ArrayList<>();
    static {
        pushMessageProcessors.add(new SubResponseProcessor());
        pushMessageProcessors.add(new HeartbeatResponseProcessor());
        pushMessageProcessors.add(new PushMessageProcessor());
    }
    public static void handleMessage(PushPacket pushPacket, ChannelContext channelContext){
        for(MessageProcessor pushMessageProcessor : pushMessageProcessors){
            if(pushMessageProcessor.match(pushPacket)){
                pushMessageProcessor.process(pushPacket,channelContext);
                return;
            }
        }
    }
}
