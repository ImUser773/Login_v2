package com.droid_wizard.iamuser773.login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by IamUser773 on 20/6/2559.
 */
public class LoginActivity extends AppCompatActivity {
    private String USER;
    private String PICTURE;
    ImageView img_user;
    TextView txt_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showuser);
        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();

            if (bundle != null){
                USER = bundle.getString("USERNAME");
                PICTURE = bundle.getString("PICTURE");
                Log.d("UserList", PICTURE);
                bindView();
                setView();
            }

        }




    }
    private void bindView(){
        img_user = (ImageView) findViewById(R.id.img_user);
        txt_name = (TextView) findViewById(R.id.txt_name);

    }

    private void setView(){
        Bitmap bitmap = BitmapFactory.decodeFile(PICTURE);
        img_user.setImageBitmap(bitmap);
        txt_name.setText(USER);

    }
}
