package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class King extends Piece {
  public King(int x, int y, PieceColor color) {
    super(x, y, color);
  }

  @Override
  public List<int[]> getMoves() {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wk : R.drawable.bk;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
  }
}
