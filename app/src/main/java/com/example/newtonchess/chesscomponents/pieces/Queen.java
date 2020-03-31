package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Queen extends Piece {
  public Queen(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.QUEEN);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = getStraightMoves(pieces);
    moves.addAll(getDiagonalMoves(pieces));
    return moves;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wqueen : R.drawable.bqueen;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.QUEEN;
  }
}
