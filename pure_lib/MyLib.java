package dev.mizux.bazel;

/** @author Mizux */
public class MyLib {
  int intValue;
  long longValue;

  public static final int staticMethod(int input) {
    System.out.println("static method: " + input);
    return input;
  }

  public final int getInt() {
    return intValue; 
  }
  
  public final void setInt(int value) {
    intValue = value;    
  }

  public final long getInt64() {
    return longValue; 
  }
  
  public final void setInt64(long value) {
    longValue = value;    
  }
}
