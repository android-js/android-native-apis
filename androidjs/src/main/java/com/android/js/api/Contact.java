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
    public String getAllContacts() throws JSONException {
        if(this.contacts.length() == 0) {
            this.cursor = this.activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (this.cursor.moveToNext()) {
                JSONObject contact = new JSONObject();
                contact.put("name", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.put("phone_number", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contact.put("email", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                contact.put("company", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)));
                contact.put("job_title", this.cursor.getString(this.cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE)));
                this.contacts.put(contact);
            }
            this.cursor.close();
        }
        return this.contacts.toString();
    }

    @ReactMethod
    public void addContact(String name, String number, String email, String company, String job_title) throws OperationApplicationException, RemoteException {
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
        if (company != null && job_title != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, job_title)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }
        this.activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getContactByName(String name) throws JSONException{
        if (this.contacts.length() == 0) this.getAllContacts();
        for(int i = 0; i < this.contacts.length(); i++){
            if(this.contacts.getJSONObject(i).has(name)) return this.contacts.getJSONObject(i).toString();
        }
        return "Not Found";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getContactsCount() throws JSONException{
        if (this.contacts.length() == 0) this.getAllContacts();
        return this.contacts.length();
    }

    @Override
    public String getName(){
        return "Contact";
    }
}
