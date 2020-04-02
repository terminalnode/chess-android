package com.example.newtonchess.api.retrofitservices;

import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.PlayerEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChallengeService {
  @POST("challenges")
  Call<ChallengeEntity> createNewChallenge(
      @Header("Token") String token,
      @Body PlayerEntity player);

  @GET("challenges/challenged")
  Call<List<ChallengeEntity>> getChallengesToMe(@Header("Token") String token);
}
