package com.example.newtonchess.api.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TokenEntity implements Parcelable {
  @SerializedName("tokenString")
  private String tokenString;

  @SerializedName("player")
  private PlayerEntity player;

  //----- Methods -----//
  @Override
  public String toString() {
    return String.format(
        "<TokenEntity string=\"%s\", player=%s>",
        tokenString,
        player
    );
  }

  //----- Getters -----//
  public String getTokenString() {
    return tokenString;
  }

  public PlayerEntity getPlayer() {
    return player;
  }

  //----- Setters -----//
  public void setTokenString(String tokenString) {
    this.tokenString = tokenString;
  }

  public void setPlayer(PlayerEntity player) {
    this.player = player;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.tokenString);
    dest.writeParcelable(this.player, flags);
  }

  public TokenEntity() {
  }

  protected TokenEntity(Parcel in) {
    this.tokenString = in.readString();
    this.player = in.readParcelable(PlayerEntity.class.getClassLoader());
  }

  public static final Creator<TokenEntity> CREATOR = new Creator<TokenEntity>() {
    @Override
    public TokenEntity createFromParcel(Parcel source) {
      return new TokenEntity(source);
    }

    @Override
    public TokenEntity[] newArray(int size) {
      return new TokenEntity[size];
    }
  };
}
