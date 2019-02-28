package com.comsince.github.configuration;

import com.comsince.github.PushServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-28 下午3:01
 **/
@Configuration
@Component("pushServerConfiguration")
public class PushServerConfiguration extends PushCommonConfiguration{
    @Bean
    public PushServer pushServer() throws IOException {
        PushServer pushServer = new PushServer();
        pushServer.init(redissonClient());
        return pushServer;
    }
}
