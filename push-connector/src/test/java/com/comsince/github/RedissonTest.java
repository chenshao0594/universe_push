package com.comsince.github;

import com.comsince.github.utils.RedisUtils;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RedissonClient;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-25 下午5:17
 **/
public class RedissonTest {
    RedissonClient redissonClient;
    @Before
    public void prepare(){
         redissonClient = RedisUtils.getInstance().getRedisson("172.16.46.213","6379");
    }

    @Test
    public void testPut(){
         redissonClient.getMap("test").fastPut("id",1);
         System.out.println(redissonClient.getMap("online_status").get("2069c17d858e2462a6c089a3af42222d"));
    }
}
