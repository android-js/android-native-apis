package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Location extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Location location;

    public Location(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.location = new com.android.js.api.Location(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getLocation() {
        return this.location.getLocation();
    }

    @Override
    public String getName() {
        return "Location";
    }
}
