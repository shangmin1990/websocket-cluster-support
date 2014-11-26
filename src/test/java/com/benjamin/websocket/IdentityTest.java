package com.benjamin.websocket;

import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.entity.IntegerIdentity;
import com.benjamin.websocket.entity.StringIdentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benjamin on 11/26/14.
 */
public class IdentityTest {
  private static Map<Identity, String> map;
  public static void main(String... args){
    map = new HashMap<>();
    Identity identity = new IntegerIdentity();

    map.put(identity, "fafa");
    Identity identity1 = new IntegerIdentity();
    map.put(identity1,"lala");
    System.out.println(map.size());
    boolean equals = identity.equals(identity1);
    System.out.println(equals);
    System.out.println(identity1.hashCode());
    System.out.println(identity.hashCode());
    System.out.println("bab".hashCode());
    System.out.println("bab".hashCode());
    String value = map.get(identity);
    String value1 = map.get(identity1);
    System.out.println(value);
    System.out.println(value1);
  }
}
