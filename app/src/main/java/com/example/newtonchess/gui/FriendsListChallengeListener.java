package com.example.newtonchess.gui;

import android.view.View;
import android.widget.Button;

import com.example.newtonchess.R;
import com.example.newtonchess.api.ApiChallenge;
import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    deactivateButton();

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
    String internalName = null;

    try {
      JSONObject json = new JSONObject(responseBody.string());
      internalName = json.getString("internalName");
    } catch (IOException | JSONException | NullPointerException e) {
      e.printStackTrace();
    }

    if (internalName == null || internalName.equals(RetrofitHelper.INTERNAL_SERVER_ERROR)) {
      unknownError();
    } else if (internalName.equals(ApiChallenge.CHALLENGE_ALREADY_EXISTS)) {
      button.setText(R.string.challengeNotSentButtonText);
      Snackbar.make(
          view,
          R.string.challengeAlreadyExistsSnackbar,
          Snackbar.LENGTH_LONG
      ).show();
    } else {
      unknownError();
    }
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
