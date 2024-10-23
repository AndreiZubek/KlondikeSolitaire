package cs3500.klondike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Card;
import java.util.List;

/**
 * A class that is a mock of a klondike model, to
 * be used for testing purposes for when IOexceptions are thrown.
 */
public class MockModelThrows implements KlondikeModel {
  private final MockAppendable log;
  private final BasicKlondike k = new BasicKlondike();
  private final List<Card> deck1 = k.getDeck();
  private final List<Card> deck = changeDeck1(deck1);
  private final List<Card> drawCards = new ArrayList<>();
  private int a = 0;

  private List<Card> changeDeck1(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "4♣"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♡"));
    return ret;
  }

  private Card retCard(List<Card> old, String s) {
    // returns the card in the given list that matches the given string
    for (Card c : old) {
      if (c.toString().equals(s)) {
        return c;
      }
    }
    return old.get(0);
  }

  /**
   * A constructor for a mockmodelthrows.
   */
  public MockModelThrows(MockAppendable log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public List<Card> getDeck() {
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    this.drawCards.add(retCard(deck1, "A♡"));
    this.drawCards.add(retCard(deck1, "2♡"));

  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    try {
      log.append(String.format("srcPile = %d, numCards = %d, destPile = %d\n",
              srcPile, numCards, destPile));
    } catch (IOException e) {
      this.a = 1;
    }
  }

  @Override
  public void moveDraw(int destPile) {
    try {
      log.append(String.format("destPile = %d\n",
              destPile));
    } catch (IOException e) {
      this.a = 2;
    }
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    try {
      log.append(String.format("srcPile = %d, foundationPile = %d\n",
              srcPile, foundationPile));
    } catch (IOException e) {
      this.a = 3;
    }
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) {
    try {
      log.append(String.format("foundationPile = %d\n",
              foundationPile));
    } catch (IOException e) {
      this.a = 4;
    }
  }

  @Override
  public void discardDraw() {
    try {
      log.append("discarded");
    } catch (IOException e) {
      this.a = 5;
    }
  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumPiles() {
    return 0;
  }

  @Override
  public int getNumDraw() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getScore() {
    return 0;
  }

  @Override
  public int getPileHeight(int pileNum) {
    return 0;
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) {
    return false;
  }

  @Override
  public Card getCardAt(int pileNum, int card) {
    return null;
  }

  @Override
  public Card getCardAt(int foundationPile) {
    return null;
  }

  @Override
  public List<Card> getDrawCards() {
    return this.drawCards;
  }

  @Override
  public int getNumFoundations() {
    return this.a;
  }
}
