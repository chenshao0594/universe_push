package com.comsince.github.process;

import com.comsince.github.PushPacket;
import org.tio.core.ChannelContext;

import java.util.ArrayList;
import java.util.List;

public class MessageDispatcher {
    private static List<PushMessageProcessor> pushMessageProcessors = new ArrayList<>();
    static {
        pushMessageProcessors.add(new SubResponseProcessor());
    }
    public static void handleMessage(PushPacket pushPacket, ChannelContext channelContext){
        for(PushMessageProcessor pushMessageProcessor : pushMessageProcessors){
            if(pushMessageProcessor.match(pushPacket)){
                pushMessageProcessor.process(pushPacket,channelContext);
                return;
            }
        }
    }
}
