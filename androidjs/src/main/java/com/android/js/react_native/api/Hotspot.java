package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.lang.reflect.InvocationTargetException;

public class Hotspot extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Hotspot hotspot;

    public Hotspot(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.hotspot = new com.android.js.api.Hotspot(reactContext.getCurrentActivity());
    }

    @ReactMethod
    public void enableHotspot(String ssid) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.hotspot.enableHotspot(ssid);
    }

    @ReactMethod
    public void disableHotspot() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.hotspot.disableHotspot();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isHotspotEnabled(){
        return this.hotspot.isHotspotEnabled();
    }

    @Override
    public String getName() {
        return "HotSpot";
    }
}
