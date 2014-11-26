package com.benjamin.websocket.sessionprovider.factory;

import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;

/**
 * Created by benjamin on 11/26/14.
 */
public interface WebSocketSessionProviderFactory {

  WebSocketSessionProvider getWebSocketSessionProvider(boolean global);

}
