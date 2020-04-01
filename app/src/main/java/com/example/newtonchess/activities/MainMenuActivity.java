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
import com.google.android.material.snackbar.Snackbar;

public class MainMenuActivity extends AppCompatActivity {
  TokenEntity token;
  Button activeGamesButton, newGameButton, friendsListButton, logoutButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    // Set buttons
    activeGamesButton = findViewById(R.id.activeGamesButton);
    newGameButton = findViewById(R.id.newGameButton);
    friendsListButton = findViewById(R.id.friendsListButton);
    logoutButton = findViewById(R.id.logoutButton);

    // Extract token from intent
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Main menu started, token: %s", token));

    // Set username in UI
    TextView welcomeTextView = findViewById(R.id.welcomeTextView);
    PlayerEntity player = token.getPlayer();
    String welcomeTextString = player == null ?
        getString(R.string.mainmenuWelcome, getString(R.string.userUnknown)) :
        getString(R.string.mainmenuWelcome, player.getName());
    welcomeTextView.setText(welcomeTextString);

    // Set button listeners
    activeGamesButton.setOnClickListener(this::activeGamesButtonPress);
    newGameButton.setOnClickListener(this::newGameButtonPress);
    friendsListButton.setOnClickListener(this::friendsButtonPress);
    logoutButton.setOnClickListener(this::logoutButtonPress);
  }

  private void activeGamesButtonPress(View view) {
    Snackbar.make(
        view,
        R.string.activeGamesButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();
  }

  private void newGameButtonPress(View view) {
    Snackbar.make(
        view,
        R.string.newGameButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();
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
