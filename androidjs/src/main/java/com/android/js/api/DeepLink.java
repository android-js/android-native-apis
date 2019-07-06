package com.android.js.api;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class DeepLink extends ReactContextBaseJavaModule {
    private Activity activity;
    private ReactApplicationContext reactContext;

    public DeepLink(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(this.activity == null) this.activity = getCurrentActivity();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getLink(){
        Intent intent = this.activity.getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if(data != null)
            return data.toString();
        else return "-1";
    }

    @Override
    public String getName(){
        return "DeepLink";
    }
}
