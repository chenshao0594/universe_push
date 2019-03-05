package com.comsince.github;

import com.alipay.remoting.exception.RemotingException;
import com.comsince.github.command.SubscribeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-3-4 下午4:26
 **/
public class SofaBoltStarter {

    static Logger logger                    = LoggerFactory
            .getLogger(SofaBoltStarter.class);

    static PushClient          client;

    static String             addr                      = "172.16.177.107:6789";


    public SofaBoltStarter(){
        client = new PushClient();
        client.init();
    }

    public static void main(String[] args){
        new SofaBoltStarter();
        try {
             client.onewaySub(addr, new SubscribeCommand(""));
        } catch (RemotingException e) {
            String errMsg = "RemotingException caught in oneway!";
            logger.error(errMsg, e);
        } catch (InterruptedException e) {
            logger.error("interrupted!");
        }
        client.shutdown();
    }
}
