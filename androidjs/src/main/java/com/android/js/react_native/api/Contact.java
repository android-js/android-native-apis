package com.android.js.react_native.api;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;

public class Contact extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private com.android.js.api.Contact contact;

    public Contact(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.contact = new com.android.js.api.Contact(reactContext.getCurrentActivity());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getAllContacts(Boolean force) throws JSONException {
        return this.contact.getAllContacts(force);
    }

    @ReactMethod
    public String addContact(String name, String number, String email) {
        return this.contact.addContact(name, number, email);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getContactByName(String name) throws JSONException {
        return this.contact.getContactByName(name);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getContactsCount() throws JSONException {
        return this.contact.getContactsCount();
    }

    @Override
    public String getName() {
        return "Contact";
    }
}
