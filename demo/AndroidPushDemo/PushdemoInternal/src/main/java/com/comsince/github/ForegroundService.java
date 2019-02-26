package com.comsince.github;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.comsince.github.alarm.AlarmWrapper;
import com.comsince.github.alarm.Timer;
import com.comsince.github.client.AndroidNIOClient;
import com.comsince.github.client.PushMessageCallback;
import com.comsince.github.logger.JavaLogger;
import com.comsince.github.push.Signal;
import com.meizu.cloud.pushinternal.DebugLogger;


/**
 * Created by liaojinlong on 16-4-21.
 */
public class ForegroundService extends Service implements PushMessageCallback {

    public static int FOREGROUND_SERVICE = 102;
    public static String START_FOREGROUD_SERVICE = "start_foreground_service";

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.

    AndroidNIOClient androidNIOClient;
    AlarmWrapper alarmWrapper;
    Timer hearbeatTimer;
    Timer reconnectTimer;
    int interval = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        alarmWrapper = new AlarmWrapper(this,"push-connector");
        alarmWrapper.start();
        androidNIOClient = new AndroidNIOClient("172.16.177.107",6789);
        androidNIOClient.setPushMessageCallback(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DebugLogger.i("LocalService", "Received start id " + startId + ": " + intent);
        // In this sample, we'll use the same text for the ticker and the expanded notification
        if(START_FOREGROUD_SERVICE.equals(intent.getAction())){
            CharSequence text = getText(R.string.local_service_started);

            // The PendingIntent to launch our activity if the user selects this notification
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, PushLogActivity.class), 0);

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
        androidNIOClient.connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        stopForeground(true);
        super.onDestroy();
        DebugLogger.i(START_FOREGROUD_SERVICE,"close push channel");
        alarmWrapper.stop();
        androidNIOClient.close();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void receiveMessage(Signal signal,String s) {
        PushDemoApplication.sendMessage("receive signal ["+signal.name()+"] body:"+s);
        if(reconnectTimer != null){
            alarmWrapper.cancel(reconnectTimer);
        }
        if(Signal.SUB == signal){
            schedule();
        } else if(Signal.PING == signal){
            schedule();
        }
    }

    @Override
    public void receiveException(Exception e) {
        String logMessage = "disconnect from remote";
        if(e != null){
            logMessage = JavaLogger.getStackMsg(e);
        }
        PushDemoApplication.sendMessage("receive error message "+logMessage);
        if(hearbeatTimer != null){
            alarmWrapper.cancel(hearbeatTimer);
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
        alarmWrapper.schedule(hearbeatTimer =
                new Timer.Builder().period((30 + 30 * interval++) * 1000)
                .wakeup(true)
                .action(new Runnable() {
                    @Override
                    public void run() {
                         androidNIOClient.heart();
                    }
                }).build());
    }
}