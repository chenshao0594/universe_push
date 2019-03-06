package com.comsince.github.protocol;

import com.alipay.remoting.CommandDecoder;
import com.comsince.github.command.PushCommand;
import com.comsince.github.command.ResponsePushCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-6 上午9:31
 **/
public class PushCommandDecoder implements CommandDecoder{
    static Logger logger = LoggerFactory.getLogger(PushCommandDecoder.class);
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("decode command readable length "+in.readableBytes());
        if(in.readableBytes() >= PushCommand.LENGTH){
            byte[] header = new byte[PushCommand.LENGTH];
            in.readBytes(header);
            ResponsePushCommand responsePushCommand = new ResponsePushCommand(header);
            int contentLength = responsePushCommand.getContentLength();
            if(contentLength != 0 && in.readableBytes() >= contentLength){
                byte[] content = new byte[contentLength];
                in.readBytes(content);
                responsePushCommand.setContent(content);
                out.add(responsePushCommand);
                responsePushCommand.deserialize();
                logger.info("response "+responsePushCommand.response);
            }
        }

    }
}
