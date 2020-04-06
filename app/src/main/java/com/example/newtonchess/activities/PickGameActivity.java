package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.gui.ChallengesListAdapter;
import com.example.newtonchess.gui.GamesListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Activity for starting games and accepting/denying challenges.
 * Whenever the showGamesButton or showChallengesButton are pressed
 * the latest games or challenges are fetched from the server and fed
 * into the corresponding ListAdapters, then displayed in the list
 * view at the center of the screen.
 *
 * @author Alexander Rundberg
 */
public class PickGameActivity extends AppCompatActivity {
  TokenEntity token;
  Button showGamesButton, showChallengesButton;
  ListView listView;
  ChallengesListAdapter challengesListAdapter;
  GamesListAdapter gamesListAdapter;
  TextView header, emptyListTop, emptyListBottom;

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
    emptyListTop = findViewById(R.id.pickActivityEmptyListTop);
    emptyListBottom = findViewById(R.id.pickActivityEmptyListBottom);

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
        token,
        getString(R.string.userUnknown),
        getResources().getColor(R.color.colorAccentDark));

    // Set button listeners
    showGamesButton.setOnClickListener(this::activateGamesListAdapter);
    showChallengesButton.setOnClickListener(this::activateChallengesListAdapter);

    // Default view is active games, so lets activate that
    showGamesButton.callOnClick();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void activateGamesListAdapter(View view) {
    deactivateButton(showGamesButton);
    activateButton(showChallengesButton);
    gamesListAdapter.clear();
    listView.setAdapter(gamesListAdapter);
    header.setText(R.string.showGamesText);
    updateGames(view);
  }

  private void activateChallengesListAdapter(View view) {
    deactivateButton(showChallengesButton);
    activateButton(showGamesButton);
    challengesListAdapter.clear();
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
    setFetchTextFetchingList();

    RetrofitHelper
        .getGameService()
        .getAllGames(token.getTokenString())
        .enqueue(new Callback<List<GameEntity>>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<List<GameEntity>> call, Response<List<GameEntity>> response) {
            List<GameEntity> games = response.body();

            if (games != null && games.size() > 0) {
              gamesListAdapter.clear();
              gamesListAdapter.addAll(games);
              hideFetchText();

            } else if (games != null) {
              gamesListAdapter.clear();
              setFetchTextNothingHere();

            } else {
              setFetchTextFailed();
            }
          }

          @Override
          @EverythingIsNonNull
          public void onFailure(Call<List<GameEntity>> call, Throwable t) {
            setFetchTextFailed();
          }
        });
  }

  private void updateChallenges(View view) {
    setFetchTextFetchingList();

    RetrofitHelper
        .getChallengeService()
        .getChallengesToMe(token.getTokenString())
        .enqueue(new Callback<List<ChallengeEntity>>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<List<ChallengeEntity>> call, Response<List<ChallengeEntity>> response) {
            List<ChallengeEntity> challenges = response.body();

            if (challenges != null && challenges.size() > 0) {
              challengesListAdapter.clear();
              challengesListAdapter.addAll(challenges);
              hideFetchText();

            } else if (challenges != null) {
              challengesListAdapter.clear();
              setFetchTextNothingHere();

            } else {
              setFetchTextFailed();
              Log.i("CHALLENGES", "Challenges is null!");
            }
          }

          @Override
          @EverythingIsNonNull
          public void onFailure(Call<List<ChallengeEntity>> call, Throwable t) {
            setFetchTextFailed();
          }
        });
  }

  private void setFetchTextNothingHere() {
    emptyListTop.setText(R.string.emptyListTextInanimate);
    emptyListBottom.setText(R.string.thatsTooBad);
    showFetchText();
  }

  private void setFetchTextFetchingList() {
    emptyListTop.setText(R.string.fetchingList);
    emptyListBottom.setText(R.string.standBy);
    showFetchText();
  }

  private void setFetchTextFailed() {
    emptyListTop.setText(R.string.failedToFetchList);
    emptyListBottom.setText(R.string.thatsTooBad);
    showFetchText();
  }

  private void showFetchText() {
    emptyListTop.setVisibility(View.VISIBLE);
    emptyListBottom.setVisibility(View.VISIBLE);
  }

  private void hideFetchText() {
    emptyListTop.setVisibility(View.GONE);
    emptyListBottom.setVisibility(View.GONE);
  }
}
