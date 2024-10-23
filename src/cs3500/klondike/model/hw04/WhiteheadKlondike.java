package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A game type of klondike where all the cascade cards are always visible, a valid build of a
 * cascade pile means that each card is of the same color, and if you move multiple cards from
 * one cascade to another, they all have to be the same suit. Any card can be moved to an empty
 * cascade pile, not just a king.
 */
public class WhiteheadKlondike extends KlondikeAbstractModel {
  public WhiteheadKlondike() {
    super();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    super.startGame(deck, shuffle, numPiles, numDraw);
    this.invisibleCascadeCards.clear();
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    super.checkStart();
    super.checkSourcePile(srcPile);
    super.checkNumCards(numCards, srcPile);
    super.checkDestPile(destPile);
    super.equalPiles(srcPile, destPile);
    if (numCards == 1) {
      this.singleCardMove(getCascadeCard(srcPile, 1), destPile, srcPile);
    }
    else {
      this.multipleCardMove(srcPile, numCards, destPile);
    }
  }

  @Override
  public void moveDraw(int destPile) {
    super.checkStart();
    super.checkDestPile(destPile);
    super.checkDrawIsEmpty();
    this.moveDrawToCascade(destPile);
    super.moveDrawRestToVisible();
  }

  private void moveDrawToCascade(int destPile) {
    // moves the current draw card to the given cascade pile
    // throws an illegal state exception if the move is invalid
    Card c = this.visibleDrawCards.get(0);
    if (this.cascadePiles.get(destPile).isEmpty()) {
      this.moveDrawCard(destPile);
    }
    else if (this.sameColor(c, getCascadeCard(destPile, 1))
            && this.validNumber(c, getCascadeCard(destPile, 1))) {
      this.moveDrawCard(destPile);
    }
    else {
      throw new IllegalStateException("Cannot make this move");
    }
  }

  private void moveDrawCard(int destPile) {
    // adds the current draw card to the given cascade pile and removes the current draw card
    this.cascadePiles.get(destPile).add(this.visibleDrawCards.get(0));
    this.visibleDrawCards.remove(0);
  }

  private void multipleCardMove(int srcPile, int numCards, int destPile) {
    // moves multiple cards from the given source pile to the given destination pile
    // throws an illegal state exception if the move is invalid
    if (this.cascadePiles.get(destPile).isEmpty()) {
      this.moveMultipleCards(srcPile, numCards, destPile);
    }
    else {
      if (this.sameColor(getCascadeCard(srcPile, numCards), getCascadeCard(destPile, 1))
              && this.validNumber(getCascadeCard(srcPile, numCards), getCascadeCard(destPile, 1))) {
        this.moveMultipleCards(srcPile, numCards, destPile);
      }
      else {
        throw new IllegalStateException("Cannot make this move");
      }
    }
  }

  private void singleCardMove(Card moving, int destPile, int srcPile) {
    // moves a single cascade card from the given source pile to the given destination pile
    // throws an illegal state exception if the move is invalid
    if (this.cascadePiles.get(destPile).isEmpty()) {
      this.moveSingleCard(srcPile, destPile, moving);
    }
    else {
      if (this.sameColor(moving, getCascadeCard(destPile, 1))
              && this.validNumber(moving, getCascadeCard(destPile, 1))) {
        this.moveSingleCard(srcPile, destPile, moving);
      }
      else {
        throw new IllegalStateException("Cannot make this move");
      }
    }
  }

  private void moveMultipleCards(int srcPile, int numCards, int destPile) {
    // moves multiple cascade cards from the given source pile to the given destination pile
    // throws an illegal state exception if not all the cards moving are in the same suit
    Card c = getCascadeCard(srcPile, 1);
    for (int i = 1; i <= numCards; i++) {
      if (!this.sameSuit(c, getCascadeCard(srcPile, i))) {
        throw new IllegalStateException("Not all cards in the moving pile have the same suit");
      }
    }
    for (int i = numCards; i > 0; i--) {
      this.cascadePiles.get(destPile).add(getCascadeCard(srcPile, i));
      this.cascadePiles.get(srcPile).remove(getCascadeCard(srcPile, i));
    }
  }

  private boolean sameSuit(Card one, Card two) {
    // returns true if the given cards are of the same suit
    return (one.toString().contains("♡") && two.toString().contains("♡"))
            || (one.toString().contains("♢") && two.toString().contains("♢"))
            || (one.toString().contains("♣") && two.toString().contains("♣"))
            || (one.toString().contains("♠") && two.toString().contains("♠"));
  }

  private void moveSingleCard(int srcPile, int destPile, Card moving) {
    // adds the given card to the given destination pile and removes the card from the given
    // source pile
    this.cascadePiles.get(destPile).add(moving);
    this.cascadePiles.get(srcPile).remove(moving);
  }

  private boolean sameColor(Card moving, Card to) {
    // returns true if the given cards are of the same color
    return (moving.toString().contains("♡") && to.toString().contains("♡"))
            || (moving.toString().contains("♡") && to.toString().contains("♢"))
            || (moving.toString().contains("♣") && to.toString().contains("♣"))
            || (moving.toString().contains("♣") && to.toString().contains("♠"))
            || (moving.toString().contains("♢") && to.toString().contains("♢"))
            || (moving.toString().contains("♢") && to.toString().contains("♡"))
            || (moving.toString().contains("♠") && to.toString().contains("♠"))
            || (moving.toString().contains("♠") && to.toString().contains("♣"));
  }

  private boolean validNumber(Card moving, Card to) {
    // returns true if the moving card has a number that is one less than the
    // card that it is moving to
    return moving.getNum() == to.getNum() - 1;
  }

  @Override
  public boolean isGameOver() {
    super.checkStart();

    for (List<Card> lo : this.cascadePiles) {
      for (List<Card> loc : this.cascadePiles) {
        if (!loc.equals(lo) && lo.isEmpty() && !loc.isEmpty()) {
          return false;
        }
      }
    }

    List<Card> topCards = new ArrayList<>();
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      if (!this.cascadePiles.get(i).isEmpty()) {
        topCards.add(this.getCascadeCard(i, 1));
      }
      else {
        topCards.add(null);
      }
    }

    boolean oneValid = false;
    boolean multipleValid = false;

    for (int i = 0; i < this.cascadePiles.size(); i++) {
      for (int a = 1; a <= this.cascadePiles.get(i).size(); a++) {
        for (Card card : topCards) {
          if (card != null && !this.cascadePiles.get(i).contains(card)) {
            if (a == 1) {
              oneValid = this.sameColor(this.getCascadeCard(i, 1), card)
                      && this.validNumber(this.getCascadeCard(i, 1), card);
              if (oneValid) {
                return false;
              }
            } else {
              multipleValid = this.sameSuit(this.getCascadeCard(i, a), card)
                      && this.validNumber(this.getCascadeCard(i, a), card);
              if (multipleValid) {
                return false;
              }
            }
          }
        }
      }
    }
    return true;
  }
}
