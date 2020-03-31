package com.example.newtonchess.chesscomponents.pieces;

import com.example.newtonchess.R;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
  public King(int x, int y, PieceColor color) {
    super(x, y, color, PieceType.KING);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int[][] theoreticalMoves = new int[][]{
        { x    , y + 1 },
        { x    , y - 1 },
        { x + 1, y     },
        { x - 1, y     },
        { x + 1, y + 1 },
        { x + 1, y - 1 },
        { x - 1, y + 1 },
        { x - 1, y - 1 }
    };

    for (int[] theoreticalMove : theoreticalMoves) {
      addMoveToList(moves, theoreticalMove[0], theoreticalMove[1], pieces);
    }
    return moves;
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
