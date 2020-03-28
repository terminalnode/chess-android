package com.example.newtonchess.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserData implements Parcelable {
  private String userName;
  private List<Friend> friendList = new ArrayList<>();

  //----- Constructors -----//
  public UserData(String userName) {
    this.userName = userName;
  }

  //----- Methods -----//
  public void addFriend(String friendName, int friendId) {
    friendList.add(new Friend(friendName, friendId));
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userName);
    dest.writeTypedList(friendList);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  protected UserData(Parcel in) {
    userName = in.readString();
    in.readTypedList(friendList, Friend.CREATOR);
  }

  //----- Getters and Setters -----//
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public List<Friend> getFriendList() {
    return this.friendList;
  }

  public void addFriendToList(Friend newFriend) {
    this.friendList.add(newFriend);
  }

  //----- Creator -----//
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

