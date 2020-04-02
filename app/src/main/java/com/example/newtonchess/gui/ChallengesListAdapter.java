package com.example.newtonchess.gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newtonchess.api.entities.ChallengeEntity;
import com.example.newtonchess.api.entities.TokenEntity;

import java.util.List;

public class ChallengesListAdapter extends ArrayAdapter<ChallengeEntity> {
  private Context context;
  private int listLayout;
  private TokenEntity token;

  public ChallengesListAdapter(
      @NonNull Context context,
      int listLayout,
      @NonNull List<ChallengeEntity> objects,
      TokenEntity token) {

    super(context, listLayout, objects);
    this.context = context;
    this.listLayout = listLayout;
    this.token = token;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return convertView;
  }
}
