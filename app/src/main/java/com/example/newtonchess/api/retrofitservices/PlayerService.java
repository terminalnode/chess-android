package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.PlayerEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlayerService {
  @GET("players")
  Call<List<PlayerEntity>> getAll();

  @POST("players")
  Call<PlayerEntity> createPlayer(
      @Body PlayerEntity player);

  @GET("friends")
  Call<List<PlayerEntity>> getFriends(
      @Header("Token") String token);

  @POST("friends")
  Call<PlayerEntity> addFriend(
      @Header("Token") String token,
      @Body PlayerEntity friend);

  @GET("friends/search/{searchTerm}")
  Call<List<PlayerEntity>> searchFriend(
      @Header("Token") String token,
      @Path("searchTerm") String searchTerm);
}
