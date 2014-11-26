package com.benjamin.websocket.entity;

/**
 * Created by benjamin on 11/19/14.
 */
public class StringIdentity implements Identity<String> {

  private String identity;

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public StringIdentity(){

  }

  public StringIdentity(String identity){
    this.identity = identity;
  }

  @Override
  public String getIdentify() {
    return identity;
  }

  @Override
  public boolean equals(Object obj) {

    if(obj == null)
      return false;
    Identity identity = (Identity)obj;
    //数据类型相同
    if(identity.getIdentify().getClass().getName().equals(this.getIdentify().getClass().getName())){
      if(identity.getIdentify() != null && identity.getIdentify().equals(this.getIdentify())){
        return true;
      }
    }
    return false;
  }
  public int hashCode(){
    return this.identity.hashCode();
  }
}
