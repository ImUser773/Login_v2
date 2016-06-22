package com.droid_wizard.iamuser773.login;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DataHandler;
import model.User;

/**
 * Created by IamUser773 on 25/5/2559.
 */
public class RegisterActivity extends AppCompatActivity{
    private static final int SELECT_IMAGE = 1;
    private String Username = null;
    private String Password = null;
    private String Email = null;
    private String Picture = null;
    DataHandler database;
    EditText edt_username;
    EditText edt_password;
    EditText edt_email;
    Button btn_addimage;
    Button btn_register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = new DataHandler(RegisterActivity.this);
        Bindingview();
        GetImage();
        ButtonRegister();
    }

    private void Bindingview() {
        edt_username = (EditText) findViewById(R.id.edit1);
        edt_password = (EditText) findViewById(R.id.edit2);
        edt_email = (EditText) findViewById(R.id.edit3);
        btn_addimage = (Button) findViewById(R.id.button_image);
        btn_register = (Button) findViewById(R.id.button_register);
    }

    private void addUser() {
        User user = new User();
        user.setUsername(Username);
        user.setPassword(Password);
        user.setEmail(Email);
        user.setPicture(Picture);
        database.addUser(user);
    }

    private void ButtonRegister() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });
    }

    private void GetData() {
        Username = edt_username.getText().toString();
        Password = edt_password.getText().toString();
        Email = edt_email.getText().toString();
        if(!Username.equals("")&&!Password.equals("")&&!Email.equals("")&& Picture != null){
            // เพิ่มข้อมูลไปยังฐานข้อมูล
            addUser();
            Toast.makeText(this,"สมัครสมาชิกเรียบร้อย",Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
        }
    }

    private void GetImage() {
        btn_addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_IMAGE){
                Uri imageuri = data.getData();
                Picture = getRealPathFromURI(imageuri);
                Toast.makeText(this,""+Picture,Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(uri,projection,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


}
