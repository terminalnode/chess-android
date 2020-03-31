package com.example.newtonchess.api.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlayerEntity implements Parcelable {
  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("password")
  private String password;

  //----- Constructors -----//
  public PlayerEntity() {
    // Empty no-args constructor
  }

  public PlayerEntity(String name, String password) {
    this.name = name;
    this.password = password;
  }

  //----- Methods -----//
  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeString(this.password);
  }

  protected PlayerEntity(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
    this.password = in.readString();
  }

  public static final Creator<PlayerEntity> CREATOR = new Creator<PlayerEntity>() {
    @Override
    public PlayerEntity createFromParcel(Parcel source) {
      return new PlayerEntity(source);
    }

    @Override
    public PlayerEntity[] newArray(int size) {
      return new PlayerEntity[size];
    }
  };

  @Override
  public String toString() {
    return String.format(
        "<PlayerEntity name=\"%s\">",
        name
    );
  }

  //----- Getters -----//
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  //----- Setters -----//
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
