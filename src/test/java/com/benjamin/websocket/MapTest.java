package com.benjamin.websocket;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by benjamin on 11/19/14.
 */
public class MapTest {
  static Object object = new Object();
  public static void main(String... args){
    object.hashCode();
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    map.put(object, object.hashCode());
    System.out.println(map.size());
    Set<Object> keys = map.keySet();
    for(Object key: keys){
      System.out.println(key);
    }
  }
}
