package com.example.newtonchess.api.entities;

import android.util.Log;

import com.example.newtonchess.chesscomponents.pieces.Bishop;
import com.example.newtonchess.chesscomponents.pieces.King;
import com.example.newtonchess.chesscomponents.pieces.Knight;
import com.example.newtonchess.chesscomponents.pieces.Pawn;
import com.example.newtonchess.chesscomponents.pieces.Piece;
import com.example.newtonchess.chesscomponents.pieces.Queen;
import com.example.newtonchess.chesscomponents.pieces.Rook;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * TypeAdapter for pieces, used for serializing and deserializing Piece-type objects.
 * @author Alexander Rundberg
 */
public class PieceAdapter extends TypeAdapter<Piece> {
  @Override
  public void write(JsonWriter out, Piece value) throws IOException {
    Log.i("PIECE ADAPTER", "PieceAdapter#write let's goooo!");
    // not implemented yet
  }

  @Override
  public Piece read(JsonReader in) throws IOException {
    Log.i("PIECE ADAPTER", "Inside PieceAdapter#read, defining fields...");
    Piece piece;
    String type = "unknown";
    int internalId = 0;
    int x = 0;
    int y = 0;
    boolean moved = false;
    boolean white = false;

    in.beginObject();
    while (in.hasNext()) {
      String field = in.nextName();
      Log.i("PIECE ADAPTER", "Resolving field: " + field);
      Log.i("PIECE ADAPTER", "Peek: " + in.peek());

      switch (field) {
        case "class":       type = in.nextString(); break;
        case "internalId":  internalId = in.nextInt(); break;
        case "x":           x = in.nextInt(); break;
        case "y":           y = in.nextInt(); break;
        case "moved":       moved = in.nextBoolean(); break;
        case "white":       white = in.nextBoolean(); break;
        default:            in.skipValue();
      }
    }
    in.endObject();

    Log.i("PIECE ADAPTER", "Creating piece of type: " + type);
    switch(type) {
      case "bishop":  piece = new Bishop(); break;
      case "king":    piece = new King(); break;
      case "knight":  piece = new Knight(); break;
      case "pawn":    piece = new Pawn(); break;
      case "queen":   piece = new Queen(); break;
      case "rook":    piece = new Rook(); break;
      default:
        internalId = 0;
        piece = new Pawn();
        break;
    }

    Log.i("PIECE ADAPTER", "Internal ID: " + internalId);
    piece.setInternalId(internalId);
    Log.i("PIECE ADAPTER", "X: " + x);
    piece.setX(x);
    Log.i("PIECE ADAPTER", "Y: " + y);
    piece.setY(y);
    Log.i("PIECE ADAPTER", "Moved: " + moved);
    piece.setMoved(moved);
    Log.i("PIECE ADAPTER", "white: " + white);
    piece.setWhite(white);
    Log.i("PIECE ADAPTER", "Returning piece: " + piece);
    return piece;
  }
}
