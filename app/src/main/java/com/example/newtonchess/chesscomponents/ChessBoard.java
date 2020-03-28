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

  public ChessBoard(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    darkPaint = new Paint();
    lightPaint = new Paint();
    currentPaint = lightPaint;
    flipped = false;

    // TODO set better colors or something
    darkPaint.setColor(getResources().getColor(R.color.darkSquare));
    lightPaint.setColor(getResources().getColor(R.color.lightSquare));
  }

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
      for (int y = 0; y < 8; y++) {
        flipColor();
        int xCoordinate = getXCoordinate(x);
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

  int getSquareSize(int length) {
    return length / 8;
  }

  protected int getXCoordinate(int x) {
    return x0 + squareSize * (flipped ? 7 - x : x);
  }

  protected int getYCoordinate(int y) {
    return y0 + squareSize * (flipped ? y : 7 - y);
  }

  private void flipColor() {
    currentPaint =
        currentPaint == lightPaint ?
            darkPaint : lightPaint;

  }
}
