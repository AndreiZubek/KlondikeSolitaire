package cs3500.klondike.view;

import java.io.IOException;

/**
 * A representation of the textual view of a klondike
 * game. The game state can be displayed using the
 * toString method, whereas the render method
 * appends the view of the model to an appendable.
 */
public interface TextualView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

  /**
   * Returns the klondike model displayed as a string.
   *
   * @return the textual view of the given klondike model
   */
  String toString();
}
