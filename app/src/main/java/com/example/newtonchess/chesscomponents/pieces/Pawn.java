package com.example.newtonchess.chesscomponents.pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  protected Pawn(int x, int y) {
    super(x, y);
  }

  @Override
  public List<int[]> getMoves() {
    List<int[]> moves = new ArrayList<>();
    int dy = isWhite ? 1 : -1;
    int yPlusOne = y + dy;
    int yPlusTwo = y + dy * 2;
    addMoveToList(moves, x, yPlusOne);

    if (!hasMoved) {
      addMoveToList(moves, x, yPlusTwo);
    }

    return moves;
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
