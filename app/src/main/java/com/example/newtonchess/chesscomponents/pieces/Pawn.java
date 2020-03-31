package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  public Pawn(int x, int y, PieceColor color) {
    super(x, y, color);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int dy = isWhite ? 1 : -1;
    int yPlusOne = y + dy;
    int yPlusTwo = y + dy * 2;
    int diagonalRight = x + 1;
    int diagonalLeft = x - 1;

    // Check if pawn can make diagonal moves and/or if pawn is blocked
    boolean yPlusOneBlocked = false;
    boolean yPlusTwoBlocked = false;
    boolean pieceOnRight = false;
    boolean pieceOnLeft = false;

    for (Piece piece : pieces) {
      int otherX = piece.getX();
      int otherY = piece.getY();
      boolean sameColor = piece.getColor() == color;

      if (otherX == x && otherY == yPlusOne) {
        yPlusOneBlocked = true;
      } else if (otherX == x && otherY == yPlusTwo) {
        yPlusTwoBlocked = true;
      } else if (otherX == diagonalRight && otherY == yPlusOne && !sameColor) {
        pieceOnRight = true;
      } else if (otherX == diagonalLeft && otherY == yPlusOne && !sameColor) {
        pieceOnLeft = true;
      }
    }

    // Add moves to list
    if (!yPlusOneBlocked) {
      addMoveToList(moves, x, yPlusOne);

      if (!yPlusTwoBlocked && !hasMoved) {
        addMoveToList(moves, x, yPlusTwo);
      }
    }
    if (pieceOnLeft) {
      addMoveToList(moves, diagonalLeft, yPlusOne);
    }
    if (pieceOnRight) {
      addMoveToList(moves, diagonalRight, yPlusOne);
    }

    return moves;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wpawn : R.drawable.bpawn;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.PAWN;
  }

  @Override
  public PieceType[] upgrade() {
    int edgeY = isWhite ? 7 : 0;
    if (edgeY == y) {
      return new PieceType[] {
          PieceType.QUEEN,
          PieceType.ROOK,
          PieceType.BISHOP,
          PieceType.KNIGHT
      };
    }
    return null;
  }
}
