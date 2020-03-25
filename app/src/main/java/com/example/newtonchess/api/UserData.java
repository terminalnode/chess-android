package com.example.newtonchess.api;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {



  private String userName;

  //private List<Friend> friendList;

  //private List<Game> activeGames;

  //----- Constructors -----//

  public UserData(String userName) {

    this.userName = userName;

  }

  //----- Methods -----//

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userName);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  protected UserData(Parcel in) {
    userName = in.readString();
  }


  //----- Getters and Setters -----//







  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public static final Creator<UserData> CREATOR = new Creator<UserData>() {
    @Override
    public UserData createFromParcel(Parcel in) {
      return new UserData(in);
    }

    @Override
    public UserData[] newArray(int size) {
      return new UserData[size];
    }
  };



}
