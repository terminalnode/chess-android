package com.example.newtonchess.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.newtonchess.R;
import com.example.newtonchess.chesscomponents.ChessBoard;

public class PlayScreenActivity extends AppCompatActivity {
  private ChessBoard chessBoard;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_screen);

    chessBoard = findViewById(R.id.chessBoard);
    chessBoard.setOnTouchListener(chessBoard::onTouch);
  }
}
