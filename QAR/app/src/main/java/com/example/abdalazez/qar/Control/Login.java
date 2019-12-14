package com.example.abdalazez.qar.Control;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Model.MLogin;
import com.example.abdalazez.qar.R;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText passWord;
    MLogin MLogin;
    Boolean success = false;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        SharedPreferences pref = getSharedPreferences("UserIsRegister",MODE_PRIVATE);
        String typeuser = pref.getString("UserRType","");
        if (typeuser.equalsIgnoreCase("teacher")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("typeUser", 3);
            startActivity(intent);
            finish();
        } else if (typeuser.equalsIgnoreCase("admin")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("typeUser", 2);
            startActivity(intent);
            finish();
        }
    }

    public void butSignin(View view) {

        if(!userName.getText().toString().isEmpty() && !passWord.getText().toString().isEmpty()){
        new VolleyRequests().setIReceiveData(new VolleyRequests.IReceiveData() {
            @Override
            public void onDataReceived(Object o) {
                if (o != null) {
                    MLogin = (MLogin) o;
                    type = MLogin.getType();

                    SharedPreferences pref = getSharedPreferences("UserIsRegister", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("UserIsRegister", true);
                    editor.putInt("UserID", MLogin.getId());
                    editor.putString("UserNmae", MLogin.getName());
                    editor.putString("UserMobile", MLogin.getMobile());
                    editor.putString("UserImage", MLogin.getImage());
                    editor.putString("UserRToken", MLogin.getRemember_token());
                    editor.putString("UserRType", MLogin.getType());
                    editor.putBoolean("UserRNotification",true);
                    editor.commit();
                    //Log.d("JSON", MLogin.getName() + "");
                    //textView.setText("" + trans.getText().get(0));
                    Toast.makeText(Login.this,"?!"+ MLogin.getType()+"||"+ MLogin.getName()+"||"+ MLogin.getMobile(),Toast.LENGTH_LONG).show();

                    if (type.equalsIgnoreCase("teacher")) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("typeUser", 3);
                        startActivity(intent);
                        finish();
                    } else if (type.equalsIgnoreCase("admin")) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("typeUser", 2);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(Login.this, "Error  in email or password", Toast.LENGTH_SHORT).show();
                }
                }
        }).doRequestForLogin(userName.getText().toString(),passWord.getText().toString());
        //Toast.makeText(getApplicationContext(), "Txt :" + "Lng:" , Toast.LENGTH_SHORT).show();

        }else{
            //Intent intent = new Intent(MLogin.this, MLogin.class);
            //startActivity(intent);
            //finish();
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Fill all the boxes", Toast.LENGTH_SHORT).show();
        }
    }
    public void showHide(View view) {
        TextView pass = findViewById(R.id.passWord);
        ImageButton imgbut = findViewById(R.id.showHide);
        int type = pass.getInputType();
        //129 hide
        //145 show
        if(type == 129){
            pass.setInputType(145);
            imgbut.setBackgroundResource(R.drawable.e2);
        }else{
            pass.setInputType(129);
            imgbut.setBackgroundResource(R.drawable.e1);
        }
        // Toast.makeText(LoginActivity.this, "+"+ nAccount.getInputType(), Toast.LENGTH_SHORT).show();
    }

}
