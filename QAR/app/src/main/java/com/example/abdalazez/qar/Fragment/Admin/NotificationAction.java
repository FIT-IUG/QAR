package com.example.abdalazez.qar.Fragment.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class NotificationAction extends AppCompatActivity {
    Notification notifications;
    EditText editText;
    TextView tstate;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_action);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarActionNoifi);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Request Reply");


        TextView tname = findViewById(R.id.senderNotification);
        TextView ttype = findViewById(R.id.typeNotification);
        TextView tdate = findViewById(R.id.dateNotification);
        TextView tcontent = findViewById(R.id.contentNotification);
        tstate = findViewById(R.id.stateNotification);
        editText = findViewById(R.id.enterReply);

        notifications = (Notification)getIntent().getSerializableExtra("itemSelectNotifi");
        tname.setText(notifications.getName());
        ttype.setText(notifications.getNotificationType());
        tdate.setText(notifications.getCreated_at());
        tcontent.setText(notifications.getContent());
        tstate.setText(notifications.getState());
        id = notifications.getId();

        final Button button = findViewById(R.id.acceptRequest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()) {
                    String reply = editText.getText().toString();
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {

                        }
                    }).doRequestForEnterReply(""+id,reply);
                    tstate.setText(reply);
                    Toast.makeText(NotificationAction.this, "Request accepted...", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(NotificationAction.this, Login.class);
                    //startActivity(intent);
                    //finish();
                }else {
                    Toast.makeText(NotificationAction.this, "Enter reply for controller", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
