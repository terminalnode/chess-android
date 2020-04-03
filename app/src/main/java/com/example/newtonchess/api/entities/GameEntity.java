package com.example.newtonchess.api.entities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Entity class for deserializing GameEntities sent by the server.
 * Like all entities, this class is meant to represent a game as sent by the server.
 * The actual ChessBoard view class contains a lot more information than this, and as
 * such they are separated. However the ChessBoard class has getters and setters for
 * injecting the information contained in this object and thus alter it's state to
 * one representative of the information contained in the JSON.
 *
 * @author Alexander Rundberg
 */
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

  //----- Methods -----//
  @NonNull
  @Override
  public String toString() {
    return String.format(
        "<Game id=%s, whitePlayer=%s blackPlayer=%s, whitesTurn=%s, turnsTaken=%s, finished=%s>",
        id, whitePlayer, blackPlayer, whitesTurn, turnsTaken, finished
    );
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
