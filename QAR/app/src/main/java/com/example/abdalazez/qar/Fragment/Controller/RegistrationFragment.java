package com.example.abdalazez.qar.Fragment.Controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abdalazez.qar.Control.MainActivity;
import com.example.abdalazez.qar.Control.NFC.NFCinterface;
import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Fragment.Admin.ShowStudents;
import com.example.abdalazez.qar.Model.AttendanceStudant;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;

import java.io.IOException;
import java.util.ArrayList;

//public class MLogin extends AppCompatActivity {

/*
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
 */

/**
 * Created by ABD ALAZEZ on 04/04/2018.
 */

public class RegistrationFragment extends Fragment  implements NFCinterface{
    boolean isconn = true;
    TextView internetConn;
    ImageView imageView;
    TextView textStudentID;
    TextView textViewState;
    NfcAdapter mAdapter;
    String token;
    int userId;
    ArrayList<Exams> myExams = new ArrayList<>();
    ArrayList<AttendanceStudant> attendanceStudants = new ArrayList<>();
    ArrayList<AttendanceStudant> AllStudants = new ArrayList<>();
    ArrayList<Exams> listExams;
    ArrayList<String> nameExams = new ArrayList<>();
    Spinner spinnerCouresController;
    String examIDs;
    String IDStudent;
    //ArrayList<AttendanceStudant> dataLoad = new ArrayList<>();
    //SQL_Operations db;
    //SQLiteDatabase sdb;
    ////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registration, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("UserIsRegister", getActivity().MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        token = pref.getString("UserRToken","");
        userId = pref.getInt("UserID",-1);



        spinnerCouresController = (Spinner) v.findViewById(R.id.selectForExam);

        //nameExams.add("Select Exam >");
        //dataAdapterCourses = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameExams);
        //dataAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerCouresController.setAdapter(dataAdapterCourses);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                //==r0ArrayAdapter<String> dataAdapterCourses;
                listExams = o;
                nameExams = new ArrayList<>();
                if (o != null) {
                    nameExams.add("Select Exam >");
                    for (int i = 0; i < listExams.size(); i++) {
                        nameExams.add(listExams.get(i).getCoursename());
                    }

                    //dataAdapterCourses = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nameExams);
                    //dataAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //spinnerCouresController.setAdapter(dataAdapterCourses);
                    courseNames(nameExams);
                }
            }
        }).doRequestForTable(""+userId,"teacher");

        spinnerCouresController.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    examIDs = "" + listExams.get(i - 1).getExam_id();
                    Toast.makeText(getActivity(), "Wait...", Toast.LENGTH_LONG).show();
                    //getDataNotification();
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            attendanceStudants = o;
                            if (o != null && attendanceStudants.size()>0) {
                                for (int j = 0; j <attendanceStudants.size() ; j++) {
                                    AllStudants.add(attendanceStudants.get(j));
                                    //Toast.makeText(getActivity(), ""+AllStudants.size(), Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(getActivity(), "Students synchronized", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Don't have studants", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).doRequestForattendanceStudant(examIDs+"");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "Select Exam from list...", Toast.LENGTH_SHORT).show();
            }
        });
        internetConn = v.findViewById(R.id.internetConn);
        imageView = v.findViewById(R.id.imageView);
        textStudentID = v.findViewById(R.id.textStudentID);
        textViewState = v.findViewById(R.id.textViewState);

        try {
            backgroundText(isInternetAvailable());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////
        FloatingActionButton floatingShowStudent = v.findViewById(R.id.floatingShowStudent);
        floatingShowStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showStudent = new Intent(getActivity(), ShowStudents.class);
                startActivity(showStudent);
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////

        FloatingActionButton floatingAction = v.findViewById(R.id.floatingAddstd);
        floatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setBackgroundResource(R.drawable.rounded_corner);
                textStudentID.setText("Student ID");
                textViewState.setText("Status");
                View vewInflater = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_addstd, (ViewGroup) getView(), false);
                final EditText input = (EditText) vewInflater.findViewById(R.id.stdId);
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.ic_action_addstd)
                        .setTitle("Add student ")
                        .setView(vewInflater)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String stdid = input.getText().toString();
                                if(!stdid.isEmpty()) {
                                    for (int j = 0; j < AllStudants.size(); j++) {
                                        //Toast.makeText(getActivity(), "Add Student", Toast.LENGTH_SHORT).show();
                                        if(AllStudants.get(j).getStd_id().equalsIgnoreCase(stdid)){
                                            String img = AllStudants.get(j).getStd_picture();
                                            Glide.with(RegistrationFragment.this.getActivity())
                                                    .load(img)
                                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                    .skipMemoryCache(true)
                                                    .into(imageView);
                                            textStudentID.setText(AllStudants.get(j).getStd_id());
                                            textViewState.setText("Exist");
                                            textViewState.setBackgroundResource(R.color.Green);
                                            new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                                @Override
                                                public void onDataReceivedA(ArrayList o) {
                                                    ArrayList<String> arrayList = o;
                                                    if(o!=null){
                                                        String phone = arrayList.get(0);
                                                        String[] output = phone.split(":");
                                                        Toast.makeText(getActivity(), ""+output[2], Toast.LENGTH_SHORT).show();
                                                        //Toast.makeText(getActivity(), "Student successfully added", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }).doRequestForaddAttendanceStudant(stdid,examIDs+"",token);
                                            dialogInterface.dismiss();
                                            break;
                                        }else {
                                            //Toast.makeText(getActivity(), "No Student", Toast.LENGTH_SHORT).show();
                                            imageView.setBackgroundResource(R.drawable.logo);
                                            textStudentID.setText("Student ID");
                                            textViewState.setText("Not exist");
                                            textViewState.setBackgroundResource(R.color.Red);
                                            dialogInterface.dismiss();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Enter Student ID", Toast.LENGTH_LONG).show();
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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        int permission1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.NFC);
        if (permission1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.NFC}, 1);
        }

        MainActivity.setNFCInterfaceListner(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return v;
    }



    public void backgroundText(boolean x){
        if (x) {
            internetConn.setBackgroundResource(R.color.Green);
            isconn = false;
        } else {
            internetConn.setBackgroundResource(R.color.Red);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isconn = false;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean getDataNotification(){
    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
        @Override
        public void onDataReceivedA(ArrayList o) {
            myExams =  o;
            if(o != null) {
                //SharedPreferences pref = getActivity().getSharedPreferences("JunitTestQar", getActivity().MODE_PRIVATE);
                //SharedPreferences.Editor editor = pref.edit();
                //editor.putInt("JunitTestQarNumExams", myExams.size());
                //Toast.makeText(getActivity(), "Exm :"+myExams.size(), Toast.LENGTH_SHORT).show();
                //editor.commit();
                for (int i = 0; i <myExams.size() ; i++) {
                    //Toast.makeText(getActivity(), "" + myExams.get(i).getExam_id(), Toast.LENGTH_SHORT).show();
                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            attendanceStudants = o;
                            if (o != null && attendanceStudants.size()>0) {
                                //Toast.makeText(getActivity(), "Students synchronized", Toast.LENGTH_SHORT).show();
                                for (int j = 0; j <attendanceStudants.size() ; j++) {
                                        AllStudants.add(attendanceStudants.get(j));
                                        //Toast.makeText(getActivity(), ""+AllStudants.size(), Toast.LENGTH_SHORT).show();
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
    }).doRequestForTable(userId+"","teacher");
    return myExams!=null;
}
    public boolean isInternetAvailable() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }

    ArrayAdapter<String> dataAdapterCourses;
    public void courseNames(ArrayList<String> arrayList) {
        dataAdapterCourses = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        dataAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCouresController.setAdapter(dataAdapterCourses);
    }

    @Override
    public void OnIDRecived(String ID) {
        IDStudent = ID;
        imageView.setBackgroundResource(R.drawable.rounded_corner);
        textStudentID.setText("Student ID");
        textViewState.setText("Status");
                       if(!IDStudent.isEmpty()) {
                            for (int j = 0; j < AllStudants.size(); j++) {
                                //Toast.makeText(getActivity(), "Add Student", Toast.LENGTH_SHORT).show();
                                if(AllStudants.get(j).getStd_id().equalsIgnoreCase(IDStudent)){
                                    String img = AllStudants.get(j).getStd_picture();
                                    Glide.with(RegistrationFragment.this.getActivity())
                                            .load(img)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(imageView);
                                    textStudentID.setText(AllStudants.get(j).getStd_id());
                                    textViewState.setText("Exist");
                                    textViewState.setBackgroundResource(R.color.Green);
                                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                                        @Override
                                        public void onDataReceivedA(ArrayList o) {
                                            ArrayList<String> arrayList = o;
                                            if(o!=null){
                                                String phone = arrayList.get(0);
                                                String[] output = phone.split(":");
                                                Toast.makeText(getActivity(), ""+output[2], Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(getActivity(), "Student successfully added", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }).doRequestForaddAttendanceStudant(IDStudent,examIDs+"",token);
                                    break;
                                }else {
                                    //Toast.makeText(getActivity(), "No Student", Toast.LENGTH_SHORT).show();
                                    imageView.setBackgroundResource(R.drawable.logo);
                                    textStudentID.setText("Student ID");
                                    textViewState.setText("Not exist");
                                    textViewState.setBackgroundResource(R.color.Red);
                                }
                            }
                        }else {
                            Toast.makeText(getActivity(), "Enter Student ID", Toast.LENGTH_LONG).show();
                        }

        //Toast.makeText(getActivity(), " OnIDRecived :"+ID, Toast.LENGTH_SHORT).show();
    }

}
