package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import retrofit2.Call;

public class ApiLogin {
  public static Call<TokenEntity> login(PlayerEntity player) {
    return RetrofitHelper
        .getLoginService()
        .login(player);
  }
}
