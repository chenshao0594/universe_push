package com.comsince.github.protocol;

import com.alipay.remoting.CommandEncoder;
import com.comsince.github.command.PushCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:30
 **/
public class PushCommandEncoder implements CommandEncoder{
    private static final Logger logger = LoggerFactory.getLogger("PushCommandEncoder");
    @Override
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
        if(msg instanceof PushCommand){
            ((PushCommand) msg).serialize();
             out.writeBytes(((PushCommand) msg).getHeader());
             if(((PushCommand) msg).getContentLength() != 0){
                 out.writeBytes(((PushCommand) msg).getContent());
             }
        } else {
            String warnMsg = "msg type [" + msg.getClass() + "] is not subclass of PushCommand";
            logger.warn(warnMsg);
        }
    }
}
