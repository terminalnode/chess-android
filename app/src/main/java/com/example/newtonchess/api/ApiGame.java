package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import java.util.List;

import retrofit2.Call;

public class ApiGame {
  public static Call<List<GameEntity>> getAllGames(String token) {
    return RetrofitHelper
        .getGameService()
        .getAllGames(token);
  }
}