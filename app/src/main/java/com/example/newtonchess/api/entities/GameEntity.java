package com.example.newtonchess.api.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.newtonchess.chesscomponents.pieces.Piece;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
public class GameEntity implements Parcelable {
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

  @SerializedName("pieces")
  List<Piece> pieces;

  //----- Constructors -----//
  public GameEntity() {
    // Required no-arg constructor
  }

  //----- Methods -----//
  @NonNull
  @Override
  public String toString() {
    return String.format(
        "<Game id=%s, whitePlayer=%s blackPlayer=%s, whitesTurn=%s, turnsTaken=%s, finished=%s, pieces=%s>",
        id, whitePlayer, blackPlayer, whitesTurn, turnsTaken, finished, pieces.size()
    );
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.id);
    dest.writeParcelable(this.whitePlayer, flags);
    dest.writeParcelable(this.blackPlayer, flags);
    dest.writeByte(this.whitesTurn ? (byte) 1 : (byte) 0);
    dest.writeLong(this.turnsTaken);
    dest.writeByte(this.finished ? (byte) 1 : (byte) 0);
    dest.writeList(this.pieces);
  }

  protected GameEntity(Parcel in) {
    this.id = in.readLong();
    this.whitePlayer = in.readParcelable(PlayerEntity.class.getClassLoader());
    this.blackPlayer = in.readParcelable(PlayerEntity.class.getClassLoader());
    this.whitesTurn = in.readByte() != 0;
    this.turnsTaken = in.readLong();
    this.finished = in.readByte() != 0;
    this.pieces = new ArrayList<Piece>();
    in.readList(this.pieces, Piece.class.getClassLoader());
  }

  public static final Creator<GameEntity> CREATOR = new Creator<GameEntity>() {
    @Override
    public GameEntity createFromParcel(Parcel source) {
      return new GameEntity(source);
    }

    @Override
    public GameEntity[] newArray(int size) {
      return new GameEntity[size];
    }
  };

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

  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
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

  public List<Piece> getPieces() {
    return pieces;
  }
}
