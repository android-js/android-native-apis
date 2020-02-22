package com.android.js.api;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Contact extends ReactContextBaseJavaModule {
    private Activity activity;
    private ReactApplicationContext reactContext;
    private Cursor cursor;
    private JSONArray contacts;

    public Contact(@Nullable Activity activity, @Nullable ReactApplicationContext reactContext){
        super(reactContext);
        this.activity = activity;
        this.reactContext = reactContext;
        if(activity == null) this.activity = reactContext.getCurrentActivity();
        contacts = new JSONArray();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getAllContacts(Boolean force) throws JSONException {
        if(this.contacts.length() == 0 || force) {
            this.cursor = this.activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (this.cursor.moveToNext()) {
                JSONObject contact = new JSONObject();
                contact.put("name", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.put("phone_number", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                String id = this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor ce = this.activity.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);

                if(ce != null && ce.moveToFirst()) {
                    contact.put("email", ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                    ce.close();
                }
                this.contacts.put(contact);
            }
            this.cursor.close();
        }
        return this.contacts.toString();
    }

    @ReactMethod
    public String addContact(String name, String number, String email){
        ArrayList< ContentProviderOperation > ops = new ArrayList <ContentProviderOperation> ();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        if (name != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());
        }
        if (number != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }
        if (email != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        try {
            this.activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            this.getAllContacts(true);
            return "{\"error\": false, \"msg\": \"contact added\"}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\"error\": true, \"err\": \""+ e.toString() + "\"}";
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getContactByName(String name) throws JSONException{
        if (this.contacts.length() == 0) this.getAllContacts(false);
        for(int i = 0; i < this.contacts.length(); i++){
            if(this.contacts.getJSONObject(i).getString("name").equals(name)) return this.contacts.getJSONObject(i).toString();
        }
        return "{\"error\": false, \"msg\": \"record not found\"}";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getContactsCount() throws JSONException{
        if (this.contacts.length() == 0) this.getAllContacts(false);
        return this.contacts.length();
    }

    @Override
    public String getName(){
        return "Contact";
    }
}
