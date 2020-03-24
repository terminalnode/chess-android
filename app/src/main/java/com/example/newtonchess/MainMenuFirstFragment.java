package com.example.newtonchess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

}
