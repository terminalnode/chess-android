package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.List;

public class Rook extends Piece {
  public Rook() {
    super();
  }

  public Rook(Parcel in) {
    super(in);
  }

  public Rook(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return getStraightMoves(pieces);
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wrook : R.drawable.brook;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }

  public static final Creator<Bishop> CREATOR = new Creator<Bishop>() {
    @Override
    public Bishop createFromParcel(Parcel source) {
      return new Bishop(source);
    }

    @Override
    public Bishop[] newArray(int size) {
      return new Bishop[size];
    }
  };
}
