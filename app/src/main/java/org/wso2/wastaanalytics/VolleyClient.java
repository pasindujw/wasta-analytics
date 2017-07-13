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
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.wastaanalytics.Util.Constants;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * This method handles HTTP request communication with WebApp.
 */
public class VolleyClient {
    private static final String TAG = VolleyClient.class.getSimpleName();

    public void getURL(final Context context, String sharedData){
        final String data = sharedData;
        RequestQueue MyRequestQueue =Volley.newRequestQueue(context);
        String url = Constants.URL_TO_POST;

        String[] separated = data.split(Constants.TOKEN_SPITTER);
        final JSONObject requestBody = new JSONObject();
        try {
            requestBody.put(Constants.JSONElements.TOKEN, separated[0]);
            requestBody.put(Constants.JSONElements.USER, separated[1]);
        } catch (JSONException e) {
            Log.e(TAG, "Exception in getURL: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d(TAG, "Response received from server: " +response);
                if(!response.isNull(Constants.JSONElements.URL)) {
                    try {
                        String url = response.getString(Constants.JSONElements.URL);
                        url = Constants.HTTPS_HOST_URL + url;
                        //Restarting the webview.
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        Log.d(TAG, "Redirecting to the URL: " + url);
                        intent.putExtra(Constants.INTENT_EXTRA_URL, url);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception occurred when receiving response." + e);
                    }
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.d(TAG,error.toString());
            }
        }) ;
        MyRequestQueue.add(jsonObjectRequest);
    }

}


