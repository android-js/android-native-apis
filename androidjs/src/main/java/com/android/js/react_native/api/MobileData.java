package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class MobileData extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.MobileData mobileData;

    public MobileData(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.mobileData = new com.android.js.api.MobileData(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isEnabled() {
        return this.mobileData.isEnabled();
    }

    @Override
    public String getName() {
        return "MobileData";
    }
}
