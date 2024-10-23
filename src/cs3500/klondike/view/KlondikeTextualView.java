package cs3500.klondike.view;

import java.io.IOException;
import java.util.Objects;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A simple text-based rendering of the Klondike game.
 * You can get the view of the game as a string using
 * the toString method, or append it to this appendable
 * using the render method.
 */
public class KlondikeTextualView implements TextualView {

  private final KlondikeModel model;

  private Appendable ap;

  /**
   * Constructor for the textual view of a klondike game.
   */
  public KlondikeTextualView(KlondikeModel model) {
    this.model = Objects.requireNonNull(model);
  }

  public KlondikeTextualView(KlondikeModel model, Appendable ap) {
    this.model = Objects.requireNonNull(model);
    this.ap = Objects.requireNonNull(ap);
  }

  @Override
  public void render() throws IOException {
    String s = this.toString() + System.lineSeparator();
    this.ap.append(s);
  }

  @Override
  public String toString() {
    String display = "Draw: ";
    if (this.model.getDrawCards().isEmpty()) {
      display = display.concat("\n");
    }
    else {
      for (Card c : this.model.getDrawCards()) {
        display = display.concat(c.toString());
        if (!c.equals(this.model.getDrawCards().get(this.model.getDrawCards().size() - 1))) {
          display = display.concat(", ");
        } else {
          display = display.concat("\n");
        }
      }
    }

    display = display.concat("Foundation: ");
    for (int i = 0; i < this.model.getNumFoundations(); i++) {
      if (this.model.getCardAt(i) == null) {
        display = display.concat("<none>");
      }
      else {
        display = display.concat(this.model.getCardAt(i).toString());
      }
      if (i < this.model.getNumFoundations() - 1) {
        display = display.concat(", ");
      }
      else {
        display = display.concat("\n");
      }
    }

    boolean bottom = true;

    for (int i = 0; i < this.model.getNumRows(); i++) {
      for (int a = 0; a < this.model.getNumPiles(); a++) {
        if (bottom && this.model.getPileHeight(a) == 0) {
          display = display.concat("  X");
        }
        else if (this.model.getPileHeight(a) < i + 1) {
          display = display.concat("   ");
        }
        else {
          if (this.model.isCardVisible(a, i)) { // c==1
            if (this.model.getCardAt(a, i).toString().contains("10")) {
              display = display.concat(this.model.getCardAt(a, i).toString());
            }
            else {
              display = display.concat(" ");
              display = display.concat(this.model.getCardAt(a, i).toString());
            }
          }
          else {
            display = display.concat("  ?");
          }
        }
        if (a == this.model.getNumPiles() - 1) {
          display = display.stripTrailing().concat("\n");
        }
      }
      bottom = false;
    }
    return display.trim();
  }
}
