package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardRep;
import cs3500.klondike.view.KlondikeTextualView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the cardrep, klondliketextual view, and basicklondike classes.
 */
public class OtherKlondikeTests {

  private BasicKlondike k;
  private BasicKlondike k1;
  private BasicKlondike k3;
  private BasicKlondike k4;
  private List<Card> deck1;


  @Before
  public void setUpKlondike() {
    k = new BasicKlondike();
    k1 = new BasicKlondike();
    BasicKlondike k2 = new BasicKlondike();
    k3 = new BasicKlondike();
    k4 = new BasicKlondike();

    List<Card> deck5 = k2.getDeck();
    deck1 = k.getDeck();

    k.startGame(changeDeck1(deck1), false, 3, 1);
    k1.startGame(changeDeck2(k1.getDeck()), false, 3, 1);
    k2.startGame(changeDeck3(deck5), false, 3, 1);
    k4.startGame(changeDeck7(k4.getDeck()), false, 1, 1);
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

  private List<Card> changeDeck4(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "4♣"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "7♡"));
    return ret;
  }

  private List<Card> changeDeck2(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "4♣"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "A♣"));
    ret.add(retCard(old, "2♡"));
    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "3♡"));
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

  private List<Card> changeDeck7(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "K♡"));
    ret.add(retCard(old, "10♡"));
    ret.add(retCard(old, "Q♡"));
    ret.add(retCard(old, "J♡"));
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

  private List<Card> changeDeck5(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "6♡"));
    ret.add(retCard(old, "5♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "2♡"));

    return ret;
  }

  private List<Card> changeDeck6(List<Card> old) {
    List<Card> ret = new ArrayList<>();
    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "6♡"));
    ret.add(retCard(old, "5♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "4♡"));
    ret.add(retCard(old, "2♡"));

    return ret;
  }

  private List<Card> changeDeck(List<Card> old) {
    List<Card> ret = new ArrayList<>();

    ret.add(retCard(old, "2♡"));

    ret.add(retCard(old, "3♡"));

    ret.add(retCard(old, "4♡"));

    ret.add(retCard(old, "5♡"));

    ret.add(retCard(old, "2♢"));

    ret.add(retCard(old, "3♢"));


    ret.add(retCard(old, "4♢"));

    ret.add(retCard(old, "3♣"));
    ret.add(retCard(old, "2♣"));
    ret.add(retCard(old, "4♣"));

    ret.add(retCard(old, "A♡"));
    ret.add(retCard(old, "3♡"));
    ret.add(retCard(old, "4♡"));

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
  public void testValidMoveToFoundation() {
    setUpKlondike();
    BasicKlondike kl = new BasicKlondike();
    List<Card> c = kl.getDeck();
    List<Card> d = changeDeck6(c);
    kl.startGame(d, false, 3, 1);
    kl.moveToFoundation(0, 0);
    kl.moveToFoundation(2, 0);
    kl.moveToFoundation(1, 0);
    kl.moveToFoundation(2, 0);
    kl.moveToFoundation(2, 0);
    kl.moveToFoundation(1, 0);
    k.moveDraw(2);
    k.moveToFoundation(2, 0);
    Card c1 = retCard(deck1, "6♡");
    Assert.assertEquals(c1, kl.getCardAt(0));
    System.out.println(kl.getCardAt(0).toString());
  }

  @Test
  public void testTextualView() {
    // tests the toString method in the klondiketextualview class
    setUpKlondike();
    KlondikeTextualView view = new KlondikeTextualView(k);
    String s = view.toString();
    String correct = "Draw: A♡\nFoundation: <none>, <none>\n 4♣  ?  ?\n    3♡  ?\n       2♣";
    Assert.assertEquals(correct, s);
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    String correct1 = "Draw: A♡\nFoundation: <none>, <none>\n 4♣ 4♡ A♣\n 3♡ 3♣\n 2♣";
    KlondikeTextualView view1 = new KlondikeTextualView(k);
    Assert.assertEquals(correct1, view1.toString());
    k.moveToFoundation(2, 0);
    k.moveToFoundation(0, 0);
    k.moveDrawToFoundation(1);
    String correct2 = "Draw: 2♡\nFoundation: 2♣, A♡\n 4♣ 4♡  X\n 3♡ 3♣";
    KlondikeTextualView view2 = new KlondikeTextualView(k);
    Assert.assertEquals(correct2, view2.toString());
  }

  @Test
  public void testCards() {
    // tests all the methods in my cardrep class
    setUpKlondike();
    Assert.assertEquals(4, k.getCardAt(0, 0).getNum());
    CardRep c = new CardRep(1, CardRep.Suit.HEARTS);
    CardRep k = new CardRep(1, CardRep.Suit.HEARTS);
    Assert.assertEquals(1, c.getNum());
    String s = "A♡";
    Assert.assertEquals(s, c.toString());
    Assert.assertEquals(31, c.hashCode());
  }

  @Test
  public void testDeckShuffle() {
    BasicKlondike kl = new BasicKlondike();
    List<Card> c = kl.getDeck();
    List<Card> d = changeDeck1(c);
    kl.startGame(d, true, 3, 1);
    Card g = retCard(deck1, "4♣");
    Assert.assertNotEquals(g, kl.getCardAt(0, 0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDeckNull() {
    BasicKlondike kl = new BasicKlondike();
    kl.startGame(null, false, 3, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDeckCantFillCascades() {
    BasicKlondike kl = new BasicKlondike();
    List<Card> c = kl.getDeck();
    List<Card> d = changeDeck1(c);
    kl.startGame(d, false, 10, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDeckRunsNotEqual() {
    BasicKlondike kl = new BasicKlondike();
    List<Card> c = kl.getDeck();
    List<Card> d = changeDeck4(c);
    kl.startGame(d, false, 3, 1);
  }

  @Test
  public void testValidMovePile() {
    setUpKlondike();
    k.movePile(2, 1, 1);
    Card c = retCard(deck1, "2♣");
    Assert.assertEquals(c, k.getCardAt(1, 2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMovePileTooManyCards() {
    setUpKlondike();
    k.movePile(2, 10, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMovePileInvalidDest() {
    setUpKlondike();
    k.movePile(2, 1, 9);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMovePileInvalidSrc() {
    setUpKlondike();
    k.movePile(5, 1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMovePileSamePiles() {
    setUpKlondike();
    k.movePile(2, 1, 2);
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidMovePile() {
    setUpKlondike();
    k.movePile(2, 1, 0);
  }

  @Test
  public void testValidMoveDrawToEmptyCascade() {
    setUpKlondike();
    k4.moveToFoundation(0, 0);
    k4.moveDraw(0);
    Assert.assertTrue(k4.getCardAt(0, 0).toString().contains("K"));
  }

  @Test
  public void testValidMoveDrawToNonEmptyCascade() {
    setUpKlondike();
    k.movePile(2, 1, 1);
    k.discardDraw();
    k.moveDraw(2);
    Card c = retCard(deck1, "2♡");
    Assert.assertEquals(c, k.getCardAt(2, 2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveCascadeDrawInvalidDestination() {
    setUpKlondike();
    k.moveDraw(10);
  }

  @Test (expected = IllegalStateException.class)
  public void testMoveCascadeDrawInvalidMove() {
    setUpKlondike();
    k.moveDraw(0);
  }

  @Test (expected = IllegalStateException.class)
  public void testMoveCascadeDrawNoDrawLeft() {
    setUpKlondike();
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
    k.moveDraw(0);
  }

  @Test
  public void testValidToFoundationMove() {
    // tests to empty foundation and non empty
    setUpKlondike();
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    Card c = retCard(deck1, "A♣");
    Assert.assertEquals(c, k.getCardAt(0));
    k.moveToFoundation(0, 0);
    Card c1 = retCard(deck1, "2♣");
    Assert.assertEquals(c1, k.getCardAt(0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInValidToFoundationPileDestNumberInvalid() {
    setUpKlondike();
    k.moveToFoundation(10, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInValidToFoundationPileSrcNumberInvalid() {
    setUpKlondike();
    k.moveToFoundation(1, 10);
  }

  @Test (expected = IllegalStateException.class)
  public void testInValidToFoundationMoveNotAllowed() {
    setUpKlondike();
    k.moveToFoundation(1, 0);
  }

  @Test (expected = IllegalStateException.class)
  public void testInValidToFoundationMoveSrcIsEmpty() {
    setUpKlondike();
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    k.moveToFoundation(2, 0);
  }

  @Test
  public void testValidDrawToFoundationMove() {
    // tests valid move to empty foundation and non empty
    setUpKlondike();
    k.moveDrawToFoundation(0);
    Card c = retCard(deck1, "A♡");
    Assert.assertEquals(c, k.getCardAt(0));
    k.moveDrawToFoundation(0);
    Card c1 = retCard(deck1, "2♡");
    Assert.assertEquals(c1, k.getCardAt(0));
  }

  @Test (expected = IllegalStateException.class)
  public void testMoveToFoundationInvalidMoveNotOneAbove() {
    setUpKlondike();
    k1.moveDrawToFoundation(0);
    k1.moveDrawToFoundation(0);
  }

  @Test (expected = IllegalStateException.class)
  public void testMoveEmptyDrawToFoundationInvalidMove() {
    setUpKlondike();
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidMoveDrawToNonEmptyFoundationInvalidMoveWrongSuit() {
    setUpKlondike();
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    k.moveDrawToFoundation(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveDrawToFoundationInvalidFoundation() {
    setUpKlondike();
    k.moveDrawToFoundation(4);
  }

  @Test (expected = IllegalStateException.class)
  public void testDiscardDrawNotValidMove() {
    setUpKlondike();
    k.moveDrawToFoundation(0);
    k.moveDrawToFoundation(0);
    k.discardDraw();
  }

  @Test
  public void testDiscardDrawValidMove() {
    setUpKlondike();
    k.discardDraw();
    List<Card> lo = new ArrayList<>();
    Card c = retCard(deck1, "2♡");
    lo.add(c);
    Assert.assertEquals(lo, k.getDrawCards());
  }

  @Test
  public void testGameOver() {
    // tests if the game isnt over, if it is over when all the cards are in foundation
    // piles, and if it is over when there are no possible moves
    setUpKlondike();
    Assert.assertFalse(k.isGameOver());
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    k.moveToFoundation(0, 0);
    k.moveToFoundation(1, 0);
    k.moveDrawToFoundation(1);
    k.moveDrawToFoundation(1);
    k.moveToFoundation(0, 1);
    k.moveToFoundation(1, 1);
    k.moveToFoundation(0, 0);
    Assert.assertTrue(k.isGameOver());
    BasicKlondike kl = new BasicKlondike();
    List<Card> c = kl.getDeck();
    List<Card> d = changeDeck5(c);
    kl.startGame(d, false, 3, 1);
    Assert.assertTrue(kl.isGameOver());
  }

  @Test
  public void testGetNumRows() {
    // height is the same even if a cascade pile is empty
    setUpKlondike();
    Assert.assertEquals(3, k.getNumRows());
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    Assert.assertEquals(3, k.getNumRows());
  }

  @Test
  public void testGetNumPiles() {
    // width is the same even if a cascade pile is empty
    setUpKlondike();
    Assert.assertEquals(3, k.getNumPiles());
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    Assert.assertEquals(3, k.getNumPiles());
  }

  @Test
  public void testGetNumDraw() {
    // tests if it returns the same thing, even if the draw is empty
    setUpKlondike();
    Assert.assertEquals(1, k.getNumDraw());
    k.moveDrawToFoundation(1);
    k.moveDrawToFoundation(1);
    Assert.assertEquals(1, k.getNumDraw());
  }

  @Test
  public void testGetScore() {
    setUpKlondike();
    k.movePile(2, 1, 1);
    k.movePile(1, 2, 0);
    k.movePile(2, 1, 1);
    k.moveToFoundation(2, 0);
    Assert.assertEquals(1, k.getScore());
    k.moveToFoundation(0, 0);
    Assert.assertEquals(2, k.getScore());
    k.moveDrawToFoundation(1);
    Assert.assertEquals(3, k.getScore());
  }

  @Test
  public void testEmptyScore() {
    setUpKlondike();
    Assert.assertEquals(0, k.getScore());
  }

  @Test
  public void testGetPileHeight() {
    setUpKlondike();
    Assert.assertEquals(1, k.getPileHeight(0));
    Assert.assertEquals(3, k.getPileHeight(2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidCoordinatesGetPileHeight() {
    setUpKlondike();
    k.getPileHeight(9);
  }

  @Test
  public void testIsCardVisible() {
    setUpKlondike();
    Assert.assertTrue(k.isCardVisible(0, 0));
    Assert.assertFalse(k.isCardVisible(1, 0));
  }

  @Test
  public void testCascadeGetCardAt() {
    setUpKlondike();
    Card c = retCard(deck1, "3♡");
    Assert.assertEquals(c, k.getCardAt(1, 1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidCoordinatesCascadeGetCardAt() {
    setUpKlondike();
    k.getCardAt(3, 9);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidCoordinatesIsVisible() {
    setUpKlondike();
    k.isCardVisible(3, 9);
  }

  @Test
  public void testNullFoundationGetCardAt() {
    setUpKlondike();
    Assert.assertEquals(null, k.getCardAt(1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidCoordinatesFoundationGetCardAt() {
    setUpKlondike();
    k.getCardAt(3);
  }

  @Test
  public void testFoundationGetCardAt() {
    setUpKlondike();
    k.moveDrawToFoundation(0);
    Card c = retCard(deck1, "A♡");
    Assert.assertEquals(c, k.getCardAt(0));
  }

  @Test
  public void testGetDrawCards() {
    setUpKlondike();
    List<Card> draw = k.getDrawCards();
    Card c = retCard(deck1, "A♡");
    Assert.assertEquals(c, draw.get(0));
  }

  @Test
  public void testGetNumFoundations() {
    setUpKlondike();
    Assert.assertEquals(2, k.getNumFoundations());
  }

  @Test (expected = IllegalStateException.class)
  public void getNumFoundationsGameNotStarted() {
    setUpKlondike();
    k3.getNumFoundations();
  }

  @Test (expected = IllegalStateException.class)
  public void getDrawCardsGameNotStarted() {
    setUpKlondike();
    k3.getDrawCards();
  }

  @Test (expected = IllegalStateException.class)
  public void getCardAtFoundationGameNotStarted() {
    setUpKlondike();
    k3.getCardAt(1);
  }

  @Test (expected = IllegalStateException.class)
  public void getCardAtCascadeGameNotStarted() {
    setUpKlondike();
    k3.getCardAt(1, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void isCardVisibleGameNotStarted() {
    setUpKlondike();
    k3.isCardVisible(1, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void getPileHeightGameNotStarted() {
    setUpKlondike();
    k3.getPileHeight(1);
  }

  @Test (expected = IllegalStateException.class)
  public void getScoreGameNotStarted() {
    setUpKlondike();
    k3.getScore();
  }

  @Test (expected = IllegalStateException.class)
  public void isGameOverGameNotStarted() {
    setUpKlondike();
    k3.isGameOver();
  }

  @Test (expected = IllegalStateException.class)
  public void getNumDrawGameNotStarted() {
    setUpKlondike();
    k3.getNumDraw();
  }

  @Test (expected = IllegalStateException.class)
  public void getNumPilesGameNotStarted() {
    setUpKlondike();
    k3.getNumPiles();
  }

  @Test (expected = IllegalStateException.class)
  public void getNumRowsGameNotStarted() {
    setUpKlondike();
    k3.getNumRows();
  }

  @Test (expected = IllegalStateException.class)
  public void discardDrawGameNotStarted() {
    setUpKlondike();
    k3.discardDraw();
  }

  @Test (expected = IllegalStateException.class)
  public void moveDrawToFoundationGameNotStarted() {
    setUpKlondike();
    k3.moveDrawToFoundation(1);
  }

  @Test (expected = IllegalStateException.class)
  public void moveToFoundationGameNotStarted() {
    setUpKlondike();
    k3.moveToFoundation(1, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void moveDrawGameNotStarted() {
    setUpKlondike();
    k3.moveDraw(1);
  }

  @Test (expected = IllegalStateException.class)
  public void movePileGameNotStarted() {
    setUpKlondike();
    k3.movePile(1, 1, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void startGameNotStarted() {
    setUpKlondike();
    k.startGame(deck1, false, 1, 1);
  }
}
