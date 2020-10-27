package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class SMS extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.SMS sms;

    public SMS(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.sms = new com.android.js.api.SMS(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String sendSMS(String number, String message) {
        return this.sms.sendSMS(number, message);
    }


    @Override
    public String getName() {
        return "SMS";
    }
}
