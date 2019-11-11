package com.example.israe.beanand;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import 	android.security.keystore.KeyGenParameterSpec;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    Button botonAbrir;
    Button botonGuardar;

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
        botonAbrir=(Button)findViewById(R.id.botonAbrir);
        botonGuardar=(Button) findViewById(R.id.botonGuardar);
        final Date date = new Date();
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
        botonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nombres= fileList();
                int len = nombres.length;
                System.out.println(len);
                for (int i=0;i<len;i++)
                    System.out.println(nombres[i]);
            }
        });
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ssddMMyyyy");
                //System.out.println("Hora y fecha: "+hourdateFormat.format(date));
                String hora=hourdateFormat.format(date);

                System.out.println("Hora y fecha: "+hora);
                try {
                    OutputStreamWriter archivo= new OutputStreamWriter(openFileOutput(hora+".key", Activity.MODE_PRIVATE));
                    archivo.write(editText.getText().toString());
                    archivo.flush();
                    archivo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),"guardado",Toast.LENGTH_SHORT).show();

            }
        });
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

