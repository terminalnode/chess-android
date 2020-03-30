package com.example.newtonchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.ApiPlayer;
import com.example.newtonchess.api.UserData;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginScreenCredentialsFragment extends Fragment {
  private Button signInButton, signUpButton;
  private EditText userNameTextBox, passwordTextBox;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.login_screen_credentials_fragment, container, false);
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    signInButton = view.findViewById(R.id.logInButton);
    signUpButton = view.findViewById(R.id.signUpButton);
    userNameTextBox = view.findViewById(R.id.userNameEditBox);
    passwordTextBox = view.findViewById(R.id.passwordEditBox);

    // Set up listeners
    signInButton.setOnClickListener(this::loginButtonPress);
    signUpButton.setOnClickListener(this::signUpButtonPress);
  }

  private void loginButtonPress(View view) {
    String username = userNameTextBox.getText().toString();
    String password = passwordTextBox.getText().toString();

    // Verify that login credentials were filled in and correct.
    if (username.isEmpty() && password.isEmpty()) {
      Snackbar.make(
          view,
          R.string.missingUsernamePasswordSnackbar,
          Snackbar.LENGTH_LONG
      ).show();

    } else if (username.isEmpty()) {
      Snackbar.make(
          view,
          R.string.missingUsernameSnackbar,
          Snackbar.LENGTH_LONG
      ).show();

    } else if (password.isEmpty()) {
      Snackbar.make(
          view,
          R.string.missingPasswordSnackbar,
          Snackbar.LENGTH_LONG
      ).show();

    } else if (!ApiLogin.verifyUsername(username, password)) {
      Snackbar.make(
          view,
          R.string.incorrectLoginCredentialsSnackbar,
          Snackbar.LENGTH_LONG
      ).show();

    } else {

      UserData loggedInUserData = ApiLogin.getUserData(404);

      Intent mainMenuIntent = new Intent(getContext(), MainMenuActivity.class);
      mainMenuIntent.putExtra("UserData", loggedInUserData);

      startActivity(mainMenuIntent);

      Snackbar.make(
          view,
          "Login successful placeholder!!!",
          Snackbar.LENGTH_LONG
      ).show();
    }
  }

  private void signUpButtonPress(View view) {
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
          accountCreatedSnackbar(view, response.body());
        } else {
          accountNotCreatedSnackbar(view, response);
        }
        enableButtons();
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<PlayerEntity> call, Throwable t) {
        accountCreationGenericError(view);
        enableButtons();
      }
    });
  }

  private void accountCreatedSnackbar(View view, PlayerEntity player) {
    Snackbar.make(
        view,
        getString(R.string.accountCreationSuccessful, player.getName()),
        Snackbar.LENGTH_LONG
    ).show();
  }

  private void accountNotCreatedSnackbar(View view, Response response) {
    String internalName = null;
    int error = R.string.accountCreationGenericError;

    // Try to read the message body to identify the error
    try {
      JSONObject responseBody = new JSONObject(response.errorBody().string());
      internalName = responseBody.getString("internalName");
    } catch (IOException | JSONException | NullPointerException ignored) { }

    System.out.println(internalName);
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

    error = R.string.accountCreationGenericError;
    Snackbar.make(
        view,
        error,
        Snackbar.LENGTH_LONG
    ).show();
  }

  private void accountCreationGenericError(View view) {
    Snackbar.make(
        view,
        R.string.accountCreationGenericError,
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

