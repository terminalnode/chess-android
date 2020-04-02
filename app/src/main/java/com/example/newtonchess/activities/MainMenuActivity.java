package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;

public class MainMenuActivity extends AppCompatActivity {
  TokenEntity token;
  Button activeGamesButton, newGameButton, friendsListButton, logoutButton;
  TextView userNameTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    // Set buttons
    activeGamesButton = findViewById(R.id.playButton);
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
    activeGamesButton.setOnClickListener(this::activeGamesButtonPress);
    newGameButton.setOnClickListener(this::newGameButtonPress);
    friendsListButton.setOnClickListener(this::friendsButtonPress);
    logoutButton.setOnClickListener(this::logoutButtonPress);
  }

  private void activeGamesButtonPress(View view) {
  }

  private void newGameButtonPress(View view) {
  }

  private void friendsButtonPress(View view) {
    Intent friendsListIntent = new Intent(view.getContext(), FriendsListActivity.class);
    friendsListIntent.putExtra("TokenEntity", token);
    startActivity(friendsListIntent);
  }

  private void logoutButtonPress(View view) {
    ApiLogin.logout(token.getTokenString());
    Intent loginScreenIntent = new Intent(view.getContext(), LoginScreenActivity.class);
    startActivity(loginScreenIntent);
  }
}
