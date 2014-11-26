package com.benjamin.websocket.sessionprovider.impl;

import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by benjamin on 9/28/14.
 * 本地的sessionProvider
 */
public class SimpWebSocketSessionProvider implements WebSocketSessionProvider<WebSocketSession> {

  private ConcurrentHashMap<Identity, WebSocketSession> websockets = new ConcurrentHashMap<>();

  @Override
  public boolean checkSession(Identity key) {
    if(key == null){
      return false;
    }
    return websockets.containsKey(key);
  }

  @Override
  public void saveSession(Identity key, WebSocketSession session) {

    websockets.putIfAbsent(key, session);
  }

  @Override
  public void deleteSession(Identity key) {
    websockets.remove(key);
  }

  @Override
  public Identity getKeyByValue(WebSocketSession session) {
    if(websockets.containsValue(session)){
      Set<Identity> keySet = websockets.keySet();
      for(Identity key: keySet){
        if(websockets.get(key).getId().equals(session.getId())){
          return key;
        }
      }
    }
    return null;
  }

  @Override
  public Map<Identity, WebSocketSession> getAllSession() {
    return websockets;
  }

  @Override
  public WebSocketSession getSession(Identity key) {
    if(websockets.containsKey(key)){
      return websockets.get(key);
    }
    return null;
  }

  @Override
  public int size() {
    return websockets.size();
  }
}
