package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.json.JSONException;

public class App extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.App app;

    public App(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.app = new com.android.js.api.App(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPath(String name) {
        return app.getPath(name);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public static String exec(String[] cmdarray) throws JSONException { 
        return app.exec(cmdarray);
    }

    @Override
    public String getName() {
        return "Call";
    }
}
