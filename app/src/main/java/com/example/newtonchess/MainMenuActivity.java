package com.example.newtonchess;

import android.os.Bundle;

import com.example.newtonchess.api.UserData;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {
  TokenEntity token;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Main menu started, token: %s", token));
  }
}
