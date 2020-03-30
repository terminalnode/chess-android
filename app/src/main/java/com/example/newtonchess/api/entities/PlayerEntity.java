package com.example.newtonchess.api.entities;

import com.google.gson.annotations.SerializedName;

public class PlayerEntity {
  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("password")
  private String password;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
