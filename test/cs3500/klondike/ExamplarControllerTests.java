package cs3500.klondike;

// import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the klondike controller.
 */
public class ExamplarControllerTests {

  private BasicKlondike k;
  private List<Card> deck2;
  private List<Card> deck4;

  @Before
  public void setUpKlondikeController() {
    k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    deck2 = changeDeck1(deck1);
    List<Card> deck3 = k.getDeck();
    deck4 = changeDeck2(deck3);
    List<Card> deck5 = k.getDeck();
    List<Card> deck6 = changeDeck4(deck5);
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
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "Q♡"));
    ret.add(retCard(old, "J♡"));
    ret.add(retCard(old, "10♡"));
    ret.add(retCard(old, "K♡"));
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

  private List<Card> changeDeck4(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "A♡"));

    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "4♣"));

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
  public void testGameEndFirstMove() {
    setUpKlondikeController();
    StringReader in = new StringReader("q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Game quit!"));
  }

  @Test
  public void testMoveDrawWrongCascadeDiffSuitDiffGreaterThan1() {
    setUpKlondikeController();
    StringReader in = new StringReader("md 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testTwoAcesOneDrawOneCascadeToFoundationCorrectScore() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpp 3 1 2 mpp 2 2 1 mpp 3 1 2 mdf 1 mpf 3 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Score: 2"));
  }

  @Test
  public void testMoveDrawWrongCascadeSameSuitDiffOf1() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf -1 mpf -1 -1 md -1 mpp -1 -1 -1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveWrongCardToEmptyCascade() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpp 3 1 2 mpp 2 2 1 mpp 3 1 2 mdf 3 1 mpp 2 1 3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveWrongCardToEmptyCascade2() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf 1 mpf mdf dd 1 md 1 1 mdf 1 1 md mpp mpp 1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testNullIn() {
    setUpKlondikeController();
    StringReader in = new StringReader("q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck4, false, 1, 1);
    String s = out.toString();
    Assert.assertTrue(s.contains("Draw: A♡" + System.lineSeparator() + "Foundation: <none>, <none>"
            + System.lineSeparator() + " A♣" + System.lineSeparator() + "Score: 0"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullOut() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf 1 mpf mdf dd 1 md 1 1 mdf 1 1 md mpp mpp 1 q");
    KlondikeController kc = new KlondikeTextualController(in, null);
  }

  @Test
  public void testCompletedGame() {
    setUpKlondikeController();
    StringReader in = new StringReader("q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    String s = out.toString();
    Assert.assertTrue(s.contains("Game quit!" + System.lineSeparator()
            + "State of game when quit:"));
  }
}
