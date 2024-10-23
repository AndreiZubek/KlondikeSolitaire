package cs3500.klondike.model.hw02;

import cs3500.klondike.model.hw04.KlondikeAbstractModel;

/**
 * Represents the primary class for playing a game of Klondike.
 * You can move cards from the draw pile to either the cascade piles or
 * the foundation piles. You can move a card from a cascade pile to a
 * foundation pile, or a stack of cards from a cascade pile to a
 * different cascade pile, so long as all these moves are
 * allowed by the rules of the game.
 */
public class BasicKlondike extends KlondikeAbstractModel {

  // the constructor for a basic klondike game
  public BasicKlondike() {
    super();
  }
}
