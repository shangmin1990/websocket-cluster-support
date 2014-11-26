package com.benjamin.websocket.entity;

import java.io.Serializable;

/**
 * Created by benjamin on 11/18/14.
 */
public interface Identity<T> extends Serializable{

  /**
   * 获取对象的唯一标识
   * @return
   */
  T getIdentify();

  boolean equals(Object identity);

  int hashCode();

}
