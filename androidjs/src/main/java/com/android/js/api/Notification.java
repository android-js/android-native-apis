package com.android.js.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.android.js.R;
import com.android.js.webview.AndroidJSActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Notification extends ReactContextBaseJavaModule {
    private Activity activity;
    private NotificationCompat.Builder notification_builder;
    private NotificationManager notification_manager;
    private NotificationCompat.InboxStyle inbox_style;
    private NotificationChannel notification_channel;
    private Intent intent;
    private int iconId;
    private ReactApplicationContext reactContext;

    @SuppressLint("PrivateApi")
    public Notification(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext, int iconId){
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = getCurrentActivity();
        this.notification_manager = (NotificationManager) ((this.activity != null) ? this.activity:this.reactContext).getSystemService(Context.NOTIFICATION_SERVICE);
        this.iconId = iconId;
        try {
            this.intent = new Intent(this.activity, Class.forName("com.android.js.webview.MainActivity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notification_channel = new NotificationChannel("androidjs", "androidjs.notifications", NotificationManager.IMPORTANCE_DEFAULT);
            this.notification_channel.setDescription("androidjs.notification.channel");
            this.notification_manager.createNotificationChannel(this.notification_channel);
        }
    }
//    public void setSmallIcon(){
//
//    }

    @ReactMethod
    public void initNotification(String title, String msg){
        PendingIntent pendingIntent = PendingIntent.getActivity(this.activity, 0, this.intent, 0);

        this.notification_builder = new NotificationCompat.Builder((this.activity != null) ? this.activity:this.reactContext, "androidjs");
        this.notification_builder.setContentTitle(title);
        this.notification_builder.setContentText(msg);
        this.notification_builder.setSmallIcon(this.iconId);
        this.notification_builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        this.notification_builder.setContentIntent(pendingIntent);
        this.notification_builder.setAutoCancel(true);


    }

    @ReactMethod
    public void showNotification(int id){
        this.notification_manager.notify(id, notification_builder.build());
    }

    @ReactMethod
    public void initBigNotification(String title, String [] msg){
        this.inbox_style = new NotificationCompat.InboxStyle();
        inbox_style.setBigContentTitle(title);
        for(int i = 0; i < msg.length && i < 6; i++){
            inbox_style.addLine(msg[i]);
        }
        notification_builder.setStyle(inbox_style);
    }

    @Override
    public String getName(){
        return "Notification";
    }
}
