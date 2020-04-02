package com.example.newtonchess.api.entities;

import com.google.gson.annotations.SerializedName;

public class GameEntity {
  @SerializedName("id")
  private long id;

  @SerializedName("whitePlayer")
  private PlayerEntity whitePlayer;

  @SerializedName("blackPlayer")
  private PlayerEntity blackPlayer;

  @SerializedName("whitesTurn")
  private boolean whitesTurn;

  @SerializedName("turnsTaken")
  private long turnsTaken;

  @SerializedName("finished")
  private boolean finished;

  //No good piece entity yet
  //@SerializedName("pieces")
  //List<Piece> pieces;

  //----- Constructors -----//
  public GameEntity() {
    // Required no-arg constructor
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setWhitePlayer(PlayerEntity whitePlayer) {
    this.whitePlayer = whitePlayer;
  }

  public void setBlackPlayer(PlayerEntity blackPlayer) {
    this.blackPlayer = blackPlayer;
  }

  public void setWhitesTurn(boolean whitesTurn) {
    this.whitesTurn = whitesTurn;
  }

  public void setTurnsTaken(long turnsTaken) {
    this.turnsTaken = turnsTaken;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public PlayerEntity getWhitePlayer() {
    return whitePlayer;
  }

  public PlayerEntity getBlackPlayer() {
    return blackPlayer;
  }

  public boolean isWhitesTurn() {
    return whitesTurn;
  }

  public long getTurnsTaken() {
    return turnsTaken;
  }

  public boolean isFinished() {
    return finished;
  }
}
