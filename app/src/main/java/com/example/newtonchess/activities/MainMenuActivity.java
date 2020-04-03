// Skriven av Sebastian, redigerad av Alexander.
package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
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
    newGameButton = findViewById(R.id.newGameButton);
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
    Intent playIntent = new Intent(this, PickGameActivity.class);
    playIntent.putExtra("TokenEntity", token);
    startActivity(playIntent);
  }

  private void friendsButtonPress(@Nullable View view) {
    Intent intent = new Intent(this, FriendsListActivity.class);
    intent.putExtra("TokenEntity", token);
    startActivity(intent);
  }

  private void logoutButtonPress(@Nullable View view) {
    ApiLogin.logout(token.getTokenString());
    Intent loginScreenIntent = new Intent(this, LoginScreenActivity.class);
    startActivity(loginScreenIntent);
  }
}
