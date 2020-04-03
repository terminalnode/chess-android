package com.example.newtonchess.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.chesscomponents.ChessBoard;

public class PlayScreenActivity extends AppCompatActivity {
  private ChessBoard chessBoard;
  private TokenEntity token;

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

    chessBoard = findViewById(R.id.chessBoard);
    chessBoard.loadFromGameEntity(game, token.getPlayer());
    chessBoard.setOnTouchListener(chessBoard::onTouch);
  }
}
