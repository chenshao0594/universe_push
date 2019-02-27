package com.comsince.github;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.comsince.github.push.Signal;
import com.meizu.cloud.pushinternal.DebugLogger;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by liaojinlong on 16-4-21.
 */
public class PushService extends Service implements MessageCallback{

    public static int FOREGROUND_SERVICE = 102;
    public static String START_FOREGROUD_SERVICE = "start_foreground_service";


    ConnectService connectService;
    GroupService groupService;

    @Override
    public void onCreate() {
        super.onCreate();
        connectService = new ConnectService(this,"push-connector");
        connectService.setMessageCallback(this);
        groupService = new GroupService(connectService);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLogger.i("LocalService", "Received start id " + startId + ": " + intent);
        switchIntent(intent);
        connectService.connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        stopForeground(true);
        super.onDestroy();
        DebugLogger.i(START_FOREGROUD_SERVICE,"close push channel");
        connectService.stop();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroudService(Intent intent){
        CharSequence text = getText(R.string.local_service_started);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, PushMainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.flyme_status_ic_notification ))
                .setSmallIcon(R.drawable.mz_push_notification_small_icon)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("PushSevice foreground")  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        startForeground(FOREGROUND_SERVICE,notification);
    }

    @Override
    public void receiveMessage(Signal signal, String message) {
         if(Signal.SUB == signal){

         }
    }

    private void switchIntent(Intent intent){
        if(START_FOREGROUD_SERVICE.equals(intent.getAction())){
            startForegroudService(intent);
        } else if(GroupService.ACTION_GROUP_JOIN_GROUP.equals(intent.getAction())){
            groupService.joinGroup(intent.getStringExtra(GroupService.GROUP_NAME));
        } else if(GroupService.ACTION_SEND_PUBLIC_MESSAGE.equals(intent.getAction())){
            groupService.sendPublicMessage(intent.getStringExtra(GroupService.GROUP_NAME),intent.getStringExtra(GroupService.GROUP_MESSAGE));
        } else if(GroupService.ACTION_SEND_PRIVATE_MESSAGE.equals(intent.getAction())){

        }
    }
}