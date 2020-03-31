package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Bishop extends Piece {
  public Bishop(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.BISHOP);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wbishop : R.drawable.bbishop;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }
}
