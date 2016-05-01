package com.teampet.petsitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // just forward to main activity
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }
}