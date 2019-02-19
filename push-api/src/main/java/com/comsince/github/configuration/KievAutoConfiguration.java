package com.comsince.github.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-19 下午4:52
 **/
@Configuration
public class KievAutoConfiguration {

    @Configuration
    @ImportResource("classpath*:kiev-client.xml")
    public static class KievClientConfiguration{

    }
}
