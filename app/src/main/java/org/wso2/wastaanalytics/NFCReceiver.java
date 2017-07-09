package org.wso2.wastaanalytics;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by pasindu on 7/7/17.
 */

public class NFCReceiver extends Activity {
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
                //Intent launch = new Intent(this, CallAPI.class);
                //launch.putExtra("android.app.extra.token", token);
                //startActivity(launch);
                Log.d("NFC", "TOKEN RECEIVED: " + token);
                Toast.makeText(this,token,Toast.LENGTH_SHORT).show();

                VolleyClient volleyClient = new VolleyClient();
                volleyClient.getURL(this.getApplicationContext(), token);
            }
        } else
            Toast.makeText(this,"Waiting for NFC Bump", Toast.LENGTH_SHORT);
    }

}
