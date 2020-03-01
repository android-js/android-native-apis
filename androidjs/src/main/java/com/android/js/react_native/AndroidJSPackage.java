package com.android.js.react_native;


import android.app.Activity;

import com.android.js.api.App;
import com.android.js.api.Call;
import com.android.js.api.Contact;
import com.android.js.api.DeepLink;
import com.android.js.api.Hotspot;
import com.android.js.api.Location;
import com.android.js.api.Notification;
import com.android.js.api.SMS;
import com.android.js.api.Toast;
import com.android.js.api.Wifi;
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
        modules.add(new App(null, reactContext));
        modules.add(new Call(null, reactContext));
        modules.add(new Hotspot(null, reactContext));
        modules.add(new Notification(null, reactContext, this.iconId));
        modules.add(new Toast(null, reactContext));
        modules.add(new Wifi(null, reactContext));
        modules.add(new Contact(null, reactContext));
        modules.add(new DeepLink(null, reactContext));
        modules.add(new SMS(null, reactContext));
        modules.add(new Location(null, reactContext));

        return modules;
    }
}
