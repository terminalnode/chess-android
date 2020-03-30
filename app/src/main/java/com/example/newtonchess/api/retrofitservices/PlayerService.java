package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.PlayerEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayerService {
  @GET("players")
  Call<List<PlayerEntity>> getAll();
}
