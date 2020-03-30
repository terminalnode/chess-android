package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.PlayerEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PlayerService {
  @GET("players")
  Call<List<PlayerEntity>> getAll();

  @POST("players")
  Call<PlayerEntity> createPlayer(@Body PlayerEntity player);
}
