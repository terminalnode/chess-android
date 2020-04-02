package com.example.newtonchess.api.entities;

import com.google.gson.annotations.SerializedName;

public class ChallengeEntity {
  @SerializedName("id")
  private long id;

  @SerializedName("challenger")
  private PlayerEntity challenger;

  @SerializedName("challenged")
  private PlayerEntity challenged;

  @SerializedName("createdAt")
  private long createdAt;

  //----- Constructors -----//
  public ChallengeEntity() {
    // Empty no-args constructor
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public PlayerEntity getChallenger() {
    return challenger;
  }

  public PlayerEntity getChallenged() {
    return challenged;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setChallenger(PlayerEntity challenger) {
    this.challenger = challenger;
  }

  public void setChallenged(PlayerEntity challenged) {
    this.challenged = challenged;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }
}
