package com.comsince.github.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-19 下午4:57
 **/
@Component
@Validated
@ConfigurationProperties(prefix = "kiev")
public class KievProperties {
    private String zookeeperUrl;
    private String applicationName;
}
