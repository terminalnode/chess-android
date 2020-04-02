package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.TokenEntity;

public class PickGameMainScreen extends AppCompatActivity {
  TokenEntity token;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_game_activity);

    // Extract token from intent
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("PlaPlay activity started, token: %s", token));
  }

}
