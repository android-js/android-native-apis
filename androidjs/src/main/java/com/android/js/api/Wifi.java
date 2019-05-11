package com.android.js.api;

import android.app.Activity;
import android.content.Context;
import android.net.NetworkSpecifier;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Wifi extends ReactContextBaseJavaModule {
    private WifiManager main_wifi;
    private Activity activity;
    private ReactApplicationContext reactContext;

    public Wifi(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = getCurrentActivity();
        main_wifi = (WifiManager) ((this.activity != null) ? this.activity : this.reactContext).getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @ReactMethod
    public void enableWifi(){
        if(! this.main_wifi.isWifiEnabled())
            main_wifi.setWifiEnabled(true);
    }

    @ReactMethod
    public void disableWifi(){
        if(this.main_wifi.isWifiEnabled())
            main_wifi.setWifiEnabled(false);
    }

    @ReactMethod
    public void disconnectWifi(){
        main_wifi.disconnect();
    }

    @ReactMethod
    public int getWifiState(){
        return main_wifi.getWifiState();
    }

    @ReactMethod
    public boolean isWifiEnabled(){
        return main_wifi.isWifiEnabled();
    }

    @ReactMethod
    public String getWifiScanResults() throws JSONException {
//        System.out.println("wifi api called");
        List<ScanResult> res = main_wifi.getScanResults();
        JSONArray final_res = new JSONArray();
        for(int i = 0; i < res.size(); i++) {
            JSONObject item = new JSONObject();
            item.put("SSID", res.get(i).SSID);
            item.put("BSSID", res.get(i).BSSID);
            item.put("capabilities", res.get(i).capabilities);
            item.put("level", res.get(i).level);
            item.put("frequency", res.get(i).frequency);
            item.put("timestamp", res.get(i).timestamp);
//            final_res[i].put("passpoint", res.get(i).isPasspointNetwork());
//            final_res[i].put("ChannelBandwidth", res.get(i).channelWidth);
//            final_res[i].put("centerFreq0", res.get(i).centerFreq0);
//            final_res[i].put("centerFreq1", res.get(i).centerFreq1);
//            final_res[i].put("80211mcResponder", res.get(i).is80211mcResponder());
//            System.out.println(res.get(i));
            final_res.put(item);
        }
        return final_res.toString();
    }

    @ReactMethod
    public void connectWifi(String ssid, String password){
        System.out.println("Connect Called " + ssid);
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", ssid);
        if(password.equals(""))
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        else
            conf.preSharedKey = String.format("\"%s\"", password);
        int netId = main_wifi.addNetwork(conf);
        main_wifi.disconnect();
        main_wifi.enableNetwork(netId, true);
        main_wifi.reconnect();
    }

    @Override
    public String getName(){
        return "Wifi";
    }
}
