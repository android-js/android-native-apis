package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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

    @Override
    public String getName() {
        return "Call";
    }
}
