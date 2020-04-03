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
  private GameEntity game;
  private TokenEntity token;
  private Button button;
  private Context context;

  GamesListStartGameListener(GameEntity game, TokenEntity token, Button button, Context context) {
    this.game = game;
    this.token = token;
    this.button = button;
    this.context = context;
  }

  @Override
  public void onClick(View view) {
    Log.i("GAMES", "Starting a game, just have to create the intent.");
    deactivateButton();

    Log.i("GAMES", "Creating playScreenIntent");
    Intent playScreenIntent = new Intent(view.getContext(), PlayScreenActivity.class);
    Log.i("GAMES", "Packing token: " + token);
    playScreenIntent.putExtra("TokenEntity", token);
    Log.i("GAMES", "Packing game: " + game);
    playScreenIntent.putExtra("GameEntity", game);
    Log.i("GAMES", "Starting activity playScreenIntent");
    context.startActivity(playScreenIntent);
    Log.i("GAMES", "playScreenIntent activity started. Bye...");
  }

  private void deactivateButton() {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }
}
