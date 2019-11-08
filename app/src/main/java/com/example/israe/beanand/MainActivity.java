package com.example.israe.beanand;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import 	android.security.keystore.KeyGenParameterSpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import android.util.Log;
import static javax.crypto.Cipher.getInstance;

public class MainActivity extends AppCompatActivity  implements NfcAdapter.CreateNdefMessageCallback{
    private EditText editText;
    private TextView textKey;
    private TextView textciphe;
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        setContentView(R.layout.activity_main);
        textKey=(TextView) findViewById(R.id.textKeyp);
        textKey.setText("key aqui va");
        textciphe=(TextView) findViewById(R.id.textCip);
*/
        editText=(EditText) findViewById(R.id.idText);
        NfcAdapter nfcAdapter=NfcAdapter.getDefaultAdapter(this);//inicializador
        if(nfcAdapter==null){
            Toast.makeText(this,"No tiene NFC",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,"NFC apagado!!!!!",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this,"Todo listo!!!",Toast.LENGTH_SHORT).show();
        }

        nfcAdapter.setNdefPushMessageCallback(this,this);
        //nfcAdapter.set
    }
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = editText.getText().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
/*
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            editText.setText(new String(message.getRecords()[0].getPayload()));

        } else
            editText.setText("Waiting for NDEF Message");

    }
    */
}

