package com.example.newtonchess.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.entities.TokenEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class SplashScreenActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    // We want the splash screen to show for at least four seconds.
    long startTime = System.currentTimeMillis() + 4000;

    // Check if there's a saved token available
    SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
    String previous_token = sp.getString(StaticValues.INTENT_TOKEN, null);
    Log.i(StaticValues.SPLASHSCREEN, "Previous token is: " + previous_token);

    // Validate the token and start the activity
    validateToken(previous_token, startTime);
  }

  private void validateToken(String token, long preferredStartTime) {
    if (token == null) {
      // No need for API call in this scenario
      startLoginScreen(preferredStartTime);

    } else {
      ApiLogin.validateToken(token)
          .enqueue(new Callback<TokenEntity>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
              TokenEntity body = response.body();
              if (body != null) {
                Log.i(
                    StaticValues.SPLASHSCREEN,
                    "Token is valid, sending user to main menu.");
                startMainMenu(preferredStartTime, body);

              } else {
                Log.i(
                    StaticValues.SPLASHSCREEN,
                    "Token is not valid, sending user to login screen.");
                startLoginScreen(preferredStartTime);
              }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<TokenEntity> call, Throwable t) {
              t.printStackTrace();
              Log.i(
                  StaticValues.SPLASHSCREEN,
                  "Token validation threw an exception, sending user to login screen");
              startLoginScreen(preferredStartTime);
            }
          });
    }
  }

  /**
   * Create intent to start the login screen.
   * @param preferredStartTime The preferred time at which we'd like to start the login screen.
   */
  private void startLoginScreen(long preferredStartTime) {
    Intent intent = new Intent(this, LoginScreenActivity.class);
    startActivityAtTime(preferredStartTime, intent);
  }

  /**
   * Create intent to start the main menu.
   * @param preferredStartTime The preferred time at which we'd like to start the main menu.
   */
  private void startMainMenu(long preferredStartTime, TokenEntity token) {
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivityAtTime(preferredStartTime, intent);
  }

  /**
   * Start an activity from an intent at the preferred starting time, or if the preferred
   * starting time has passed start it immediately.
   * @param preferredStartTime The preferred time at which we'd like to start the activity.
   * @param intent
   */
  private void startActivityAtTime(long preferredStartTime, Intent intent) {
    long startIn = preferredStartTime - System.currentTimeMillis();
    Log.i(StaticValues.SPLASHSCREEN, "Starting activity in " + startIn);

    if (startIn < 1) {
      startActivity(intent);
    } else {
      new Handler().postDelayed(() -> {
        startActivity(intent);
      }, startIn);
    }
  }
}
