package com.example.newtonchess.activities;

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

    this.loggedInUserData = getIntent().getParcelableExtra("UserData");

    final ListView listView = findViewById(R.id.listView);

    final StableArrayAdapter adapter = new StableArrayAdapter(this, R.id.textView2, this.loggedInUserData.getFriendList());

    listView.setAdapter(adapter);



  }


  private class StableArrayAdapter extends ArrayAdapter<Friend> {
    //TODO Understand how this works and refactor.

    HashMap<Friend, Integer> mIdMap = new HashMap<Friend, Integer>();


    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<Friend> objects) {
      super(context,R.layout.custom_list_view , textViewResourceId, objects);

      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
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
