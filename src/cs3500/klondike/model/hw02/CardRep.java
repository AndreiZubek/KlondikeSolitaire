package cs3500.klondike.model.hw02;

/**
 * A class that is the representation of a playing card. It
 * has a suit, a number, and it can be either face up or down.
 */
public class CardRep implements Card {

  // represents the number on a card
  private final int number;

  // represents the suit of a card
  private final Suit suit;

  /**
   * A class that represents the suit of a playing card.
   */
  public enum Suit {
    HEARTS("♡"),
    DIAMONDS("♢"),
    CLUBS("♣"),
    SPADES("♠");

    private final String symbol;

    Suit(String symbol) {
      this.symbol = symbol;
    }

    public String getSymbol() {
      return symbol;
    }
  }

  /**
   * The constructor for a playing card.
   */
  public CardRep(int number, Suit suit) {
    if (number < 1 || number > 13) {
      throw new IllegalArgumentException("Number has to be between 1 and 13");
    }
    this.number = number;
    this.suit = suit;
  }

  @Override
  public String toString() {
    String n = getNumberString();
    return n + suit.getSymbol();
  }

  @Override
  public int getNum() {
    return this.number;
  }

  private String getNumberString() {
    // returns the string of a playing card corresponding to the given number
    switch (number) {
      case 1:
        return "A";
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
      default:
        return String.valueOf(number);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CardRep)) {
      return false;
    }

    CardRep card = (CardRep) obj;
    return this.number == card.number && this.suit == card.suit;
  }

  @Override
  public int hashCode() {
    return this.number * 31;
  }
}
