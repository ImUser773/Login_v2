package com.droid_wizard.iamuser773.login;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import data.DataHandler;
import model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String USERNAME="admin";
    private static final String PASSWORD="admin";
    private static boolean status = false;
    private ArrayList<User> UserList = new ArrayList<>();
    String username,password;
    LinearLayout content;
    EditText ed_username;
    EditText ed_password;
    Button bt_login;
    Button bt_register;
    Button bt_logout;
    Animation animTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindingview();
        checkstatus();
        bt_login.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_login:
                login();
                break;
            case R.id.button_logout:
                logout();
                break;
            case R.id.button_register:
                anim(v);
                checkstatus();
                RegisterIntent();
                break;


        }
    }

    private void checkstatus(){
        if(status){
            content.setVisibility(View.VISIBLE);
            bt_logout.setVisibility(View.GONE);
            bt_register.setVisibility(View.GONE);
        }else{
            content.setVisibility(View.GONE);
            bt_logout.setVisibility(View.VISIBLE);
            bt_register.setVisibility(View.VISIBLE);
        }
    }

    private void bindingview() {
        content = (LinearLayout) findViewById(R.id.content);
        ed_username = (EditText) findViewById(R.id.edit1);
        ed_password = (EditText) findViewById(R.id.edit2);
        bt_login = (Button) findViewById(R.id.button_login);
        bt_logout = (Button) findViewById(R.id.button_logout);
        bt_register = (Button) findViewById(R.id.button_register);
    }

    private void login(){
        DataHandler db = new DataHandler(this);
        username = ed_username.getText().toString();
        password = ed_password.getText().toString();
        UserList = db.getUser(username,password);


        if(!username.isEmpty() && !password.isEmpty() && !UserList.isEmpty()){
            if(username.equals(UserList.get(0).getUsername()) && password.equals(UserList.get(0).getPassword())){
                status = false;
                checkstatus();
                LoginActivityIntent();
                Toast.makeText(this,"เข้าสู่ระบบเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"ข้อมูลไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
        }

    }
    private void logout(){
        status = true;
        checkstatus();
    }
    private void anim(View v){
        animTranslate = AnimationUtils.loadAnimation(this,R.anim.translate);
        v.startAnimation(animTranslate);
    }

    private void RegisterIntent(){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    private void LoginActivityIntent(){
        Intent i = new Intent(this,LoginActivity.class);
        i.putExtra("USERNAME",UserList.get(0).getUsername());
        i.putExtra("PICTURE",UserList.get(0).getPicture());
        startActivity(i);
    }
}
