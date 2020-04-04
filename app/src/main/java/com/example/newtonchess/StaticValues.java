package com.example.newtonchess;

public class StaticValues {
  // Intent extras names
  public static final String INTENT_TOKEN = "TokenEntity";
  public static final String INTENT_GAME = "GameEntity";

  // Log tags
  public static final String PICKSCREEN = "PICKSCREEN";
  public static final String PLAYSCREEN = "PLAYSCREEN";
  public static final String CHESSBOARD = "CHESSBOARD";
  public static final String ADDFRIEND = "ADDFRIEND";
  public static final String FRIENDLIST = "FRIENDLIST";

  // API Errors
  public static final String INTERNAL_NAME = "internalName";
  public static final String TOKEN_INVALID = "TokenInvalidException";
  public static final String NOT_PART_OF_GAME = "NotPartOfThisGameException";
  public static final String NO_SUCH_TOKEN = "NoSuchTokenException";
  public static final String NO_SUCH_GAME = "NoSuchGameException";
  public static final String INTERNAL_SERVER_ERROR = "InternalServerErrorException";

  // We use this error as a default value for out error strings,
  // if something goes wrong and we see this we know the server didn't send it.
  public static final String THIS_IS_NOT_AN_ERROR = "'tis not a real error my dude";
}
