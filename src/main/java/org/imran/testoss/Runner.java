package org.imran.testoss;

public class Runner {
  public static void main(String[] args) {
    Parameterizer.setA("new_A_Value");
    System.out.println(StaticInit.getA());
    System.out.println(StaticInit.getB());
    System.out.println(StaticInit.getC());
    System.out.println(StaticInit.getD());
  }

}
