package com.benjamin.websocket;

/**
 * Created by benjamin on 11/19/14.
 */
public class Child1 extends Parent {
  public static void main(String... args){
    Child1 child1 = new Child1();
    System.out.println(child1.map.hashCode());
    Child2 child2 = new Child2();
    System.out.println(child2.map.hashCode());
    System.out.println(child1.map == child2.map);
  }
}
