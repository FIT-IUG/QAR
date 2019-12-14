package com.example.abdalazez.qar.Fragment.Controller;

import android.content.SharedPreferences;
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
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 04/04/2018.
 */

public class TablesFragment extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    int id;
    String typeUser;
    boolean isconn = true;
    ArrayList<Exams> dataArray = new ArrayList<>();
    ArrayList<Exams> dataLoad = new ArrayList<>();
    SQL_Operations dbTable;
    SQLiteDatabase sdbTable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tables, container, false);
        mRecyclerView = v.findViewById(R.id.table_recyclerView);

        SharedPreferences pref = getActivity().getSharedPreferences("UserIsRegister", getActivity().MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        typeUser = pref.getString("UserRType","");
        id = pref.getInt("UserID",-1);

        dbTable = new SQL_Operations(getActivity());
        sdbTable = dbTable.getWritableDatabase();


        if (dbTable.getAllExams("0").size() > 0) {
            dataLoad = (ArrayList<Exams>) dbTable.getAllExams("0");
            Toast.makeText(getActivity(), "SSS"+dataLoad.get(0).getCoursename(), Toast.LENGTH_SHORT).show();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new TableAdapter(dataLoad, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataNotification();
        //if(getDataNotification() == false){
            //Toast.makeText(getActivity(), "No Internt Connection", Toast.LENGTH_LONG).show();
        //}

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_containerTable);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(value==true) {
                isconn = true;
                Toast.makeText(getActivity(), "Refresh Done", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(getActivity(), "No id", Toast.LENGTH_LONG).show();
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

        /*
        if(value==true) {
            new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                @Override
                public void onDataReceivedA(ArrayList o) {
                    dataArray =  o;
                     Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "!!!"+dataArray.get(0).getCoursename(), Toast.LENGTH_LONG).show();
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    // mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mAdapter = new TableAdapter(dataArray, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                }
            }).doRequestForTable(""+id);
        }else{
            Toast.makeText(getActivity(), "No id", Toast.LENGTH_LONG).show();
        }
        */
        return v;
    }
    public boolean getDataNotification(){
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    dbTable = new SQL_Operations(getActivity());
                    sdbTable = dbTable.getWritableDatabase();
                    sdbTable.execSQL("delete from "+ "Exams");
                    dbTable.addExams(o,"0");
                //Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "!!!"+dataArray.get(0).getCoursename(), Toast.LENGTH_LONG).show();
                //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                // mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new TableAdapter(dataArray, getActivity());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                dbTable.close();
                sdbTable.close();
            }
            }
        }).doRequestForTable(""+id,typeUser);
        return dataArray!=null;
    }
}
