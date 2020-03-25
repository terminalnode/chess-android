package com.example.newtonchess;

import android.os.Bundle;

import com.example.newtonchess.api.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

  UserData loggedInUserData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    loggedInUserData = getIntent().getParcelableExtra("UserData");

  }

  public UserData getLoggedInUserData() {
    return loggedInUserData;
  }
}
