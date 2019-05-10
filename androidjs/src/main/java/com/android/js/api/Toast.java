package com.android.js.api;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Toast extends ReactContextBaseJavaModule {
    public Toast(@Nullable ReactApplicationContext rectContext){
        super(rectContext);
    }

    @ReactMethod
    public static void showToast(Activity activity, String text, int duration){
        if(duration > 1) duration = 1;
        if(duration < 0) duration = 0;
        android.widget.Toast toast = android.widget.Toast.makeText(activity, text, duration);
        toast.show();
    }

    @Override
    public String getName(){
        return "Toast";
    }
}
