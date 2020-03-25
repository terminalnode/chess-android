package com.example.newtonchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.newtonchess.api.ApiLogin;
import com.example.newtonchess.api.UserData;
import com.google.android.material.snackbar.Snackbar;

public class LoginScreenCredentialsFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.login_screen_credentials_fragment, container, false);
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Set up listeners
    view.findViewById(R.id.signUpButton)
        .setOnClickListener(this::signUpButtonPress);

    view.findViewById(R.id.logInButton)
        .setOnClickListener(this::loginButtonPress);
  }

  private void loginButtonPress(View view) {
    EditText userNameTextBox = getView().findViewById(R.id.userNameEditBox);
    EditText passwordTextBox = getView().findViewById(R.id.passwordEditBox);
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
    NavHostFragment
        .findNavController(LoginScreenCredentialsFragment.this)
        .navigate(R.id.action_FirstFragment_to_SecondFragment);
  }
}
