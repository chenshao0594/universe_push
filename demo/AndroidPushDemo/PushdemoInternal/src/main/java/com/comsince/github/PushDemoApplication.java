package com.comsince.github;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.meizu.cloud.pushinternal.DebugLogger;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PushDemoApplication extends Application {
    private static DemoHandler sHandler = null;
    private static PushMainActivity pushLogActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        DebugLogger.initDebugLogger(this);
        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }
    }

    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setPushLogActivity(PushMainActivity activity) {
        pushLogActivity = activity;
    }

    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            PushMainActivity.logList.add(0, getSimpleDate() + " " + s);
            if (pushLogActivity != null) {
                pushLogActivity.refreshLogInfo();
            }
            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }

        @SuppressLint("SimpleDateFormat")
        private static String getSimpleDate() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        }
    }

    public static void sendMessage(String log){
        Message msg = Message.obtain();
        msg.obj = log;
        PushDemoApplication.getHandler().sendMessage(msg);
    }

}
