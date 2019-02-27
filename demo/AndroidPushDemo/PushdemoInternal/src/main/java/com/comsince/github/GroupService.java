package com.comsince.github;

import com.comsince.github.model.PublicMessage;
import com.comsince.github.push.Signal;
import com.comsince.github.utils.Json;
import com.meizu.cloud.pushinternal.DebugLogger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GroupService {
    private static final String TAG = "GroupService";
    public static final String ACTION_GROUP_JOIN_GROUP = "join_group";
    public static final String ACTION_SEND_PUBLIC_MESSAGE = "send_public_message";
    public static final String ACTION_SEND_PRIVATE_MESSAGE = "send_private_message";
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_MESSAGE = "group_message";
    public static final String PRIVATE_MESSAGE = "private_message";

    OkHttpClient client = new OkHttpClient();

    ConnectService connectService;

    public GroupService(ConnectService connectService){
        this.connectService = connectService;
    }

    public void joinGroup(String group){
        DebugLogger.i(TAG,"join group "+group);
        final Request request = new Request.Builder()
                .url("http://172.16.46.201:8080/group/joinGroup?group="+group+"&token="+connectService.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DebugLogger.e(TAG,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PushDemoApplication.sendMessage("join group "+response.body().string());
            }
        });
    }

    public void sendPublicMessage(String group,String message){
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setFrom(connectService.getToken());
        publicMessage.setGroup(group);
        publicMessage.setMessage(message);
        publicMessage.setMessageType(PublicMessage.TEXT_MESSAGE_TYPE);
        publicMessage.setType(PublicMessage.TYPE_PUBLIC);

        String body = Json.toJson(publicMessage);
        DebugLogger.i(TAG,"send group message "+body);
        connectService.sendMessage(Signal.CONTACT,body);
        PushDemoApplication.sendMessage("send group message"+body);
    }

    public void sendPrivateMessage(String fromToken,String toToken,String message){

    }
}
