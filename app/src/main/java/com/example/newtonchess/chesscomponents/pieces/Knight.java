package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a knight, used by ChessBoard to know what graphics to use,
 * what moves should be allowed and so on. Implements parcelable so that it can be
 * sent from the PickGameActivity to the PlayScreenActivity through intents.
 * @author Alexander Rundberg
 */
public class Knight extends Piece {
  public Knight() {
    super();
  }

  private Knight(Parcel in) {
    super(in);
  }

  public Knight(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int x = getX();
    int y = getY();

    int[][] theoreticalMoves = new int[][]{
        { x - 1, y - 2 },
        { x + 1, y - 2 },
        { x - 1, y + 2 },
        { x + 1, y + 2 },
        { x - 2, y - 1 },
        { x - 2, y + 1 },
        { x + 2, y - 1 },
        { x + 2, y + 1 }
    };

    for (int[] theoreticalMove : theoreticalMoves) {
      addMoveToList(moves, theoreticalMove[0], theoreticalMove[1], pieces);
    }

    return moves;
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wknight : R.drawable.bknight;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KNIGHT;
  }

  public static final Creator<Knight> CREATOR = new Creator<Knight>() {
    @Override
    public Knight createFromParcel(Parcel source) {
      return new Knight(source);
    }

    @Override
    public Knight[] newArray(int size) {
      return new Knight[size];
    }
  };
}
