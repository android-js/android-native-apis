package com.android.js.webview;

import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.android.js.api.App;
import com.android.js.api.Call;
import com.android.js.api.Contact;
import com.android.js.api.Hotspot;
import com.android.js.api.Notification;
import com.android.js.api.Toast;
import com.android.js.api.Wifi;
import com.facebook.react.bridge.ReactApplicationContext;

import org.json.JSONException;

public class JavaWebviewBridge {
    private AndroidJSActivity activity;
    private WebView myWebView;
    private Notification notification;
    private Call call;
    private Wifi wifi;
    private Hotspot hotspot;
    private Toast toast;
    private App app;
    private Contact contact;
    private int iconId;
    private ReactApplicationContext reactContext;

    public JavaWebviewBridge(AndroidJSActivity activity, @Nullable ReactApplicationContext reactContext, WebView myWebView, int iconId){
        this.activity = activity;
        this.myWebView = myWebView;
        this.notification = new Notification(activity, reactContext,iconId);
        this.call = new Call(activity, reactContext);
        this.wifi = new Wifi(activity, reactContext);
        this.hotspot = new Hotspot(activity, reactContext);
        this.toast = new Toast(activity, reactContext);
        this.app = new App(activity, reactContext);
        this.contact = new Contact(activity, reactContext);
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
        return app.getPath(name);
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
        toast.showToast(text, duration);
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

    @JavascriptInterface
    public String getAllContacts() throws JSONException {
        return this.contact.getAllContacts(false);
    }
    @JavascriptInterface
    public String getContactByName(String name) throws JSONException {
        return this.contact.getContactByName(name);
    }
    @JavascriptInterface
    public int getContactsCount() throws JSONException {
        return this.contact.getContactsCount();
    }
    @JavascriptInterface
    public String addContact(String name, String number, String email) {
        return this.contact.addContact(name, number, email);
    }
}
