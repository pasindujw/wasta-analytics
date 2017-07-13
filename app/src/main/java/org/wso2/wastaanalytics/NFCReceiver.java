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
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class NFCReceiver extends Activity {
    public static final String TAG = NFCReceiver.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            String token = (new String(message.getRecords()[0].getPayload()));
            if (token != null && !token.equals("")) {
                Log.d(TAG, "Token received via NFC :  " + token);

                //Letting user know that logging is in the processing.
                Toast.makeText(this,"Logging using NFC Bump..",Toast.LENGTH_SHORT).show();

                VolleyClient volleyClient = new VolleyClient();
                volleyClient.getURL(this.getApplicationContext(), token);
                finish();
            }
        } else
            Toast.makeText(this,"Waiting for NFC Bump", Toast.LENGTH_SHORT);
    }

}
