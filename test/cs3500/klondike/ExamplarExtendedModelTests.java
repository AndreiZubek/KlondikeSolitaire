package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Examplar tests for the extended models.
 */
public class ExamplarExtendedModelTests {

  private List<Card> deck1;
  private List<Card> deck3;
  private List<Card> deck4;

  @Before
  public void setUpKlondike() {
    BasicKlondike k = new BasicKlondike();
    deck1 = changeDeck1(k.getDeck());
    List<Card> deck2 = changeDeck2(k.getDeck());
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

  @Test
  public void testStartLimitedDrawGame() {
    setUpKlondike();
    KlondikeModel km = new LimitedDrawKlondike(1);
    km.startGame(deck3, false, 1, 3);
    Assert.assertEquals(3, km.getNumDraw());
  }

  @Test (expected = IllegalStateException.class)
  public void testLimitedDrawDiscardInvalidMoveDrawToFoundation() {
    setUpKlondike();
    KlondikeModel km = new LimitedDrawKlondike(1);
    km.startGame(deck1, false, 3, 2);
    km.discardDraw();
    km.discardDraw();
    km.moveDrawToFoundation(0);
    km.moveDrawToFoundation(0);
  }

  @Test
  public void testWhiteheadBuildSameSuitPileMovePileToEmptyCascade() {
    setUpKlondike();
    KlondikeModel km = new WhiteheadKlondike();
    km.startGame(deck3, false, 3, 1);
    km.moveToFoundation(1, 0);
    km.moveToFoundation(2, 1);
    km.moveToFoundation(0, 0);
    km.moveDraw(0);
    km.movePile(1, 1, 0);
    km.movePile(2, 1, 0);
    km.movePile(0, 3, 1);
    Assert.assertEquals(2, km.getCardAt(1, 2).getNum());
  }

  @Test
  public void testWhiteheadMoveAnyCardToEmptyCascade() {
    setUpKlondike();
    KlondikeModel km = new WhiteheadKlondike();
    km.startGame(deck3, false, 3, 1);
    km.moveToFoundation(1, 0);
    km.moveToFoundation(0, 0);
    km.movePile(1, 1, 0);
    Assert.assertEquals(3, km.getCardAt(0, 0).getNum());
  }

  @Test (expected = IllegalStateException.class)
  public void testWhiteheadInvalidBuildMove() {
    setUpKlondike();
    KlondikeModel km = new WhiteheadKlondike();
    km.startGame(deck3, false, 3, 1);
    km.movePile(2, 1, 0);
  }

  @Test
  public void testWhiteheadMoveDrawToFoundationMoveCascadeToFoundation() {
    setUpKlondike();
    KlondikeModel km = new WhiteheadKlondike();
    km.startGame(deck1, false, 3, 1);
    km.moveDrawToFoundation(0);
    km.moveDrawToFoundation(0);
    km.moveToFoundation(1, 0);
    km.moveToFoundation(1, 0);
    Assert.assertEquals(4, km.getCardAt(0).getNum());
  }

  @Test (expected = IllegalStateException.class)
  public void movingPileWithCardsOfDiffSuitThrows() {
    setUpKlondike();
    KlondikeModel km = new WhiteheadKlondike();
    km.startGame(deck4, false, 3, 1);
    km.moveToFoundation(2, 0);
    km.moveToFoundation(2, 0);
    km.moveToFoundation(1, 1);
    km.moveToFoundation(1, 2);
    km.discardDraw();
    km.moveDraw(2);
    km.movePile(2, 2, 1);
  }
}
