package com.benjamin.websocket.sessionprovider;


import com.benjamin.websocket.entity.Identity;

import java.io.Serializable;

/**
 * Created by benjamin on 11/3/14.
 * 由于WebSocketSession 不可序列化,所以将websocketsesion的部分属性
 * 放入此类,保存到memcache中
 */
public class WebSocketSessionPropertyHolder implements Serializable{
  //此sesion所在服务器的host
  private String host;
  //此sesion所在服务器的port
  private int port;
  //此sesion所对应的客户端标识
  private Identity identity;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }


}
