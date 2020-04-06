package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ApiLogin {
  public static Call<TokenEntity> login(PlayerEntity player) {
    return RetrofitHelper
        .getLoginService()
        .login(player);
  }

  public static void logout(String token) {
    Call<String> call = RetrofitHelper.getLoginService().logout(token);
    call.enqueue(new Callback<String>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<String> call, Response<String> response) {
        // Do nothing
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<String> call, Throwable t) {
        // Do nothing
      }
    });
  }

  public static Call<TokenEntity> validateToken(String token) {
    return RetrofitHelper
        .getLoginService()
        .validateToken(token);
  }
}
