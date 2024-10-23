package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.view.KlondikeTextualView;

/**
 * Tests for the whitehead klondike model class.
 */
public class WhiteheadModelTests {
  private KlondikeModel k;
  private List<Card> deck1;
  private List<Card> deck2;
  private List<Card> deck3;
  private List<Card> deck4;

  @Before
  public void setUpKlondike() {
    k = new WhiteheadKlondike();
    deck1 = changeDeck1(k.getDeck());
    deck2 = changeDeck2(k.getDeck());
    deck3 = changeDeck3(k.getDeck());
    deck4 = changeDeck4(k.getDeck());
  }

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

  private List<Card> changeDeck2(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "A♡"));

    return ret;
  }

  private List<Card> changeDeck3(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "A♡"));

    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "4♣"));

    return ret;
  }

  private List<Card> changeDeck4(List<Card> old) { // ♢ ♠
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♠"));
    ret.add(retCard(old, "A♠"));
    ret.add(retCard(old, "2♢"));
    ret.add(retCard(old, "A♢"));

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

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartGameTooManyPiles() {
    setUpKlondike();
    k.startGame(deck1, false, 30, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartGameNegativePiles() {
    setUpKlondike();
    k.startGame(deck1, false, -1, 3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidStartGameNegativeDraw() {
    setUpKlondike();
    k.startGame(deck1, false, 3, -1);
  }

  @Test (expected = IllegalStateException.class)
  public void invalidStartGameAlrStarted() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 1);
    k.startGame(deck1, false, 3, 1);
  }

  @Test
  public void testShuffle() {
    setUpKlondike();
    k.startGame(deck2, true, 1, 1);
    Assert.assertTrue(k.getCardAt(0, 0).toString().contains("♡"));
  }

  @Test
  public void testGameOverWon() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    k.moveToFoundation(0, 0);
    Assert.assertFalse(k.isGameOver());
    k.moveDrawToFoundation(1);
    Assert.assertTrue(k.isGameOver());
  }

  @Test (expected = IllegalArgumentException.class)
  public void getCardThrowsInvalidArg() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    k.getCardAt(2, 2);
  }

  @Test
  public void getCardReturnsCorrectCard() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    Assert.assertEquals("A♣", k.getCardAt(0, 0).toString());
  }

  @Test
  public void allCardsDealtFaceUp() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 1);
    Assert.assertEquals("A♣", k.getCardAt(2, 0).toString());
    Assert.assertEquals("2♣", k.getCardAt(2, 2).toString());
    Assert.assertEquals("3♣", k.getCardAt(2, 1).toString());
  }

  @Test
  public void validBuilds() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 1);
    k.discardDraw();
    k.moveDraw(1);
    Assert.assertEquals("2♡", k.getCardAt(1, 2).toString());
    k.moveDrawToFoundation(0);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 0);
    k.movePile(2, 1, 1);
    k.movePile(2, 1, 0);
    Assert.assertEquals("3♣", k.getCardAt(0, 1).toString());
  }

  @Test
  public void moveMultipleSameSuitToNonEmptyAndEmptyCascade() {
    setUpKlondike();
    k.startGame(deck3, false, 3, 1);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(0, 0);
    k.movePile(2, 2, 1);
    Assert.assertEquals("A♡", k.getCardAt(1, 2).toString());
    k.movePile(1, 3, 0);
    Assert.assertEquals("A♡", k.getCardAt(0, 2).toString());
  }

  @Test
  public void moveAnyCardToEmptyCascade() {
    setUpKlondike();
    k.startGame(deck3, false, 3, 1);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(0, 0);
    k.movePile(1, 1, 0);
    Assert.assertEquals("3♡", k.getCardAt(0, 0).toString());
  }

  @Test (expected = IllegalStateException.class)
  public void moveCardToInvalidBuild() {
    setUpKlondike();
    k.startGame(deck3, false, 3, 1);
    k.movePile(2, 1, 0);
  }

  @Test (expected = IllegalStateException.class)
  public void moveMultipleCardsDiffSuitSameColor() {
    setUpKlondike();
    k.startGame(deck4, false, 3, 1);
    k.movePile(2, 1, 0);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 1);
    k.movePile(0, 2, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void moveDrawToInvalidBuild() {
    setUpKlondike();
    k.startGame(deck4, false, 3, 1);
    k.discardDraw();
    k.moveDraw(0);
  }

  @Test
  public void moveDrawToValidBuild() {
    setUpKlondike();
    k.startGame(deck4, false, 3, 1);
    k.discardDraw();
    k.moveToFoundation(2, 0);
    k.moveToFoundation(2, 0);
    k.moveDraw(2);
    Assert.assertEquals("A♢", k.getCardAt(2, 1).toString());
  }

  @Test
  public void moveDrawToEmptyBuild() {
    setUpKlondike();
    k.startGame(deck4, false, 3, 1);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 1);
    k.moveDraw(1);
    Assert.assertEquals("2♢", k.getCardAt(1, 0).toString());
  }

  @Test
  public void textualViewDoesntContainHiddenCards() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 1);
    KlondikeTextualView kv = new KlondikeTextualView(k);
    Assert.assertFalse(kv.toString().contains("  ?"));
  }
}
