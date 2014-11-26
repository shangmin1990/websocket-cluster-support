package com.benjamin.websocket.sessionprovider.factory.impl;

import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.factory.GlobalWebSocketSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.factory.LocalWebSocketSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.factory.WebSocketSessionProviderFactory;

/**
 * Created by benjamin on 11/26/14.
 */
public class DefaultWebSocketSessionProviderFactory implements WebSocketSessionProviderFactory {

  private GlobalWebSocketSessionProviderFactory globalWebSocketSessionProviderFactory;

  private LocalWebSocketSessionProviderFactory localWebSocketSessionProviderFactory;


  public DefaultWebSocketSessionProviderFactory(){
    globalWebSocketSessionProviderFactory = new MemCacheGlobalSessionProviderFactory();
    localWebSocketSessionProviderFactory = new DefaultLocalSessionProviderFactory();
  }

  public DefaultWebSocketSessionProviderFactory(GlobalWebSocketSessionProviderFactory globalWebSocketSessionProviderFactory){
    this(globalWebSocketSessionProviderFactory, null);
    localWebSocketSessionProviderFactory = new DefaultLocalSessionProviderFactory();
  }

  public DefaultWebSocketSessionProviderFactory(LocalWebSocketSessionProviderFactory localWebSocketSessionProviderFactory){
    this(null, localWebSocketSessionProviderFactory);
    globalWebSocketSessionProviderFactory = new MemCacheGlobalSessionProviderFactory();
  }

  public DefaultWebSocketSessionProviderFactory(GlobalWebSocketSessionProviderFactory globalWebSocketSessionProviderFactory, LocalWebSocketSessionProviderFactory localWebSocketSessionProviderFactory){
    this.globalWebSocketSessionProviderFactory = globalWebSocketSessionProviderFactory;
    this.localWebSocketSessionProviderFactory = localWebSocketSessionProviderFactory;
  }

  public GlobalWebSocketSessionProviderFactory getGlobalWebSocketSessionProviderFactory() {
    return globalWebSocketSessionProviderFactory;
  }

  public void setGlobalWebSocketSessionProviderFactory(GlobalWebSocketSessionProviderFactory globalWebSocketSessionProviderFactory) {
    this.globalWebSocketSessionProviderFactory = globalWebSocketSessionProviderFactory;
  }

  public LocalWebSocketSessionProviderFactory getLocalWebSocketSessionProviderFactory() {
    return localWebSocketSessionProviderFactory;
  }

  public void setLocalWebSocketSessionProviderFactory(LocalWebSocketSessionProviderFactory localWebSocketSessionProviderFactory) {
    this.localWebSocketSessionProviderFactory = localWebSocketSessionProviderFactory;
  }

  @Override
  public WebSocketSessionProvider getWebSocketSessionProvider(boolean global) {
    if(global){
      return globalWebSocketSessionProviderFactory.getGlobalWebSocketSessionProvider();
    }
    return localWebSocketSessionProviderFactory.getLocalWebSocketSessionProvider();
  }
}
