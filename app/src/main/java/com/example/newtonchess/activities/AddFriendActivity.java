package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.gui.FriendsListAdapter;
import com.example.newtonchess.gui.FriendsListListenerType;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AddFriendActivity extends AppCompatActivity {
  TokenEntity token;
  ListView friendsListView;
  FriendsListAdapter friendsListAdapter;
  TextView emptyListTextViewTop, emptyListTextViewBottom;
  Button searchFriendButton;
  EditText searchFriendsEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_friend);

    // Retrieve the token
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Add friends list started, token: %s", token));

    // Find the views
    friendsListView = findViewById(R.id.friendsListView);
    emptyListTextViewTop = findViewById(R.id.emptyListTextViewTop);
    emptyListTextViewBottom = findViewById(R.id.emptyListTextViewBottom);
    searchFriendButton = findViewById(R.id.searchFriendsButton);
    searchFriendsEditText = findViewById(R.id.searchFriendsEditText);

    // Set empty adapter on friendsListView
    friendsListAdapter = new FriendsListAdapter(
        this,
        R.layout.list_single_friend,
        new ArrayList<>(),
        FriendsListListenerType.ADD_FRIEND,
        token);
    friendsListView.setAdapter(friendsListAdapter);

    // Set button listener
    searchFriendButton.setOnClickListener(this::getFriendsList);
  }

  private void getFriendsList(View view) {
    String searchTerm = searchFriendsEditText.getText().toString();
    Call<List<PlayerEntity>> call = RetrofitHelper
        .getPlayerService()
        .searchFriend(token.getTokenString(), searchTerm);

    call.enqueue(new Callback<List<PlayerEntity>>() {
      @Override
      @EverythingIsNonNull
      public void onResponse(Call<List<PlayerEntity>> call, Response<List<PlayerEntity>> response) {
        List<PlayerEntity> body = response.body();
        if (body != null) {
          friendsListAdapter.clear();
          friendsListAdapter.addAll(body);
        } else {
          try {
            Log.i("FRIENDS", "Error response: " + response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
          unknownError(view);
        }
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<List<PlayerEntity>> call, Throwable t) {
        unknownError(view);
      }
    });
  }

  private void unknownError(View view) {
    Snackbar.make(
        view,
        R.string.somethingWentWrong,
        Snackbar.LENGTH_LONG
    ).show();
  }
}
