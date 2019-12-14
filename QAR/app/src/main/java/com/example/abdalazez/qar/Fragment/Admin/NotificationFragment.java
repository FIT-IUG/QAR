package com.example.abdalazez.qar.Fragment.Admin;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.SQL_Operations;
import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.Model.User;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 08/04/2018.
 */

public class NotificationFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    User user;
    ArrayList<Notification> dataArray;
    ArrayList<Notification> dataItem = new ArrayList<>();
    ArrayList<Notification> dataTest;
    boolean isconn =true;
    ArrayList<Notification> dataLoad = new ArrayList<>();
    SQL_Operations dbNotification;
    SQLiteDatabase sdbNotification;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        mRecyclerView = v.findViewById(R.id.notificstion_recyclerView);

        dbNotification = new SQL_Operations(getActivity());
        sdbNotification = dbNotification.getWritableDatabase();

        if (dbNotification.getAllNotification() != null) {
            dataLoad = new ArrayList<>();
            dataLoad = (ArrayList<Notification>) dbNotification.getAllNotification();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new NotificationAdapter(dataLoad, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataNotification();

        //if(getDataNotification() == false){
          //  Toast.makeText(getActivity(), "No Internt Connection", Toast.LENGTH_LONG).show();
        //}
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_containerNotifi);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isconn = true;
                Toast.makeText(getActivity(), "Refresh Done", Toast.LENGTH_LONG).show();
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        while (isconn) {
                            try {
                                boolean co = getDataNotification();
                                if (co == true) {
                                    isconn = false;
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

        return v;
    }
    public boolean getDataNotification() {
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = new ArrayList<>();
                dataItem = new ArrayList<>();
                dataArray = o;
                if (o != null) {
                        for (int i = 0; i < dataArray.size(); i++) {
                            if(!dataArray.get(i).getNotificationType().equalsIgnoreCase("public") && !dataArray.get(i).getHideadmin().equalsIgnoreCase("1") ) {
                                dataItem.add(dataArray.get(i));
                            }
                        }
                    //SharedPreferences pref = getActivity().getSharedPreferences("JunitTestQar", getActivity().MODE_PRIVATE);
                    //SharedPreferences.Editor editor = pref.edit();
                    //editor.putInt("JunitTestQarNumNoti", dataItem.size());
                    //Toast.makeText(getActivity(), "Noti :"+dataItem.size(), Toast.LENGTH_SHORT).show();
                    //editor.commit();
                    dbNotification = new SQL_Operations(getActivity());
                    sdbNotification = dbNotification.getWritableDatabase();
                    sdbNotification.execSQL("delete from "+ "Notification");
                    dbNotification.addNotification(dataItem);
                    //Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mAdapter = new NotificationAdapter(dataItem, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    dbNotification.close();
                    sdbNotification.close();
                }
            }
        }).doRequestForRequests("");
        return dataArray!=null;
    }
}