package com.comsince.github.controller;

import com.comsince.github.model.PushResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-26 上午11:40
 **/
@RestController
@RequestMapping("connectmanager")
public class ConnectManagerController {

    @RequestMapping(value = "closeByToken")
    public PushResponse closeByToken(String token){
        return new PushResponse(200,"success");
    }
}
