package com.comsince.github;

import android.content.Context;
import android.text.TextUtils;

import com.comsince.github.alarm.AlarmWrapper;
import com.comsince.github.alarm.Timer;
import com.comsince.github.client.AndroidNIOClient;
import com.comsince.github.client.PushMessageCallback;
import com.comsince.github.core.callback.CompletedCallback;
import com.comsince.github.logger.JavaLogger;
import com.comsince.github.model.NodeInfo;
import com.comsince.github.model.RedirectResponse;
import com.comsince.github.push.Signal;
import com.comsince.github.utils.Json;
import com.comsince.github.utils.PreferenceUtil;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectService implements PushMessageCallback,Callback {
    public static final String TAG = "ConnectService";
    Context context;
    AndroidNIOClient androidNIOClient;
    AlarmWrapper alarmWrapper;
    Timer heartbeatTimer;
    Timer reconnectTimer;
    int interval = 0;
    MessageCallback messageCallback;
    String token;

    OkHttpClient client = new OkHttpClient();

    public ConnectService(Context context,String authority){
        this.context  = context;
        alarmWrapper = new AlarmWrapper(context,authority);
    }

    public void setMessageCallback(MessageCallback messageCallback){
        this.messageCallback = messageCallback;
    }

    public void connect(){
        if(androidNIOClient != null){
            DebugLogger.i(TAG,"start connect");
            androidNIOClient.connect();
        }
    }

    public void disConnect(){
        cancelHeartTimer();
        cancelReconnectTimer();
        androidNIOClient.close();
    }


    public void start(){
        alarmWrapper.start();
        redirect();
    }

    public void stop(){
        disConnect();
        alarmWrapper.stop();
    }

    public String getToken(){
        return token;
    }

    public void sendMessage(Signal signal,String body){
        androidNIOClient.sendMessage(signal, body, new CompletedCallback() {
            @Override
            public void onCompleted(Exception e) {
                DebugLogger.e(TAG,e != null ? e.getMessage() : "send success");
            }
        });
    }

    @Override
    public void receiveMessage(Signal signal, String message) {
        PushDemoApplication.sendMessage("receive signal ["+signal.name()+"] body:"+message);
        if(reconnectTimer != null){
            alarmWrapper.cancel(reconnectTimer);
        }
        if(Signal.SUB == signal){
            try {
                JSONObject jsonObject = new JSONObject(message);
                this.token = jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            schedule();
        } else if(Signal.PING == signal){
            schedule();
        }
        if(Signal.PING != signal && messageCallback != null){
            messageCallback.receiveMessage(signal,message);
        }
    }

    @Override
    public void receiveException(Exception e) {
        String logMessage = "disconnect from remote";
        if(e != null){
            logMessage = JavaLogger.getStackMsg(e);
        }
        PushDemoApplication.sendMessage("receive error message "+logMessage);
        if(heartbeatTimer != null){
            alarmWrapper.cancel(heartbeatTimer);
        }
        interval = 0;
        alarmWrapper.schedule(reconnectTimer =
                new Timer.Builder().period(10 * 1000)
                        .wakeup(true)
                        .action(new Runnable() {
                            @Override
                            public void run() {
                                androidNIOClient.connect();
                            }
                        }).build());
    }

    @Override
    public void onConnected() {
        DebugLogger.i(TAG,"onConnected");
        cancelReconnectTimer();
        String uid = MzSystemUtils.getDeviceId(context);
        if(TextUtils.isEmpty(uid)){
            androidNIOClient.sub();
        } else {
            androidNIOClient.sub(uid);
        }
    }

    private void schedule(){
        alarmWrapper.schedule(heartbeatTimer =
                new Timer.Builder().period((30 + 30 * interval) * 1000)
                        .wakeup(true)
                        .action(new Runnable() {
                            @Override
                            public void run() {
                                long current = (30 + 30 * ++interval) * 1000;
                                if(current > 20 * 60 * 1000){
                                    current = 15 * 60 * 1000;
                                }
                                androidNIOClient.heart(current);
                            }
                        }).build());
    }

    public void redirect(){
        final Request request = new Request.Builder()
                .url("http://172.16.185.114:8080/manager/redirect")
                .build();

        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    private void cancelHeartTimer(){
        if(heartbeatTimer != null){
            alarmWrapper.cancel(heartbeatTimer);
        }
    }

    private void cancelReconnectTimer(){
        if(reconnectTimer != null){
            alarmWrapper.cancel(reconnectTimer);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String redirect = response.body().string();
        PushDemoApplication.sendMessage("redirect "+redirect);
        RedirectResponse redirectResponse = Json.toBean(redirect,RedirectResponse.class);
        NodeInfo nodeInfo = redirectResponse.getNodeInfos().get(new Random().nextInt(3));
        nodeInfo = new NodeInfo("172.16.46.201",6789);
        androidNIOClient = new AndroidNIOClient("172.16.177.107",6789);
        PushDemoApplication.sendMessage("connect node "+nodeInfo.getIp()+":"+nodeInfo.getPort());
        androidNIOClient = new AndroidNIOClient(nodeInfo.getIp(),nodeInfo.getPort());
        androidNIOClient.setPushMessageCallback(this);
        androidNIOClient.connect();
    }
}
