package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Rook extends Piece {
  public Rook(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.ROOK);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wrook : R.drawable.brook;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }
}
