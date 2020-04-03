package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.List;

public class Queen extends Piece {
  public Queen() {
    super();
  }

  public Queen(Parcel in) {
    super(in);
  }

  public Queen(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = getStraightMoves(pieces);
    moves.addAll(getDiagonalMoves(pieces));
    return moves;
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wqueen : R.drawable.bqueen;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.QUEEN;
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
