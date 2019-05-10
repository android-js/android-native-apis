package com.android.js.api;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.android.js.R;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Notification extends ReactContextBaseJavaModule {
    private Activity activity;
    private NotificationCompat.Builder notification_builder;
    private NotificationManager notification_manager;
    private NotificationCompat.InboxStyle inbox_style;
    private int iconId;
    private ReactApplicationContext reactContext;

    public Notification(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext, int iconId){
        super(reactContext);
        this.activity = activity;
        this.notification_manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        this.iconId = iconId;
        this.reactContext = reactContext;
    }
//    public void setSmallIcon(){
//
//    }

    @ReactMethod
    public void initNotification(String title, String msg){
        this.notification_builder = new NotificationCompat.Builder(this.activity);
        this.notification_builder.setContentTitle(title);
        this.notification_builder.setContentText(msg);
        this.notification_builder.setSmallIcon(this.iconId);

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
