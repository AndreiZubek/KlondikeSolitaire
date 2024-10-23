package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * Tests for the klondike creator class.
 */
public class CreatorKlondikeTests {
  private List<Card> deck1;
  private List<Card> deck2;

  @Before
  public void setUpKlondike() {
    KlondikeModel k = new WhiteheadKlondike();
    deck1 = changeDeck1(k.getDeck());
    deck2 = changeDeck2(k.getDeck());
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
  public void createBasicKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    k.startGame(k.getDeck(), false, 7, 3);
    Assert.assertEquals(3, k.getNumDraw());
    Assert.assertEquals(7, k.getNumPiles());
  }

  @Test
  public void createWhiteheadKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    k.startGame(k.getDeck(), false, 6, 2);
    Assert.assertEquals(2, k.getNumDraw());
    Assert.assertEquals(6, k.getNumPiles());
  }

  @Test
  public void createLimitedKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    k.startGame(k.getDeck(), false, 5, 1);
    Assert.assertEquals(1, k.getNumDraw());
    Assert.assertEquals(5, k.getNumPiles());
  }

  @Test
  public void playCreateBasicKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    k.startGame(deck1, false, 3, 1);
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    Assert.assertEquals("2♣", k.getCardAt(0, 2).toString());
    k.discardDraw();
    k.discardDraw();
    k.moveDrawToFoundation(0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 1);
    k.moveDraw(1);
    Assert.assertEquals(2, k.getScore());
    Assert.assertEquals("2♡", k.getCardAt(1, 2).toString());
  }

  @Test
  public void playCreateWhiteheadKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    k.startGame(deck1, false, 3, 1);
    Assert.assertEquals("4♡", k.getCardAt(1, 0).toString());
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
    k.moveToFoundation(1, 0);
    k.moveToFoundation(1, 0);
    k.movePile(0, 1, 1);
    k.movePile(2, 1, 0);
    k.movePile(2, 1, 1);
    k.movePile(2, 1, 0);
    k.movePile(0, 2, 1);
    k.movePile(1, 4, 2);
    Assert.assertEquals("A♣", k.getCardAt(2, 3).toString());
  }

  @Test
  public void playCreateLimitedKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    k.startGame(deck1, false, 3, 1);
    k.discardDraw();
    k.discardDraw();
    k.discardDraw();
    Assert.assertEquals(1, k.getDrawCards().size());
    k.movePile(2, 1, 1);
    k.moveDraw(2);
    Assert.assertEquals("2♡", k.getCardAt(2, 2).toString());
  }

  @Test
  public void gameFinishCreateBasicKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    k.startGame(deck2, false, 1, 1);
    k.moveDrawToFoundation(0);
    k.moveToFoundation(0, 1);
    Assert.assertTrue(k.isGameOver());
    Assert.assertEquals(2, k.getScore());
  }

  @Test
  public void gameFinishCreateWhiteheadKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    k.startGame(deck2, false, 1, 1);
    k.moveDrawToFoundation(0);
    k.moveToFoundation(0, 1);
    Assert.assertTrue(k.isGameOver());
    Assert.assertEquals(2, k.getScore());
  }

  @Test
  public void gameFinishCreateLimitedKlondike() {
    KlondikeModel k = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    k.startGame(deck2, false, 1, 1);
    k.moveDrawToFoundation(0);
    k.moveToFoundation(0, 1);
    Assert.assertTrue(k.isGameOver());
    Assert.assertEquals(2, k.getScore());
  }
}
