package com.example.newtonchess.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;

import java.util.List;

public class FriendsListAdapter extends ArrayAdapter<PlayerEntity> {
  private Context context;
  private int listLayout;
  private FriendsListListenerType listenerType;
  private TokenEntity token;

  public FriendsListAdapter(
      @NonNull Context context,
      int listLayout,
      @NonNull List<PlayerEntity> objects,
      FriendsListListenerType listenerType,
      TokenEntity token) {

    super(context, listLayout, objects);
    this.context = context;
    this.listLayout = listLayout;
    this.listenerType = listenerType;
    this.token = token;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    String name = getItem(position).getName();

    LayoutInflater inflater = LayoutInflater.from(context);
    convertView = inflater.inflate(listLayout, parent, false);

    // Find the views inside the adapter
    TextView friendNameTextView = convertView.findViewById(R.id.friendName);
    ImageView friendIcon = convertView.findViewById(R.id.friendIcon);
    Button button = convertView.findViewById(R.id.friendsListEntryButton);

    // Set button text and listener
    if (listenerType == FriendsListListenerType.CHALLENGE) {
      button.setText(R.string.challengeButton);
      button.setOnClickListener(new FriendsListChallengeListener(getItem(position), token, button));
    } else {
      button.setText(R.string.addFriendButton);
      button.setOnClickListener(new FriendsListAddFriendListener(getItem(position), token, button));
    }

    // Set views to correct values
    friendNameTextView.setText(name);
    if (position % 2 == 0) {
      friendIcon.setImageResource(R.drawable.bpawn);
    } else {
      friendIcon.setImageResource(R.drawable.wpawn);
    }

    return convertView;
  }
}
