package com.comsince.github.configuration;

import com.comsince.github.sub.SubService;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-28 下午12:36
 **/

public class PushCommonConfiguration {

    Logger logger = LoggerFactory.getLogger(PushCommonConfiguration.class);

    @Autowired
    RedisProperties redisProperties;

    @Reference
    private SubService subService;

    @Bean(destroyMethod="shutdown")
    RedissonClient redissonClient() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress(redisProperties.getAddress());
        return Redisson.create(config);
    }

    public SubService subService(){
        return subService;
    }
}
