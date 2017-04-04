package com.example.jalapeno.eaglevision2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class SplashTheme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_theme);
        Intent intent = new Intent(this, NavActivity.class);
        startActivity(intent);
        finish();
    }
}
