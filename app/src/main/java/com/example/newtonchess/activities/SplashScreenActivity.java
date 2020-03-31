package com.example.newtonchess.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.newtonchess.R;

public class SplashScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    new Handler().postDelayed(() -> {
      Intent loginIntent = new Intent(this, LoginScreenActivity.class);
      startActivity(loginIntent);
      finish();
    }, 4000);
  }
}
