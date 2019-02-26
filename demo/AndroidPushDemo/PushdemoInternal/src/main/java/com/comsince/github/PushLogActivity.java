package com.comsince.github;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.meizu.cloud.pushinternal.DebugLogger;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liaojinlong on 2019/2/23.
 */

public class PushLogActivity extends Activity implements View.OnClickListener{

    private String TAG = "PushLogActivity";

    private TextView logTv;

    public static List<String> logList = new CopyOnWriteArrayList<String>();

    private EditText groupEt;
    private EditText sendMessageEt;
    private Button joinGroupBt;
    private Button sendBt;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        logTv = findViewById(R.id.push_log);

        PushDemoApplication.setPushLogActivity(this);

        initView();

        Intent intent = new Intent(this,ForegroundService.class);
        intent.setAction(ForegroundService.START_FOREGROUD_SERVICE);
        startService(intent);
    }

    private void initView(){
        groupEt = findViewById(R.id.push_group);
        sendMessageEt = findViewById(R.id.send_message);
        joinGroupBt = findViewById(R.id.join_group);
        sendBt = findViewById(R.id.send);

        joinGroupBt.setOnClickListener(this);
        sendBt.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                break;
            case R.id.join_group:
                joinGroup(groupEt.getText().toString(),PushDemoApplication.pushToken);
                break;
            default:
                break;
        }
    }

    private void joinGroup(String group,String token){
        final Request request = new Request.Builder()
                .url("http://192.168.0.100:8080/group/joinGroup?group="+group+"&token="+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DebugLogger.e(TAG,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DebugLogger.i(TAG,"join group "+response.body().string());
            }
        });
    }
}
