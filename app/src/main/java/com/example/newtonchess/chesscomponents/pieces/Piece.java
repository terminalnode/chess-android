package com.example.newtonchess.chesscomponents.pieces;

import java.util.List;

public abstract class Piece {
  int x, y;
  boolean isWhite;
  boolean hasMoved;


  //----- Constructor ----//
  Piece(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasMoved = false;
  }


  //----- Abstract methods ----//
  public abstract List<int[]> getMoves();


  //----- Methods -----//
  public void move(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasMoved = true;
  }

  public PieceType[] upgrade() {
    return null;
  }

  void addMoveToList(List<int[]> moves, int x, int y) {
    if (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
      moves.add(new int[]{x, y});
    }
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

  public boolean isWhite() {
    return isWhite;
  }

  public void setWhite(boolean white) {
    isWhite = white;
  }

  public boolean isHasMoved() {
    return hasMoved;
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = hasMoved;
  }
}
