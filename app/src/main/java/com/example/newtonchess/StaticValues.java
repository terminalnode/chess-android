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
  public static final String LOGINSCREEN = "LOGINSCREEN";
  public static final String SPLASHSCREEN = "SPLASHSCREEN";

  // API Errors
  public static final String INTERNAL_NAME = "internalName";
  public static final String TOKEN_INVALID = "TokenInvalidException";
  public static final String NOT_PART_OF_GAME = "NotPartOfThisGameException";
  public static final String NO_SUCH_TOKEN = "NoSuchTokenException";
  public static final String NO_SUCH_GAME = "NoSuchGameException";
  public static final String INTERNAL_SERVER_ERROR = "InternalServerErrorException";
  public static final String MISSING_FIELDS_EXCEPTION = "PlayerCreateMissingFieldsException";
  public static final String USERNAME_TAKEN = "PlayerCreateUsernameTaken";
  public static final String NO_SUCH_USER = "LoginNoSuchUserException";
  public static final String WRONG_PASSWORD = "LoginIncorrectPasswordException";
  public static final String FAILED_TO_CREATE_TOKEN = "LoginFailedToCreateTokenException";

  // We use this error as a default value for out error strings,
  // if something goes wrong and we see this we know the server didn't send it.
  public static final String THIS_IS_NOT_AN_ERROR = "'tis not a real error my dude";
}
