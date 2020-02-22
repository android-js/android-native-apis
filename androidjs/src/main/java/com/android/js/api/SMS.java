package com.android.js.api;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;

public class SMS extends ReactContextBaseJavaModule {
    private SmsManager smsManager;
    private Activity activity;
    private ReactApplicationContext reactContext;

    public SMS(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = getCurrentActivity();
        this.smsManager = SmsManager.getDefault();
    }

    @ReactMethod (isBlockingSynchronousMethod = true)
    public String sendSMS(String number, String message) {
        try {
            ArrayList<String> messageParts = this.smsManager.divideMessage(message);
            this.smsManager.sendMultipartTextMessage(number, null, messageParts, null, null);
            return "{\"error\": false, \"msg\": \"Message Sent\"}";
        } catch (Exception e){
            e.printStackTrace();
            return "{\"error\": true, \"msg\": \"" + e.getMessage().toString() + "\"}";
        }
    }

    @Override
    public String getName() {
        return "SMS";
    }
}
