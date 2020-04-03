package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.List;

public class Bishop extends Piece {
  public Bishop() {
    super();
  }

  public Bishop(int internalId, int x, int y, boolean isWhite) {
    super(internalId, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return getDiagonalMoves(pieces);
  }

  @Override
  public int getDrawableId() {
    return isWhite() ? R.drawable.wbishop : R.drawable.bbishop;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }
}
