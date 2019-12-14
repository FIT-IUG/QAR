package com.example.abdalazez.qar.Fragment.Admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.AttendanceStudant;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class ShowStudents extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    int id;
    String typeUser;
    boolean isconn = true;
    ArrayList<AttendanceStudant> dataArray = new ArrayList<>();
    ArrayList<Exams> listExam;
    ArrayList<String> nameExam = new ArrayList<>();
    ArrayList<AttendanceStudant> attendanceStudants = new ArrayList<>();
    ArrayList<AttendanceStudant> AllStudants = new ArrayList<>();
    Spinner spinnerCoures;
    String examID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students);

        SharedPreferences pref = getSharedPreferences("UserIsRegister",MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        typeUser = pref.getString("UserRType","");
        id = pref.getInt("UserID",-1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarShowStudents);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Show Students");

        mRecyclerView = findViewById(R.id.ShowStudents_recyclerView);

        spinnerCoures = (Spinner) findViewById(R.id.selectExamForShow);

        nameExam.add("Select Exam >");
        courseNames(nameExam);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                listExam = o;
                nameExam = new ArrayList<>();
                if (o != null) {
                    nameExam.add("Select Exam >");
                    for (int i = 0; i < listExam.size(); i++) {
                        nameExam.add(listExam.get(i).getCoursename());
                    }
                    courseNames(nameExam);
                }
            }
        }).doRequestForTable(""+id,"teacher");

        spinnerCoures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AllStudants =new ArrayList<>();
                dataArray = new ArrayList<>();

                if(i != 0) {
                    examID = "" + listExam.get(i-1).getExam_id();
                    Toast.makeText(ShowStudents.this, "Wait...", Toast.LENGTH_LONG).show();

                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            attendanceStudants = o;
                            if (o != null && attendanceStudants.size()>0) {
                                for (int j = 0; j <attendanceStudants.size() ; j++) {
                                    AllStudants.add(attendanceStudants.get(j));
                                    //Toast.makeText(ShowStudents.this, ""+AllStudants.size(), Toast.LENGTH_SHORT).show();
                                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                    @Override
                                    public void onDataReceivedA(ArrayList o) {
                                        dataArray = o;
                                        if (o != null) {
                                            //SharedPreferences pref = getSharedPreferences("JunitTestQar", MODE_PRIVATE);
                                            //SharedPreferences.Editor editor = pref.edit();
                                            //editor.putInt("JunitTestQarNumStudentsIsAttendance", dataArray.size());
                                            //Toast.makeText(ShowStudents.this, "Exm :"+dataArray.size(), Toast.LENGTH_SHORT).show();
                                            //editor.commit();
                                            //Toast.makeText(ShowStudents.this, "OK", Toast.LENGTH_SHORT).show();
                                            mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowStudents.this));
                                            mAdapter = new ShowStudentsAdapter(dataArray, AllStudants,ShowStudents.this);
                                            mRecyclerView.setAdapter(mAdapter);
                                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                        }else {
                                            Toast.makeText(ShowStudents.this, "Don't have studants", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).doRequestForShowStudent(examID);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }else {
                                Toast.makeText(ShowStudents.this, "Don't have studants", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).doRequestForattendanceStudant(examID+"");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ShowStudents.this, "Select Exam from list...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ArrayAdapter<String> dataAdapterCourse;
    public void courseNames(ArrayList<String> arrayList) {
        dataAdapterCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoures.setAdapter(dataAdapterCourse);
    }
}