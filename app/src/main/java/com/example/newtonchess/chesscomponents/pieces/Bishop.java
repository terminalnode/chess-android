package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.List;

public class Bishop extends Piece {
  public Bishop() {
    super();
  }

  public Bishop(Parcel in) {
    super(in);
  }

  public Bishop(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return getDiagonalMoves(pieces);
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wbishop : R.drawable.bbishop;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
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
