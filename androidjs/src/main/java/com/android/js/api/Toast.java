package com.android.js.api;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Toast extends ReactContextBaseJavaModule {
    private Activity activity;
    private ReactApplicationContext reactContext;

    public Toast(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.activity = activity;
        if(activity == null) this.activity = getCurrentActivity();
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void showToast(String text, int duration){
        if(duration > 1) duration = 1;
        if(duration < 0) duration = 0;
        android.widget.Toast toast = android.widget.Toast.makeText(this.activity, text, duration);
        toast.show();
    }

    @Override
    public String getName(){
        return "Toast";
    }
}
