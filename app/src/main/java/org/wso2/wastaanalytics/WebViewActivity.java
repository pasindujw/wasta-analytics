/*
 *
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.wastaanalytics;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionEntry;
import android.content.RestrictionsManager;
import android.content.res.Configuration;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import org.wso2.wastaanalytics.Util.Constants;
import org.wso2.wastaanalytics.Util.Preference;
import java.util.List;

public class WebViewActivity extends Activity {
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebView webView;
    private MyWebViewClient myWebViewClient;
    String url;
    RestrictionsManager manager;
    private Bundle savedInstanceState;

    public void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);

        initRestrictionManager();

        setContentView(R.layout.webview);
        myWebViewClient = new MyWebViewClient();

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(myWebViewClient);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_EXTRA_URL)) {
            url = intent.getStringExtra(Constants.INTENT_EXTRA_URL);
            webView.loadUrl(url);
        } else {
            webView.loadUrl(Constants.HTTPS_LOGIN_URL);
        }
    }

    /**
     * This method initiates the restriction manager which is responsible of receving app restrictions
     * pushed by the administrator through IoT Agent.
     */
    private void initRestrictionManager() {
        manager =(RestrictionsManager) this.getSystemService(Context.RESTRICTIONS_SERVICE);
        IntentFilter restrictionsFilter =
                new IntentFilter(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED);

        BroadcastReceiver restrictionsReceiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                resolveRestrictions();
            }
        };
        registerReceiver(restrictionsReceiver, restrictionsFilter);
    }

    @Override
    public void onBackPressed() {
        if(webView.getUrl().equals(Constants.HTTPS_LOGIN_URL)){
            Toast.makeText(this,"Enter credentials or bump with your NFC App.",Toast.LENGTH_SHORT).show();
        }
        else if (webView.canGoBack()) {
            webView.goBack();
        }
        else {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl(Constants.HTTPS_LOGIN_URL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        webView.clearCache(true);
        webView.destroy();
        super.onDestroy();
    }

    /**
     * This method avoids web view getting refresh everytime the device is rotated.
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    /**
     * WebView Client overriding the methods to handle SSL certificate errors.
     */
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            webView.clearSslPreferences();
        }
    }

    private void resolveRestrictions() {
        Bundle restrictions = manager.getApplicationRestrictions();
        List<RestrictionEntry> entries = manager.getManifestRestrictions(
                this.getApplicationContext().getPackageName());
        for (RestrictionEntry entry : entries) {
            String key = entry.getKey();
            Log.d(TAG, "Restriction received key: " + key +" entry: " + entry);
            if (key.equals(Constants.AppRestrictions.KEYWORD_URL)) {
                updateCustomURL(entry, restrictions);
            }
        }
    }

    /**
     * This method received the custom URL sent via app restriction operation and load it in the web view.
     * @param entry
     * @param restrictions
     */
    private void updateCustomURL(RestrictionEntry entry, Bundle restrictions) {
        String customURL;
        if (restrictions == null || !restrictions.containsKey(Constants.AppRestrictions.KEYWORD_URL)) {
            customURL = entry.getSelectedString();
        } else {
            customURL = restrictions.getString(Constants.AppRestrictions.KEYWORD_URL);
        }
        String savedUrl = Preference.getString(this,Constants.RESTRTICTION_URL);
        if(customURL != savedUrl) {
            Preference.putString(this, Constants.RESTRTICTION_URL, customURL);
            webView.loadUrl(customURL);
            Log.d(TAG, "Webview is loaded with a cumstomr URL : " + customURL);
        }
    }
}