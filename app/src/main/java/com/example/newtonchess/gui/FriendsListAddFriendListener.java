package com.example.newtonchess.gui;

import android.view.View;
import android.widget.Button;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListAddFriendListener implements View.OnClickListener {
  private PlayerEntity friend;
  private String token;
  private Button button;

  FriendsListAddFriendListener(
      PlayerEntity friend,
      TokenEntity token,
      Button button) {
    this.friend = friend;
    this.token = token.getTokenString();
    this.button = button;
  }

  @Override
  public void onClick(View view) {
    deactivateButton();

    Call<PlayerEntity> call = RetrofitHelper
        .getPlayerService()
        .addFriend(token, friend);

    call.enqueue(new Callback<PlayerEntity>() {
      @Override
      public void onResponse(Call<PlayerEntity> call, Response<PlayerEntity> response) {
        PlayerEntity body = response.body();
        if (body != null) {
          button.setText(R.string.addedFriendButton);
        } else {
          unknownError(view);
        }
      }

      @Override
      public void onFailure(Call<PlayerEntity> call, Throwable t) {
        unknownError(view);
      }
    });
  }

  private void unknownError(View view) {
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
