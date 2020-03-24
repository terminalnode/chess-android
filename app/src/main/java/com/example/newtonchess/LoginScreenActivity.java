package com.example.newtonchess;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_screen);

    /*
    // Keeping this commented out for reference on how to use the snackbar

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
   */
  }


}
