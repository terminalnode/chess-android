package com.example.newtonchess.chesscomponents.pieces;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
  int x, y;
  PieceColor color;
  PieceType type;
  boolean isWhite;
  boolean hasMoved;


  //----- Constructor ----//
  Piece(int x, int y, PieceColor color, PieceType type) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.type = type;
    this.hasMoved = false;
    isWhite = color == PieceColor.WHITE;
  }


  //----- Abstract methods ----//
  public abstract List<int[]> getMoves(List<Piece> pieces);
  public abstract int getDrawableId();
  public abstract PieceType getPieceType();


  //----- Methods -----//
  public void move(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasMoved = true;
  }

  List<int[]> getStraightMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    boolean downBlocked = false;
    boolean upBlocked = false;
    boolean rightBlocked = false;
    boolean leftBlocked = false;

    for (int i = 1; i < 8; i++) {
      Log.i("PIECE", String.format("Calculating straight lines %s moves from %s", i, this));
      int[] down = new int[]{x, y + i};
      int[] up = new int[]{x, y - i};
      int[] right = new int[]{x + i, y};
      int[] left = new int[]{x - i, y};

      if (!downBlocked) {
        Log.i("PIECE", "Down is not blocked, adding move.");
        downBlocked = !addMoveToList(moves, down, pieces);
      }

      if (!upBlocked) {
        Log.i("PIECE", "Up is not blocked, adding move.");
        upBlocked = !addMoveToList(moves, up, pieces);
      }

      if (!rightBlocked) {
        Log.i("PIECE", "Right is not blocked, adding move.");
        rightBlocked = !addMoveToList(moves, right, pieces);
      }

      if (!leftBlocked) {
        Log.i("PIECE", "Left is not blocked, adding move.");
        leftBlocked = !addMoveToList(moves, left, pieces);
      }
    }

    return moves;
  }

  List<int[]> getDiagonalMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    boolean upRightBlocked = false;
    boolean upLeftBlocked = false;
    boolean downRightBlocked = false;
    boolean downLeftBlocked = false;

    for (int i = 1; i < 8; i++) {
      Log.i("PIECE", String.format("Calculating straight lines %s moves from %s", i, this));
      int[] upRight   = new int[]{x + i, y - i};
      int[] upLeft    = new int[]{x - i, y - i};
      int[] downRight = new int[]{x + i, y + i};
      int[] downLeft  = new int[]{x - i, y + i};

      if (!upRightBlocked) {
        Log.i("PIECE", "Up-Right is not blocked, adding move.");
        upRightBlocked = !addMoveToList(moves, upRight, pieces);
      }

      if (!upLeftBlocked) {
        Log.i("PIECE", "Up-Left is not blocked, adding move.");
        upLeftBlocked = !addMoveToList(moves, upLeft, pieces);
      }

      if (!downRightBlocked) {
        Log.i("PIECE", "Down-Right is not blocked, adding move.");
        downRightBlocked = !addMoveToList(moves, downRight, pieces);
      }

      if (!downLeftBlocked) {
        Log.i("PIECE", "Down-Left is not blocked, adding move.");
        downLeftBlocked = !addMoveToList(moves, downLeft, pieces);
      }
    }

    return moves;
  }

  private Piece pieceAtPosition(int[] position, List<Piece> pieces) {
    int xHere = position[0];
    int yHere = position[1];

    for (Piece piece : pieces) {
      if (piece.getX() == xHere && piece.getY() == yHere) {
        Log.i("PIECE", String.format("Space occupied by %s", piece));
        return piece;
      }
    }
    return null;
  }

  private boolean isPositionOutOfBounds(int[] position) {
    int xHere = position[0];
    int yHere = position[1];

    return
        xHere < 0 || xHere > 7 ||
        yHere < 0 || yHere > 7;
  }

  public PieceType[] upgrade() {
    return null;
  }

  boolean addMoveToList(List<int[]> moves, int x, int y, List<Piece> otherPieces) {
    return addMoveToList(moves, new int[]{x, y}, otherPieces);
  }

  boolean addMoveToList(List<int[]> moves, int[] position, List<Piece> otherPieces) {
    if (isPositionOutOfBounds(position)) {
      Log.i("PIECE", "Move is out of bounds, returning false");
      return false;
    }

    Piece pieceHere = pieceAtPosition(position, otherPieces);
    boolean blockedByPiece = pieceHere != null;
    boolean blockedByOwnColor = blockedByPiece && pieceHere.getColor() == color;

    if (blockedByOwnColor) {
      Log.i("PIECE", "Blocked by own color, returning false");
      return false;
    } else if (blockedByPiece) {
      Log.i("PIECE", "Blocked by other piece, returning false but adding position");
      moves.add(position);
      return false;
    } else {
      Log.i("PIECE", "Coast is clear, adding position");
      moves.add(position);
      return true;
    }
  }

  @Override
  public String toString() {
    return String.format("<%s (%s,%s)>", type, x, y);
  }


  //----- Getters and setters -----//
  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }

  public void setColor(PieceColor color) {
    this.color = color;
  }

  public PieceColor getColor() {
    return color;
  }

  public void setType(PieceType type) {
    this.type = type;
  }

  public PieceType getType() {
    return type;
  }
}
