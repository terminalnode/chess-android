package com.example.newtonchess.gui;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.newtonchess.R;
import com.example.newtonchess.api.ApiChallenge;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ChallengesListDenyListener implements View.OnClickListener {
  private View view;
  private ChallengeEntity challenge;
  private String token;
  private Button acceptButton, denyButton;

  ChallengesListDenyListener(ChallengeEntity challenge, TokenEntity token, Button acceptButton, Button denyButton) {
    this.challenge = challenge;
    this.token = token.getTokenString();
    this.acceptButton = acceptButton;
    this.denyButton = denyButton;
  }

  @Override
  public void onClick(View view) {
    this.view = view;
    deactivateButtons();
    denyChallenge();
  }

  private void denyChallenge() {
    Call<String> call = ApiChallenge.denyChallenge(token, challenge.getId());
    call.enqueue(new Callback<String>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<String> call, Response<String> response) {
        String body = response.body();
        Log.i("CHALLENGES", "Denied challenge, got this: " + body);
        if (body == null) {
          unknownError();
        }
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<String> call, Throwable t) {
        unknownError();
      }
    });
  }

  private void knownError(ResponseBody responseBody) {

  }

  private void unknownError() {
    Snackbar.make(
        view,
        R.string.somethingWentWrong,
        Snackbar.LENGTH_LONG
    ).show();
    activateButtons();
  }

  private void activateButtons() {
    acceptButton.setClickable(true);
    acceptButton.setAlpha(1F);
    denyButton.setClickable(true);
    denyButton.setAlpha(1F);
  }

  private void deactivateButtons() {
    acceptButton.setClickable(false);
    acceptButton.setAlpha(0.5F);
    denyButton.setClickable(false);
    denyButton.setAlpha(0.5F);
  }
}
