package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
  public King() {
    super();
  }

  public King(Parcel in) {
    super(in);
  }

  public King(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int x = getX();
    int y = getY();

    int[][] theoreticalMoves = new int[][]{
        { x    , y + 1 },
        { x    , y - 1 },
        { x + 1, y     },
        { x - 1, y     },
        { x + 1, y + 1 },
        { x + 1, y - 1 },
        { x - 1, y + 1 },
        { x - 1, y - 1 }
    };

    for (int[] theoreticalMove : theoreticalMoves) {
      addMoveToList(moves, theoreticalMove[0], theoreticalMove[1], pieces);
    }
    return moves;
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wking : R.drawable.bking;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
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
