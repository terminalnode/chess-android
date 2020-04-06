package com.example.newtonchess.gui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.newtonchess.StaticValues;
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
    Log.i("GAMES", "Is the game finished? " + game.isFinished());
    Intent intent = new Intent(view.getContext(), PlayScreenActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    intent.putExtra(StaticValues.INTENT_GAME, game);

    Log.i("GAMES", "Starting activity playScreenIntent");
    context.startActivity(intent);
  }

  private void deactivateButton() {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }
}
