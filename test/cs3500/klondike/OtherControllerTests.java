package cs3500.klondike;

// import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the klondike controller that
 * are not in my exemplar tests.
 */
public class OtherControllerTests {
  private BasicKlondike k;
  private List<Card> deck2;
  private List<Card> deck3;
  private List<Card> deck4;
  private List<Card> deck5;

  @Before
  public void setUpKlondikeController() {
    k = new BasicKlondike();
    List<Card> deck1 = k.getDeck();
    deck2 = changeDeck1(deck1);
    deck3 = changeDeck2(deck1);
    deck4 = changeDeck3(deck1);
    deck5 = changeDeck4(deck1);
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
    ret.add(retCard(old, "4♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♡"));
    return ret;
  }

  private List<Card> changeDeck3(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♣"));

    ret.add(retCard(old, "A♡"));
    return ret;
  }

  private List<Card> changeDeck4(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "3♡"));

    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "3♣"));
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
  public void unwinnableGame() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 mdf 1 mdf 1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck5, false, 2, 1);
    Assert.assertTrue(out.toString().contains("Game over"));
  }

  @Test
  public void testFullGameWin() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 mpf 1 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck4, false, 1, 1);
    Assert.assertTrue(out.toString().contains("You win!"));
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidDeckPlayGame() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck3, false, 3, 1);
  }

  @Test
  public void testInvalidMdfInvalidArg() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveDrawToValidFoundation() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Foundation: A♡"));
    Assert.assertTrue(out.toString().contains("Score: 1"));
  }

  @Test
  public void testMoveDrawToInvalidFoundation() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testModelIsNull() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(null, deck2, false, 3, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testRunOutOfInput() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 mdd");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k , deck2, false, 3, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testGameCantStartNumpiles() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 mdd q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k , deck2, false, 30, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testGameCantStartNegativeNumpiles() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 mdd q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k , deck2, false, -3, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testGameCantStartNegativeNumdraw() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 3 mdd q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k , deck2, false, 3, -1);
  }

  @Test
  public void testMoveDrawWrongCascadeSameSuitDiffOf1() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd md 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testCompletedGame() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 mdf 1 mpp 3 1 2 mpp 2 2 1 mpf 3 2 "
            + "mpf 1 2 mpf 2 2 mpf 1 1 mpf 2 1 mpf 1 2 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("You win!"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullIn() {
    setUpKlondikeController();
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(null, out);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullOut() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf 1 mpf mdf dd 1 md 1 1 mdf 1 1 md mpp mpp 1 q");
    KlondikeController kc = new KlondikeTextualController(in, null);
  }

  @Test
  public void testMultipleInvalidArgsNoValidMoveInput() {
    setUpKlondikeController();
    StringReader in = new StringReader("ra ra raa 3 4 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid"));
  }

  @Test
  public void testQuitWhileWaitingForInput() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf r r q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Game quit!"));
  }

  @Test
  public void testWaitingForInputBadInputDoesNotGiveInvalidMove() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf r r as -3 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertFalse(out.toString().contains("Invalid"));
  }

  @Test
  public void testInvalidArgsNoValidMoveInput() {
    setUpKlondikeController();
    StringReader in = new StringReader("ra q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid"));
  }

  @Test
  public void testInvalidArgsForMdf() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 9 q");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test (expected = IllegalStateException.class)
  public void noQuitCausesError() {
    setUpKlondikeController();
    StringReader in = new StringReader("dafj afjd");
    StringWriter out = new StringWriter();
    KlondikeController kc = new KlondikeTextualController(in, out);
    kc.playGame(k, deck2, false, 3, 1);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void mockTestMppInputsReadCorrect() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpp 2 4 4 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    StringBuilder log = new StringBuilder();
    KlondikeModel calc = new KlondikeModelMock(log);
    calc.startGame(deck2, false, 3, 1);

    controller5.playGame(calc, deck2, false, 3, 1);
    Assert.assertEquals("srcPile = 1, numCards = 4, destPile = 3\n", log.toString());
  }

  @Test
  public void mockTestMpfInputsReadCorrect() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf 1 1 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    StringBuilder log = new StringBuilder();
    KlondikeModel calc = new KlondikeModelMock(log);
    calc.startGame(deck2, false, 3, 1);

    controller5.playGame(calc, deck2, false, 3, 1);
    Assert.assertEquals("srcPile = 0, foundationPile = 0\n", log.toString());
  }

  @Test
  public void mockTestMdfInputsReadCorrect() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    StringBuilder log = new StringBuilder();
    KlondikeModel calc = new KlondikeModelMock(log);
    calc.startGame(deck2, false, 3, 1);

    controller5.playGame(calc, deck2, false, 3, 1);
    Assert.assertEquals("foundationPile = 0\n", log.toString());
  }

  @Test
  public void mockTestMdInputsReadCorrect() {
    setUpKlondikeController();
    StringReader in = new StringReader("md 1 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    StringBuilder log = new StringBuilder();
    KlondikeModel calc = new KlondikeModelMock(log);
    calc.startGame(deck2, false, 3, 1);

    controller5.playGame(calc, deck2, false, 3, 1);
    Assert.assertEquals("destPile = 0\n", log.toString());
  }

  @Test
  public void mockTestDdInputsReadCorrect() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    StringBuilder log = new StringBuilder();
    KlondikeModel calc = new KlondikeModelMock(log);
    calc.startGame(deck2, false, 3, 1);

    controller5.playGame(calc, deck2, false, 3, 1);
    Assert.assertEquals("discarded", log.toString());
  }

  @Test
  public void mockMppThrowsIO() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpp 1 1 2 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    MockAppendable m = new MockAppendable(true);
    KlondikeModel km = new MockModelThrows(m);
    km.startGame(deck2, false, 3, 1);

    controller5.playGame(km, deck2, false, 3, 1);
    Assert.assertEquals(1, km.getNumFoundations());
  }

  @Test
  public void mockMdThrowsIO() {
    setUpKlondikeController();
    StringReader in = new StringReader("md 1 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    MockAppendable m = new MockAppendable(true);
    KlondikeModel km = new MockModelThrows(m);
    km.startGame(deck2, false, 3, 1);

    controller5.playGame(km, deck2, false, 3, 1);
    Assert.assertEquals(2, km.getNumFoundations());
  }

  @Test
  public void mockMpfThrowsIO() {
    setUpKlondikeController();
    StringReader in = new StringReader("mpf 1 2 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    MockAppendable m = new MockAppendable(true);
    KlondikeModel km = new MockModelThrows(m);
    km.startGame(deck2, false, 3, 1);

    controller5.playGame(km, deck2, false, 3, 1);
    Assert.assertEquals(3, km.getNumFoundations());
  }

  @Test
  public void mockMdfThrowsIO() {
    setUpKlondikeController();
    StringReader in = new StringReader("mdf 1 q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    MockAppendable m = new MockAppendable(true);
    KlondikeModel km = new MockModelThrows(m);
    km.startGame(deck2, false, 3, 1);

    controller5.playGame(km, deck2, false, 3, 1);
    Assert.assertEquals(4, km.getNumFoundations());
  }

  @Test
  public void mockDdThrowsIO() {
    setUpKlondikeController();
    StringReader in = new StringReader("dd q");
    StringBuilder dontCareOutput = new StringBuilder();
    KlondikeController controller5 = new KlondikeTextualController(in, dontCareOutput);

    MockAppendable m = new MockAppendable(true);
    KlondikeModel km = new MockModelThrows(m);
    km.startGame(deck2, false, 3, 1);

    controller5.playGame(km, deck2, false, 3, 1);
    Assert.assertEquals(5, km.getNumFoundations());
  }
}
