/*
 This class is heavily inspired by the corresponding class in
 github user eviiit's material-chess-android repository:
 https://github.com/evijit/material-chess-android
 */

package com.example.newtonchess.chesscomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.newtonchess.R;
import com.example.newtonchess.chesscomponents.pieces.Bishop;
import com.example.newtonchess.chesscomponents.pieces.King;
import com.example.newtonchess.chesscomponents.pieces.Knight;
import com.example.newtonchess.chesscomponents.pieces.Pawn;
import com.example.newtonchess.chesscomponents.pieces.Piece;
import com.example.newtonchess.chesscomponents.pieces.PieceColor;
import com.example.newtonchess.chesscomponents.pieces.Queen;
import com.example.newtonchess.chesscomponents.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends View {
  Paint currentPaint, darkPaint, lightPaint;
  int xMin, yMin, squareSize;
  boolean flipped;
  boolean highlightSquare;
  List<Piece> pieces;

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
    highlightSquare = false;
    if (pieces == null) {
      pieces = new ArrayList<>();
      generatePieces();
    }

    // Set light and dark tile colors
    darkPaint.setColor(ContextCompat.getColor(context, R.color.darkSquare));
    lightPaint.setColor(ContextCompat.getColor(context, R.color.lightSquare));
  }

  private void generatePieces() {
    // Add pawns
    for (int x = 0; x < 8; x++) {
      pieces.add(new Pawn(x, 1, PieceColor.WHITE));
      pieces.add(new Pawn(x, 6, PieceColor.BLACK));
    }

    // Add rooks
    pieces.add(new Rook(0, 0, PieceColor.WHITE));
    pieces.add(new Rook(7, 0, PieceColor.WHITE));
    pieces.add(new Rook(0, 7, PieceColor.BLACK));
    pieces.add(new Rook(7, 7, PieceColor.BLACK));

    // Add knights
    pieces.add(new Knight(1, 0, PieceColor.WHITE));
    pieces.add(new Knight(6, 0, PieceColor.WHITE));
    pieces.add(new Knight(1, 7, PieceColor.BLACK));
    pieces.add(new Knight(6, 7, PieceColor.BLACK));

    // Add bishops
    pieces.add(new Bishop(2, 0, PieceColor.WHITE));
    pieces.add(new Bishop(5, 0, PieceColor.WHITE));
    pieces.add(new Bishop(2, 7, PieceColor.BLACK));
    pieces.add(new Bishop(5, 7, PieceColor.BLACK));

    // Add kings and queens
    pieces.add(new King(4, 0, PieceColor.WHITE));
    pieces.add(new King(4, 7, PieceColor.BLACK));
    pieces.add(new Queen(3, 0, PieceColor.WHITE));
    pieces.add(new Queen(3, 7, PieceColor.BLACK));
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
    squareSize = Math.min(width, height) / 8;

    // Set x0 and y0 to upper left corner of the board,
    // centering the board based on the square size.
    xMin = (width - squareSize * 8) / 2;
    yMin = (height - squareSize * 8) / 2;

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

    // Loop over the pieces and place them on the board
    for (Piece piece : pieces) {
      int xCoordinate = getXCoordinate(piece.getX());
      int yCoordinate = getYCoordinate(piece.getY());
      Drawable drawable = ContextCompat.getDrawable(getContext(), piece.getDrawableId());

      if (drawable != null) {
        drawable.setBounds(
            xCoordinate,
            yCoordinate,
            xCoordinate + squareSize,
            yCoordinate + squareSize
        );
        drawable.draw(canvas);
      }
    }
  }

  /**
   * Get the leftmost x-coordinate of a square given it's
   * x-number on the board (0 - 7 on a normal chess board).
   * @param x The x-number of the square.
   * @return The leftmost x-coordinate of the square (or column).
   */
  private int getXCoordinate(int x) {
    return xMin + squareSize * (flipped ? 7 - x : x);
  }

  /**
   * Get the topmost y-coordinate of a square given it's
   * y-number on the board (0 - 7 on a normal chess board).
   * @param y The y-number of the square.
   * @return The topmost y-coordinate of the square (or column).
   */
  private int getYCoordinate(int y) {
    return yMin + squareSize * (flipped ? y : 7 - y);
  }

  /**
   * Get the Y-position of a square (0 - 7 on a normal chess board)
   * from the y coordinate of the cursor of a MotionEvent.
   * @param y The y-coordinate of the cursor.
   * @return The Y-position of the square it's touching.
   */
  private int getYSquare(int y) {
    int ySquare = (y - yMin) / squareSize;
    return flipped ? ySquare : 7 - ySquare;
  }

  /**
   * Get the X-position of a square (0 - 7 on a normal chess board)
   * from the x coordinate of the cursor of a MotionEvent.
   * @param x The x-coordinate of the cursor.
   * @return The X-position of the square it's touching.
   */
  private int getXSquare(int x) {
    int xSquare = (x - xMin) / squareSize;
    return flipped ? 7 - xSquare : xSquare;
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

  /**
   * This method is used as onTouchListener for the ChessBoard.
   * @param view The View in which the chessboard resides.
   * @param motionEvent The MotionEvent that triggered the method.
   * @return Whether or not we're interested in all subsequent events in this gesture.
   */
  public boolean onTouch(View view, MotionEvent motionEvent) {
    // Get what square we're on
    int x = getXSquare((int) motionEvent.getX());
    int y = getYSquare((int) motionEvent.getY());
    Log.w("TOUCH", String.format("Touched square: (%s,%s)", x, y));

    return false;
  }
}
