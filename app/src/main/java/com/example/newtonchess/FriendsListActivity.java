package com.example.newtonchess;

import android.content.Context;
import android.os.Bundle;

import com.example.newtonchess.api.Friend;
import com.example.newtonchess.api.UserData;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.newtonchess.R;

import java.util.HashMap;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {

  UserData loggedInUserData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friends_list);

    System.out.println("Print A");

    this.loggedInUserData = getIntent().getParcelableExtra("UserData");

    System.out.println("Print B");

    final ListView listView = (ListView) findViewById(R.id.listView);
    if (listView == null) {
      System.out.println("ListView is null");
    }

    System.out.println("Print C");

    final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, this.loggedInUserData.getFriendList());

    System.out.println("Print D");

    listView.setAdapter(adapter);

    System.out.println("Print E");

  }


  private class StableArrayAdapter extends ArrayAdapter<Friend> {
    //TODO Understand how this works and refactor.

    HashMap<Friend, Integer> mIdMap = new HashMap<Friend, Integer>();


    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<Friend> objects) {
      super(context, textViewResourceId, objects);
      System.out.println("Adapter Constructor A");

      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
      System.out.println("Adapter Constructor B");
    }


    @Override
    public long getItemId(int position) {
      Friend item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

  }

}
