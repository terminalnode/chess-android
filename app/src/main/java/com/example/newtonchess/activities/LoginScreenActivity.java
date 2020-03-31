package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
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
  }

  private void loginButtonPress(View view) {
    Log.i("LOGIN", "Sign In-button pressed");
    // Disable the sign up button to avoid double clicking
    // They will be reenabled once we get a response.
    disableButtons();

    // Fetch the input username and password
    String username = userNameTextBox.getText().toString().trim();
    String password = passwordTextBox.getText().toString().trim();
    Snackbar snackbar = Snackbar.make(
        view, R.string.missingUsernamePasswordSnackbar, Snackbar.LENGTH_LONG);

    // Verify that login credentials were filled in and correct.
    if (username.isEmpty() && password.isEmpty()) {
      snackbar.setText(R.string.missingUsernamePasswordSnackbar);
      snackbar.show();

    } else if (username.isEmpty()) {
      snackbar.setText(R.string.missingUsernameSnackbar);
      snackbar.show();

    } else if (password.isEmpty()) {
      snackbar.setText(R.string.missingPasswordSnackbar);
      snackbar.show();

    }

    // Create the user we will send to the API
    PlayerEntity newPlayer = new PlayerEntity(username, password);
    Call<TokenEntity> call = ApiLogin.login(newPlayer);

    // Make API call
    call.enqueue(new Callback<TokenEntity>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
        Log.i("LOGIN", "Inside onResponse of loginButtonClicked");
        if (response.code() == 200) {
          handleSuccessfulLogin(response, view);
        } else {
          handleUnsuccessfulLogin(response, snackbar);
        }
        enableButtons();
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<TokenEntity> call, Throwable t) {
        Log.w("LOGIN", "Inside onFailure of loginButtonClicked");
        snackbar.setText(R.string.somethingWentWrong);
        snackbar.show();
        enableButtons();
      }
    });
  }

  private void signUpButtonPress(View view) {
    Log.i("LOGIN", "Sign Up-button pressed");
    // Disable the sign up button to avoid double clicking
    // They will be reenabled once we get a response.
    disableButtons();

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
        if (response.isSuccessful() && response.body() != null) {
          PlayerEntity player = response.body();
          Snackbar.make(
              view,
              getString(R.string.accountCreationSuccessful, player.getName()),
              Snackbar.LENGTH_LONG
          ).show();

        } else {
          accountNotCreatedSnackbar(view, response);
        }
        enableButtons();
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<PlayerEntity> call, Throwable t) {
        Snackbar.make(
            view,
            R.string.somethingWentWrong,
            Snackbar.LENGTH_LONG
        ).show();
        enableButtons();
      }
    });
  }

  /**
   * Status code of login API call was 200, we should be able to get a token.
   * @param response The response containing the token.
   * @param view The view we're in.
   */
  private void handleSuccessfulLogin(Response<TokenEntity> response, View view) {
    Intent mainMenuIntent = new Intent(view.getContext(), MainMenuActivity.class);
    mainMenuIntent.putExtra("TokenEntity", response.body());
    startActivity(mainMenuIntent);
  }

  /**
   * Status code of login API call was not 200, something wen't wrong. We need to know what.
   * @param response The response containing the error message.
   * @param snackbar A snackbar for showing useful messages.
   */
  private void handleUnsuccessfulLogin(Response<TokenEntity> response, Snackbar snackbar) {
  }

  private void accountNotCreatedSnackbar(View view, Response response) {
    String internalName = null;
    int error = R.string.somethingWentWrong;

    // Try to read the message body to identify the error
    try {
      JSONObject responseBody = new JSONObject(response.errorBody().string());
      internalName = responseBody.getString("internalName");
    } catch (IOException | JSONException | NullPointerException ignored) { }

    // Set the error message in accord with internalName
    if (internalName != null) {
      switch(internalName) {
        case "PlayerCreateMissingFieldsException":
          error = R.string.accountCreationMissingFields;
          break;

        case "PlayerCreateUsernameTaken":
          error = R.string.accountCreationUsernameTaken;
          break;

        default: break;
      }
    }

    Snackbar.make(
        view,
        error,
        Snackbar.LENGTH_LONG
    ).show();
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
}
