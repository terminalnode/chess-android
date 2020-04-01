package com.example.newtonchess.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtonchess.R;
import com.example.newtonchess.api.entities.PlayerEntity;

import java.util.List;

public class FriendsListAdapter extends ArrayAdapter<PlayerEntity> {
  private Context context;
  private int listLayout;

  public FriendsListAdapter(@NonNull Context context, int listLayout, @NonNull List<PlayerEntity> objects) {
    super(context, listLayout, objects);
    this.context = context;
    this.listLayout = listLayout;
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
