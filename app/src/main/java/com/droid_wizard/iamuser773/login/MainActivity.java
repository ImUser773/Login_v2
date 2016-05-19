package com.droid_wizard.iamuser773.login;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String USERNAME="admin";
    private static final String PASSWORD="admin";
    private static boolean status = false;
    String username,password;
    LinearLayout content;
    EditText ed_username;
    EditText ed_password;
    Button bt_login;
    Button bt_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindingview();
        checkstatus();
        bt_login.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
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


        }
    }

    private void checkstatus(){
        if(status){
            content.setVisibility(View.VISIBLE);
            bt_logout.setVisibility(View.GONE);
        }else{
            content.setVisibility(View.GONE);
            bt_logout.setVisibility(View.VISIBLE);
        }
    }

    private void bindingview() {
        content = (LinearLayout) findViewById(R.id.content);
        ed_username = (EditText) findViewById(R.id.edit1);
        ed_password = (EditText) findViewById(R.id.edit2);
        bt_login = (Button) findViewById(R.id.button_login);
        bt_logout = (Button) findViewById(R.id.button_logout);
    }

    private void login(){
        username = ed_username.getText().toString();
        password = ed_password.getText().toString();
        if(!username.equals("")&&!password.equals("")){
            if(username.equals(USERNAME)&&password.equals(PASSWORD)){
                status = false;
                checkstatus();
                Toast.makeText(this,"เข้าสู่ระบบเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"ข้อมูลไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"กรุณาใส่ข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
        }

    }
    private void logout(){
        status = true;
        checkstatus();
    }
}
