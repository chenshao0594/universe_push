package com.comsince.github.process;

import com.comsince.github.PushPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.ArrayList;
import java.util.List;

public class MessageDispatcher {
    private static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);
    private static List<MessageProcessor> pushMessageProcessors = new ArrayList<>();
    static {
        pushMessageProcessors.add(new SubResponseProcessor());
        pushMessageProcessors.add(new HeartbeatResponseProcessor());
        pushMessageProcessors.add(new PushMessageProcessor());
    }
    public static void handleMessage(PushPacket pushPacket, ChannelContext channelContext){
        logger.info("start handleMessage "+pushPacket.getHeader().getSignal());
        for(MessageProcessor pushMessageProcessor : pushMessageProcessors){
            if(pushMessageProcessor.match(pushPacket)){
                pushMessageProcessor.process(pushPacket,channelContext);
                return;
            }
        }
    }
}
