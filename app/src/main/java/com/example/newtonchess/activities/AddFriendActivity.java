package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.gui.FriendsListAdapter;
import com.example.newtonchess.gui.FriendsListListenerType;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
  TokenEntity token;
  ListView friendsListView;
  FriendsListAdapter friendsListAdapter;
  TextView emptyListTextViewTop, emptyListTextViewBottom;

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

    // Set empty adapter on friendsListView
    friendsListAdapter = new FriendsListAdapter(
        this,
        R.layout.list_single_friend,
        new ArrayList<>(),
        FriendsListListenerType.ADD_FRIEND);
    friendsListView.setAdapter(friendsListAdapter);

    // Fetch the friends list from server
    getFriendsList();
  }

  private void getFriendsList() {
  }
}







