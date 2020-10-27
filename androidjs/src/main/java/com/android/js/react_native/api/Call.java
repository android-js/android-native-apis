package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Call extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Call call;

    public Call(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.call = new com.android.js.api.Call(reactContext.getCurrentActivity());
    }

    @ReactMethod
    public void makeCall(String number){
        this.call.makeCall(number);
    }

    @Override
    public String getName() {
        return "Call";
    }
}
