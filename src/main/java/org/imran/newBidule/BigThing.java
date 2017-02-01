package org.imran.newBidule;

public class BigThing {

  public static int mac(int n) {
    if (n > 100)
      return n - 10;
    else
      return mac(mac(n + 11));
  }

  public static void main(String[] args) {
    System.out.println("to be integrated in next release");
    System.out.println(mac(10));
  }
}
