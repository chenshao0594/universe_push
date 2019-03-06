package com.comsince.github;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventHandler;
import com.alipay.remoting.config.switches.GlobalSwitch;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-5 下午3:48
 **/
public class PushConnectionEventHandler extends ConnectionEventHandler {
    Logger logger = LoggerFactory.getLogger(PushConnectionEventHandler.class);

    public PushConnectionEventHandler() {
        super();
    }

    public PushConnectionEventHandler(GlobalSwitch globalSwitch) {
        super(globalSwitch);
    }

    /**
     * @see com.alipay.remoting.ConnectionEventHandler#channelInactive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive "+ctx.name());
       // Connection conn = ctx.channel().attr(Connection.CONNECTION).get();
//        if (conn != null) {
//            this.getConnectionManager().remove(conn);
//        }
        super.channelInactive(ctx);
    }
}