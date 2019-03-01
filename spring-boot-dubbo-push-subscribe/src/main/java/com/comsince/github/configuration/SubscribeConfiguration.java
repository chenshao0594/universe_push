package com.comsince.github.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.utils.hutool.Snowflake;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-28 下午12:52
 **/
@Configuration
public class SubscribeConfiguration {
    @Bean
    public Snowflake snowflake(){
        Snowflake snowflake = new Snowflake(1,1);
        return snowflake;
    }
}
