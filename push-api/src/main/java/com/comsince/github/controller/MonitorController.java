package com.comsince.github.controller;

import com.comsince.github.PushResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-20 上午10:44
 **/
@RestController
@RequestMapping("monitor")
public class MonitorController {

    @RequestMapping("alive")
    public PushResponse active(){
        return new PushResponse(200,"success");
    }
}
