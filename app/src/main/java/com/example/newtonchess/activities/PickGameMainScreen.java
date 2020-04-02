package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.gui.ChallengesListAdapter;
import com.example.newtonchess.gui.GamesListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    showGamesButton.callOnClick();
  }

  private void activateGamesListAdapter(View view) {
    deactivateButton(showGamesButton);
    activateButton(showChallengesButton);
    listView.setAdapter(gamesListAdapter);
    header.setText(R.string.showGamesText);
    updateGames(view);
  }

  private void activateChallengesListAdapter(View view) {
    deactivateButton(showChallengesButton);
    activateButton(showGamesButton);
    listView.setAdapter(challengesListAdapter);
    header.setText(R.string.showChallengesText);
    updateChallenges(view);
  }

  private void deactivateButton(Button button) {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }

  private void activateButton(Button button) {
    button.setClickable(true);
    button.setAlpha(1F);
  }

  private void updateGames(View view) {
  }

  private void updateChallenges(View view) {
    Log.i("CHALLENGES", "Updating challenges, my token is: " + token);

    Call<List<ChallengeEntity>> call = RetrofitHelper
        .getChallengeService()
        .getChallengesToMe(token.getTokenString());

    call.enqueue(new Callback<List<ChallengeEntity>>() {
      @Override
      public void onResponse(Call<List<ChallengeEntity>> call, Response<List<ChallengeEntity>> response) {
        List<ChallengeEntity> challenges = response.body();

        if (challenges != null) {
          challengesListAdapter.clear();
          challengesListAdapter.addAll(challenges);
          Log.i("CHALLENGES", "Got challenges, there are: " + challenges.size());
        } else {
          Log.i("CHALLENGES", "Challenges is null!");
        }
      }

      @Override
      public void onFailure(Call<List<ChallengeEntity>> call, Throwable t) {

      }
    });
  }

  private void unknownError(View view) {
    Snackbar.make(
        view,
        R.string.somethingWentWrong,
        Snackbar.LENGTH_LONG
    ).show();
  }
}