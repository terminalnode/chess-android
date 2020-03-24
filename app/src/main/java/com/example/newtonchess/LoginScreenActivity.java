package com.example.newtonchess;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.api.ApiLogin;
import com.google.android.material.snackbar.Snackbar;

public class LoginScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_screen);
  }

  public void loginButtonPress(View view) {
    EditText userNameTextBox = findViewById(R.id.userNameEditBox);
    EditText passwordTextBox = findViewById(R.id.passwordEditBox);
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
      Snackbar.make(
          view,
          "Login successful placeholder!!!",
          Snackbar.LENGTH_LONG
      ).show();
    }
  }
}
