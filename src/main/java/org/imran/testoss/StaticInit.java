package org.imran.testoss;

public class StaticInit {
  public static final String a;
  public static final String b;
  public static final String c;
  public static final String d;

  static {
    a = Parameterizer.getA();
    b = Parameterizer.getB();
    c = Parameterizer.getC();
    Parameterizer.setD("D for macakInit");
    d = Parameterizer.getD();
  }

  public static String getA() {
    return a;
  }

  public static String getB() {
    return b;
  }

  public static String getC() {
    return c;
  }

  public static String getD() {
    return d;
  }

}
