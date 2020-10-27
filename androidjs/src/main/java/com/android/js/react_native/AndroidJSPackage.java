package com.android.js.react_native;


import android.app.Activity;

import com.android.js.react_native.api.App;
import com.android.js.react_native.api.Call;
import com.android.js.react_native.api.Contact;
import com.android.js.react_native.api.DeepLink;
import com.android.js.react_native.api.Hotspot;
import com.android.js.react_native.api.Location;
import com.android.js.react_native.api.MobileData;
import com.android.js.react_native.api.Notification;
import com.android.js.react_native.api.SMS;
import com.android.js.react_native.api.Toast;
import com.android.js.react_native.api.Wifi;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndroidJSPackage implements ReactPackage {

    private int iconId;

    public AndroidJSPackage (int iconId){
        this.iconId = iconId;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext){
        List<NativeModule> modules = new ArrayList<>();
        modules.add((NativeModule) new App(reactContext));
        modules.add(new Call(reactContext));
        modules.add(new Hotspot(reactContext));
        modules.add(new Notification(reactContext, this.iconId));
        modules.add(new Toast(reactContext));
        modules.add(new Wifi(reactContext));
        modules.add(new Contact(reactContext));
        modules.add(new DeepLink(reactContext));
        modules.add(new SMS(reactContext));
        modules.add(new Location(reactContext));
        modules.add(new MobileData(reactContext));

        return modules;
    }
}
