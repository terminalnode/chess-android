package com.example.newtonchess.gui;

import android.view.View;
import android.widget.Button;

import com.example.newtonchess.R;
import com.example.newtonchess.api.ApiChallenge;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FriendsListChallengeListener implements View.OnClickListener {
  private View view;
  private PlayerEntity friend;
  private String token;
  private Button button;

  FriendsListChallengeListener(PlayerEntity friend, TokenEntity token, Button button) {
    this.friend = friend;
    this.token = token.getTokenString();
    this.button = button;
  }

  @Override
  public void onClick(View view) {
    this.view = view;
    button.setClickable(false);
    sendChallenge();
  }

  private void sendChallenge() {
    Call<ChallengeEntity> call = ApiChallenge.createNewChallenge(token, friend);
    call.enqueue(new Callback<ChallengeEntity>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<ChallengeEntity> call, Response<ChallengeEntity> response) {
        ChallengeEntity challenge = response.body();
        ResponseBody responseBody = response.errorBody();
        if (challenge == null) {
          if (responseBody == null) {
            unknownError();
          } else {
            knownError(responseBody);
          }
        } else {
          button.setText(R.string.challengeSentButtonText);
        }
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<ChallengeEntity> call, Throwable t) {
        unknownError();
      }
    });
  }

  private void knownError(ResponseBody responseBody) {
    button.setClickable(true);
  }

  private void unknownError() {
    Snackbar.make(
        view,
        R.string.somethingWentWrong,
        Snackbar.LENGTH_LONG
    ).show();
    button.setClickable(true);
  }
}
