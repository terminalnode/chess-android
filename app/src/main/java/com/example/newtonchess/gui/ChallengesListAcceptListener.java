package com.example.newtonchess.gui;

import android.view.View;
import android.widget.Button;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.ResponseBody;

public class ChallengesListAcceptListener implements View.OnClickListener {
  private View view;
  private ChallengeEntity challenge;
  private String token;
  private Button button;

  ChallengesListAcceptListener(ChallengeEntity challenge, TokenEntity token, Button button) {
    this.challenge = challenge;
    this.token = token.getTokenString();
    this.button = button;
  }

  @Override
  public void onClick(View view) {
    this.view = view;
    deactivateButton();
    sendChallenge();
  }

  private void sendChallenge() {
  }

  private void knownError(ResponseBody responseBody) {

  }

  private void unknownError() {
    Snackbar.make(
        view,
        R.string.somethingWentWrong,
        Snackbar.LENGTH_LONG
    ).show();
    activateButton();
  }

  private void activateButton() {
    button.setClickable(true);
    button.setAlpha(1F);
  }

  private void deactivateButton() {
    button.setClickable(false);
    button.setAlpha(0.5F);
  }
}
