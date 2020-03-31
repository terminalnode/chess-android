package com.example.newtonchess.api;

import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;

import retrofit2.Call;

public class ApiLogin {
  public static Call<TokenEntity> login(PlayerEntity player) {
    return RetrofitHelper
        .getLoginService()
        .login(player);
  }

  /*
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
  */
}
