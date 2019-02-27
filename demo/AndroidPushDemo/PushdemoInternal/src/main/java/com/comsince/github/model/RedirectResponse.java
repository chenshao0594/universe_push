package com.comsince.github.model;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-2-27 下午5:10
 **/
public class RedirectResponse extends PushResponse {

    public RedirectResponse(){
        super();
    }

    private List<NodeInfo> nodeInfos;

    public RedirectResponse(int code, String message) {
        super(code, message);
    }

    public List<NodeInfo> getNodeInfos() {
        return nodeInfos;
    }

    public void setNodeInfos(List<NodeInfo> nodeInfos) {
        this.nodeInfos = nodeInfos;
    }
}
