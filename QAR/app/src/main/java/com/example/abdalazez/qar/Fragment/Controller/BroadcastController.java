package com.example.abdalazez.qar.Fragment.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.SQL_Operations;
import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

public class BroadcastController extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    boolean isconn =true;
    String smsReq;
    String typeUser;
    int id;
    ArrayList<Notification> dataArray;
    ArrayList<Integer> dataArrayID;
    ArrayList<Notification> dataItem = new ArrayList<>();
    ArrayList<Notification> dataLoad = new ArrayList<>();
    SQL_Operations dbBroadcastCont;
    SQLiteDatabase sdbBroadcastCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_controller);

        SharedPreferences pref = this.getSharedPreferences("UserIsRegister", this.MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        id = pref.getInt("UserID",-1);
        typeUser = pref.getString("UserRType","-1");
        if(typeUser.equalsIgnoreCase("teacher")){
            smsReq = ""+id;
        }else{
            smsReq = "";
        }

        dataArrayID = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBroadcastCont);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Notifications");

        mRecyclerView = findViewById(R.id.BroadcastCont_RecyclerView);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    for (int i = 0; i < dataArray.size(); i++) {
                        dataArrayID.add(dataArray.get(i).getId());
                    }
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            dataArray = o;
                            if (o != null) {
                            }
                        }
                    }).doRequestForSeenNotification(dataArrayID,"");

                }
            }
        }).doRequestForMyNotification(""+id);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();



        dbBroadcastCont = new SQL_Operations(BroadcastController.this);
        sdbBroadcastCont = dbBroadcastCont.getWritableDatabase();

        if (dbBroadcastCont.getAllBroadcast() != null) {
            dataLoad = (ArrayList<Notification>) dbBroadcastCont.getAllBroadcast();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(BroadcastController.this));
            mAdapter = new BroadcastControllerAdapter(dataLoad, BroadcastController.this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataNotification();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_containerBroadcastCont);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(value==true) {
                isconn = true;
                Toast.makeText(BroadcastController.this, "Refresh Done", Toast.LENGTH_LONG).show();
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        while (isconn) {
                            try {
                                if(value==true) {
                                    boolean co = getDataNotification();
                                    if (co == true) {
                                        isconn = false;
                                    }
                                }else{
                                    Toast.makeText(BroadcastController.this, "No id", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                };
                thread.start();
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.stopNestedScroll();
            }
        });

    }
    public boolean getDataNotification(){
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    for (int i = 0; i < dataArray.size(); i++) {
                        if(dataArray.get(i).getNotificationType().equalsIgnoreCase("public")) {
                            dataItem.add(dataArray.get(i));
                        }
                    }
                    dbBroadcastCont = new SQL_Operations(BroadcastController.this);
                    sdbBroadcastCont = dbBroadcastCont.getWritableDatabase();
                    sdbBroadcastCont.execSQL("delete from "+ "Broadcast");
                    dbBroadcastCont.addBroadcast(dataItem);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(BroadcastController.this));
                    mAdapter = new BroadcastControllerAdapter(dataItem, BroadcastController.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }
        }).doRequestForMyNotification(""+id);
        return dataItem!=null;
    }
}
