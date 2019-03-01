package com.comsince.github.process;

import com.alibaba.fastjson.JSON;
import com.comsince.github.GroupContactService;
import com.comsince.github.PushGroupService;
import com.comsince.github.PushPacket;
import com.comsince.github.Signal;
import com.comsince.github.configuration.PushCommonConfiguration;
import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.model.GroupRequest;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 下午5:58
 **/
public class GroupContactProcessor implements MessageProcessor{
    Logger logger = LoggerFactory.getLogger(GroupContactProcessor.class);
    @Override
    public void process(PushPacket pushPacket, ChannelContext channelContext) {
        sendGroupRequest(Json.toBean(new String(pushPacket.getBody()), GroupRequest.class));
    }

    @Override
    public boolean match(PushPacket pushPacket) {
        return Signal.CONTACT == pushPacket.getHeader().getSignal();
    }

    private void sendGroupRequest(GroupRequest groupRequest){
        GroupContactService groupContactService = (GroupContactService) SpringApplicationContext.getBean("groupContactServiceImpl");
        RedissonClient redissonClient = ((RedissonClient)SpringApplicationContext.getBean("redissonClient"));
        String requestJson = JSON.toJSONString(groupRequest);
        logger.info("receive request "+requestJson);
        if(GroupRequest.SIGNAL_CONTACT_MESSAGE == groupRequest.getType()){
            groupContactService.pushByToken(groupRequest.getTo(),groupRequest.getMessage());
        } else if(GroupRequest.GROUP_CONTACT_MESSAGE == groupRequest.getType()){
            RList<String> rList = redissonClient.getList(groupRequest.getGroup());
            for(String token : rList){
                if(groupRequest.getFrom().equals(token)){
                    continue;
                }
                groupContactService.pushByToken(token,requestJson);
            }
        }
    }
}
