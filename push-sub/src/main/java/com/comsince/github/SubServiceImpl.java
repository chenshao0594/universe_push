package com.comsince.github;

import com.comsince.github.context.SpringApplicationContext;
import com.comsince.github.sub.SubService;
import com.comsince.github.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.utils.hutool.Snowflake;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-18 上午10:19
 **/
@Service
public class SubServiceImpl implements SubService {
    Logger logger = LoggerFactory.getLogger(SubServiceImpl.class);
    @Autowired
    private Snowflake snowflake;
    @Override
    public String generateToken() {
        String token = MD5Util.md5Encode(String.valueOf(snowflake.nextId()));
        logger.info("generateToken "+token);
        return token;
    }
}
