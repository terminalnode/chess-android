/*
 This class is heavily inspired by the corresponding class in
 github user eviiit's material-chess-android repository:
 https://github.com/evijit/material-chess-android
 */

package com.example.newtonchess.chesscomponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.newtonchess.R;
import com.example.newtonchess.StaticValues;
import com.example.newtonchess.api.entities.GameEntity;
import com.example.newtonchess.api.entities.MoveEntity;
import com.example.newtonchess.api.entities.PlayerEntity;
import com.example.newtonchess.api.entities.TokenEntity;
import com.example.newtonchess.api.retrofitservices.RetrofitHelper;
import com.example.newtonchess.chesscomponents.pieces.Piece;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * This is the view that shows us the board and lets us move pieces around it.
 * It can retrieve a GameEntity object through loadFromGameEntity which sets
 * all of its field according to the GameEntity retrieved from the server.
 *
 * @author Alexander Rundberg
 */
public class ChessBoard extends View {
  private Paint currentPaint, darkPaint, lightPaint, highlightPaint, selectionPaint;
  private int xMin, yMin, selectedX, selectedY, squareSize;
  private long turnsTaken, gameId;
  private boolean isWhite, isWhitesTurn, finished, flipped, inCheck;
  private List<Piece> pieces;
  private Piece selectedPiece;
  private TextView whoseTurnTextView;
  private TextView gameOverTextView;
  private ImageView whoseTurnPawn;
  private TokenEntity token;


  /**
   * Modified version of a default View constructor, which calls upon the built-in
   * constructor then also performs a few operations required for our application.
   * @param context The context in which the board is being drawn.
   * @param attrs The attribute set being passed to the view.
   */
  public ChessBoard(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    // Dummy values to make sure they're set to something and/or not null.
    pieces = new ArrayList<>();
    isWhite = true;
    isWhitesTurn = true;
    flipped = false;
    finished = false;
    turnsTaken = 0;
    gameId = 0;
    whoseTurnTextView = null;
    whoseTurnPawn = null;

    // Set selected tile to inactive (-1,-1)
    selectedX = -1;
    selectedY = -1;

    // Set light and dark tile colors
    darkPaint = new Paint();
    lightPaint = new Paint();
    highlightPaint = new Paint();
    selectionPaint = new Paint();

    darkPaint.setColor(ContextCompat.getColor(context, R.color.darkSquare));
    lightPaint.setColor(ContextCompat.getColor(context, R.color.lightSquare));

    selectionPaint.setColor(ContextCompat.getColor(context, R.color.colorNewtonOrange));
    selectionPaint.setStyle(Paint.Style.STROKE);
    selectionPaint.setStrokeWidth(10.0F);
    highlightPaint.setColor(ContextCompat.getColor(context, R.color.colorComplementary));
    highlightPaint.setStyle(Paint.Style.STROKE);
    highlightPaint.setStrokeWidth(10.0F);

    currentPaint = lightPaint;
  }

  /**
   * Loads settings from a GameEntity, then invalidates the board.
   * @param game The GameEntity to load data from.
   * @param thisPlayer Who this player is.
   */
  public void loadFromGameEntity(GameEntity game, PlayerEntity thisPlayer) {
    if (game == null) {
      Log.i(StaticValues.CHESSBOARD, "Game entity is null, nothing to do.");
      return;
    }

    // Retrieve values from game
    boolean isWhiteInCheck = game.isWhiteInCheck();
    boolean isBlackInCheck = game.isBlackInCheck();
    gameId = game.getId();
    turnsTaken = game.getTurnsTaken();
    finished = game.isFinished();
    pieces = game.getPieces() == null ? pieces : game.getPieces();
    isWhitesTurn = game.isWhitesTurn();
    isWhite = game.isGettingPlayerWhite();
    inCheck = (isWhite && isWhiteInCheck) || (!isWhite && isBlackInCheck);
    flipped = !isWhite;

    // Sanity checks
    Log.i(StaticValues.CHESSBOARD, "turnsTaken: " + turnsTaken);
    Log.i(StaticValues.CHESSBOARD, "finished: " + finished);
    Log.i(StaticValues.CHESSBOARD, "pieces is not null: " + (pieces != null));
    Log.i(StaticValues.CHESSBOARD, "isWhitesTurn: " + isWhitesTurn);
    Log.i(StaticValues.CHESSBOARD, "isWhite: " + isWhite);
    Log.i(StaticValues.CHESSBOARD, "whoseTurnTextView is not null: " + (whoseTurnTextView != null));

    updateWhoseTurnPawn();
    updateWhoseTurnTextView();

    if (finished && gameOverTextView != null) {
      gameOverTextView.setVisibility(View.VISIBLE);
    }

    invalidate();
  }

  /**
   * Update the ImageView that shows a white or black pawn depending on whose turn it is.
   */
  private void updateWhoseTurnPawn() {
    if (whoseTurnPawn == null) {
      // Nothing to do here
      Log.w(StaticValues.CHESSBOARD, "whoseTurnPawn is null!");

    } else if (isWhitesTurn) {
      whoseTurnPawn.setImageResource(R.drawable.wknight);

    } else {
      whoseTurnPawn.setImageResource(R.drawable.bknight);
    }
  }

  /**
   * Set the textView that says whether it's your turn or your opponents turn.
   */
  private void updateWhoseTurnTextView() {
    if (whoseTurnTextView == null) {
      // Nothing to do here
      Log.w(StaticValues.CHESSBOARD, "whoseTurnTextView is null!");

    } else if (isWhitesTurn == isWhite) {
      whoseTurnTextView.setText(R.string.yourTurn);

    } else {
      whoseTurnTextView.setText(R.string.opponentsTurn);
    }
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
    highlightSquare(canvas, xSquare, ySquare, highlightPaint);
  }

  /**
   * Highlight a square based on its x and y position in the grid.
   * @param canvas The canvas which the square resides on.
   * @param xSquare The square's x-position in the grid.
   * @param ySquare The square's y-position in the grid.
   * @param paint The paint to draw the highlight with.
   */
  private void highlightSquare(Canvas canvas, int xSquare, int ySquare, Paint paint) {
    int x0 = getXCoordinate(xSquare);
    int y0 = getYCoordinate(ySquare);

    float left = x0 + paint.getStrokeWidth() / 2;
    float top = y0 + paint.getStrokeWidth() / 2;
    float right = x0 + squareSize - paint.getStrokeWidth() / 2;
    float bottom = y0 + squareSize - paint.getStrokeWidth() / 2;

    canvas.drawRect(left, top, right, bottom, paint);
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
    highlightSquare(canvas, left, top, right, bottom, highlightPaint);
  }

  /**
   * Highlight a square based on its position on the screen. Useful for when
   * we already have the position and don't want to calculate it again.
   * @param canvas The canvas which the square resides on.
   * @param left Left-most x-coordinate of the square.
   * @param top Top-most y-coordinate of the square.
   * @param right Right-most x-coordinate of the square.
   * @param bottom Bottom-most y-coordinate of the square.
   * @param paint The paint with which the square will be highlighted
   */
  private void highlightSquare(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
    left += paint.getStrokeWidth() / 2;
    top += paint.getStrokeWidth() / 2;
    right -= paint.getStrokeWidth() / 2;
    bottom -= paint.getStrokeWidth() / 2;

    canvas.drawRect(left, top, right, bottom, paint);
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

        if (x == selectedX && y == selectedY && piece.isWhite() == isWhite) {
          Log.i("TOUCH", String.format("Setting selected piece to: %s", piece));
          selectedPiece = piece;
          highlightSquare(canvas, left, top, right, bottom, selectionPaint);

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
    isWhitesTurn = !isWhitesTurn;
    updateWhoseTurnPawn();
    updateWhoseTurnTextView();
  }

  /**
   * Attempt to move from current position to these coordinates.
   * @param x The new x-position in the grid.
   * @param y The new y-position in the grid.
   */
  private void makeMove(int x, int y) {
    List<int[]> validMoves = selectedPiece.getMoves(pieces);

    // See if we're trying to make a move
    for (int[] move : validMoves) {
      int newX = move[0];
      int newY = move[1];

      if (x == newX && y == newY) {
        makeApiMove(x, y);
        return;
      }
    }

    // Did not make a move, change selection and invalidate
    selectedX = x;
    selectedY = y;
    invalidate();
  }

  private void finalizeMove(int x, int y) {
    flipPlayerTurn();
    Log.i(
        StaticValues.CHESSBOARD,
        String.format("Finalizing move to (%s,%s) with %s", x, y, selectedPiece));
    removePieceInPosition(x, y);
    selectedPiece.move(x, y);
    selectedX = -1;
    selectedY = -1;
    invalidate();
  }

  /**
   * Make the call to the API to make a move.
   * @param x The new x-position in the grid.
   * @param y The new y-position in the grid.
   */
  private void makeApiMove(int x, int y) {
    // If we don't have a token there's no point to this.
    if (token == null) {
      showSnackbar(R.string.tokenInvalid);
      return;
    }

    // Create the move
    List<Integer> coordinates = new ArrayList<>();
    coordinates.add(x);
    coordinates.add(y);
    MoveEntity move = new MoveEntity(
        selectedPiece.getInternalId(),
        coordinates);

    // Make call
    RetrofitHelper
        .getGameService()
        .makeMove(token.getTokenString(), gameId, move)
        .enqueue(new Callback<MoveEntity>() {
          @Override
          @EverythingIsNonNull
          public void onResponse(Call<MoveEntity> call, Response<MoveEntity> response) {
            // If response was OK, finalize the move and return
            if (response.code() == 200) {
              finalizeMove(x, y);
              return;
            }

            // If not, lets see if we can find out what the error was
            String error = StaticValues.INTERNAL_SERVER_ERROR;
            try {
              String jsonError = response.errorBody().string();
              Log.e(
                  StaticValues.CHESSBOARD,
                  "Processing this error:\n" + jsonError);

              error = new JSONObject(jsonError)
                  .get(StaticValues.INTERNAL_NAME)
                  .toString();
            } catch (JSONException|IOException|NullPointerException ignored) { }

            switch (error) {
              case StaticValues.TOKEN_INVALID:
              case StaticValues.NO_SUCH_TOKEN:
                showSnackbar(R.string.tokenInvalid);
                break;
              case StaticValues.NOT_PART_OF_GAME:
                showSnackbar(R.string.notPartofGame);
                break;
              case StaticValues.NO_SUCH_GAME:
                showSnackbar(R.string.noSuchGame);
                break;
              default:
                Log.w(StaticValues.CHESSBOARD, "Got an unrecognised error! " + error);
                showSnackbar(R.string.somethingWentWrong);
            }
          }

          @Override
          @EverythingIsNonNull
          public void onFailure(Call<MoveEntity> call, Throwable t) {
            Log.e(StaticValues.CHESSBOARD, "API move call failed, got this: " + t.getMessage());
            showSnackbar(R.string.somethingWentWrong);
          }
        });
  }

  /**
   * Helper method for showing a snackbar with some message
   * @param resourceId The resource id for the message to be shown.
   */
  private void showSnackbar(int resourceId) {
    Snackbar.make(
        this,
        resourceId,
        Snackbar.LENGTH_LONG
    ).show();
    flipPlayerTurn();
  }

  /**
   * Looks through all of the pieces to see if there's a piece standing
   * there, and if there is remove it from the list of pieces.
   * @param x The x-position of the piece in the grid.
   * @param y The y-position of the piece in the grid.
   */
  private void removePieceInPosition(int x, int y) {
    Piece removePiece = null;
    for (Piece piece : pieces) {
      if (piece.getX() == x && piece.getY() == y) {
        removePiece = piece;
        break;
      }
    }
    pieces.remove(removePiece);
  }

  /**
   * This method is used as onTouchListener for the ChessBoard.
   * @param view The View in which the chessboard resides.
   * @param motionEvent The MotionEvent that triggered the method.
   * @return Whether or not we're interested in all subsequent events in this gesture.
   */
  public boolean onTouch(View view, MotionEvent motionEvent) {
    // Ignore the touch event if it's not the player's turn or the game is over.
    if ((isWhite != isWhitesTurn) || finished) {
      return false;
    }

    // Get what square we're on
    int x = getXSquare((int) motionEvent.getX());
    int y = getYSquare((int) motionEvent.getY());
    Log.i("TOUCH", String.format("Touched square: (%s,%s)", x, y));

    if (selectedX == x && selectedY == y) {
      // Unset selection if same square is touched twice
      Log.i("TOUCH", "Same square as before, deselecting.");
      selectedX = -1;
      selectedY = -1;

    } else if (selectedPiece != null) {
      // Try to make a move
      Log.i("TOUCH", String.format("New square, selecting. selectedPiece is %s", selectedPiece));
      makeMove(x, y);
      return false;

    } else {
      // Just select the tile
      Log.i("TOUCH", "Selected piece is null, will select specified tile and continue.");
      selectedX = x;
      selectedY = y;
    }

    invalidate();
    return false;
  }

  //----- Setters -----//
  public void setWhite(boolean white) {
    isWhite = white;
  }

  public void setWhitesTurn(boolean whitesTurn) {
    isWhitesTurn = whitesTurn;
  }

  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
    invalidate();
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public void setFlipped(boolean flipped) {
    this.flipped = flipped;
  }

  public void setWhoseTurnTextView(TextView whoseTurnTextView) {
    this.whoseTurnTextView = whoseTurnTextView;
  }

  public void setWhoseTurnPawn(ImageView whoseTurnPawn) {
    this.whoseTurnPawn = whoseTurnPawn;
  }

  public void setToken(TokenEntity token) {
    this.token = token;
  }

  @SuppressLint("ClickableViewAccessibility")
  public void setGameOverTextView(TextView gameOverTextView) {
    this.gameOverTextView = gameOverTextView;
  }

  public void setSelectedX(int selectedX) {
    this.selectedX = selectedX;
  }

  public void setSelectedY(int selectedY) {
    this.selectedY = selectedY;
  }

  public void setSelectedPiece(Piece selectedPiece) {
    this.selectedPiece = selectedPiece;
  }

  //----- Getters -----//
  public boolean isWhite() {
    return isWhite;
  }

  public boolean isWhitesTurn() {
    return isWhitesTurn;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public boolean isFlipped() {
    return flipped;
  }

  public boolean isFinished() {
    return finished;
  }

  public TextView getWhoseTurnTextView() {
    return whoseTurnTextView;
  }

  public ImageView getWhoseTurnPawn() {
    return whoseTurnPawn;
  }

  public TokenEntity getToken() {
    return token;
  }

  public TextView getGameOverTextView() {
    return gameOverTextView;
  }

  public int getSelectedX() {
    return selectedX;
  }

  public int getSelectedY() {
    return selectedY;
  }

  public Piece getSelectedPiece() {
    return selectedPiece;
  }
}
