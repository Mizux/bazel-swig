package dev.mizux.javanative;

import dev.mizux.javanative.Loader;
import dev.mizux.javanative.Foo;
import dev.mizux.javanative.Globals;
import java.util.logging.Logger;

/** @author Mizux */
public final class BarTest {
  private static final Logger logger = Logger.getLogger(BarTest.class.getName());

  public static void main(String[] args) throws Exception {
    Loader.loadNativeLibraries();
    try {
      Globals.freeFunction(32);
      Globals.freeFunction((long)64);
      Foo.staticFunction(32);
      Foo.staticFunction((long)64);

      Foo f = new Foo();
      f.setInt(32);
      f.setInt64((long)64);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
