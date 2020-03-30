package com.example.newtonchess.api.retrofitservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
  private static final String BASE_URL = "https://newton-sysqg3-chessapi.herokuapp.com/api/";

  public static Retrofit getBase() {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static PlayerService getPlayerService() {
    return getBase()
        .create(PlayerService.class);
  }
}
