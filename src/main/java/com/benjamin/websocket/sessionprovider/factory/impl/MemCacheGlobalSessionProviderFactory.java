package com.benjamin.websocket.sessionprovider.factory.impl;

import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.impl.MemcacheSocketSessionProvider;

/**
 * Created by benjamin on 11/26/14.
 * if you implements this interface your self,
 * be sure your data source is the same one;
 */
public class MemCacheGlobalSessionProviderFactory implements com.benjamin.websocket.sessionprovider.factory.GlobalWebSocketSessionProviderFactory {

  private static WebSocketSessionProvider globalWebSocketSessionProvider = new MemcacheSocketSessionProvider();

  public MemCacheGlobalSessionProviderFactory(){

  }

  public MemCacheGlobalSessionProviderFactory(WebSocketSessionProvider globalWebSocketSessionProvider){
    this.globalWebSocketSessionProvider = globalWebSocketSessionProvider;
  }

  public static void setGlobalWebSocketSessionProvider(WebSocketSessionProvider globalWebSocketSessionProvider) {
    MemCacheGlobalSessionProviderFactory.globalWebSocketSessionProvider = globalWebSocketSessionProvider;
  }


  @Override
  public WebSocketSessionProvider getGlobalWebSocketSessionProvider() {
    return globalWebSocketSessionProvider;
  }
}
