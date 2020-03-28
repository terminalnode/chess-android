package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Queen extends Piece {
  public Queen(int x, int y, PieceColor color) {
    super(x, y, color);
  }

  @Override
  public List<int[]> getMoves() {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wq : R.drawable.bq;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.QUEEN;
  }
}
