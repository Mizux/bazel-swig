package dev.mizux.javanative;

import java.lang.ref.WeakReference;
import java.util.AbstractList;

import dev.mizux.javanative.Loader;
import dev.mizux.javanative.Foo;
import dev.mizux.javanative.Globals;
import dev.mizux.javanative.StringVector;
import dev.mizux.javanative.StringJaggedArray;
import dev.mizux.javanative.IntPair;
import dev.mizux.javanative.PairVector;
import dev.mizux.javanative.PairJaggedArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/** @author Mizux */
public final class FooTest {
  @BeforeEach
  public void setUp() {
    Loader.loadNativeLibraries();
  }

  @Test
  public void testFreeFunctions() {
    try {
      Globals.freeFunction(32);
      Globals.freeFunction((long)64);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testStaticMethods() {
    try {
      Foo.staticFunction(32);
      Foo.staticFunction((long)64);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testStringVector() {
    try {
      {
        StringVector result = Globals.stringVectorOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          System.out.printf("%s, ", result.get(i));
        }
        System.out.printf("}\n");
      }
      {
        AbstractList<String> result = Globals.stringVectorOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          System.out.printf("%s, ", result.get(i));
        }
        System.out.printf("}\n");
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testStringJaggedArray() {
    try {
      {
        AbstractList<StringVector> result = Globals.stringJaggedArrayOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          System.out.printf("{");
          AbstractList<String> inner = result.get(i);
          for(int j=0; j < inner.size(); ++j) {
            System.out.printf("%s,", inner.get(j));
          }
          System.out.printf("},");
        }
        System.out.printf("}\n");
      }
      {
        StringVector vec1 = new StringVector(new String[]{"1", "2", "3"});
        StringVector vec2 = new StringVector(new String[]{"4", "5"});
        StringJaggedArray jag = new StringJaggedArray(new StringVector[]{vec1, vec2});
        Globals.stringJaggedArrayInput(jag);
        Globals.stringJaggedArrayRefInput(jag);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testPairVector() {
    try {
      {
        PairVector result = Globals.pairVectorOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          IntPair p = result.get(i);
          System.out.printf("[%d,%d], ", p.getFirst(), p.getSecond());
        }
        System.out.printf("}\n");
      }
      {
        AbstractList<IntPair> result = Globals.pairVectorOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          IntPair p = result.get(i);
          System.out.printf("[%d,%d], ", p.getFirst(), p.getSecond());
        }
        System.out.printf("}\n");
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testPairJaggedArray() {
    try {
      {
        AbstractList<PairVector> result = Globals.pairJaggedArrayOutput(5);
        System.out.printf("result.size(): %d\n", result.size());
        System.out.printf("{");
        for(int i=0; i < result.size(); ++i) {
          System.out.printf("{");
          AbstractList<IntPair> inner = result.get(i);
          for(int j=0; j < inner.size(); ++j) {
            System.out.printf("[%d,%d],", inner.get(j).getFirst(), inner.get(j).getSecond());
          }
          System.out.printf("},");
        }
        System.out.printf("}\n");
      }
      {
        PairVector vec1 = new PairVector(new IntPair[]{new IntPair(1,1), new IntPair(2,2), new IntPair(3,3)});
        PairVector vec2 = new PairVector(new IntPair[]{ new IntPair(4,4), new IntPair(5,5) });
        PairJaggedArray jag = new PairJaggedArray(new PairVector[]{vec1, vec2});
        Globals.pairJaggedArrayInput(jag);
        Globals.pairJaggedArrayRefInput(jag);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void testFoo() {
    try {
      Foo f = new Foo();
      f.setInt(32);
      assertEquals(32, f.getInt());

      f.setInt64((long)64);
      assertEquals(64, f.getInt64());
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
  public void testFooGC(boolean enableGC) throws Exception {
    Foo f = new Foo();
    f.setInt(32);
    f.setInt64((long)64);
    if (enableGC) {
      gc();
    }
    assertEquals(32, f.getInt());
    assertEquals(64, f.getInt64());
    if (enableGC) {
      gc();
    }
  }
}
