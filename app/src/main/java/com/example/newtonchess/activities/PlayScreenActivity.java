package com.example.newtonchess.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.chesscomponents.ChessBoard;

public class PlayScreenActivity extends AppCompatActivity {
  private ChessBoard chessBoard;
  private TokenEntity token;
  Button refreshButton;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_screen);

    // Extract contents from intent
    Log.i(StaticValues.PLAYSCREEN, "Play screen starting to extract contents from intent.");
    token = getIntent().getParcelableExtra(StaticValues.INTENT_TOKEN);
    GameEntity game = getIntent().getParcelableExtra(StaticValues.INTENT_GAME);
    Log.i(StaticValues.PLAYSCREEN, "Play screen extracted contents from intent.");
    Log.i(StaticValues.PLAYSCREEN, "Got a token? " + (token != null));
    Log.i(StaticValues.PLAYSCREEN, "Got a game? " + (game != null));

    // Set opponent name
    TextView opponentName = findViewById(R.id.opponentName);
    if (game != null && game.isGettingPlayerWhite()) {
      opponentName.setText(game.getWhitePlayer().getName());
    } else if (game != null) {
      opponentName.setText(game.getBlackPlayer().getName());
    }

    // Find views
    refreshButton = findViewById(R.id.refreshButton);

    chessBoard = findViewById(R.id.chessBoard);
    chessBoard.setWhoseTurnPawn(findViewById(R.id.whoseTurnPawn));
    chessBoard.setWhoseTurnTextView(findViewById(R.id.whoseTurnTextView));
    chessBoard.setToken(token);
    chessBoard.loadFromGameEntity(game, token.getPlayer());
    chessBoard.setOnTouchListener(chessBoard::onTouch);

    // Bind button listener
    refreshButton.setOnClickListener(this::refreshButtonClicked);
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, PickGameActivity.class);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void refreshButtonClicked(View view) {
    deactivateButton();
  }

  private void deactivateButton() {
    refreshButton.setClickable(true);
    refreshButton.setAlpha(0.5F);
  }

  private void activateButton() {
    refreshButton.setClickable(false);
    refreshButton.setAlpha(1F);
  }
}
