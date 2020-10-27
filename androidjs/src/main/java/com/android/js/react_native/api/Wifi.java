package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;

public class Wifi extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Wifi wifi;

    public Wifi(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.wifi = new com.android.js.api.Wifi(reactContext.getCurrentActivity());
    }

    @ReactMethod
    public void enableWifi(){
        this.wifi.enableWifi();
    }

    @ReactMethod
    public void disableWifi(){
        this.wifi.disableWifi();
    }

    @ReactMethod
    public void disconnectWifi() {
        this.wifi.disconnectWifi();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getWifiState(){
        return this.wifi.getWifiState();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isWifiEnabled(){
        return this.wifi.isWifiEnabled();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getWifiScanResults() throws JSONException {
        return this.wifi.getWifiScanResults();
    }

    @ReactMethod
    public void connectWifi(String ssid, String password){
        this.wifi.connectWifi(ssid, password);
    }

    @Override
    public String getName() {
        return "Wifi";
    }
}
