package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A game type of klondike where the user can specify the number of times draw cards
 * can be discarded.
 */
public class LimitedDrawKlondike extends KlondikeAbstractModel {

  // list that keeps track of the number of times the visible draw cards were discarded
  private List<Integer> numTimesDrawCardIsUsed;

  // list that keeps track of the number of times the hidden draw cards were discarded
  private List<Integer> numTimesDrawCardIsUsedInvisible;

  // number of times redraws are allowed
  private final int numTimesRedrawAllowed;

  /**
   * The constructor for a limited draw klondike game.
   *
   * @throws IllegalArgumentException if the number of times redraws are allowed is less than 1
   */
  public LimitedDrawKlondike(int numTimesRedrawAllowed) {
    super();
    if (numTimesRedrawAllowed < 0) {
      throw new IllegalArgumentException(
              "The number of times redraws are allowed cannot be negative");
    }
    this.numTimesRedrawAllowed = numTimesRedrawAllowed;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    super.startGame(deck, shuffle, numPiles, numDraw);
    numTimesDrawCardIsUsed = new ArrayList<>();
    numTimesDrawCardIsUsedInvisible = new ArrayList<>();

    for (int i = 0; i < super.visibleDrawCards.size(); i++) {
      this.numTimesDrawCardIsUsed.add(0);
    }
    for (int i = 0; i < super.restCards.size(); i++) {
      this.numTimesDrawCardIsUsedInvisible.add(0);
    }
  }

  @Override
  public void moveDraw(int destPile) {
    super.moveDraw(destPile);
    this.numTimesDrawCardIsUsed.remove(0);
    this.addCardToVisibleIfRestIsntEmpty();
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) {
    super.moveDrawToFoundation(foundationPile);
    this.numTimesDrawCardIsUsed.remove(0);
    this.addCardToVisibleIfRestIsntEmpty();
  }

  private void addCardToVisibleIfRestIsntEmpty() {
    // if the rest of the draw cards isnt empty, move the number of times a card has been
    // discarded to the visible list, and remove that from the invisible list
    if (!this.numTimesDrawCardIsUsedInvisible.isEmpty()) {
      this.numTimesDrawCardIsUsed.add(this.numTimesDrawCardIsUsedInvisible.get(0));
      this.numTimesDrawCardIsUsedInvisible.remove(0);
    }
  }

  /**
   * Discards the topmost draw-card, discards it permanently if it has been discarded
   * too many times.
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if move is not allowable
   */
  @Override
  public void discardDraw() {
    super.checkStart();
    super.checkDrawIsEmpty();

    if (!this.numTimesDrawCardIsUsed.isEmpty()) {
      int i = this.numTimesDrawCardIsUsed.get(0) + 1;
      this.numTimesDrawCardIsUsed.set(0, i);
      if (this.numTimesDrawCardIsUsed.get(0) >= this.numTimesRedrawAllowed) {
        this.visibleDrawCards.remove(0);
        this.numTimesDrawCardIsUsed.remove(0);

        super.moveDrawRestToVisible();
        this.addCardToVisibleIfRestIsntEmpty();
      }
      else {
        super.discardDraw();
        this.numTimesDrawCardIsUsedInvisible.add(this.numTimesDrawCardIsUsed.get(0));
        this.numTimesDrawCardIsUsed.add(this.numTimesDrawCardIsUsedInvisible.get(0));
        this.numTimesDrawCardIsUsed.remove(0);
        this.numTimesDrawCardIsUsedInvisible.remove(0);
      }
    }
    else {
      throw new IllegalStateException("Move not allowed");
    }
  }

  @Override
  public boolean isGameOver() {
    super.checkStart();
    if (!this.visibleDrawCards.isEmpty() || !this.restCards.isEmpty()) {
      return false;
    }
    return super.isGameOver();
  }
}
