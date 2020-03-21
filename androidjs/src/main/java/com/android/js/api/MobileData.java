package com.android.js.api;

import android.app.Activity;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nullable;

public class MobileData extends ReactContextBaseJavaModule {
    private Activity activity;
    private ReactApplicationContext reactContext;
    private TelephonyManager telephonyManager;

    public MobileData(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = reactContext.getCurrentActivity();
        this.telephonyManager = (TelephonyManager) this.activity.getSystemService(this.activity.TELEPHONY_SERVICE);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isEnabled() {
        boolean flag = false;
        if (this.telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
                flag = Settings.Global.getInt(this.activity.getContentResolver(), "mobile_data", 1) == 1;
            else
                flag = Settings.Secure.getInt(this.activity.getContentResolver(), "mobile_data", 1) == 1;
        }
        return flag;
    }

    @Override
    public String getName() {
        return "MobileData";
    }
}
