package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.gui.ChallengesListAdapter;
import com.example.newtonchess.gui.GamesListAdapter;

import java.util.ArrayList;

public class PickGameMainScreen extends AppCompatActivity {
  TokenEntity token;
  Button showGamesButton, showChallengesButton;
  ListView listView;
  ChallengesListAdapter challengesListAdapter;
  GamesListAdapter gamesListAdapter;
  TextView header;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_game_activity);

    // Extract token from intent
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Play screen activity started, token: %s", token));

    // Retrieve various views by id
    showGamesButton = findViewById(R.id.showGamesButton);
    showChallengesButton = findViewById(R.id.showChallengesButton);
    listView = findViewById(R.id.gameScreenListView);
    header = findViewById(R.id.gameScreenHeader);

    // Set up list adapters
    challengesListAdapter = new ChallengesListAdapter(
        this,
        R.layout.list_single_challenge,
        new ArrayList<>(),
        token);

    gamesListAdapter = new GamesListAdapter(
        this,
        R.layout.list_single_game,
        new ArrayList<>(),
        token);

    // Set button listeners
    showGamesButton.setOnClickListener(this::activateGamesListAdapter);
    showChallengesButton.setOnClickListener(this::activateChallengesListAdapter);

    // Default view is active games, so lets activate that
    activateGamesListAdapter(null);
  }

  private void activateGamesListAdapter(View view) {
    deactivateButton(showGamesButton);
    activateButton(showChallengesButton);
    listView.setAdapter(gamesListAdapter);
    header.setText(R.string.showGamesText);
  }

  private void activateChallengesListAdapter(View view) {
    deactivateButton(showChallengesButton);
    activateButton(showGamesButton);
    listView.setAdapter(challengesListAdapter);
    header.setText(R.string.showChallengesText);
  }

  private void deactivateButton(Button button) {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }

  private void activateButton(Button button) {
    button.setClickable(true);
    button.setAlpha(1F);
  }
}
