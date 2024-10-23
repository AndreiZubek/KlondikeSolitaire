package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A class that can make one of three types of klondike games: a basic game, a
 * whitehead game, and a limited game.
 */
public class KlondikeCreator {

  /**
   * The three different types of klondike games that can be made.
   */
  public enum GameType {
    BASIC,
    LIMITED,
    WHITEHEAD
  }

  /**
   * Returns a new klondike model that is the same type as the given GameType.
   *
   * @return a new klondike model of the specified game type
   * @throws IllegalArgumentException if the given game type isn't one of the three klondike games
   */
  public static KlondikeModel create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicKlondike();
      case LIMITED:
        return new LimitedDrawKlondike(2);
      case WHITEHEAD:
        return new WhiteheadKlondike();
      default:
        throw new IllegalArgumentException("Invalid game type");
    }
  }
}
