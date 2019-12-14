package com.example.abdalazez.qar.Sidemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.abdalazez.qar.Model.SettingsItem;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

public class SettingsApp extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    ArrayList<SettingsItem> dataArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_app);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Settings");

        mRecyclerView = findViewById(R.id.settings_RrecyclerView);
        dataArray = new ArrayList<>();
        dataArray.add(new SettingsItem("Notification",R.drawable.ic_notifications));
        //dataArray.add(new SettingsItem("Test",R.drawable.e1));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SettingsApp.this));
        mAdapter = new SettingsAppAdapter(dataArray, SettingsApp.this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
