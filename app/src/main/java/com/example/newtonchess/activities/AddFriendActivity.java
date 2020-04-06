package com.example.newtonchess.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

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

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, FriendsListActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(StaticValues.INTENT_TOKEN, token);
    startActivity(intent);
  }

  private void getFriendsList(View view) {
    showFetchText();
    emptyListTextViewTop.setText(R.string.fetchingList);
    emptyListTextViewBottom.setText(R.string.standBy);
    String searchTerm = searchFriendsEditText.getText().toString();

    RetrofitHelper
        .getPlayerService()
        .searchFriend(token.getTokenString(), searchTerm)
        .enqueue(new Callback<List<PlayerEntity>>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<List<PlayerEntity>> call, Response<List<PlayerEntity>> response) {
            List<PlayerEntity> body = response.body();

            friendsListAdapter.clear();
            if (body != null && body.size() > 0) {
              friendsListAdapter.addAll(body);
              hideFetchText();
              return; // all went well, no need for error checking

            } else if (response.body() != null) {
              emptyListTextViewTop.setText(R.string.emptyListText);
              emptyListTextViewBottom.setText(R.string.thatsTooBad);
              return; // all went well, no need for error checking

            } else {
              emptyListTextViewTop.setText(R.string.failedToFetchList);
              emptyListTextViewBottom.setText(R.string.thatsTooBad);
            }

            String internalName = StaticValues.THIS_IS_NOT_AN_ERROR;
            try {
              internalName = new JSONObject(response.errorBody().string())
                  .getString(StaticValues.INTERNAL_NAME);
            } catch (IOException | JSONException | NullPointerException ignored) { }

            switch (internalName) {
              case StaticValues.NO_SUCH_TOKEN:
              case StaticValues.TOKEN_INVALID:
                showSnackbar(view, R.string.tokenInvalid);
                break;

              case StaticValues.INTERNAL_SERVER_ERROR:
                showSnackbar(view, R.string.somethingWentWrong);
                break;

              default:
                Log.w(StaticValues.ADDFRIEND, "Got an unknown error code: " + internalName);
            }
      }

      @Override
      @EverythingIsNonNull
      public void onFailure(Call<List<PlayerEntity>> call, Throwable t) {
        emptyListTextViewTop.setTextSize(R.string.failedToFetchList);
        emptyListTextViewBottom.setText(R.string.thatsTooBad);
        showSnackbar(view, R.string.somethingWentWrong);
      }
    });
  }

  private void showSnackbar(View view, int resourceId) {
    Snackbar.make(view, resourceId, Snackbar.LENGTH_LONG).show();
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
