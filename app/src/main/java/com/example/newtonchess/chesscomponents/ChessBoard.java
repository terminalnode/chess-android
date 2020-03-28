/*
 This class is heavily inspired by the corresponding class in
 github user eviiit's material-chess-android repository:
 https://github.com/evijit/material-chess-android
 */
package com.example.newtonchess.chesscomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.newtonchess.R;

public class ChessBoard extends View {
  Paint currentPaint, darkPaint, lightPaint;
  int x0, y0, squareSize;
  boolean flipped;

  /**
   * Modified version of a default View constructor, which calls upon the built-in
   * constructor then also performs a few operations required for our application.
   * @param context The context in which the board is being drawn.
   * @param attrs The attribute set being passed to the view.
   */
  public ChessBoard(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    darkPaint = new Paint();
    lightPaint = new Paint();
    currentPaint = lightPaint;
    flipped = false;

    darkPaint.setColor(getResources().getColor(R.color.darkSquare));
    lightPaint.setColor(getResources().getColor(R.color.lightSquare));
  }

  /**
   * This is called when the board is being drawn and draws out all
   * the squares of the chessboard.
   * @param canvas The canvas upon which the chessboard is being drawn.
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Calculate best square size.
    final int width = getWidth();
    final int height = getHeight();
    squareSize = getSquareSize(Math.min(width, height));

    // Set x0 and y0 to upper left corner of the board,
    // centering the board based on the square size.
    x0 = (width - squareSize * 8) / 2;
    y0 = (height - squareSize * 8) / 2;

    // Loop over the board to paint the squares
    for (int x = 0; x < 8; x++) {
      flipColor();
      int xCoordinate = getXCoordinate(x);
      for (int y = 0; y < 8; y++) {
        flipColor();
        int yCoordinate = getYCoordinate(y);
        canvas.drawRect(
            xCoordinate,
            yCoordinate,
            xCoordinate + squareSize,
            yCoordinate + squareSize,
            currentPaint
        );
      }
    }
  }

  /**
   * Calculate the height and width of the squares on the board.
   * @param length The height or width of a chess board.
   * @return The height and width of a square on the chess board.
   */
  private int getSquareSize(int length) {
    return length / 8;
  }

  /**
   * Get the leftmost x-coordinate of a square given it's
   * x-number on the board (0 - 7 on a normal chess board).
   * @param x The x-number of the square.
   * @return The leftmost x-coordinate of the square (or column).
   */
  private int getXCoordinate(int x) {
    return x0 + squareSize * (flipped ? 7 - x : x);
  }

  /**
   * Get the topmost y-coordinate of a square given it's
   * y-number on the board (0 - 7 on a normal chess board).
   * @param y The y-number of the square.
   * @return The topmost y-coordinate of the square (or column).
   */
  private int getYCoordinate(int y) {
    return y0 + squareSize * (flipped ? y : 7 - y);
  }

  /**
   * Change currentPaint from lightPaint to darkPaint and
   * vice versa in order to flip between the two when drawing
   * the chess board.
   */
  private void flipColor() {
    currentPaint =
        currentPaint == lightPaint ?
            darkPaint : lightPaint;
  }
}
