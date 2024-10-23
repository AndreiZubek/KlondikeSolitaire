package cs3500.klondike.controller;

import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.view.KlondikeTextualView;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents the class for a klondike game controller.
 * It interacts with the view and the model in order to
 * take in input from the user and then transmit
 * output to the view.
 */
public class KlondikeTextualController implements cs3500.klondike.controller.KlondikeController {

  // stores the info that will be output to the user
  private final Appendable ap;

  // stores the user input
  private final Readable rd;

  // instantiating private model to be used for private helper methods
  private KlondikeModel model;

  // the scanner used to help read user input
  private Scanner scanner;

  // the textualview of the current game
  private KlondikeTextualView ktv;

  // determines if the game has been quit or not
  private boolean quit;

  /**
   * A constructor for a klondike controller.
   */
  public KlondikeTextualController(Readable r, Appendable a) throws IllegalArgumentException {
    if (r == null || a == null) {
      throw new IllegalArgumentException("input or output is null");
    }
    this.ap = a;
    this.rd = r;
  }

  @Override
  public void playGame(KlondikeModel model, List<Card> deck, boolean shuffle,
                       int numPiles, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    this.model = model;
    try {
      this.model.startGame(deck, shuffle, numPiles, numDraw);
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
    this.quit = false;
    ktv = new KlondikeTextualView(this.model, this.ap);
    this.scanner = new Scanner(this.rd);
    String input;
    while (!this.quit) {
      if (!this.model.isGameOver()) {
        this.renderBoard();
        this.printScore();
      }

      if (this.model.isGameOver()) {
        this.quit = true;
        this.renderBoard();
        this.printScore();

        if (this.model.getDrawCards().isEmpty() && this.model.getNumRows() == 0) {
          writeMessage("You win!" + System.lineSeparator());
        } else {
          this.printGameLose();
        }
        return;
      } else {
        try {
          input = this.scanner.next();
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("no input");
        }
        this.inputQuit(input);

        if (this.quit) {
          return;
        }
        this.inputSwitch(input);
      }
    }
  }

  private void inputSwitch(String input) {
    // Switch statement that determines what to do for the given input
    switch (input) {
      case "mpp":
        this.validMpp();
        break;
      case "md":
        this.validMd();
        break;
      case "mpf":
        this.validMpf();
        break;
      case "mdf":
        this.validMdf();
        break;
      case "dd":
        try {
          this.model.discardDraw();
        } catch (IllegalStateException e) {
          writeMessage("Invalid move. Play again. No draw cards remain: "
                  + e.getMessage() + System.lineSeparator());
        }
        break;
      default:
        writeMessage("Invalid move. Play again. Re-enter input: " + System.lineSeparator());
        break;
    }
  }

  private void renderBoard() {
    // appends the string that represents the current
    // board to this appendable
    try {
      this.ktv.render();
    } catch (IOException e) {
      writeMessage("could not append the textual view");
    }
  }

  private void validMd() {
    // goes until md has all valid arguments, and either has the model perform the
    // move if it is allowed by the game, if not it tells the user the move is invalid
    int cascadePileIndex = 0;

    boolean validCascadePileIndex = false;

    while (!validCascadePileIndex && !quit) {
      try {
        if (!validCascadePileIndex) {
          cascadePileIndex = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        this.model.moveDraw(cascadePileIndex - 1);
        validCascadePileIndex = true;
      } catch (IllegalArgumentException | IllegalStateException e) {
        if (e.getMessage().equals("invalid destination pile")) {
          this.renderBoard();
          writeMessage("Invalid move. Play again. Re-enter input: "
                  + e.getMessage() + System.lineSeparator());
        }
        else {
          writeMessage("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
          return;
        }
      }
    }
  }

  private void validMpf() {
    // goes until mpf has all valid arguments, and either has the model perform the
    // move if it is allowed by the game, if not it tells the user the move is invalid
    int cascadePileIndex = 0;
    int foundPileIndex = 0;

    boolean validCascadePileIndex = false;
    boolean validFoundPileIndex = false;

    while (!validFoundPileIndex && !quit) {
      try {
        if (!validCascadePileIndex) {
          cascadePileIndex = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        if (!validFoundPileIndex) {
          foundPileIndex = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        this.model.moveToFoundation(cascadePileIndex - 1, foundPileIndex - 1);
        validFoundPileIndex = true;
        validCascadePileIndex = true;
      } catch (IllegalArgumentException | IllegalStateException e) {
        if (e.getMessage().equals("Invalid source pile")
                || e.getMessage().equals("Invalid foundation pile")) {
          this.renderBoard();
          writeMessage("Invalid move. Play again. Re-enter input: "
                  + e.getMessage() + System.lineSeparator());
          if (e.getMessage().equals("Invalid foundation pile")) {
            validCascadePileIndex = true;
          }
        }
        else {
          writeMessage("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
          return;
        }
      }
    }
  }

  private void validMdf() {
    // goes until mdf has all valid arguments, and either has the model perform the
    // move if it is allowed by the game, if not it tells the user the move is invalid
    int foundPileIndex = 0;

    boolean validFoundPileIndex = false;

    while (!validFoundPileIndex && !quit) {
      try {
        if (!validFoundPileIndex) {
          foundPileIndex = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        this.model.moveDrawToFoundation(foundPileIndex - 1);
        validFoundPileIndex = true;
      } catch (IllegalArgumentException | IllegalStateException e) {
        // this.printScore();
        if (e.getMessage().equals("Invalid foundation pile")) {
          writeMessage("Invalid move. Play again. Re-enter input: "
                  + e.getMessage() + System.lineSeparator());
          this.renderBoard();
        }
        else {
          writeMessage("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
          return;
        }
      }
    }
  }

  private void validMpp() {
    // goes until mpp has all valid arguments, and either has the model perform the
    // move if it is allowed by the game, if not it tells the user the move is invalid
    int cascadeIndex = 0;
    int cascadeIndex2 = 0;
    int numCards = 0;

    boolean validCI = false;
    boolean validCI2 = false;
    boolean validNC = false;
    boolean allValid = false;

    while (!allValid && !quit) {
      try {
        if (!validCI) {
          cascadeIndex = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        if (!validNC) {
          numCards = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        if (!validCI2) {
          cascadeIndex2 = this.goUntilValidNumOrQuit();
        }
        if (quit) {
          return;
        }
        this.model.movePile(cascadeIndex - 1, numCards, cascadeIndex2 - 1);
        allValid = true;
      } catch (IllegalArgumentException | IllegalStateException e) {
        if (e.getMessage().equals("Source pile does not exist")
                || e.getMessage().equals("Invalid number of cards")
                || e.getMessage().equals("Dest pile does not exist")
                || e.getMessage().equals("Dest pile cannot equal source pile")) {
          this.renderBoard();
          writeMessage("Invalid move. Play again. Re-enter inputs: "
                  + e.getMessage() + System.lineSeparator());
          if (e.getMessage().equals("Dest pile cannot equal source pile")) {
            validCI = true;
            validNC = true;
            validCI2 = false;
          } else if (e.getMessage().equals("Dest pile does not exist")) {
            validCI = true;
            validNC = true;
            validCI2 = false;
          } else if (e.getMessage().equals("Invalid number of cards")) {
            validCI = true;
            validNC = false;
          } else if (e.getMessage().equals("Source pile does not exist")) {
            validCI = false;
          }
        }
        else {
          writeMessage("Invalid move. Play again. " + e.getMessage() + System.lineSeparator());
          return;
        }
      }
    }
  }

  private void inputQuit(String input) {
    // if the given string is q or Q, the game is quit and the game quit screen is
    // appended to this appendable
    if (input.equalsIgnoreCase("q")) {
      this.quit = true;
      this.printGameEnd();
      this.renderBoard();
      this.printScore();
    }
  }

  private int goUntilValidNumOrQuit() {
    // goes until a natural number or a quit is input, returns the int if it is positive
    String s;
    int i;

    boolean stop = false;
    while (!stop) {
      try {
        s = this.scanner.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("no input");
      }
      this.inputQuit(s);
      if (this.quit) {
        return -5;
      }
      try {
        i = Integer.parseInt(s);
        if (i >= 0) {
          return i;
        }
      } catch (NumberFormatException nfe) {
        // keep going
      }
    }
    return 0;
  }

  private void writeMessage(String message) throws IllegalStateException {
    // appends the given message to this appendable
    // throws an IllegalStateException if an IOException is thrown
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  private void printScore() throws IllegalStateException {
    // appends the score of a game to this appendable
    writeMessage("Score: " + this.model.getScore() + System.lineSeparator());
  }

  private void printGameEnd() throws IllegalStateException {
    // appends the first part of when a game is quit to this appendable
    writeMessage("Game quit!" + System.lineSeparator());
    writeMessage("State of game when quit:" + System.lineSeparator());
  }

  private void printGameLose() throws IllegalStateException {
    // appends the end message when a game is lost to this appendable
    writeMessage("Game over. Score: " + this.model.getScore() + System.lineSeparator());
  }
}