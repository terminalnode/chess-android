package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import retrofit2.Call;

public class ApiChallenge {
  public static Call<ChallengeEntity> createNewChallenge(String token, PlayerEntity player) {
    return RetrofitHelper
        .getChallengeService()
        .createNewChallenge(token, player);
  }
}
