package com.example.newtonchess.chesscomponents.pieces;

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

  public PieceType[] upgrade() {
    return null;
  }

  void addMoveToList(List<int[]> moves, int x, int y, List<Piece> otherPieces) {
    if (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
      boolean blockedByOwnColor = false;
      for (Piece otherPiece : otherPieces) {
        if (otherPiece.getColor() == color && otherPiece.getX() == x && otherPiece.getY() == y) {
          blockedByOwnColor = true;
          break;
        }
      }

      if (!blockedByOwnColor) {
        moves.add(new int[]{x, y});
      }
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
