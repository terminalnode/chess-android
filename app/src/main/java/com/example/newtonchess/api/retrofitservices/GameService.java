package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.MoveEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit service interface connecting to the /api/games endpoint.
 * @author Alexander Rundberg
 */
public interface GameService {
  @GET("games")
  Call<List<GameEntity>> getAllGames(
      @Header("Token") String token);

  @GET("games/{gameId}")
  Call<GameEntity> getGame(
      @Header("Token") String token,
      @Path("gameId") long gameId);

  @POST("games/{gameId}")
  Call<MoveEntity> makeMove(
      @Header("Token") String token,
      @Path("gameId") long gameId,
      @Body MoveEntity move);
}
