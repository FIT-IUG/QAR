package com.example.abdalazez.qar.Fragment.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdalazez.qar.Model.SettingsItem;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 08/04/2018.
 */

public class SettingsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    ArrayList<SettingsItem> dataArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);


        mRecyclerView = v.findViewById(R.id.settingsAdmin_recyclerView);

        dataArray = new ArrayList<>();
        dataArray.add(new SettingsItem("View all exams",R.drawable.ic_view_exams));
        dataArray.add(new SettingsItem("Assign new controller",R.drawable.ic_new_controller));
        dataArray.add(new SettingsItem("Contact course teacher",R.drawable.ic_contact_controller));
        //dataArray.add(new SettingsItem("Test",R.drawable.e1));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SettingsAdapter(dataArray, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        return v;
    }
}