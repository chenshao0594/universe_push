package com.comsince.github;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liaojinlong on 2019/2/23.
 */

public class PushLogActivity extends Activity{

    private TextView logTv;

    public static List<String> logList = new CopyOnWriteArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        logTv = findViewById(R.id.push_log);

        PushDemoApplication.setPushLogActivity(this);

        Intent intent = new Intent(this,ForegroundService.class);
        intent.setAction(ForegroundService.START_FOREGROUD_SERVICE);
        startService(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n";
        }
        //DebugLogger.i("PushLog",AllLog);
        logTv.setText(AllLog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PushDemoApplication.setPushLogActivity(null);
    }
}
