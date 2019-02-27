package com.comsince.github;

import android.app.Activity;
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
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liaojinlong on 2019/2/23.
 */

public class PushMainActivity extends Activity implements View.OnClickListener{

    private String TAG = "PushMainActivity";

    private TextView logTv;

    public static List<String> logList = new CopyOnWriteArrayList<String>();

    private EditText groupEt;
    private EditText sendMessageEt;
    private Button joinGroupBt;
    private Button sendAllBt;
    private Button sendPrivateBt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        logTv = findViewById(R.id.push_log);

        PushDemoApplication.setPushLogActivity(this);

        initView();

        Intent intent = new Intent(this,PushService.class);
        intent.setAction(PushService.START_FOREGROUD_SERVICE);
        startService(intent);
    }

    private void initView(){
        groupEt = findViewById(R.id.push_group);
        sendMessageEt = findViewById(R.id.send_message);
        joinGroupBt = findViewById(R.id.join_group);
        sendAllBt = findViewById(R.id.send_all);
        sendPrivateBt = findViewById(R.id.send_private);

        joinGroupBt.setOnClickListener(this);
        sendAllBt.setOnClickListener(this);
        sendPrivateBt.setOnClickListener(this);
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
            case R.id.send_all:
                sendAll(groupEt.getText().toString(),sendMessageEt.getText().toString());
                break;
            case R.id.join_group:
                joinGroup(groupEt.getText().toString());
                break;
            case R.id.send_private:

            default:
                break;
        }
    }

    private void joinGroup(String group){
        Intent intent = new Intent(this,PushService.class);
        intent.setAction(GroupService.ACTION_GROUP_JOIN_GROUP);
        intent.putExtra(GroupService.GROUP_NAME,group);
        startService(intent);
    }

    private void sendPrivate(String group){
        Intent intent = new Intent(this,PushService.class);
        intent.setAction(GroupService.ACTION_GROUP_JOIN_GROUP);
        intent.putExtra(GroupService.GROUP_NAME,group);
        startService(intent);
    }

    private void sendAll(String group,String message){
        Intent intent = new Intent(this,PushService.class);
        intent.setAction(GroupService.ACTION_SEND_PUBLIC_MESSAGE);
        intent.putExtra(GroupService.GROUP_NAME,group);
        intent.putExtra(GroupService.GROUP_MESSAGE,message);
        startService(intent);
    }


}
