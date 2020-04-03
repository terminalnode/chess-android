package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.List;

/**
 * A class representing a queen, used by ChessBoard to know what graphics to use,
 * what moves should be allowed and so on. Implements parcelable so that it can be
 * sent from the PickGameActivity to the PlayScreenActivity through intents.
 * @author Alexander Rundberg
 */
public class Queen extends Piece {
  public Queen() {
    super();
  }

  private Queen(Parcel in) {
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

  public static final Creator<Queen> CREATOR = new Creator<Queen>() {
    @Override
    public Queen createFromParcel(Parcel source) {
      return new Queen(source);
    }

    @Override
    public Queen[] newArray(int size) {
      return new Queen[size];
    }
  };
}
