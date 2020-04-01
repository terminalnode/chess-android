package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {
  @POST("login")
  Call<TokenEntity> login(@Body PlayerEntity player);

  @POST("logout")
  Call<String> logout(@Header("Token") String token);
}
