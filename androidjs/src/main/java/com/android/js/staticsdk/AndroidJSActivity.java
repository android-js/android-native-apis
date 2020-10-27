package com.android.js.staticsdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.js.common.JavaWebviewBridge;

public class AndroidJSActivity extends AppCompatActivity {

    public WebView myWebView;

    // override back button to webview back button

    @Override
    public void onBackPressed() {
        if (this.myWebView.canGoBack()) {
            this.myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void configureWebview(int iconId){
        this.myWebView.addJavascriptInterface(new JavaWebviewBridge(this, this.myWebView, iconId, "com.android.js.staticsdk.MainActivity"), "android");


        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.getSettings().setDomStorageEnabled(true);
        this.myWebView.getSettings().setAllowFileAccess(true);
        this.myWebView.setWebContentsDebuggingEnabled(true);
        this.myWebView.setWebViewClient(new WebViewClient());
        this.myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.myWebView.getSettings().setSupportMultipleWindows(true);

        this.myWebView.loadUrl("file:///android_asset/myapp/views/index.html");



        // entertain webview camera request

        this.myWebView.setWebChromeClient(new WebChromeClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final android.webkit.PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg)
            {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Context context = view.getContext();
                System.out.println("req:");
                System.out.println(result.toString());
                System.out.println(data.toString());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                context.startActivity(browserIntent);
                return false;
            }
        });
    }
}
