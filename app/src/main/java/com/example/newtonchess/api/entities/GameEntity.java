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
  private int turnsTaken;

  @SerializedName("finished")
  private boolean finished;

  @SerializedName("pieces")
  private
  List<Piece> pieces;

  @SerializedName("gettingPlayerWhite")
  private boolean gettingPlayerWhite;

  @SerializedName("blackInCheck")
  private boolean blackInCheck;

  @SerializedName("whiteInCheck")
  private boolean whiteInCheck;

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
    dest.writeInt(this.turnsTaken);
    dest.writeByte(this.finished ? (byte) 1 : (byte) 0);
    dest.writeByte(this.gettingPlayerWhite ? (byte) 1 : (byte) 0);
    dest.writeByte(this.blackInCheck ? (byte) 1 : (byte) 0);
    dest.writeByte(this.whiteInCheck ? (byte) 1 : (byte) 0);
    dest.writeList(this.pieces);
  }

  protected GameEntity(Parcel in) {
    this.id = in.readLong();
    this.whitePlayer = in.readParcelable(PlayerEntity.class.getClassLoader());
    this.blackPlayer = in.readParcelable(PlayerEntity.class.getClassLoader());
    this.whitesTurn = in.readByte() != 0;
    this.turnsTaken = in.readInt();
    this.finished = in.readByte() != 0;
    this.gettingPlayerWhite = in.readByte() != 0;
    this.blackInCheck = in.readByte() != 0;
    this.whiteInCheck = in.readByte() != 0;
    this.pieces = new ArrayList<>();
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

  public void setTurnsTaken(int turnsTaken) {
    this.turnsTaken = turnsTaken;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
  }

  public void setGettingPlayerWhite(boolean gettingPlayerWhite) {
    this.gettingPlayerWhite = gettingPlayerWhite;
  }

  public void setBlackInCheck(boolean blackInCheck) {
    this.blackInCheck = blackInCheck;
  }

  public void setWhiteInCheck(boolean whiteInCheck) {
    this.whiteInCheck = whiteInCheck;
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

  public int getTurnsTaken() {
    return turnsTaken;
  }

  public boolean isFinished() {
    return finished;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public boolean isGettingPlayerWhite() {
    return gettingPlayerWhite;
  }

  public boolean isBlackInCheck() {
    return blackInCheck;
  }

  public boolean isWhiteInCheck() {
    return whiteInCheck;
  }
}
