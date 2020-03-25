package com.example.newtonchess.api;

public class UserData {



  private String userName;

  //private List<Friend> friendList;

  //private List<Game> activeGames;

  //----- Constructors -----//

  public UserData(String userName) {

    this.userName = userName;

  }

  //----- Getters and Setters -----//


  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }
}
