package cs3500.klondike;

import java.io.InputStreamReader;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * This class takes in input through the command line and can run three different types of
 * klondike solitaire games based off those inputs: basic, limited, or white head. The number of
 * cascade piles and draw cards can be optionally specified, and the number of draws for a
 * limited game has to be specified.
 */
public final class Klondike {

  /**
   * The main method that runs the type of klondike game, which is determined by the input of
   * the user. Valid input is: basic o o, whitehead o o, limited r o o, where o are optional
   * positive integers and r is a positive required integer.
   *
   * @throws IllegalArgumentException if the input is null or the input is incorrect
   */
  public static void main(String[] args) {
    if (args == null) {
      throw new IllegalArgumentException("args is null");
    }
    String gameType = "";
    if (args.length == 0) {
      throw new IllegalArgumentException("no input");
    } else {
      gameType = args[0];
    }

    switch (gameType) {
      case "basic":
        checkArgsAndStartGame(args, gameType);
        break;
      case "limited":
        checkArgsAndStartLimitedGame(args, gameType);
        break;
      case "whitehead":
        checkArgsAndStartGame(args, gameType);
        break;
      default:
        throw new IllegalArgumentException("invalid game type");
    }
  }

  private static void checkArgsAndStartLimitedGame(String[] args, String gameType) {
    // checks that the input from args is valid and if it is, start a new limited klondike game
    String draws = "";
    int limited = 0;
    if (args.length == 1) {
      throw new IllegalArgumentException("invalid input for limited");
    } else {
      draws = args[1];
      try {
        limited = Integer.parseInt(draws);
      } catch (NumberFormatException nfe) {
        throw new IllegalArgumentException("invalid input for limited");
      }
    }
    if (limited < 1) {
      throw new IllegalArgumentException("the limited number of draws has to be greater than 0");
    }
    int numCascade = 7;
    int numDraw = 3;
    if (checkDrawOrCascade(args, 2)) {
      try {
        numCascade = Integer.parseInt(args[2]);
      } catch (NumberFormatException nfe) {
        // keep going
      }
    }
    if (checkDrawOrCascade(args, 3)) {
      try {
        numDraw = Integer.parseInt(args[3]);
      } catch (NumberFormatException nfe) {
        // keep going
      }
    }
    initGame(gameType, limited, numCascade, numDraw);
  }

  private static void checkArgsAndStartGame(String[] args, String gameType) {
    // checks that the input from args is valid and if it is, start a new basic or
    // whitehead klondike game
    int numCascade = 7;
    int numDraw = 3;
    if (checkDrawOrCascade(args, 1)) {
      try {
        numCascade = Integer.parseInt(args[1]);
      } catch (NumberFormatException nfe) {
        // keep going
      }
    }
    if (checkDrawOrCascade(args, 2)) {
      try {
        numDraw = Integer.parseInt(args[2]);
      } catch (NumberFormatException nfe) {
        // keep going
      }
    }
    initGame(gameType, 0, numCascade, numDraw);
  }
  
  private static void initGame(String gameType, int limited, int numCascade, int numDraw) {
    // initializes a new klondike game of the given game type, along with the given number of
    // cascades and draw cards, and the number of discards allowed if it is a limited game
    KlondikeModel km;
    KlondikeController kc = new KlondikeTextualController(
            new InputStreamReader(System.in), System.out);
    switch (gameType) {
      case "basic":
        try {
          kc.playGame(KlondikeCreator.create(KlondikeCreator.GameType.BASIC),
                  KlondikeCreator.create(KlondikeCreator.GameType.BASIC).getDeck(),
                  false, numCascade, numDraw);
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
        }
        break;
      case "limited":
        km = new LimitedDrawKlondike(limited);
        try {
          kc.playGame(km, km.getDeck(), false, numCascade, numDraw);
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
        }
        break;
      case "whitehead":
        try {
          kc.playGame(KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD),
                  KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD).getDeck(),
                  false, numCascade, numDraw);
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
        }
        break;
      default:
        break;
    }
  }

  private static boolean checkDrawOrCascade(String[] args, int start) {
    // returns true if there is an integer at the given index
    return args.length > start && isInteger(args[start]);
  }

  private static boolean isInteger(String str) {
    // returns true if the given string is an integer, false if otherwise
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}