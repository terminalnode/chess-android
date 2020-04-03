package com.example.newtonchess.gui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.newtonchess.activities.PlayScreenActivity;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.TokenEntity;

public class GamesListStartGameListener implements View.OnClickListener {
  private View view;
  private GameEntity game;
  private String token;
  private Button button;
  private Context context;

  GamesListStartGameListener(GameEntity game, TokenEntity token, Button button, Context context) {
    this.game = game;
    this.token = token.getTokenString();
    this.button = button;
    this.context = context;
  }

  @Override
  public void onClick(View view) {
    Log.i("GAMES", "Starting a game");
    this.view = view;
    deactivateButton();

    Log.i("GAMES", "Creating playScreenIntent");
    Intent playScreenIntent = new Intent(view.getContext(), PlayScreenActivity.class);
    Log.i("GAMES", "Packing token: " + token);
    playScreenIntent.putExtra("TokenEntity", token);
    Log.i("GAMES", "Packing game: " + game);
    playScreenIntent.putExtra("GameEntity", game);
    Log.i("GAMES", "Starting activity playScreenIntent");
    context.startActivity(playScreenIntent);
    Log.i("GAMES", "playScreenIntent started. I should be gone now I think.");
  }

  private void deactivateButton() {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }
}
