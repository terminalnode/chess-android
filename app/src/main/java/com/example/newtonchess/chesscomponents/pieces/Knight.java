package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Knight extends Piece {
  public Knight(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.KNIGHT);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wknight : R.drawable.bknight;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KNIGHT;
  }
}
