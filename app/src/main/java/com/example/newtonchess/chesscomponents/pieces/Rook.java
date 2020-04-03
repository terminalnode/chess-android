package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.List;

/**
 * A class representing a rook, used by ChessBoard to know what graphics to use,
 * what moves should be allowed and so on. Implements parcelable so that it can be
 * sent from the PickGameActivity to the PlayScreenActivity through intents.
 * @author Alexander Rundberg
 */
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

  public static final Creator<Rook> CREATOR = new Creator<Rook>() {
    @Override
    public Rook createFromParcel(Parcel source) {
      return new Rook(source);
    }

    @Override
    public Rook[] newArray(int size) {
      return new Rook[size];
    }
  };
}
