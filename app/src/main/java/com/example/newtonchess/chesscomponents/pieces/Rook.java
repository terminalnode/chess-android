package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Rook extends Piece {
  public Rook(int x, int y, PieceColor color) {
    super(x, y, color);
  }

  @Override
  public List<int[]> getMoves() {
    return null;
  }

  @Override
  public int getDrawableId() {
    return color == PieceColor.WHITE ?
        R.drawable.wr : R.drawable.br;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }
}