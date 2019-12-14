package com.example.abdalazez.qar.Fragment.Admin;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.abdalazez.qar.Control.SQL_Operations;
import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

public class AllExams extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    ArrayList<Exams> dataArray;
    ArrayList<Exams> dataLoad = new ArrayList<>();
    String typeUser;
    SQL_Operations dbTable;
    SQLiteDatabase sdbTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exams);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarallExams);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("All Exams");

        mRecyclerView = findViewById(R.id.allExams_recyclerView);

        SharedPreferences pref = this.getSharedPreferences("UserIsRegister", this.MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        int id = pref.getInt("UserID",-1);
        typeUser = pref.getString("UserRType","-1");

        dbTable = new SQL_Operations(AllExams.this);
        sdbTable = dbTable.getWritableDatabase();


        if (dbTable.getAllExams("1").size() > 0) {
            dataLoad = (ArrayList<Exams>) dbTable.getAllExams("1");
            //Toast.makeText(AllExams.this, "SSS"+dataLoad.get(0).getCoursename(), Toast.LENGTH_SHORT).show();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(AllExams.this));
            mAdapter = new AllExamsAdapter(dataLoad, AllExams.this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataExams();

    }
    public void getDataExams(){
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    dbTable = new SQL_Operations(AllExams.this);
                    sdbTable = dbTable.getWritableDatabase();
                    sdbTable.execSQL("delete from "+ "Exams");
                    dbTable.addExams(o,"1");
                    //Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "!!!"+dataArray.get(0).getCoursename(), Toast.LENGTH_LONG).show();
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    // mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(AllExams.this));
                    mAdapter = new AllExamsAdapter(dataArray,AllExams.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    dbTable.close();
                    sdbTable.close();
                }
            }
        }).doRequestForTable("",typeUser);
    }
}
