package com.android.js.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.facebook.infer.annotation.SuppressNullFieldAccess;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Hotspot extends ReactContextBaseJavaModule {
    private WifiManager wifi_manager;
    private Activity activity;
    private WifiManager.LocalOnlyHotspotReservation local_reservation;
    private ReactApplicationContext reactContext;

    public Hotspot(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = getCurrentActivity();
        this.wifi_manager = (WifiManager) ((this.activity != null) ? this.activity : this.reactContext).getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @ReactMethod
    public void enableHotspot(String ssid) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wifi_manager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                    super.onStarted(reservation);
//                Timber.d("Wifi Hotspot is on now , reservation is : %s", reservation.toString());
                    local_reservation = reservation;
                    String key = local_reservation.getWifiConfiguration().preSharedKey;
                    String ussid = local_reservation.getWifiConfiguration().SSID;

                }

                @Override
                public void onStopped() {
                    super.onStopped();
//                Timber.d("onStopped: ");
                }

                @Override
                public void onFailed(int reason) {
                    super.onFailed(reason);
//                Timber.d("onFailed: ");
                }
            }, new Handler());
        }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            System.out.println("trying to start hotspot");
            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = ssid;
            Method method = this.wifi_manager.getClass().getDeclaredMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            method.invoke(this.wifi_manager, conf, true);
        }
    }

    @ReactMethod
    public void disableHotspot() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (this.local_reservation != null) {
                this.local_reservation.close();
            }
        }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
//            WifiConfiguration conf = new WifiConfiguration();
//            conf.SSID = ssid;
            Method method = this.wifi_manager.getClass().getDeclaredMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            method.invoke(this.wifi_manager, null, false);
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isHotspotEnabled(){
        try {
            Method method = this.wifi_manager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(this.wifi_manager);
        }
        catch (Throwable ignored) {}
        return false;
    }

    @Override public String getName(){
        return "HotSpot";
    }
}
