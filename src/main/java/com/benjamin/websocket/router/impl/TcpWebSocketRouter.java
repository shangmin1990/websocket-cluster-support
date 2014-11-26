package com.benjamin.websocket.router.impl;

import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.router.AbstractWebSocketRouter;
import net.sf.json.JSONObject;
import org.springframework.web.socket.WebSocketMessage;

/**
 * Created by benjamin on 11/19/14.
 */
public class TcpWebSocketRouter extends AbstractWebSocketRouter {
  @Override
  public JSONObject dispatcher(Identity identify, WebSocketMessage webSocketMessage) {
    return null;
  }
}
