// Skriven av Sebastian, redigerad av Alexander.
package com.example.newtonchess.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;

public class MainMenuActivity extends AppCompatActivity {
  TokenEntity token;
  Button playButton, newGameButton, friendsListButton, logoutButton;
  TextView userNameTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    // Set buttons
    playButton = findViewById(R.id.playButton);
    friendsListButton = findViewById(R.id.friendsListButton);
    logoutButton = findViewById(R.id.logoutButton);
    userNameTextView = findViewById(R.id.userNameTextView);

    // Extract token from intent
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Main menu started, token: %s", token));

    // Set username in UI
    PlayerEntity player = token.getPlayer();
    String welcomeTextString =
        player == null ? "anonymous" : player.getName();
    userNameTextView.setText(welcomeTextString);

    // Set button listeners
    playButton.setOnClickListener(this::playButtonPress);
    friendsListButton.setOnClickListener(this::friendsButtonPress);
    logoutButton.setOnClickListener(this::logoutButtonPress);
  }

  @Override
  public void onBackPressed() {
    logoutButtonPress(null);
  }

  private void playButtonPress(@Nullable View view) {
    Intent intent = new Intent(this, PickGameActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("TokenEntity", token);
    startActivity(intent);
  }

  private void friendsButtonPress(@Nullable View view) {
    Intent intent = new Intent(this, FriendsListActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("TokenEntity", token);
    startActivity(intent);
  }

  private void logoutButtonPress(@Nullable View view) {
    ApiLogin.logout(token.getTokenString());
    Intent intent = new Intent(this, LoginScreenActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    SharedPreferences.Editor sp = PreferenceManager.getDefaultSharedPreferences(this).edit();
    sp.remove(StaticValues.PREF_TOKEN);
    sp.apply();

    startActivity(intent);
  }
}
