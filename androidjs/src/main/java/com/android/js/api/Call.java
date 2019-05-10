package com.android.js.api;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nullable;

public class Call extends ReactContextBaseJavaModule {
    private Intent callIntent;
    private Activity activity;
    private ReactApplicationContext reactContext;

    public Call(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.callIntent = new Intent(Intent.ACTION_CALL);
        this.activity = activity;
        this.reactContext = reactContext;

    }

    @ReactMethod
    public void makeCall(String number){
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity, new String[] {Manifest.permission.CALL_PHONE}, 2);
        }
        this.activity.startActivity(this.callIntent);
    }

    @Override
    public String getName(){
        return "Call";
    }
}
