package com.example.newtonchess.api;

public class ApiLogin {
  public static boolean verifyUsername(String username, String password) {
    // Placeholder until real API is built
    return username.equals("callecarlsson") && password.equals("password");
  }

  public static UserData getUserData(int userId) {
    // Placeholder method for getting user data from the database

    UserData cc = new UserData("Calle Carlsson");

    return cc;

  }

}
