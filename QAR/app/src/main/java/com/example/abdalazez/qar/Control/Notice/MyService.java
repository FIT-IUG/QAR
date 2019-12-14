package com.example.abdalazez.qar.Control.Notice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Notification;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 04/06/2018.
 */

public class MyService extends Service {
    ArrayList<Notification> dataArray;
    int id;
    boolean isconn=true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        if(intent != null) {
            id = intent.getIntExtra("ReqUserID", -1);
            Toast.makeText(getApplicationContext(), "Service Not Null: "+id, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Service Is Null: "+id, Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    for (int i = 0; i < dataArray.size() ; i++) {
                        if(dataArray.get(i).getSeen().equalsIgnoreCase("0") && !dataArray.get(i).getNotificationType().equalsIgnoreCase("public") && dataArray.get(i).getType().equalsIgnoreCase("teacher")) {
                            new CreateNotification(dataArray.get(i).getContent(), dataArray.get(i).getNotificationType(), dataArray.get(i).getId(), getApplicationContext(),"0");
                        }
                    }
                }
            }
        }).doRequestForMyNotification(""+id);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    for (int i = 0; i < dataArray.size() ; i++) {
                        if (dataArray.get(i).getSeen().equalsIgnoreCase("0") && dataArray.get(i).getNotificationType().equalsIgnoreCase("public") && dataArray.get(i).getType().equalsIgnoreCase("admin")) {
                            new CreateNotification(dataArray.get(i).getContent(), dataArray.get(i).getNotificationType(), dataArray.get(i).getId(), getApplicationContext(),"1");
                        }
                    }
                }
            }
        }).doRequestForMyNotification(""+id);
        System.out.println("Serves is:" + isconn);


        return START_STICKY;
    }

}
