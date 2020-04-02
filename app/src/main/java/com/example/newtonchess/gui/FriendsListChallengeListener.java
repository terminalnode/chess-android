package com.example.newtonchess.gui;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class FriendsListChallengeListener implements View.OnClickListener {
  @Override
  public void onClick(View view) {
    Snackbar.make(view, "Challenge accepted!!!", Snackbar.LENGTH_LONG).show();
  }
}
