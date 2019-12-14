package com.example.abdalazez.qar.Fragment.Admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class BroadcastAdmin extends AppCompatActivity {

    Spinner spinnerTypeSend;
    Spinner spinnerCoures;
    EditText theMessageBrod;
    Button btnSubmitBroadcast;
    ArrayList<Exams> listExam;
    ArrayList<Exams> listExamUsetNow;
    ArrayList<String> nameExam = new ArrayList<>();
    ArrayList<String> nameType = new ArrayList<>();
    String idOldUser;
    int isSet = 0;
    int isSetCoures=0;
    String examID;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_admin);
        spinnerTypeSend = (Spinner) findViewById(R.id.typeSend);
        spinnerCoures = (Spinner) findViewById(R.id.coures);
        theMessageBrod = findViewById(R.id.theMessageBrod);
        btnSubmitBroadcast = (Button) findViewById(R.id.butBroadcast);
        listExam = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBroadcastAdmin);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Send Broadcast Notification");

        SharedPreferences pref = getSharedPreferences("UserIsRegister", MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        int id = pref.getInt("UserID",-1);
        token = pref.getString("UserRToken","");

        nameType.add("Select type broadcast");
        nameType.add("Broadcast For All Controller");
        nameType.add("Broadcast For Course");
        settypeBroadcast(nameType);

        spinnerTypeSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    isSet=1;
                }else if(i == 2){
                    isSet=2;
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            listExam = o;
                            nameExam = new ArrayList<>();
                            if (o != null) {
                                nameExam.add("Select type broadcast");
                                for (int i = 0; i < listExam.size(); i++) {
                                    nameExam.add(listExam.get(i).getCoursename());
                                }
                                setcourseNames(nameExam);
                            }
                        }
                    }).doRequestForTable("","");
                }else {
                    isSet =0;
                    Toast.makeText(BroadcastAdmin.this, "Select type Broadcast from first list...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(BroadcastAdmin.this, "Select type Broadcast from first list...", Toast.LENGTH_SHORT).show();
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        spinnerCoures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                      examID = "" + listExam.get(i-1).getExam_id();
                    //Toast.makeText(BroadcastAdmin.this, "" + examIDlist, Toast.LENGTH_SHORT).show();
                    isSetCoures = 1;
                }else {
                    isSetCoures = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(BroadcastAdmin.this, "Select type Broadcast from first list...", Toast.LENGTH_SHORT).show();
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        btnSubmitBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = theMessageBrod.getText().toString();
                if(!msg.isEmpty()){
                if (isSet == 1) {
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {

                        }
                    }).doRequestForbroadcastUsers(msg,token);
                    Toast.makeText(BroadcastAdmin.this, ""+msg, Toast.LENGTH_SHORT).show();
                    theMessageBrod.setText("");
                } else if (isSet == 2 && isSetCoures !=0) {
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {

                        }
                    }).doRequestForbroadcastExam(examID+"", ""+msg ,token);
                    Toast.makeText(BroadcastAdmin.this, ""+examID +"||"+msg, Toast.LENGTH_SHORT).show();
                    theMessageBrod.setText("");
                } else {
                    Toast.makeText(BroadcastAdmin.this, "Enter all required data and Select Item list...", Toast.LENGTH_SHORT).show();
                }
            }else {
                    Toast.makeText(BroadcastAdmin.this, "Enter the message", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    ArrayAdapter<String> dataAdapterType ;
    public void settypeBroadcast(ArrayList<String> arrayList){
        dataAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeSend.setAdapter(dataAdapterType);

    }

    ArrayAdapter<String> dataAdapterCourse;
    public void setcourseNames(ArrayList<String> arrayList) {
        dataAdapterCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoures.setAdapter(dataAdapterCourse);
    }
}
