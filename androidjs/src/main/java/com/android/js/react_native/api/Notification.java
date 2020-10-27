package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Notification extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Notification notification;
    private int iconId;

    public Notification(ReactApplicationContext reactContext, int iconId) {
        super(reactContext);
        this.reactContext = reactContext;
        this.iconId = iconId;
        this.notification = new com.android.js.api.Notification(reactContext.getCurrentActivity(), this.iconId, "com.android.js.react_native.MainActivity");
    }

    @ReactMethod
    public void initNotification(String title, String msg){
        this.notification.initNotification(title, msg);
    }

    @ReactMethod
    public void showNotification(int id){
        this.notification.showNotification(id);
    }

    @ReactMethod
    public void initBigNotification(String title, String [] msg){
        this.notification.initBigNotification(title, msg);
    }

    @Override
    public String getName() {
        return "Notification";
    }
}
