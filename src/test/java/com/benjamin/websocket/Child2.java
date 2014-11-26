package com.benjamin.websocket;

/**
 * Created by benjamin on 11/19/14.
 */
public class Child2 extends Parent {
  public static void main(String... args){
    Child2 child2 = new Child2();
    System.out.println(child2.map.hashCode());
    System.out.println();
  }
}
