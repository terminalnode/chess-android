package com.example.newtonchess.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GamesListAdapter extends ArrayAdapter<GameEntity> {
  private Context context;
  private int listLayout;
  private TokenEntity token;
  private PlayerEntity thisPlayer;

  public GamesListAdapter(
      @NonNull Context context,
      int listLayout,
      @NonNull List<GameEntity> objects,
      TokenEntity token) {

    super(context, listLayout, objects);
    this.context = context;
    this.listLayout = listLayout;
    this.token = token;
    thisPlayer = token.getPlayer();
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    GameEntity game = getItem(position);
    PlayerEntity blackPlayer = game == null ? null : game.getBlackPlayer();
    PlayerEntity whitePlayer = game == null ? null : game.getWhitePlayer();
    String turnsTaken = game == null ? "???" : "#" + game.getTurnsTaken();

    String opponentName = convertView
        .getResources()
        .getString(R.string.userUnknown);

    if (blackPlayer != null && whitePlayer != null) {
      opponentName = blackPlayer.equals(thisPlayer) ?
          blackPlayer.getName() : whitePlayer.getName();
    }

    LayoutInflater inflater = LayoutInflater.from(context);
    convertView = inflater.inflate(listLayout, parent, false);

    // Find the views inside the adapter
    TextView opponentNameTV = convertView.findViewById(R.id.opponentName);
    TextView numGamesTV = convertView.findViewById(R.id.numberTextView);
    Button gameListEntryButton = convertView.findViewById(R.id.gameListEntryButton);

    // Set the views to correct values
    opponentNameTV.setText(opponentName);
    numGamesTV.setText(turnsTaken);

    // Set button listener
    gameListEntryButton.setOnClickListener((View v) -> {
      Snackbar.make(v, "Clicked on game with position " + position, Snackbar.LENGTH_LONG).show();
    });

    return convertView;
  }
}
