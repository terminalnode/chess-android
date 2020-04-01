package com.example.newtonchess.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.gui.FriendsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {
  TokenEntity token;
  ListView friendsListView;
  FriendsListAdapter friendsListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friends_list);

    // Retrieve the token
    token = getIntent().getParcelableExtra("TokenEntity");
    Log.i("ACTIVITY", String.format("Main menu started, token: %s", token));

    // Find the friendsListView
    friendsListView = findViewById(R.id.friendsListView);

    // Create an example list of friends to display
    List<PlayerEntity> testList = new ArrayList<>();
    testList.add(new PlayerEntity("Test 01", "Password 01"));
    testList.add(new PlayerEntity("Test 02", "Password 02"));
    testList.add(new PlayerEntity("Test 03", "Password 03"));
    testList.add(new PlayerEntity("Test 04", "Password 04"));
    testList.add(new PlayerEntity("Test 05", "Password 05"));
    testList.add(new PlayerEntity("Test 06", "Password 06"));
    testList.add(new PlayerEntity("Test 07", "Password 07"));
    testList.add(new PlayerEntity("Test 08", "Password 08"));
    testList.add(new PlayerEntity("Test 09", "Password 09"));
    testList.add(new PlayerEntity("Test 10", "Password 10"));

    // Create the adapter and set it to the list view
    friendsListAdapter = new FriendsListAdapter(this, R.layout.list_single_friend, testList);
    friendsListView.setAdapter(friendsListAdapter);

    // Add friends to list view

    //final ListView listView = findViewById(R.id.listView);
    //final StableArrayAdapter adapter = new StableArrayAdapter(this, R.id.textView2, this.loggedInUserData.getFriendList());
    //listView.setAdapter(adapter);
  }

  // private class StableArrayAdapter extends ArrayAdapter<Friend> {
  //   //TODO Understand how this works and refactor.
  //   HashMap<Friend, Integer> mIdMap = new HashMap<Friend, Integer>();
  //   public StableArrayAdapter(Context context, int textViewResourceId,
  //                             List<Friend> objects) {
  //     super(context,R.layout.custom_list_view , textViewResourceId, objects);

  //     for (int i = 0; i < objects.size(); ++i) {
  //       mIdMap.put(objects.get(i), i);
  //     }
  //   }


  //   @Override
  //   public long getItemId(int position) {
  //     Friend item = getItem(position);
  //     return mIdMap.get(item);
  //   }

  //   @Override
  //   public boolean hasStableIds() {
  //     return true;
  //   }

  // }
}
