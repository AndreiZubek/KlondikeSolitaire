package cs3500.klondike;

import java.io.IOException;

/**
 * Represents a mock of an appendable to test IOexceptions.
 */
public class MockAppendable implements Appendable {
  private final boolean throwIOException;

  public MockAppendable(boolean throwIOException) {
    this.throwIOException = throwIOException;
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    if (throwIOException) {
      throw new IOException("Mocked IOException");
    }
    return this;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    if (throwIOException) {
      throw new IOException("Mocked IOException");
    }
    return this;
  }

  @Override
  public Appendable append(char c) throws IOException {
    if (throwIOException) {
      throw new IOException("Mocked IOException");
    }
    return this;
  }
}
