package com.benjamin.websocket.sessionprovider.factory.impl;

import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.impl.SimpWebSocketSessionProvider;

/**
 * Created by benjamin on 11/26/14.
 * if you implements this interface your self,
 * be sure your data source(such as a map) is the same one;
 * or the WebSocketProvider is singleton;
 */
public class DefaultLocalSessionProviderFactory implements com.benjamin.websocket.sessionprovider.factory.LocalWebSocketSessionProviderFactory {

  private static WebSocketSessionProvider localWebSocketSessionProvider = new SimpWebSocketSessionProvider();

  public DefaultLocalSessionProviderFactory(){

  }

  public DefaultLocalSessionProviderFactory(WebSocketSessionProvider localWebSocketSessionProvider){
    this.localWebSocketSessionProvider = localWebSocketSessionProvider;
  }

  @Override
  public WebSocketSessionProvider getLocalWebSocketSessionProvider() {
    return localWebSocketSessionProvider;
  }
}
