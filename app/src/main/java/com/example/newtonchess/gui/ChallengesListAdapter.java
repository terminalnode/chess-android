package com.example.newtonchess.gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChallengesListAdapter extends ArrayAdapter<ChallengeEntity> {
  private Context context;
  private int listLayout;
  private TokenEntity token;

  public ChallengesListAdapter(
      @NonNull Context context,
      int listLayout,
      @NonNull List<ChallengeEntity> objects,
      TokenEntity token) {

    super(context, listLayout, objects);
    this.context = context;
    this.listLayout = listLayout;
    this.token = token;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Log.i("CHALLENGES", "Heck yeah baby, processing challenge #" + position);
    ChallengeEntity challenge = getItem(position);
    PlayerEntity challenger = null;

    if (challenge != null) {
      challenger = challenge.getChallenger();
    }

    LayoutInflater inflater = LayoutInflater.from(context);
    convertView = inflater.inflate(listLayout, parent, false);

    // Find the views inside the adapter
    Button acceptButton = convertView.findViewById(R.id.challengeListAcceptButton);
    Button denyButton = convertView.findViewById(R.id.challengeListDenyButton);
    TextView challengerTV = convertView.findViewById(R.id.challengerName);

    // Set the name of the challenger
    if (challenger != null) {
      challengerTV.setText(challenger.getName());
    } else {
      challengerTV.setText(R.string.userUnknown);
      acceptButton.setClickable(false);
      acceptButton.setAlpha(0.5F);
      denyButton.setClickable(false);
      denyButton.setAlpha(0.5F);
    }

    // Set up listeners for the buttons
    acceptButton.setOnClickListener(this::acceptButtonPressed);
    denyButton.setOnClickListener(this::denyButtonPressed);

    return convertView;
  }

  private void denyButtonPressed(View view) {
    Snackbar.make(
        view,
        "Deny button pressed placeholder",
        Snackbar.LENGTH_LONG
    ).show();
  }

  private void acceptButtonPressed(View view) {
    Snackbar.make(
        view,
        "Accept button pressed placeholder",
        Snackbar.LENGTH_LONG
    ).show();
  }
}
