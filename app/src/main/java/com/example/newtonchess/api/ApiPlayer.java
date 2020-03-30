package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import java.util.List;

import retrofit2.Call;

public class ApiPlayer {
  public static Call<List<PlayerEntity>> getAll() {
    return RetrofitHelper
        .getPlayerService()
        .getAll();
  }
}
