package com.example.hsp.test5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by HSP on 2017-12-15.
 */

public class MyService extends NotificationListenerService {


    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotifyBuilder;
    final int notify_id=1993;
    Context context;
    int msg;
    private SharedPreferences mPref;



    @Override
    public void onCreate() {

        super.onCreate();
    }
    public void start(RemoteMessage remoteMessage,Context context){
        this.context=context;
        inti();
        //            sendNotification(remoteMessage.getNotification().getBody());
//            sendNotificationGroup(remoteMessage.getNotification().getBody());
        inbox(remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("dd","asd1");
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        Log.i("dd","asd2");
        super.onNotificationRemoved(sbn, rankingMap);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        Log.i("dd","asd3");
        if(NotificationListenerService.REASON_USER_STOPPED==reason||
                NotificationListenerService.REASON_APP_CANCEL==reason||
                NotificationListenerService.REASON_CANCEL==reason||reason==1){
            Log.i("dd","asd4");

        }

        super.onNotificationRemoved(sbn, rankingMap, reason);
    }

    public void inti(){

        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        msg=mPref.getInt("msg",0);
        SharedPreferences.Editor editor = mPref.edit();
        msg++;
        editor.putInt("msg",msg);
        editor.commit();

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "박현근";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void sendNotificationGroup(String messageBody){
        NotificationCompat.Builder mBuilder = new  NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("G");
        mBuilder.setContentText(messageBody);
        mBuilder.setAutoCancel(true);
        mBuilder.mNumber = 1;//get your number of notification from where you have save notification

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notify_id, mBuilder.build());

    }
    private void aaa(String messageBody){
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(this);
        mCompatBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mCompatBuilder.setTicker("NotificationCompat.Builder");
        mCompatBuilder.setWhen(System.currentTimeMillis());
        mCompatBuilder.setNumber(10);
        mCompatBuilder.setContentTitle("NotificationCompat.Builder Title");
        mCompatBuilder.setContentText("NotificationCompat.Builder Massage");
        mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mCompatBuilder.setContentIntent(pendingIntent);
        mCompatBuilder.setAutoCancel(true);



        nm.notify(222, mCompatBuilder.build());

    }
    private void inbox(String messageBody){


        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("msg"+(msg-1), messageBody);
        editor.commit();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(this);

        Notification.InboxStyle style= new Notification.InboxStyle();


        Notification.InboxStyle style1=new Notification.InboxStyle();

        for(int i=0;i<msg;i++){
            style1.addLine(mPref.getString("msg"+i,"??"));
        }

        Notification notification = new Notification.Builder(this)
                .setContentTitle(msg+"New mails from")
                .setContentText("aaaa")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(style1.setSummaryText(msg-1+"more"))
                .setContentText("asd")
                .build();


        nm.notify(222,notification);





    }

}
