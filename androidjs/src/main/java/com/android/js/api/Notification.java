package com.android.js.api;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.android.js.R;

public class Notification {
    private Activity activity;
    private NotificationCompat.Builder notification_builder;
    private NotificationManager notification_manager;
    private NotificationCompat.InboxStyle inbox_style;
    private int iconId;

    public Notification(Activity activity, int iconId){
        this.activity = activity;
        this.notification_manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        this.iconId = iconId;
    }
//    public void setSmallIcon(){
//
//    }

    public void initNotification(String title, String msg){
        this.notification_builder = new NotificationCompat.Builder(this.activity);
        this.notification_builder.setContentTitle(title);
        this.notification_builder.setContentText(msg);
        this.notification_builder.setSmallIcon(this.iconId);

    }

    public void showNotification(int id){
        this.notification_manager.notify(id, notification_builder.build());
    }

    public void initBigNotification(String title, String [] msg){
        this.inbox_style = new NotificationCompat.InboxStyle();
        inbox_style.setBigContentTitle(title);
        for(int i = 0; i < msg.length && i < 6; i++){
            inbox_style.addLine(msg[i]);
        }
        notification_builder.setStyle(inbox_style);
    }
}
