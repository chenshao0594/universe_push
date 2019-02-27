package com.comsince.github;

import android.content.Context;

import com.comsince.github.alarm.AlarmWrapper;
import com.comsince.github.alarm.Timer;
import com.comsince.github.client.AndroidNIOClient;
import com.comsince.github.client.PushMessageCallback;
import com.comsince.github.core.callback.CompletedCallback;
import com.comsince.github.logger.JavaLogger;
import com.comsince.github.push.Signal;
import com.meizu.cloud.pushinternal.DebugLogger;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectService implements PushMessageCallback {
    public static final String TAG = "ConnectService";

    AndroidNIOClient androidNIOClient;
    AlarmWrapper alarmWrapper;
    Timer heartbeatTimer;
    Timer reconnectTimer;
    int interval = 0;
    MessageCallback messageCallback;
    String token;

    public ConnectService(Context context,String authority){
        alarmWrapper = new AlarmWrapper(context,authority);
        alarmWrapper.start();
        androidNIOClient = new AndroidNIOClient("172.16.177.107",6789);
        //androidNIOClient = new AndroidNIOClient("172.16.46.201",6789);
        androidNIOClient.setPushMessageCallback(this);
    }

    public void setMessageCallback(MessageCallback messageCallback){
        this.messageCallback = messageCallback;
    }

    public void connect(){
        androidNIOClient.connect();
    }

    public void stop(){
        alarmWrapper.stop();
        androidNIOClient.close();
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
}
