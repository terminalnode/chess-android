package com.example.newtonchess.chesscomponents.pieces;

import android.os.Parcel;

import com.example.newtonchess.R;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  public Pawn() {
    super();
  }

  public Pawn(Parcel in) {
    super(in);
  }

  public Pawn(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int dy = isWhite() ? 1 : -1;
    int yPlusOne = getY() + dy;
    int yPlusTwo = getY() + dy * 2;
    int diagonalRight = getX() + 1;
    int diagonalLeft = getX() - 1;

    // Check if pawn can make diagonal moves and/or if pawn is blocked
    boolean yPlusOneBlocked = false;
    boolean yPlusTwoBlocked = false;
    boolean pieceOnRight = false;
    boolean pieceOnLeft = false;

    for (Piece piece : pieces) {
      int otherX = piece.getX();
      int otherY = piece.getY();

      if (otherX == getX() && otherY == yPlusOne) {
        yPlusOneBlocked = true;
      } else if (otherX == getX() && otherY == yPlusTwo) {
        yPlusTwoBlocked = true;
      } else if (otherX == diagonalRight && otherY == yPlusOne) {
        pieceOnRight = true;
      } else if (otherX == diagonalLeft && otherY == yPlusOne) {
        pieceOnLeft = true;
      }
    }

    // Add moves to list
    if (!yPlusOneBlocked) {
      addMoveToList(moves, getX(), yPlusOne, pieces);

      if (!yPlusTwoBlocked && !isMoved()) {
        addMoveToList(moves, getX(), yPlusTwo, pieces);
      }
    }
    if (pieceOnLeft) {
      addMoveToList(moves, diagonalLeft, yPlusOne, pieces);
    }
    if (pieceOnRight) {
      addMoveToList(moves, diagonalRight, yPlusOne, pieces);
    }

    return moves;
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wpawn : R.drawable.bpawn;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.PAWN;
  }

  @Override
  public PieceType[] upgrade() {
    int edgeY = isWhite() ? 7 : 0;
    if (edgeY == getY()) {
      return new PieceType[] {
          PieceType.QUEEN,
          PieceType.ROOK,
          PieceType.BISHOP,
          PieceType.KNIGHT
      };
    }
    return null;
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
