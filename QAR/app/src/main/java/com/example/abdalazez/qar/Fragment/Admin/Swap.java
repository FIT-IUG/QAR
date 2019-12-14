package com.example.abdalazez.qar.Fragment.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.AllUser;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class Swap extends AppCompatActivity {

    Spinner spinnerExams;
    Spinner spinnerUsers;
    Button btnSubmitSwap;
    ArrayList<AllUser> listUser;
    ArrayList<Exams> listExam;
    ArrayList<Exams> listExamUsetNow;
    ArrayList<String> nameExam = new ArrayList<>();
    ArrayList<String> nameUser = new ArrayList<>();
    String idOldUser;
    boolean isSet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAssignNewCont);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Assign Controller");

        spinnerExams = (Spinner) findViewById(R.id.userExams);
        spinnerUsers = (Spinner) findViewById(R.id.userName);
        btnSubmitSwap = (Button) findViewById(R.id.butSwap);
        listUser = new ArrayList<>();
        listExam = new ArrayList<>();

        /*
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                listExam = o;
                if (o != null) {
                    nameExam.add("Select Course Name");
                    for (int i = 0; i < listExam.size(); i++) {
                        nameExam.add(listExam.get(i).getCoursename());
                    }
                    CourseName(nameExam);
                }
            }
        }).doRequestForTable(idOldUser,"teacher");
*/
        if(getIntent().getExtras()!= null) {
            idOldUser = getIntent().getStringExtra("UserIdForExam");
            Toast.makeText(Swap.this, ""+idOldUser, Toast.LENGTH_SHORT).show();

            new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                @Override
                public void onDataReceivedA(ArrayList o) {
                    listExam = o;
                    if (o != null) {
                        nameExam.add("Select Course Name");
                        for (int i = 0; i < listExam.size(); i++) {
                            nameExam.add(listExam.get(i).getCoursename());
                        }
                        setCourseName(nameExam);
                    }
                }
            }).doRequestForTable(idOldUser,"teacher");
        }


        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                listUser = o;
                if (o != null) {
                    nameUser.add("Select User Name");
                    for (int i = 0; i < listUser.size(); i++) {
                        nameUser.add(listUser.get(i).getName());
                    }
                    setUserName(nameUser);
                }
            }
        }).doRequestForAllUser();

        btnSubmitSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  numUserNow = spinnerUsers.getSelectedItemPosition()-1;
                final int  numExamNow= spinnerExams.getSelectedItemPosition()-1;
                String examIDlistNow = ""+listExam.get(numExamNow).getExam_id();
                String userIDlistNow = listUser.get(numUserNow).getId();
                    //Toast.makeText(Swap.this,  "U :" + userIDlistNow +"E :"+examIDlistNow, Toast.LENGTH_SHORT).show();
                new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                    @Override
                    public void onDataReceivedA(ArrayList o) {
                        listExamUsetNow = new ArrayList<>();
                        listExamUsetNow = o;
                        for (int i = 0; i < listExamUsetNow.size(); i++) {
                            if(listExamUsetNow.get(i).getDate().equalsIgnoreCase(listExam.get(numExamNow).getDate()) && listExamUsetNow.get(i).getTime().equalsIgnoreCase(listExam.get(numExamNow).getTime())){
                                isSet =false;
                                Toast.makeText(Swap.this, "There is a conflict", Toast.LENGTH_SHORT).show();
                            }else {
                                //isSet = true;
                                //Toast.makeText(Swap.this, "There is no conflict", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
                }).doRequestForTable(userIDlistNow,"teacher");
                
                    if(isSet){
                        ///Toast.makeText(Swap.this, "OnClickListener : " + "\nSpinner 1 : "+ String.valueOf(spinnerExams.getSelectedItem())+ "\nSpinner 2 : "+ String.valueOf(spinnerUsers.getSelectedItem()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Swap.this, "No conflict", Toast.LENGTH_SHORT).show();
                        int  numExam= spinnerExams.getSelectedItemPosition()-1;
                        int  numUser = spinnerUsers.getSelectedItemPosition()-1;
                        if(numExam != -1 && numUser != -1) {
                            String examIDlist= ""+listExam.get(numExam).getExam_id();
                            String userIDlist= listUser.get(numUser).getId();
                            Toast.makeText(Swap.this, "E :" + examIDlist + " || " + idOldUser +" || "+userIDlist, Toast.LENGTH_SHORT).show();

                            new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                @Override
                                public void onDataReceivedA(ArrayList o) {
                                }
                            }).doRequestForModifiyUser(examIDlist,idOldUser,userIDlist);

                        }else{
                            Toast.makeText(Swap.this, "Select Coures and User for Swap!!!", Toast.LENGTH_SHORT).show();
                        }
                }else {
                        Toast.makeText(Swap.this, "There is a conflict", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    ArrayAdapter<String> dataAdapterExam ;
    public void setCourseName(ArrayList<String> arrayList){
        dataAdapterExam = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapterExam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExams.setAdapter(dataAdapterExam);

    }

    ArrayAdapter<String> dataAdapterUser;
    public void setUserName(ArrayList<String> arrayList) {
        dataAdapterUser = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers.setAdapter(dataAdapterUser);
    }
}
