package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import java.util.List;

import retrofit2.Call;

public class ApiChallenge {
  public static final String CHALLENGE_ALREADY_EXISTS = "ChallengeAlreadyExistsException";
  public static final String CHALLENGE_ID_MISMATCH = "ChallengeIdMismatchException";

  public static Call<ChallengeEntity> createNewChallenge(String token, PlayerEntity player) {
    return RetrofitHelper
        .getChallengeService()
        .createNewChallenge(token, player);
  }

  public static Call<List<ChallengeEntity>> getChallengesToMe(String token) {
    return RetrofitHelper
        .getChallengeService()
        .getChallengesToMe(token);
  }

  public static Call<GameEntity> acceptChallenge(String token, long challengeId) {
    return  RetrofitHelper
        .getChallengeService()
        .acceptChallenge(token, challengeId);
  }
}
