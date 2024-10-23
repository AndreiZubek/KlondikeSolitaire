package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * Tests for the controller for limited and whitehead klondike models.
 */
public class WhiteheadAndLimitedControllerTests {
  private KlondikeModel k;
  private KlondikeModel k1;
  private List<Card> deck1;
  private List<Card> deck2;
  private List<Card> deck3;
  private List<Card> deck4;

  @Before
  public void setUpKlondikeController() {
    k = new LimitedDrawKlondike(2);
    k1 = new WhiteheadKlondike();
    deck1 = changeDeck1(k.getDeck());
    deck2 = changeDeck2(k.getDeck());
    deck3 = changeDeck1(k1.getDeck());
    deck4 = changeDeck2(k1.getDeck());
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
  public void testLimitedGameIsLost() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd dd dd dd mpp 3 1 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck1, false, 3, 2);
    Assert.assertTrue(out.toString().contains("Game over"));
  }

  @Test
  public void testLimitedGameTooManyDiscards() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd dd dd dd dd dd dd q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck1, false, 3, 2);
    Assert.assertTrue(out.toString().contains("Invalid move"));
  }

  @Test
  public void testLimitedGameTooManyDiscardsMoveDraw() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd dd dd dd dd dd dd mdf 1 md 1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck1, false, 3, 2);
    Assert.assertTrue(out.toString().contains("Invalid move"));
  }

  @Test
  public void testLimitedGameIsWon() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 1 mpf 1 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 1, 1);
    Assert.assertTrue(out.toString().contains("You win"));
  }

  @Test
  public void testLimitedGameLoseAfterPermanentDiscard() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd dd mpf 1 1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 1, 1);
    Assert.assertTrue(out.toString().contains("lost"));
  }

  @Test
  public void testWhiteheadLongGameWon() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd md 2 md 2 mdf 2 1 mdf 2 1 mdf 2 1 mdf 2 1 mpp 1 1 "
            + "2 mpp 3 1 1 mpp 3 1 2 mpf 3 2 mpf 1 2 mpf 2 2 mpf 2 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k1, deck3, false, 3, 2);
    Assert.assertTrue(out.toString().contains("You win"));
  }

  @Test
  public void testWhiteheadGameWon() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 1 mpf 1 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k1, deck4, false, 1, 1);
    Assert.assertTrue(out.toString().contains("You win"));
  }

  @Test
  public void testWhiteheadInvalidMoves() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpp 3 1 2 mpp 3 2 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k1, deck3, false, 3, 2);
    Assert.assertTrue(out.toString().contains("Invalid move"));
  }
}
