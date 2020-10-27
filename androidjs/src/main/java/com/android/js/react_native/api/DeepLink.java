package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class DeepLink extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.DeepLink deepLink;

    public DeepLink(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.deepLink = new com.android.js.api.DeepLink(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getLink(){
        return this.deepLink.getLink();
    }

    @Override
    public String getName() {
        return "DeepLink";
    }
}
