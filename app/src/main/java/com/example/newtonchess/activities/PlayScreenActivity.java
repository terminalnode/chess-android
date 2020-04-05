package com.example.newtonchess.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.chesscomponents.ChessBoard;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class PlayScreenActivity extends AppCompatActivity {
  private ChessBoard chessBoard;
  private TokenEntity token;
  private GameEntity game;
  private Button refreshButton;
  private long lastRefresh;
  private long refreshRate;
  private Handler threadHandler;
  private Runnable autoUpdateFunction;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_screen);

    // Extract contents from intent
    Log.i(StaticValues.PLAYSCREEN, "Play screen starting to extract contents from intent.");
    token = getIntent().getParcelableExtra(StaticValues.INTENT_TOKEN);
    game = getIntent().getParcelableExtra(StaticValues.INTENT_GAME);
    Log.i(StaticValues.PLAYSCREEN, "Play screen extracted contents from intent.");
    Log.i(StaticValues.PLAYSCREEN, "Got a token? " + (token != null));
    Log.i(StaticValues.PLAYSCREEN, "Got a game? " + (game != null));
    Log.i(StaticValues.PLAYSCREEN, "Game is finished? " + game.isFinished());

    // Set opponent name
    String opponentsName;
    if (game != null) {
      opponentsName = game.isGettingPlayerWhite() ?
          game.getBlackPlayer().getName() : game.getWhitePlayer().getName();
    } else {
      opponentsName = getString(R.string.userUnknown);
    }
    ((TextView) findViewById(R.id.opponentName)).setText(opponentsName);

    // Find views then update the game
    refreshButton = findViewById(R.id.refreshButton);
    chessBoard = findViewById(R.id.chessBoard);

    // Set initial state of the chessboard
    chessBoard.setWhoseTurnPawn(findViewById(R.id.whoseTurnPawn));
    chessBoard.setWhoseTurnTextView(findViewById(R.id.whoseTurnTextView));
    chessBoard.setGameOverTextView(findViewById(R.id.gameOverTextView));
    chessBoard.setToken(token);
    chessBoard.loadFromGameEntity(game, token.getPlayer());

    // Bind button listener
    chessBoard.setOnTouchListener(chessBoard::onTouch);
    refreshButton.setOnClickListener(this::refreshButtonClicked);

    // Create handler to automatically check for updates
    autoUpdateFunction = () -> {
      long nextRefresh = lastRefresh + refreshRate;

      if (System.currentTimeMillis() > nextRefresh) {
        if (chessBoard.isWhitesTurn() != chessBoard.isWhite()) {
          refreshButtonClicked(null);
        } else {
          Log.i(StaticValues.PLAYSCREEN, "Still my turn, not refreshing.");
        }
        lastRefresh = System.currentTimeMillis();
        nextRefresh = lastRefresh + refreshRate;
      }

      // Run again in 10 seconds
      Log.i(StaticValues.PLAYSCREEN, "Next refresh in: " + (nextRefresh - System.currentTimeMillis()));
      threadHandler.postDelayed(autoUpdateFunction, nextRefresh - System.currentTimeMillis());
    };

    // Start the auto updater
    refreshRate = 10000;
    lastRefresh = System.currentTimeMillis();
    threadHandler = new Handler();
    threadHandler.postDelayed(autoUpdateFunction, refreshRate + 100);
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, PickGameActivity.class);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void refreshButtonClicked(@Nullable View view) {
    Log.i(StaticValues.PLAYSCREEN, "Doing a refresh!");
    deactivateButton();
    int currentTurnsTaken = game.getTurnsTaken();
    lastRefresh = System.currentTimeMillis();

    RetrofitHelper
        .getGameService()
        .getGame(token.getTokenString(), game.getId())
        .enqueue(new Callback<GameEntity>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<GameEntity> call, Response<GameEntity> response) {
            GameEntity gameEntity = response.body();
            if (gameEntity != null) {
              if (gameEntity.getTurnsTaken() != currentTurnsTaken) {
                game = gameEntity;
                chessBoard.setSelectedX(-1);
                chessBoard.setSelectedY(-1);
                chessBoard.setSelectedPiece(null);
                chessBoard.loadFromGameEntity(game, token.getPlayer());
              }
              activateButton();
              return;
            }

            if (view != null) {
              showSnackbar(R.string.somethingWentWrong, view);
            }
          }

          @Override
          @EverythingIsNonNull
          public void onFailure(Call<GameEntity> call, Throwable t) {
            if (view != null) {
              showSnackbar(R.string.somethingWentWrong, view);
            }
          }
        });
  }

  private void showSnackbar(int resourceId, View view) {
    Snackbar.make(
        view,
        resourceId,
        Snackbar.LENGTH_LONG
    ).show();
    activateButton();
  }

  private void deactivateButton() {
    refreshButton.setAlpha(0.5F);
    refreshButton.setClickable(false);
    refreshButton.setText(R.string.refreshing);
  }

  @SuppressLint("ClickableViewAccessibility")
  private void activateButton() {
    refreshButton.setAlpha(1F);
    refreshButton.setClickable(true);
    refreshButton.setText(R.string.refresh);
  }
}
