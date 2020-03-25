package com.example.newtonchess.api;

import android.os.Parcel;
import android.os.Parcelable;

class Friend implements Parcelable {
  private String name;
  private int userId;
  private int gameId;

  //----- Constructors -----//
  public Friend(String friendName, int friendId) {
    this(friendName, friendId, -1);
  }

  public Friend(String friendName, int friendId, int gameId) {
    this.name = friendName;
    this.userId = friendId;
    this.gameId = gameId;
  }

  protected Friend(Parcel in) {
    userId = in.readInt();
    gameId = in.readInt();
    name = in.readString();
  }

  //----- Creator -----//
  public static final Creator<Friend> CREATOR = new Creator<Friend>() {
    @Override
    public Friend createFromParcel(Parcel in) {
      return new Friend(in);
    }

    @Override
    public Friend[] newArray(int size) {
      return new Friend[size];
    }
  };

  //----- Methods -----//
  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(userId);
    dest.writeInt(gameId);
    dest.writeString(name);
  }

  //----- Getters and Setters -----//
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }
}
