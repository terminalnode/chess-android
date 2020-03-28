package com.example.newtonchess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.newtonchess.api.UserData;
import com.google.android.material.snackbar.Snackbar;

public class MainMenuFirstFragment extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.main_menu_fragment_first, container, false);
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Set up listeners
    view.findViewById(R.id.activeGamesButton)
        .setOnClickListener(this::activeGamesButtonPress);

    view.findViewById(R.id.newGameButton)
        .setOnClickListener(this::newGameButtonPress);

    view.findViewById(R.id.friendsListButton)
        .setOnClickListener(this::friendsButtonPress);

    view.findViewById(R.id.logoutButton)
        .setOnClickListener(this::logoutButtonPress);

    UserData tempUserData = ((MainMenuActivity) getActivity()).getLoggedInUserData();

    ((TextView) view.findViewById(R.id.welcomeTextView))
        .setText("Welcome, " + tempUserData.getUserName());

  }

  private void activeGamesButtonPress(View view) {
    NavHostFragment.findNavController(MainMenuFirstFragment.this)
        .navigate(R.id.action_FirstFragment_to_SecondFragment);

    Snackbar.make(
        view,
        R.string.activeGamesButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();




  }

  private void newGameButtonPress(View view) {

    Snackbar.make(
        view,
        R.string.newGameButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();

  }

  private void friendsButtonPress(View view) {

    Snackbar.make(
        view,
        R.string.friendsListButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();

  }

  private void logoutButtonPress(View view) {

    Snackbar.make(
        view,
        R.string.logoutButtonPressed,
        Snackbar.LENGTH_LONG
    ).show();

  }

}
