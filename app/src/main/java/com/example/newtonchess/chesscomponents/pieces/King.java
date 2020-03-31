package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class King extends Piece {
  public King(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.KING);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wking : R.drawable.bking;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
  }
}
