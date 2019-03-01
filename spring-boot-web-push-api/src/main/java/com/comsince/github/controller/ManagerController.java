package com.comsince.github.controller;

import com.comsince.github.model.NodeInfo;
import com.comsince.github.model.RedirectResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-27 下午5:09
 **/
@RestController
@RequestMapping("manager")
public class ManagerController {

    static RedirectResponse redirectResponse;

    static {
        redirectResponse = new RedirectResponse(200,"success");
        List<NodeInfo> nodeInfos = new ArrayList<>();
        NodeInfo nodeInfo1 = new NodeInfo("172.16.177.107",6789);
        NodeInfo nodeInfo2 = new NodeInfo("172.16.176.23",6789);
        NodeInfo nodeInfo3 = new NodeInfo("172.16.176.25",6789);
        nodeInfos.add(nodeInfo1);
        nodeInfos.add(nodeInfo2);
        nodeInfos.add(nodeInfo3);
        redirectResponse.setNodeInfos(nodeInfos);
    }

    @RequestMapping(value = "redirect")
    public RedirectResponse redirect(){
        return redirectResponse;
    }
}
