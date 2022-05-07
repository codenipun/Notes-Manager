package com.codenipun.notesandpasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class splashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        Thread thread = new Thread(){
            public void run(){
            try {
                sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(mAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(splashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(splashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            }
        };
        thread.start();
    }
}