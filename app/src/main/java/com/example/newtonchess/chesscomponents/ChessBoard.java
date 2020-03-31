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
  Paint currentPaint, darkPaint, lightPaint, highlightPaint;
  int xMin, yMin, selectedX, selectedY, squareSize;
  boolean flipped;
  List<Piece> pieces;
  PieceColor playerColor, playerTurn;
  Piece selectedPiece;

  /**
   * Modified version of a default View constructor, which calls upon the built-in
   * constructor then also performs a few operations required for our application.
   * @param context The context in which the board is being drawn.
   * @param attrs The attribute set being passed to the view.
   */
  public ChessBoard(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    // Generate pieces if they do not exist
    if (pieces == null) {
      pieces = new ArrayList<>();
      generatePieces();
    }

    // Set default player color and whose turn it is.
    playerColor = PieceColor.WHITE;
    playerTurn = PieceColor.WHITE;

    // Board is not flipped by default
    flipped = false;

    // Set selected tile to inactive (-1,-1)
    selectedX = -1;
    selectedY = -1;

    // Set light and dark tile colors
    darkPaint = new Paint();
    lightPaint = new Paint();
    highlightPaint = new Paint();
    darkPaint.setColor(ContextCompat.getColor(context, R.color.darkSquare));
    lightPaint.setColor(ContextCompat.getColor(context, R.color.lightSquare));
    highlightPaint.setColor(ContextCompat.getColor(context, R.color.colorComplementary));
    highlightPaint.setStyle(Paint.Style.STROKE);
    highlightPaint.setStrokeWidth(10.0F);

    currentPaint = lightPaint;
  }

  /**
   * Generate a standard set of pieces, and add these to the pieces list.
   */
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
    drawSquares(canvas);

    // Loop over the pieces and place them on the board
    drawPieces(canvas);
  }

  /**
   * Draw the squares of the chessboard.
   * @param canvas The canvas on which the board will be drawn.
   */
  private void drawSquares(Canvas canvas) {
    for (int x = 0; x < 8; x++) {
      flipCurrentPaint();
      int xCoordinate = getXCoordinate(x);
      for (int y = 0; y < 8; y++) {
        flipCurrentPaint();
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
   * Highlight a square based on its x and y position in the grid.
   * @param canvas The canvas which the square resides on.
   * @param xSquare The square's x-position in the grid.
   * @param ySquare The square's y-position in the grid.
   */
  private void highlightSquare(Canvas canvas, int xSquare, int ySquare) {
    int x0 = getXCoordinate(xSquare);
    int y0 = getYCoordinate(ySquare);

    float left = x0 + highlightPaint.getStrokeWidth() / 2;
    float top = y0 + highlightPaint.getStrokeWidth() / 2;
    float right = x0 + squareSize - highlightPaint.getStrokeWidth() / 2;
    float bottom = y0 + squareSize - highlightPaint.getStrokeWidth() / 2;

    canvas.drawRect(left, top, right, bottom, highlightPaint);
  }

  /**
   * Highlight a square based on its position on the screen. Useful for when
   * we already have the position and don't want to calculate it again.
   * @param canvas The canvas which the square resides on.
   * @param left Left-most x-coordinate of the square.
   * @param top Top-most y-coordinate of the square.
   * @param right Right-most x-coordinate of the square.
   * @param bottom Bottom-most y-coordinate of the square.
   */
  private void highlightSquare(Canvas canvas, float left, float top, float right, float bottom) {
    left += highlightPaint.getStrokeWidth() / 2;
    top += highlightPaint.getStrokeWidth() / 2;
    right -= highlightPaint.getStrokeWidth() / 2;
    bottom -= highlightPaint.getStrokeWidth() / 2;

    canvas.drawRect(left, top, right, bottom, highlightPaint);
  }

  /**
   * Draw all the pieces on the board.
   * @param canvas The canvas on which the pieces will be drawn.
   */
  private void drawPieces(Canvas canvas) {
    selectedPiece = null;
    for (Piece piece : pieces) {
      int x = piece.getX();
      int y = piece.getY();

      int left = getXCoordinate(x);
      int top = getYCoordinate(y);
      int right = left + squareSize;
      int bottom = top + squareSize;

      Drawable drawable = ContextCompat.getDrawable(getContext(), piece.getDrawableId());

      if (drawable != null) {
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);

        if (x == selectedX && y == selectedY && piece.getColor() == playerColor) {
          Log.w("TOUCH", String.format("Setting selected piece to: %s", piece));
          selectedPiece = piece;
          highlightSquare(canvas, left, top, right, bottom);

          for (int[] move : piece.getMoves(pieces)) {
            highlightSquare(canvas, move[0], move[1]);
          }
        }
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
  private void flipCurrentPaint() {
    currentPaint =
        currentPaint == lightPaint ?
            darkPaint : lightPaint;
  }

  /**
   * Flip the current player color from PieceColor.WHITE
   * to PieceColor.BLACK and vice versa.
   */
  private void flipPlayerTurn() {
    playerTurn =
        playerTurn == PieceColor.WHITE ?
            PieceColor.BLACK : PieceColor.WHITE;
  }

  /**
   * Attempt to move from current position to these coordinates.
   * @param x The new x-position in the grid.
   * @param y The new y-position in the grid.
   */
  public void makeMove(int x, int y) {
    Log.w("TOUCH", String.format("Checking if we can move to (%s,%s)", x, y));

    for (int[] move : selectedPiece.getMoves(pieces)) {
      if (x == move[0] && y == move[1]) {
        Log.w("TOUCH", String.format("Moving piece to (%s,%s)", x, y));
        selectedPiece.move(x, y);

        flipPlayerTurn();
        return;
      }
    }
  }

  /**
   * This method is used as onTouchListener for the ChessBoard.
   * @param view The View in which the chessboard resides.
   * @param motionEvent The MotionEvent that triggered the method.
   * @return Whether or not we're interested in all subsequent events in this gesture.
   */
  public boolean onTouch(View view, MotionEvent motionEvent) {
    // Ignore the touch event if it's not the player's turn.
    if (playerColor != playerTurn) {
      return false;
    }

    // Get what square we're on
    int x = getXSquare((int) motionEvent.getX());
    int y = getYSquare((int) motionEvent.getY());
    Log.w("TOUCH", String.format("Touched square: (%s,%s)", x, y));

    if (selectedX == x && selectedY == y) {
      // Unset selection if same square is touched twice
      Log.w("TOUCH", "Same square as before, deselecting.");
      selectedX = -1;
      selectedY = -1;

    } else if (selectedPiece != null) {
      // Try to make a move
      Log.w("TOUCH", String.format("New square, selecting. selectedPiece is %s", selectedPiece));
      makeMove(x, y);
      selectedX = -1;
      selectedY = -1;

    } else {
      // Just select the tile
      Log.w("TOUCH", "Selected piece is null, will select specified tile and continue.");
      selectedX = x;
      selectedY = y;
    }

    invalidate();
    return false;
  }

  /**
   * Change the player color.
   * @param playerColor The player's new color.
   */
  private void setPlayerColor(PieceColor playerColor) {
    this.playerColor = playerColor;
  }
}
