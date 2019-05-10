package com.android.js.webview;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.android.js.api.Call;
import com.android.js.api.Hotspot;
import com.android.js.api.Notification;
import com.android.js.api.Toast;
import com.android.js.api.Wifi;
import com.android.js.webview.AndroidJSWebviewActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;

import static android.os.Environment.DIRECTORY_ALARMS;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_MOVIES;
import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_NOTIFICATIONS;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.DIRECTORY_PODCASTS;
import static android.os.Environment.DIRECTORY_RINGTONES;

public class JavaWebviewBridge {
    private AndroidJSWebviewActivity activity;
    private WebView myWebView;
    private Notification notification;
    private Call call;
    private Wifi wifi;
    private Hotspot hotspot;
    private int iconId;
    private ReactApplicationContext reactContext;

    public JavaWebviewBridge(AndroidJSWebviewActivity activity, @Nullable ReactApplicationContext reactContext, WebView myWebView, int iconId){
        this.activity = activity;
        this.myWebView = myWebView;
        this.notification = new Notification(activity, reactContext,iconId);
        this.call = new Call(activity, reactContext);
        this.wifi = new Wifi(activity, reactContext);
        this.hotspot = new Hotspot(activity, reactContext);
        this.iconId = iconId;
        this.reactContext = reactContext;
    }

    @JavascriptInterface
    public String helloWorld(){
        System.out.println("Java IPC Works");
        return "Hello World";
    }
    @JavascriptInterface
    public String getPath(String name) {
        if (name.equals("root")) {
            return Environment.getRootDirectory().getPath();
        } else if (name.equals("data")) {
            return Environment.getDataDirectory().getPath();
        } else if (name.equals("cache")) {
            return Environment.getDownloadCacheDirectory().getPath();
        } else if (name.equals("storage")) {
            return Environment.getExternalStorageDirectory().getPath();
        } else if (name.equals("alarms")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_ALARMS).getPath();
        } else if (name.equals("dcim")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getPath();
        } else if (name.equals("downloads")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
        } else if (name.equals("movies")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_MOVIES).getPath();
        } else if (name.equals("music")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).getPath();
        } else if (name.equals("notifications")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_NOTIFICATIONS).getPath();
        } else if (name.equals("pictures")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath();
        } else if (name.equals("podcasts")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_PODCASTS).getPath();
        } else if (name.equals("ringtones")) {
            return Environment.getExternalStoragePublicDirectory(DIRECTORY_RINGTONES).getPath();
        } else if (name.equals("appData")) {
            return activity.getFilesDir().getPath();
        } else if (name.equals("userData")) {
            return activity.getExternalFilesDir(null).getPath();
        } else {
            return "-1";
        }
    }

    @JavascriptInterface
    public void initNotification(String title, String msg){
        notification.initNotification(title, msg);
    }

    @JavascriptInterface
    public void showNotification(int id){
        notification.showNotification(id);
    }

    @JavascriptInterface
    public void initBigNotification(String title, String [] msg){
        notification.initBigNotification(title,  msg);
    }

    @JavascriptInterface
    public void showToast(String text, int duration){
        Toast.showToast(this.activity, text, duration);
    }

    @JavascriptInterface
    public void makeCall(String number){
        call.makeCall(number);
    }

    @JavascriptInterface
    public void enableWifi(){
        wifi.enableWifi();
    }

    @JavascriptInterface
    public void disableWifi(){
        wifi.disableWifi();
    }

    @JavascriptInterface
    public void disconnectWifi(){
        wifi.disconnectWifi();
    }

    @JavascriptInterface
    public int getWifiState(){
        return wifi.getWifiState();
    }

    @JavascriptInterface
    public boolean isWifiEnabled(){
        return wifi.isWifiEnabled();
    }

    @JavascriptInterface
    public String getWifiScanResults() throws JSONException {
        return wifi.getWifiScanResults();
    }

    @JavascriptInterface
    public void connectWifi(String ssid, String password){
        wifi.connectWifi(ssid, password);
    }

    @JavascriptInterface
    public void enableHotspot(String ssid){
        try {
            hotspot.enableHotspot(ssid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void disableHotspot(){
        try{
            hotspot.disableHotspot();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public boolean isHotspotEnabled(){
        return hotspot.isHotspotEnabled();
    }
}
