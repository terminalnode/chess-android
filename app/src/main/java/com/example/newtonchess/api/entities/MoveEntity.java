package com.example.newtonchess.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MoveEntity is sent to the API in order to say what move we want to make.
 * @author Alexander Rundberg
 */
public class MoveEntity {
  @SerializedName("pieceNumber")
  private int pieceNumber;

  @SerializedName("destination")
  private List<Integer> destination;

  //----- Constructors -----//
  public MoveEntity() {
    // Empty default constructor
  }

  public MoveEntity(int pieceNumber, List<Integer> destination) {
    this.pieceNumber = pieceNumber;
    this.destination = destination;
  }

  //----- Setters -----//
  public void setPieceNumber(int pieceNumber) {
    this.pieceNumber = pieceNumber;
  }

  public void setDestination(List<Integer> destination) {
    this.destination = destination;
  }

  //----- Getters -----//
  public int getPieceNumber() {
    return pieceNumber;
  }

  public List<Integer> getDestination() {
    return destination;
  }
}
