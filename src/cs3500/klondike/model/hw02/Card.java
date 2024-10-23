package cs3500.klondike.model.hw02;

/**
 * This interface represents a playing card. A card can be one of
 * 4 suits, and can be any number from A-K. A card has a string representation,
 * and you can turn a card up or down and check if it is face up.
 */
public interface Card {

  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   * @return the formatted card
   */
  String toString();

  /**
   * Gives you the number of this playing card.
   *
   * @return the number on a card
   */
  int getNum();
}
