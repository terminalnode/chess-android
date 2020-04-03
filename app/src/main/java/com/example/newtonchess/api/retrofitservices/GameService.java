package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.GameEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Retrofit service interface connecting to the /api/games endpoint.
 * @author Alexander Rundberg
 */
public interface GameService {
  @GET("games")
  Call<List<GameEntity>> getAllGames(@Header("Token") String token);

  /* Move entity is not implemented yet
  @POST("game/{gameId}")
  Call<String> makeMove(@Header("Token") String token, @Path("gameId") String gameId, @Body MoveEntity move)
   */
}
