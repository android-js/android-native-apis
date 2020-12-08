package com.android.js.common;


import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.android.js.api.App;
import com.android.js.api.Call;
import com.android.js.api.Contact;
import com.android.js.api.DeepLink;
import com.android.js.api.Hotspot;
import com.android.js.api.Location;
import com.android.js.api.MobileData;
import com.android.js.api.Notification;
import com.android.js.api.SMS;
import com.android.js.api.Toast;
import com.android.js.api.Wifi;

import org.json.JSONException;

public class JavaWebviewBridge {
    private Activity activity;
    private WebView myWebView;
    private Notification notification;
    private Call call;
    private Wifi wifi;
    private Hotspot hotspot;
    private Toast toast;
    private App app;
    private Contact contact;
    private DeepLink deepLink;
    private SMS sms;
    private Location location;
    private MobileData mobileData;
    private int iconId;
    private String className;

    public JavaWebviewBridge(Activity activity, WebView myWebView, int iconId, String className){
        this.activity = activity;
        this.myWebView = myWebView;
        this.notification = new Notification(activity, iconId, className);
        this.call = new Call(activity);
        this.wifi = new Wifi(activity);
        this.hotspot = new Hotspot(activity);
        this.toast = new Toast(activity);
        this.app = new App(activity);
        this.contact = new Contact(activity);
        this.deepLink = new DeepLink(activity);
        this.sms = new SMS(activity);
        this.location = new Location(activity);
        this.mobileData = new MobileData(activity);
        this.iconId = iconId;
        this.className = className;
        this.className = className;
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
    public static String exec(String[] cmdarray) throws JSONException { 
        return app.exec(cmdarray);
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

    @JavascriptInterface
    public String getDeepLink(){
        return this.deepLink.getLink();
    }

    @JavascriptInterface
    public String sendSMS(String number, String message) {
        return this.sms.sendSMS(number, message);
    }

    @JavascriptInterface
    public String getLocation(){
        return this.location.getLocation();
    }

    @JavascriptInterface
    public boolean isMobileDataEnabled() {
        return this.mobileData.isEnabled();
    }
}
