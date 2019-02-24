package com.comsince.github;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.comsince.github.client.NIOClient;
import com.comsince.github.client.PushMessageCallback;
import com.comsince.github.logger.JavaLogger;
import com.comsince.github.push.Signal;
import com.meizu.cloud.pushinternal.DebugLogger;


/**
 * Created by liaojinlong on 16-4-21.
 */
public class ForegroundService extends Service implements PushMessageCallback {

    public static int FOREGROUND_SERVICE = 101;
    public static String START_FOREGROUD_SERVICE = "start_foreground_service";

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.

    NIOClient nioClient;

    @Override
    public void onCreate() {
        super.onCreate();

        nioClient = new NIOClient("172.16.177.107",6789);
        nioClient.setPushMessageCallback(this);
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
        nioClient.connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        stopForeground(true);
        super.onDestroy();
        DebugLogger.i(START_FOREGROUD_SERVICE,"close push channel");
        nioClient.close();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void receiveMessage(Signal signal,String s) {
        PushDemoApplication.sendMessage("receive signal ["+signal.name()+"] body:"+s);
    }

    @Override
    public void receiveException(Exception e) {
        String logMessage = "disconnect from remote";
        if(e != null){
            logMessage = JavaLogger.getStackMsg(e);
        }
        PushDemoApplication.sendMessage("receive error message "+logMessage);
    }
}