package com.example.abdalazez.qar.Fragment.Controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.SQL_Operations;
import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.AttendanceStudant;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.Model.User;
import com.example.abdalazez.qar.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 08/04/2018.
 */

public class RequestsFragment extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    User user;
    ArrayList<Notification> dataArray;
    ArrayList<Notification> dataItem = new ArrayList<>();
    int id;
    String token;
    boolean isconn = true;
    boolean isselect=false;
    String selectReq = "";
    String msg="";
    ArrayList<Notification> dataLoad = new ArrayList<>();
    ArrayList<Exams> myExams = new ArrayList<>();
    ArrayList<AttendanceStudant> attendanceStudants = new ArrayList<>();
    public ArrayList<String> AllStudants = new ArrayList<>();
    public  SQL_Operations dbRequest;
    public  SQLiteDatabase sdbRequest;
    public Spinner spinnerselectStudents;
    String studentName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_requests, container, false);
        mRecyclerView = v.findViewById(R.id.request_recyclerView);

        SharedPreferences pref = getActivity().getSharedPreferences("UserIsRegister", getActivity().MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        id = pref.getInt("UserID",-1);
        token = pref.getString("UserRToken","");

            dbRequest = new SQL_Operations(getActivity());
            sdbRequest = dbRequest.getWritableDatabase();


        if (dbRequest.getAllRequests() != null) {
            dataLoad = (ArrayList<Notification>) dbRequest.getAllRequests();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new RequestAdapter(dataLoad, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataNotification();
        //if(getDataNotification() == false){
            //Toast.makeText(getActivity(), "No Internt Connection", Toast.LENGTH_LONG).show();
        //}
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_containerRequest);
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
            getDataNotification();

            new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                @Override
                public void onDataReceivedA(ArrayList o) {
                    dataArray = o;
                    if (o != null) {
                    //Toast.makeText(getActivity(), "No" + dataArray.size(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "!!!"+dataArray.get(0).getCoursename(), Toast.LENGTH_LONG).show();
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    // mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mAdapter = new RequestAdapter(dataArray, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
                }
            }).doRequestForRequests(""+id);

        }else{
            Toast.makeText(getActivity(), "No id", Toast.LENGTH_LONG).show();
        }
*/
        FloatingActionButton floatingAction = v.findViewById(R.id.floatingRequests);
        floatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                AllStudants =new ArrayList<>();
                AllStudants.add("Select Student Name >");
                new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                    @Override
                    public void onDataReceivedA(ArrayList o) {
                        myExams =  o;
                        if(o != null) {
                            for (int i = 0; i <myExams.size() ; i++) {
                                //Toast.makeText(getActivity(), "" + myExams.get(i).getExam_id(), Toast.LENGTH_SHORT).show();
                                new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                    @Override
                                    public void onDataReceivedA(ArrayList o) {
                                        attendanceStudants = o;
                                        if (o != null && attendanceStudants.size()>0) {
                                            for (int j = 0; j <attendanceStudants.size() ; j++) {
                                                AllStudants.add(attendanceStudants.get(j).getStd_name());
                                                Toast.makeText(getActivity(), ""+AllStudants.size(), Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(getActivity(), "Don't have studants", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).doRequestForattendanceStudant(myExams.get(i).getExam_id()+"");
                            }
                        }else {
                            Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).doRequestForTable(id+"","teacher");
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                View vewInflater = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_requests, (ViewGroup) getView(), false);
                final EditText input = (EditText) vewInflater.findViewById(R.id.detailsRequest);
                final EditText roomNum = (EditText) vewInflater.findViewById(R.id.roomNumRequest);
                spinnerselectStudents = (Spinner) vewInflater.findViewById(R.id.selectStudents);
                spinnerselectStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                     studentName= AllStudants.get(i);
                        //Toast.makeText(getActivity(), ""+studentName, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                dataAdapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AllStudants);
                dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerselectStudents.setAdapter(dataAdapterType);

                RadioGroup radioGroup = vewInflater.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        isselect = true;
                        switch (i){
                            case R.id.radioButton1 :
                                selectReq = "Apology";
                                spinnerselectStudents.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.radioButton2 :
                                selectReq = "Paper";
                                spinnerselectStudents.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.radioButton3 :
                                selectReq = "Cheat";
                                spinnerselectStudents.setVisibility(View.VISIBLE);
                                break;
                            case R.id.radioButton4 :
                                selectReq = "Help";
                                spinnerselectStudents.setVisibility(View.INVISIBLE);
                                break;
                        }
                        //Toast.makeText(getActivity(),""+selectReq+""+radioGroup.getId()+"||"+i,Toast.LENGTH_LONG).show();
                    }
                });
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.ic_menu_send)
                        .setTitle("Send Requests")
                        .setView(vewInflater)
                        .setCancelable(false)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                String msgReq = input.getText().toString();
                                String msgReqRoom = roomNum.getText().toString();
                                if(selectReq.equalsIgnoreCase("Cheat")){
                                     msg = msgReq + "...رقم القاعة :" + msgReqRoom + "...اسم الطالب :" + studentName;
                                }else {
                                     msg = msgReq + "...رقم القاعة :" + msgReqRoom;
                                }
                                if(!msgReq.isEmpty()&&isselect&&!msgReqRoom.isEmpty()) {
                                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                                    isselect =false;

                                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                        @Override
                                        public void onDataReceivedA(ArrayList o) {

                                        }
                                    }).doRequestForSendRequests("18",msg,selectReq,token);
                                    //18
                                    /*
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                                    String datetime = dateformat.format(c.getTime());
                                    dataItem.add(new Notification(selectReq,msg,datetime,dataItem.get(0).getId()+1));
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    mAdapter = new RequestAdapter(dataItem, getActivity());
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    */
                                    getDataNotification();
                                    dialogInterface.dismiss();

                                }else{
                                    Toast.makeText(getActivity(), "Enter details & Select one case", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });

        try {
            if(isInternetAvailable()){
                sdbRequest.execSQL("delete from "+ "Requests");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return v;
    }

    public boolean getDataNotification(){
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = new ArrayList<>();
                dataItem = new ArrayList<>();
                dataArray = o;
                if (o != null) {
                    for (int i = 0; i < dataArray.size(); i++) {
                        if(!dataArray.get(i).getNotificationType().equalsIgnoreCase("public") && !dataArray.get(i).getHide().equalsIgnoreCase("1")) {
                            dataItem.add(dataArray.get(i));
                            }
                    }
                    //Toast.makeText(getActivity(), ""+dataItem.size(), Toast.LENGTH_SHORT).show();
                    //SQL_Operations dbRequests = new SQL_Operations(getActivity());
                    //SQLiteDatabase sdbRequests = dbRequests.getWritableDatabase();
                    //sdbRequest.execSQL("delete from "+ "Requests");
                    dbRequest.addRequests(dataItem);
                    //Toast.makeText(getActivity(), "No" + dataArray.size(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "!!!"+dataArray.get(0).getCoursename(), Toast.LENGTH_LONG).show();
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    // mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mAdapter = new RequestAdapter(dataItem, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
           //         dbRequest.close();
            //        sdbRequest.close();
                }
            }
        }).doRequestForRequests(""+id);
        return dataItem!=null;
    }

    @Override
    public void onPause() {
        super.onPause();
        dbRequest.close();
        sdbRequest.close();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ArrayAdapter<String> dataAdapterType ;
    public void typeBroadcast(ArrayList<String> arrayList){
        dataAdapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerselectStudents.setAdapter(dataAdapterType);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isInternetAvailable() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        //Toast.makeText(this,"Internt is :"+(Runtime.getRuntime().exec (command).waitFor() == 0),Toast.LENGTH_LONG).show();
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }

}
