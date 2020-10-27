package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Toast extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Toast toast;

    public Toast(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.toast = new com.android.js.api.Toast(reactContext.getCurrentActivity());
    }

    @ReactMethod
    public void showToast(String text, int duration){
        this.toast.showToast(text, duration);
    }

    @Override
    public String getName() {
        return "Toast";
    }
}
