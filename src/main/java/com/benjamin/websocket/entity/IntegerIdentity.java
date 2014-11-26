package com.benjamin.websocket.entity;

/**
 * Created by benjamin on 11/19/14.
 */
public class IntegerIdentity implements Identity<Integer> {

  private int idenity;

  public void setIdenity(int idenity) {
    this.idenity = idenity;
  }


  @Override
  public boolean equals(Object obj) {
    Identity identity = (Identity)obj;
    if(identity == null)
      return false;
    if(identity.getIdentify().getClass().getName().equals(this.getIdentify().getClass().getName()) && identity.getIdentify() == this.getIdentify())
      return true;
    return false;
  }

  @Override
  public Integer getIdentify() {
    return this.idenity;
  }

  @Override
  public int hashCode() {
    return this.idenity;
  }
}
