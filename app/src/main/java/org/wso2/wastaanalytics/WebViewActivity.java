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
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.wso2.wastaanalytics.Util.Constants;

public class WebViewActivity extends Activity {
    private WebView webView;
    private MyWebViewClient myWebViewClient;
    String url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        myWebViewClient = new MyWebViewClient();
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(myWebViewClient);
        Intent intent = getIntent();
        if(intent.hasExtra("url")){
            url = intent.getStringExtra("url");
            webView.loadUrl(url);
        }
        else {
            webView.loadUrl(Constants.HTTPS_LOGIN_URL);
        }
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
    }

    @Override
    protected void onDestroy() {
        webView.clearCache(true);
        webView.destroy();
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
            webView.clearSslPreferences();

        }
    }

}