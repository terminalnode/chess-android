package com.example.newtonchess.api.entities;

import com.google.gson.annotations.SerializedName;

public class GameEntity {
  @SerializedName("id")
  long id;

  //----- Constructors -----//
  public GameEntity() {
    // Required no-arg constructor
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }


  //----- Getters -----//
  public long getId() {
    return id;
  }
}
