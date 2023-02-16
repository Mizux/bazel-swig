package dev.mizux.bazel;

import java.lang.ref.WeakReference;

import dev.mizux.bazel.MyLib;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/** @author Mizux */
public class MyLibTest {
  @Test
  public void testStaticMethod() {
    try {
      MyLib.staticMethod(42);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testMyLib() {
    try {
      MyLib l = new MyLib();
      l.setInt(32);
      assertEquals(32, l.getInt());

      l.setInt64((long)64);
      assertEquals(64, l.getInt64());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void gc() {
    Object obj = new Object();
    WeakReference ref = new WeakReference<Object>(obj);
    obj = null;
    while(ref.get() != null) {
      System.gc();
    }
  }

  @ParameterizedTest
  @ValueSource(booleans = { false, true })
  public void testMyLibGC(boolean enableGC) throws Exception {
    MyLib l = new MyLib();
    l.setInt(32);
    l.setInt64((long)64);
    if (enableGC) {
      gc();
    }
    assertEquals(32, l.getInt());
    assertEquals(64, l.getInt64());
    if (enableGC) {
      gc();
    }
  }
}
