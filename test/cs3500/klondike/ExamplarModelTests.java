package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the klondike model.
 */
public class ExamplarModelTests {

  // a deck with A-4, 2 different suits/colors
  private List<Card> changeDeck4(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "4♣"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));

    return ret;
  }

  // a deck with A-K, 1 suit
  private List<Card> changeDeck3(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "K♡"));
    ret.add(retCard(old, "Q♡"));
    ret.add(retCard(old, "J♡"));
    ret.add(retCard(old, "10♡"));
    ret.add(retCard(old, "9♡"));
    ret.add(retCard(old, "8♡"));
    ret.add(retCard(old, "7♡"));
    ret.add(retCard(old, "6♡"));
    ret.add(retCard(old, "5♡"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "2♡"));

    return ret;
  }

  // A deck with A-4, 1 suit
  private List<Card> changeDeck2(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "2♡"));

    return ret;
  }

  // A deck with A-4, 2 different suits/colors
  private List<Card> changeDeck1(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "4♣"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "3♡"));

    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "A♣"));

    return ret;
  }

  // A deck with A-4, 2 different suits/colors
  private List<Card> changeDeck(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "4♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "4♡"));

    return ret;
  }


  // returns the card in the given list that matches the given string
  private Card retCard(List<Card> old, String s) {
    for (Card c : old) {
      if (c.toString().equals(s)) {
        return c;
      }
    }
    return old.get(0);
  }

  @Test
  public void finishedGame1() {
    // initializes and plays through a game of klondike, moves from draw and cascades to
    // foundations, game ends with a score of 8
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 0);

    k.discardDraw();
    k.moveDrawToFoundation(1);
    k.moveDrawToFoundation(1);
    k.moveDrawToFoundation(1);
    k.moveDrawToFoundation(1);
    Assert.assertFalse(k.isGameOver());
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 0);
    k.moveDrawToFoundation(0);
    Assert.assertTrue(k.isGameOver());
    Assert.assertEquals(8, k.getScore());
  }

  @Test
  public void finishedGame2() {
    // moves a pile of 3 cards to a different cascade pile, moves draw cards and
    // cascade cards to foundations, checks if game is over
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck1(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveDraw(1);
    k.moveDraw(1);
    k.movePile(1, 3, 0);

    k.moveDraw(1);
    k.moveDraw(1);
    k.moveDrawToFoundation(0);

    k.moveToFoundation(0, 1);
    k.moveToFoundation(1, 1);
    k.moveToFoundation(0, 0);
    k.moveToFoundation(0, 1);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(0, 0);
    k.moveToFoundation(1, 1);
    Assert.assertTrue(k.isGameOver());
  }

  @Test
  public void finishedGame3() {
    // discards draw cards in order to finish the game, checks if it is over
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck2(deck1);

    k.startGame(deck2, false, 1, 1);
    k.moveToFoundation(0, 0);
    k.discardDraw();
    k.discardDraw();
    k.moveDrawToFoundation(0);
    k.discardDraw();
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
    Assert.assertTrue(k.isGameOver());
  }

  @Test (expected = IllegalStateException.class)
  public void wrongDrawToDestPile() {
    // moves a draw card to a logically impossible destination pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck3(deck1);

    k.startGame(deck2, false, 1, 1);

    k.moveToFoundation(0, 0);

    k.moveDraw(0);
    k.moveDraw(0);
  }

  @Test (expected = IllegalStateException.class)
  public void wrongMovePileToCascade() {
    // moves a pile to a logically impossible destination pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.movePile(0, 1, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void wrongDrawToFoundation() {
    // moves a draw card to a logically impossible foundation pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveDrawToFoundation(0);
  }

  @Test (expected = IllegalStateException.class)
  public void wrongCascadeToEmptyFoundation() {
    // moves a non ace cascade card to an empty foundation pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(1, 0);
  }

  @Test (expected = IllegalStateException.class)
  public void wrongCascadeToEmptyFoundation1() {
    // moves a non ace cascade card to a logically impossible foundation pile
    // after a correct cascade to foundation pile move
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 0);
    k.moveToFoundation(0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void wrongMovePileToCascade1() {
    // moves a draw card to a cascade, then moves a pile including that card to a
    // logically impossible cascade
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 0);

    k.discardDraw();
    k.moveDraw(1);
    k.movePile(1, 2, 1);
  }

  @Test
  public void checkScore() {
    // checks that the score after moving an A to a foundation is 1
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 1);
    Assert.assertEquals(1, k.getScore());
  }

  @Test (expected = IllegalStateException.class)
  public void moveNonKingToEmptyCascade() {
    // moves a non king card to an empty cascade
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 1);
    k.moveDraw(0);
  }

  @Test (expected = IllegalStateException.class)
  public void wrongCascadeToNonEmptyFoundation() {
    // moves a logically impossible cascade card to a non empty foundation pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck4(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 0);
    k.moveToFoundation(1, 0);
  }

  @Test (expected = IllegalStateException.class)
  public void moveDrawToNonEmptyFoundation() {
    // moves a logically impossible draw card to a non empty foundation pile
    BasicKlondike k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    List<Card> deck2 = changeDeck4(deck1);

    k.startGame(deck2, false, 2, 1);
    k.moveToFoundation(0, 0);
    k.moveDrawToFoundation(0);
  }
}
