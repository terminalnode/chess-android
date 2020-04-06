package com.example.newtonchess.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.ApiPlayer;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginScreenActivity extends AppCompatActivity {
  private Button signInButton, signUpButton;
  private EditText userNameTextBox, passwordTextBox;
  private SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_screen);

    signInButton = findViewById(R.id.logInButton);
    signUpButton = findViewById(R.id.signUpButton);
    userNameTextBox = findViewById(R.id.userNameEditBox);
    passwordTextBox = findViewById(R.id.passwordEditBox);

    signInButton.setOnClickListener(this::loginButtonPress);
    signUpButton.setOnClickListener(this::signUpButtonPress);

    sharedPreferences = getPreferences(Context.MODE_PRIVATE);

    // Fill in previous username/password if we have it
    String savedName = sharedPreferences.getString(StaticValues.PREF_USERNAME, null);
    String savedPassword = sharedPreferences.getString(StaticValues.PREF_PASSWORD, null);
    if (savedName != null) {
      Log.i(StaticValues.LOGINSCREEN, "Found saved username, filling it in.");
      userNameTextBox.setText(savedName);
    }

    if (savedPassword != null) {
      Log.i(StaticValues.LOGINSCREEN, "Found saved password, filling it in.");
      passwordTextBox.setText(savedPassword);
    }

    // Log what we have in preferences for sanity's sake
    Log.i(StaticValues.LOGINSCREEN, "Values in shared preferences: " + sharedPreferences.getAll().size());
    for (String key : sharedPreferences.getAll().keySet()) {
      Log.i(StaticValues.LOGINSCREEN, ">>> Key: " + key);
    }
  }

  @Override
  public void onBackPressed() {
    // Do nothing, intentionally left blank
  }

  private void loginButtonPress(View view) {
    Log.i(StaticValues.LOGINSCREEN, "Sign In-button pressed");
    // Disable the sign up button to avoid double clicking
    // They will be reenabled once we get a response.
    disableButtons();
    userTextBoxToLowerCase();

    // Fetch the input username and password
    String username = userNameTextBox.getText().toString().trim();
    String password = passwordTextBox.getText().toString().trim();

    // Verify that login credentials were filled in and correct.
    if (username.isEmpty() && password.isEmpty()) {
      showSnackbar(view, R.string.missingUsernamePasswordSnackbar);

    } else if (username.isEmpty()) {
      showSnackbar(view, R.string.missingUsernameSnackbar);

    } else if (password.isEmpty()) {
      showSnackbar(view, R.string.missingPasswordSnackbar);
    }

    // Create the user we will send to the API
    PlayerEntity newPlayer = new PlayerEntity(username, password);
    Call<TokenEntity> call = ApiLogin.login(newPlayer);

    // Make API call
    call.enqueue(new Callback<TokenEntity>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
        Log.i(StaticValues.LOGINSCREEN, "Inside onResponse of loginButtonClicked");
        TokenEntity body = response.body();

        if (body != null) {
          Log.i(StaticValues.LOGINSCREEN, "Login successful, saving token and user info.");
          SharedPreferences.Editor sp = sharedPreferences.edit();
          sp.putString(StaticValues.PREF_USERNAME, username);
          sp.putString(StaticValues.PREF_PASSWORD, password);
          sp.putString(StaticValues.PREF_TOKEN, body.getTokenString());
          sp.apply();

          Intent mainMenuIntent = new Intent(view.getContext(), MainMenuActivity.class);
          mainMenuIntent.putExtra(StaticValues.INTENT_TOKEN, body);
          startActivity(mainMenuIntent);
        } else {
          showError(view, response);
        }
        enableButtons();
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<TokenEntity> call, Throwable t) {
        Log.w(StaticValues.LOGINSCREEN, "Inside onFailure of loginButtonClicked");
        showSnackbar(view, R.string.somethingWentWrong);
        enableButtons();
      }
    });
  }

  private void signUpButtonPress(View view) {
    Log.i("LOGIN", "Sign Up-button pressed");
    // Disable the sign up button to avoid double clicking
    // They will be reenabled once we get a response.
    disableButtons();
    userTextBoxToLowerCase();

    // Create the new user
    PlayerEntity newPlayer = new PlayerEntity();
    newPlayer.setName(userNameTextBox.getText().toString().trim());
    newPlayer.setPassword(passwordTextBox.getText().toString().trim());

    // Send the new user to the server
    Call<PlayerEntity> call = ApiPlayer.createPlayer(newPlayer);
    call.enqueue(new Callback<PlayerEntity>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<PlayerEntity> call, Response<PlayerEntity> response) {
        PlayerEntity body = response.body();

        if (body != null) {
          Log.i(StaticValues.LOGINSCREEN, "Sign up successful, user info.");
          SharedPreferences.Editor sp = sharedPreferences.edit();
          sp.putString(StaticValues.PREF_USERNAME, userNameTextBox.getText().toString());
          sp.putString(StaticValues.PREF_PASSWORD, passwordTextBox.getText().toString());
          sp.apply();

          Snackbar.make(
              view,
              getString(R.string.accountCreationSuccessful, body.getName()),
              Snackbar.LENGTH_LONG
          ).show();

        } else {
          showError(view, response);
        }
        enableButtons();
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<PlayerEntity> call, Throwable t) {
        showSnackbar(view, R.string.somethingWentWrong);
        enableButtons();
      }
    });
  }

  /**
   * Try to read the error response in order to figure out what went wrong, then
   * show a snackbar with the appropriate error.
   * @param view The view in which the snackbar should be shown.
   * @param response The API error response we're diagnosing.
   */
  private void showError(View view, Response response) {
    String internalName = StaticValues.THIS_IS_NOT_AN_ERROR;
    try {
      internalName = new JSONObject(response.errorBody().string())
          .getString(StaticValues.INTERNAL_NAME);
    } catch (IOException | JSONException | NullPointerException ignored) { }

    switch (internalName) {
      case StaticValues.MISSING_FIELDS_EXCEPTION:
        showSnackbar(view, R.string.accountCreationMissingFields);
        break;

      case StaticValues.USERNAME_TAKEN:
        showSnackbar(view, R.string.accountCreationUsernameTaken);
        break;

      case StaticValues.NO_SUCH_USER:
        showSnackbar(view, R.string.incorrectUsername);
        break;

      case StaticValues.WRONG_PASSWORD:
        showSnackbar(view, R.string.incorrectPassword);
        break;

      case StaticValues.FAILED_TO_CREATE_TOKEN:
        showSnackbar(view, R.string.failedToCreateToken);
        break;

      default:
        showSnackbar(view, R.string.somethingWentWrong);
    }
  }

  /**
   * Show a snackbar in the given view containing the string with the given resource id.
   * @param view The view in which the snackbar should be shown.
   * @param resourceId The resource id of the string.
   */
  private void showSnackbar(View view, int resourceId) {
    Snackbar.make(view, resourceId, Snackbar.LENGTH_LONG).show();
  }

  private void disableButtons() {
    signUpButton.setClickable(false);
    signInButton.setClickable(false);
    signUpButton.setAlpha(0.5F);
    signInButton.setAlpha(0.5F);
  }

  private void enableButtons() {
    signUpButton.setClickable(true);
    signInButton.setClickable(true);
    signUpButton.setAlpha(1F);
    signInButton.setAlpha(1F);
  }

  private void userTextBoxToLowerCase() {
    userNameTextBox
        .setText(userNameTextBox
            .getText()
            .toString()
            .toLowerCase());
  }
}
