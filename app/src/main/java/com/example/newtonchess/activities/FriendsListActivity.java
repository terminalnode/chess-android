package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.gui.FriendsListAdapter;
import com.example.newtonchess.gui.FriendsListListenerType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FriendsListActivity extends AppCompatActivity {
  private TokenEntity token;
  private ListView friendsListView;
  private FriendsListAdapter friendsListAdapter;
  private TextView emptyListTextViewTop;
  private TextView emptyListTextViewBottom;
  private Button findNewFriendsButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friends_list);

    // Retrieve the token
    token = getIntent().getParcelableExtra(StaticValues.INTENT_TOKEN);
    Log.i(StaticValues.FRIENDLIST, String.format("Friends list started, token: %s", token));

    // Find the views
    friendsListView = findViewById(R.id.friendsListView);
    emptyListTextViewTop = findViewById(R.id.emptyListTextViewTop);
    emptyListTextViewBottom = findViewById(R.id.emptyListTextViewBottom);
    findNewFriendsButton = findViewById(R.id.findNewFriendsButton);

    // Set listener for findNewFriendsButton
    findNewFriendsButton.setOnClickListener(this::goToFindFriends);

    // Set empty adapter on friendsListView
    friendsListAdapter = new FriendsListAdapter(
        this,
        R.layout.list_single_friend,
        new ArrayList<>(),
        FriendsListListenerType.CHALLENGE,
        token);
    friendsListView.setAdapter(friendsListAdapter);

    // Fetch the friends list from server
    getFriendsList();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void goToFindFriends(View view) {
    Intent intent = new Intent(view.getContext(), AddFriendActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void getFriendsList() {
    RetrofitHelper
        .getPlayerService()
        .getFriends(token.getTokenString())
        .enqueue(new Callback<List<PlayerEntity>>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<List<PlayerEntity>> call, Response<List<PlayerEntity>> response) {
            List<PlayerEntity> body = response.body();

            if (body != null && body.size() > 0) {
              friendsListAdapter.addAll(body);
              hideFetchText();

            } else if (body != null) {
              emptyListTextViewTop.setText(R.string.emptyListText);
              emptyListTextViewBottom.setText(R.string.thatsTooBad);
              showFetchText();

            } else {
              emptyListTextViewTop.setText(R.string.failedToFetchList);
              emptyListTextViewBottom.setText(R.string.thatsTooBad);
              showFetchText();
            }
          }

          @Override
          @EverythingIsNonNull
          public void onFailure(Call<List<PlayerEntity>> call, Throwable t) {
            emptyListTextViewTop.setTextSize(R.string.failedToFetchList);
            emptyListTextViewBottom.setText(R.string.thatsTooBad);
            showFetchText();
          }
        });
  }

  private void hideFetchText() {
    emptyListTextViewTop.setVisibility(View.GONE);
    emptyListTextViewBottom.setVisibility(View.GONE);
  }

  private void showFetchText() {
    emptyListTextViewTop.setVisibility(View.VISIBLE);
    emptyListTextViewBottom.setVisibility(View.VISIBLE);
  }
}
