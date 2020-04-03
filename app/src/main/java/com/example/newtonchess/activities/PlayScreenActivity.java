package com.example.newtonchess.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
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
    Log.i("ACTIVITY", "Play screen starting to extract contents from intent.");
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", "Token is: " + token);
    GameEntity game = getIntent().getParcelableExtra("GameEntity");
    Log.i("ACTIVITY", "Game is: " + game);

    chessBoard = findViewById(R.id.chessBoard);
    chessBoard.loadFromGameEntity(game, token.getPlayer());
    chessBoard.setOnTouchListener(chessBoard::onTouch);
  }
}
