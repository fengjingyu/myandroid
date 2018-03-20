package com.jingyu.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jingyu.app.R;
import com.jingyu.app.middle.MyBaseActivity;

public class WebViewActivity extends MyBaseActivity {

    private WebView webView;
    public static final String KEY_URL = "KEY_URL";
    public static final String KEY_TITLE = "KEY_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }

    public void initWidgets() {
        webView = getViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(getUrl());
    }

    private String getUrl() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra(KEY_URL);
        }
        return "";
    }

    private String getTitleText() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra(KEY_TITLE);
        }
        return "";
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public static void actionStart(Activity activity, String url, String title) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);
    }
}
