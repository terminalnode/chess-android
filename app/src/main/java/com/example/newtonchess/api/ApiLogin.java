package com.example.newtonchess.api;

public class ApiLogin {
  public static boolean verifyUsername(String username, String password) {
    // Placeholder until real API is built
    return username.equals("callecarlsson") && password.equals("password");
  }

  public static UserData getUserData(int userId) {
    // Placeholder method for getting user data from the database

    UserData cc = new UserData("Calle Carlsson");

    cc.addFriend("Friend Alpha", 128);
    cc.addFriend("Friend Bravo", 256);
    cc.addFriend("Friend Gamma", 512);
    cc.addFriend("Friend Delta", 512);

    return cc;

  }

}
