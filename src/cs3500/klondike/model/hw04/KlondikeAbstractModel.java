package cs3500.klondike.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardRep;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * Represents the base structure and functionality of a klondike solitaire game,
 * which can be extended in order to make different klondike games with different rules.
 * It is public so that the basic klondike class in the hw02 package can extend it.
 */
public abstract class KlondikeAbstractModel implements KlondikeModel {

  // fields are protected so that the other models in a different package can use them

  // all the cards that are not visible and in cascades
  protected List<Card> invisibleCascadeCards;

  // the visible draw cards, first draw card is the 0th element
  protected List<Card> visibleDrawCards;

  // the invisible draw cards, first card is the 0th element
  protected List<Card> restCards;

  // represents the cascade piles, index 0 is the 0th cascade pile,
  // the 0th index of the 0th pile is the cascade card at 0, 0.
  protected List<List<Card>> cascadePiles;

  // represents the foundation piles, index 0 is the 0th foundation pile,
  // the 0th index of the 0th pile is the bottom most foundation card at the 0th pile.
  // The bottom card will always be an ace.
  protected List<List<Card>> foundationPiles;

  // true if the game has started
  protected boolean hasStarted = false;

  // the number of draw cards
  private int numDraw;

  // the whole deck that is being played in the game
  private List<Card> deck;

  /**
   * Constructor for a basic klondike game with a normal deck.
   */
  public KlondikeAbstractModel() {
    this.deck = new ArrayList<>();
    for (CardRep.Suit suit : CardRep.Suit.values()) {
      for (int number = 1; number <= 13; number++) {
        this.deck.add(new CardRep(number, suit));
      }
    }
  }

  @Override
  public List<Card> getDeck() {
    return this.deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    this.numDraw = numDraw;

    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }

    List<Card> preserved = new ArrayList<>(deck);

    if (!validDeck(deck)) {
      throw new IllegalArgumentException("Deck is invalid");
    }

    int m = numPiles * (numPiles + 1) / 2;
    if (numDraw < 1 || numPiles < 1 || m > deck.size()) { // +numdraw  < 0   both were < 0 before
      throw new IllegalArgumentException("Numpiles or numdraw is invalid");
    }

    if (hasStarted) {
      throw new IllegalStateException("Game has already started.");
    }
    this.hasStarted = true;

    this.deck = preserved;

    if (shuffle) {
      Collections.shuffle(this.deck);
    }

    this.invisibleCascadeCards = new ArrayList<>();
    this.cascadePiles = new ArrayList<>();
    this.visibleDrawCards = new ArrayList<>();
    this.restCards = new ArrayList<>();
    this.foundationPiles = new ArrayList<>();

    for (int i = 0; i < numPiles; i++) {
      List<Card> cascadePile = new ArrayList<>();
      this.cascadePiles.add(cascadePile);
    }

    int n = 0;

    for (int i = 1; i <= numPiles; i++) {
      n = i;
      for (int a = 0; a < i; a++) {
        if (a != i - 1) {
          Card c = this.deck.get(n - a - 1);
          this.invisibleCascadeCards.add(c);
        }
        Card c = this.deck.get(n - a - 1);
        this.cascadePiles.get(i - 1).add(c);
        n = n + numPiles - a;
      }
    }
    for (int i = m; i < m + numDraw; i++) {
      if (i == this.deck.size()) {
        break;
      }
      Card c = this.deck.get(i);
      this.visibleDrawCards.add(c);
    }
    m = m + numDraw;
    for (int i = m; i < this.deck.size(); i++) {
      Card c = this.deck.get(i);
      this.restCards.add(c);
    }
    for (Card c : this.deck) {
      if (c.toString().contains("A")) {
        List<Card> foundationPile = new ArrayList<>();
        this.foundationPiles.add(foundationPile);
      }
    }
  }

  private boolean validDeck(List<Card> deck) {
    // returns true if the given deck is a valid deck to play klondike with
    List<Card> clubs = new ArrayList<>();
    List<Card> spades = new ArrayList<>();
    List<Card> diamonds = new ArrayList<>();
    List<Card> hearts = new ArrayList<>();
    for (Card c : deck) {
      if (c == null) {
        throw new IllegalArgumentException("Card is null");
      }
    }
    for (Card c : deck) {
      if (c.toString().contains("♣")) {
        clubs.add(c);
      }
      else if (c.toString().contains("♠")) {
        spades.add(c);
      }
      else if (c.toString().contains("♢")) {
        diamonds.add(c);
      }
      else {
        hearts.add(c);
      }
    }
    ArrayList<Integer> clubNums;
    ArrayList<Integer> spadeNums;
    ArrayList<Integer> diamondNums;
    ArrayList<Integer> heartNums;
    ArrayList<ArrayList<Integer>> loNums = new ArrayList<>();
    ArrayList<Boolean> loGood = new ArrayList<>();
    boolean clubGood = false;
    boolean spadeGood = false;
    boolean diamondGood = false;
    boolean heartGood = false;

    if (!clubs.isEmpty()) {
      clubNums = numberOfEachCard(clubs);
      loNums.add(clubNums);
      loGood.add(clubGood);
    }

    if (!spades.isEmpty()) {
      spadeNums = numberOfEachCard(spades);
      loNums.add(spadeNums);
      loGood.add(spadeGood);
    }

    if (!diamonds.isEmpty()) {
      diamondNums = numberOfEachCard(diamonds);
      loNums.add(diamondNums);
      loGood.add(diamondGood);
    }

    if (!hearts.isEmpty()) {
      heartNums = numberOfEachCard(hearts);
      loNums.add(heartNums);
      loGood.add(heartGood);
    }

    if (loNums.isEmpty()) {
      return false;
    }

    ArrayList<Integer> first = loNums.get(0);

    for (ArrayList<Integer> list : loNums) {
      if (!first.equals(list)) {
        return false;
      }
    }

    for (int i = 0; i < loNums.size(); i++) {
      loGood.set(i, continuousRunOfNumbers(loNums.get(i)));
    }

    for (boolean b : loGood) {
      if (!b) {
        return false;
      }
    }
    return true;
  }

  private boolean continuousRunOfNumbers(ArrayList<Integer> numCards) {
    // returns if all the numbers in the given arraylist are the same number, or if
    // the first couple numbers are the same and the rest are 0
    int n = numCards.get(0);
    boolean foundzero = false;
    for (int i = 1; i < numCards.size(); i++) {
      if (n != numCards.get(i) && numCards.get(i) != 0) {
        return false;
      }
      else if (n != numCards.get(i) && numCards.get(i) == 0) {
        n = 0;
        foundzero = true;
      }
      if (foundzero && numCards.get(i) != n) {
        return false;
      }
    }
    return true;
  }

  private ArrayList<Integer> numberOfEachCard(List<Card> loCard) {
    // returns an arraylist of size 13, with each element representing the number of
    // that card there are in the given list of cards. Ex: the first element being 3
    // tells you that there are 3 aces in the given deck
    ArrayList<Integer> ret = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    for (Card c : loCard) {
      ret.set(c.getNum() - 1, ret.get(c.getNum() - 1) + 1);
    }
    return ret;
  }

  protected Card getCascadeCard(int pile, int up) {
    // gets the cascade card at the given pile and how many cards up from the bottom of the pile
    return this.cascadePiles.get(pile).get(this.cascadePiles.get(pile).size() - up);
  }

  private Card getFoundationCard(int pile) {
    // gets the top foundation card at the given pile
    return this.foundationPiles.get(pile).get(this.foundationPiles.get(pile).size() - 1);
  }

  // methods are protected so that the other models in a different package can use them

  protected void checkStart() {
    // throws an exception if the game has not started yet
    if (!this.hasStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
  }

  protected void checkSourcePile(int srcPile) {
    // checks if the given source pile index is valid
    if (srcPile < 0 || this.cascadePiles.size() - 1 < srcPile) {
      throw new IllegalArgumentException("Source pile does not exist");
    }
  }

  protected void checkNumCards(int numCards, int srcPile) {
    // checks if the given number of cards can be taken from the given source pile
    if (numCards < 1 || this.cascadePiles.get(srcPile).size() < numCards) {
      throw new IllegalArgumentException("Invalid number of cards");
    }
  }

  protected void checkDestPile(int destPile) {
    // checks if the given destination pile index is valid
    if (destPile < 0 || this.cascadePiles.size() - 1 < destPile) {
      throw new IllegalArgumentException("Dest pile does not exist");
    }
  }

  protected void equalPiles(int srcPile, int destPile) {
    // checks if the given pile indexes are the same
    if (srcPile == destPile) {
      throw new IllegalArgumentException("Dest pile cannot equal source pile");
    }
  }

  protected void checkDrawIsEmpty() {
    // checks if there are no more cards left in the draw pile
    if (this.visibleDrawCards.isEmpty() && this.restCards.isEmpty()) {
      throw new IllegalStateException("No draw cards left");
    }
  }

  protected void checkFoundPile(int foundationPile) {
    // checks if the given foundation pile index is valid
    if (foundationPile < 0
            || foundationPile > this.foundationPiles.size() - 1) {
      throw new IllegalArgumentException("Invalid foundation pile");
    }
  }

  protected void checkSrcPileIsEmpty(int srcPile) {
    // checks if the source pile at the given index is empty
    if (this.cascadePiles.get(srcPile).isEmpty()) {
      throw new IllegalStateException("Source pile is empty");
    }
  }

  protected void visibleDrawEmptyMoveFromRest() {
    // if the visible draw cards is empty, add a card from the rest of the draw cards
    if (this.visibleDrawCards.isEmpty()) {
      this.visibleDrawCards.add(this.restCards.get(0));
      this.restCards.remove(0);
    }
  }

  protected void moveDrawRestToVisible() {
    // if the rest of the draw cards is not empty, add a card to the visible draw cards
    // from the rest of the draw cards
    if (!this.restCards.isEmpty()) {
      this.visibleDrawCards.add(this.restCards.get(0));
      this.restCards.remove(0);
    }
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    this.checkStart();
    this.checkSourcePile(srcPile);
    this.checkNumCards(numCards, srcPile);
    this.checkDestPile(destPile);
    this.equalPiles(srcPile, destPile);
    if (emptyCascadeMoveAllowed(getCascadeCard(srcPile, numCards), destPile)
            || (!this.cascadePiles.get(destPile).isEmpty()
            && cascadeMoveAllowed(getCascadeCard(srcPile, numCards),
            getCascadeCard(destPile, 1)))) {
      for (int i = numCards; i > 0; i--) {
        this.cascadePiles.get(destPile).add(getCascadeCard(srcPile, i));
      }
      for (int i = 0; i < numCards; i++) {
        this.cascadePiles.get(srcPile).remove(getCascadeCard(srcPile, 1));
      }
      if (!this.cascadePiles.get(srcPile).isEmpty()
              && this.invisibleCascadeCards.contains(getCascadeCard(srcPile, 1))) {
        this.invisibleCascadeCards.remove(getCascadeCard(srcPile, 1));
      }
    }
    else {
      throw new IllegalStateException("move not allowed");
    }
  }

  @Override
  public void moveDraw(int destPile) {
    this.checkStart();
    this.checkDestPile(destPile);
    this.checkDrawIsEmpty();
    this.visibleDrawEmptyMoveFromRest();
    if (emptyCascadeMoveAllowed(this.visibleDrawCards.get(0), destPile)
            || (!this.cascadePiles.get(destPile).isEmpty()
            && cascadeMoveAllowed(this.visibleDrawCards.get(0), getCascadeCard(destPile, 1)))) {
      this.cascadePiles.get(destPile).add(this.visibleDrawCards.get(0));
      this.visibleDrawCards.remove(0);
      this.moveDrawRestToVisible();
    }
    else {
      throw new IllegalStateException("move not allowed");
    }
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    this.checkStart();
    this.checkSourcePile(srcPile);
    this.checkFoundPile(foundationPile);
    this.checkSrcPileIsEmpty(srcPile);
    if (emptyFoundMoveAllowed(getCascadeCard(srcPile, 1), foundationPile)
            || (!this.foundationPiles.get(foundationPile).isEmpty()
            && foundationMoveAllowed(getCascadeCard(srcPile, 1),
            getFoundationCard(foundationPile)))) {
      this.foundationPiles.get(foundationPile).add(getCascadeCard(srcPile, 1));
      this.cascadePiles.get(srcPile).remove(getCascadeCard(srcPile, 1));
      if (!this.cascadePiles.get(srcPile).isEmpty()
              && this.invisibleCascadeCards.contains(getCascadeCard(srcPile, 1))) {
        this.invisibleCascadeCards.remove(getCascadeCard(srcPile, 1));
      }
    }
    else if (!emptyFoundMoveAllowed(getCascadeCard(srcPile, 1), foundationPile)) {
      throw new IllegalStateException("Empty found move");
    }
    else if ((!this.foundationPiles.get(foundationPile).isEmpty()
            && !foundationMoveAllowed(getCascadeCard(srcPile, 1),
            getFoundationCard(foundationPile)))) {
      throw new IllegalStateException("Non empty");
    }
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) {
    this.checkStart();
    this.checkFoundPile(foundationPile);
    this.checkDrawIsEmpty();
    this.visibleDrawEmptyMoveFromRest();
    if (emptyFoundMoveAllowed(this.visibleDrawCards.get(0), foundationPile)
            || (!this.foundationPiles.get(foundationPile).isEmpty()
            && foundationMoveAllowed(
                    this.visibleDrawCards.get(0), this.getCardAt(foundationPile)))) {
      this.foundationPiles.get(foundationPile).add(this.visibleDrawCards.get(0));
      this.visibleDrawCards.remove(0);
      this.moveDrawRestToVisible();
    }
    else {
      throw new IllegalStateException("Move not allowed");
    }
  }

  protected boolean emptyFoundMoveAllowed(Card moved, int foundpile) {
    // returns true if the given cascade card "moved" can be moved to the empty foundation
    // pile indexed from 0 on the left at foundpile
    if (this.invisibleCascadeCards.contains(moved)) {
      return false;
    }
    return this.foundationPiles.get(foundpile).isEmpty() && moved.toString().contains("A");
  }

  protected boolean emptyCascadeMoveAllowed(Card moved, int cascadepile) {
    // returns true if the given cascade card "moved" can be moved to the empty cascade
    // pile indexed from 0 on the left at cascadepile
    if (this.invisibleCascadeCards.contains(moved)) {
      return false;
    }
    return this.cascadePiles.get(cascadepile).isEmpty() && moved.toString().contains("K");
  }

  protected boolean foundationMoveAllowed(Card moved, Card to) {
    // returns true if the given cascade card "moved" can be moved to the
    // given foundation card "to"
    if (this.invisibleCascadeCards.contains(moved) || this.invisibleCascadeCards.contains(to)) {
      return false;
    }
    boolean b = ((moved.toString().contains("♡") && to.toString().contains("♡"))
            || (to.toString().contains("♣") && moved.toString().contains("♣"))
            || (moved.toString().contains("♢") && to.toString().contains("♢"))
            || (to.toString().contains("♠") && moved.toString().contains("♠")));
    return (moved.getNum() == to.getNum() + 1 && b);
  }

  protected boolean cascadeMoveAllowed(Card moved, Card to) {
    // returns true if the given cascade card "moved" can be moved to the
    // given cascade card "to"
    if (this.invisibleCascadeCards.contains(moved) || this.invisibleCascadeCards.contains(to)) {
      return false;
    }
    boolean b = ((moved.toString().contains("♡") || moved.toString().contains("♢"))
            && (to.toString().contains("♣") || to.toString().contains("♠")))
            || ((to.toString().contains("♡") || to.toString().contains("♢"))
            && (moved.toString().contains("♣") || moved.toString().contains("♠")));
    return (moved.getNum() == to.getNum() - 1 && b);
  }

  @Override
  public void discardDraw() {
    this.checkStart();
    if (this.visibleDrawCards.isEmpty()) {
      throw new IllegalStateException("Move not allowed");
    }
    this.restCards.add(this.visibleDrawCards.get(0));
    this.visibleDrawCards.remove(0);

    this.visibleDrawCards.add(this.restCards.get(0));
    this.restCards.remove(0);
  }

  @Override
  public int getNumRows() {
    this.checkStart();
    int n = 0;
    for (List<Card> loCard : this.cascadePiles) {
      if (loCard.size() > n) {
        n = loCard.size();
      }
    }
    return n;
  }

  @Override
  public int getNumPiles() {
    this.checkStart();
    return this.cascadePiles.size();
  }

  @Override
  public int getNumDraw() {
    this.checkStart();
    return this.numDraw;
  }

  @Override
  public boolean isGameOver() {
    this.checkStart();
    boolean b = this.visibleDrawCards.isEmpty() && this.restCards.isEmpty();
    if (!b) {
      return false;
    }
    if (this.cascadePiles.isEmpty()) {
      return true;
    }
    boolean c = true;
    boolean d = true;
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      for (int a = 0; a < this.foundationPiles.size(); a++) {
        if (!this.cascadePiles.get(i).isEmpty()) {
          c = emptyFoundMoveAllowed(getCascadeCard(i, 1), a)
                  || (!this.foundationPiles.get(a).isEmpty()
                  && foundationMoveAllowed(getCascadeCard(i, 1), getCardAt(a)));
          if (c) {
            return false;
          }
        }
      }
    }
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      for (int a = 0; a < this.cascadePiles.get(i).size(); a++) {
        for (int e = 0; e < this.cascadePiles.size(); e++) {
          if (!this.cascadePiles.get(i).isEmpty() && !this.invisibleCascadeCards
                  .contains(getCascadeCard(i, a + 1)) && e != i) {
            d = emptyCascadeMoveAllowed(getCascadeCard(i, a + 1), e)
                    || (!this.cascadePiles.get(e).isEmpty()
                    && cascadeMoveAllowed(getCascadeCard(i, a + 1),
                    getCascadeCard(e, a + 1)));
            if (d) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public int getScore() {
    this.checkStart();
    int n = 0;
    for (int i = 0; i < this.foundationPiles.size(); i++) {
      if (!this.foundationPiles.get(i).isEmpty()) {
        n = n + this.getCardAt(i).getNum();
      }
    }
    return n;
  }

  @Override
  public int getPileHeight(int pileNum) {
    this.checkStart();
    if (pileNum < 0 || pileNum > this.cascadePiles.size() - 1) {
      throw new IllegalArgumentException("Invalid pileNum");
    }
    return this.cascadePiles.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) {
    this.checkStart();
    if (pileNum < 0 || card < 0 || pileNum > this.cascadePiles.size() - 1
            || card > this.cascadePiles.get(pileNum).size() - 1) {
      throw new IllegalArgumentException("Invalid pileNum or card");
    }
    return !this.invisibleCascadeCards.contains(this.cascadePiles.get(pileNum).get(card));
  }

  @Override
  public Card getCardAt(int pileNum, int card) {
    this.checkStart();
    if (pileNum < 0 || card < 0 || pileNum > this.cascadePiles.size() - 1
            || card > this.cascadePiles.get(pileNum).size() - 1
            || this.invisibleCascadeCards.contains(this.cascadePiles.get(pileNum).get(card))) {
      throw new IllegalArgumentException("Invalid pileNum or card");
    }
    return this.cascadePiles.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int foundationPile) {
    this.checkStart();
    this.checkFoundPile(foundationPile);
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      return null;
    }
    return this.foundationPiles.get(foundationPile)
            .get(this.foundationPiles.get(foundationPile).size() - 1);
  }

  @Override
  public List<Card> getDrawCards() {
    this.checkStart();
    List<Card> ret = new ArrayList<>();
    if (!this.visibleDrawCards.isEmpty()) {
      ret.addAll(this.visibleDrawCards);
    }
    return ret;
  }

  @Override
  public int getNumFoundations() {
    this.checkStart();
    return this.foundationPiles.size();
  }
}
