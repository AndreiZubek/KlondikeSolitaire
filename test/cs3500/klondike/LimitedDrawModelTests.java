package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * Tests for the limited draw klondike model class.
 */
public class LimitedDrawModelTests {
  private KlondikeModel k;
  private List<Card> deck1;
  private List<Card> deck2;
  private KlondikeModel k1;
  private List<Card> deck5;

  @Before
  public void setUpKlondike() {
    k = new LimitedDrawKlondike(1);
    deck1 = changeDeck1(k.getDeck());
    deck2 = changeDeck2(k.getDeck());
    List<Card> deck3 = changeDeck3(k.getDeck());
    List<Card> deck4 = changeDeck4(k.getDeck());

    k1 = new LimitedDrawKlondike(2);
    deck5 = changeDeck1(k1.getDeck());
    List<Card> deck6 = changeDeck2(k1.getDeck());
    List<Card> deck7 = changeDeck3(k1.getDeck());
    List<Card> deck8 = changeDeck4(k1.getDeck());
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
  public void givenDrawsLessThanZero() {
    KlondikeModel km = new LimitedDrawKlondike(-2);
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

  @Test (expected = IllegalStateException.class)
  public void testTooManyDiscards() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    k.discardDraw();
    k.discardDraw();
    k.discardDraw();
  }

  @Test (expected = IllegalStateException.class)
  public void testTooManyDiscardsMoveDraw() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    k.discardDraw();
    k.moveDraw(0);
  }

  @Test (expected = IllegalStateException.class)
  public void testTooManyDiscardsMoveDrawToFoundation() {
    setUpKlondike();
    k.startGame(deck2, false, 1, 1);
    k.discardDraw();
    k.moveDrawToFoundation(0);
  }

  @Test
  public void testDiscardRemovesPermanently() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 2);
    k.discardDraw();
    Assert.assertEquals(1, k.getDrawCards().size());
    k.discardDraw();
    Assert.assertEquals(0, k.getDrawCards().size());
  }

  @Test
  public void testUseCardAfterPermanentDiscard() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 2);
    k.discardDraw();
    Assert.assertEquals(1, k.getDrawCards().size());
    k.movePile(2, 1, 1);
    k.moveDraw(2);
    Assert.assertEquals("2♡", k.getCardAt(2, 2).toString());
  }

  @Test
  public void testUseCardAfterDiscardedOnce() {
    setUpKlondike();
    k1.startGame(deck5, false, 3, 2);
    k1.discardDraw();
    k1.discardDraw();
    Assert.assertEquals(2, k1.getDrawCards().size());
    k1.moveDraw(2);
    Assert.assertEquals(1, k1.getDrawCards().size());
    Assert.assertEquals("A♡", k1.getCardAt(2, 3).toString());
  }

  @Test
  public void testMoveCardToFoundationAfterDiscardedOnce() {
    setUpKlondike();
    k1.startGame(deck5, false, 3, 2);
    k1.discardDraw();
    k1.discardDraw();
    Assert.assertEquals(2, k1.getDrawCards().size());
    k1.moveDrawToFoundation(0);
    Assert.assertEquals(1, k1.getDrawCards().size());
    Assert.assertEquals(1, k1.getScore());
  }

  @Test
  public void testGameNotOverAfterDrawPermanentlyDiscarded() {
    setUpKlondike();
    k.startGame(deck1, false, 3, 2);
    k.discardDraw();
    k.discardDraw();
    Assert.assertFalse(k.isGameOver());
    k.movePile(2, 1, 1);
    Assert.assertEquals("2♣", k.getCardAt(1, 2).toString());
  }
}
