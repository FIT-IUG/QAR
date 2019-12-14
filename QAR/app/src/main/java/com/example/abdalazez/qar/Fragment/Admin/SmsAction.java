package com.example.abdalazez.qar.Fragment.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class SmsAction extends AppCompatActivity {

    EditText editText;
    Button button;
    String number ="";
    int id;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_action);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSmsAction);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Send Message");

        editText = findViewById(R.id.enterMessage);
        button = findViewById(R.id.acceptMessage);

        SharedPreferences pref = getSharedPreferences("UserIsRegister", MODE_PRIVATE);
        id = pref.getInt("UserID",-1);
        token = pref.getString("UserRToken","");

        if(getIntent().getExtras() != null){
            number = getIntent().getStringExtra("NumberPhoneForSms");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();

                if(!msg.isEmpty() && !number.isEmpty()){

                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            if (o != null) {
                            }
                        }
                    }).doRequestForSendSMS("18",msg,token);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                    intent.putExtra("sms_body", msg);
                    startActivity(intent);
                    editText.setText("");
                }else{
                    Toast.makeText(SmsAction.this, "Enter Message!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
