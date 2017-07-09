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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * This method handles HTTP request communication with WebApp.
 */
public class VolleyClient {
    private static final String TAG = VolleyClient.class.getSimpleName();

    public void getURL(final Context context, String tk){
        final String token = tk;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        String url = "http://www.mocky.io/v2/5960ef961000004107ac5aa5";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d(TAG, "Response received from server: " +response);

                Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url",response);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                String[] separated = token.split("/");

                if(separated[1]=="#") {
                    Toast.makeText(context, "User session expired! Please relogin from your NFC App.", Toast.LENGTH_SHORT);
                    return null;
                }
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("token", separated[0]); //Add the data you'd like to send to the server.
                MyData.put("user", separated[1]);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

}


