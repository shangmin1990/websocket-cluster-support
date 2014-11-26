package com.benjamin.websocket.sessionprovider.impl;


import com.benjamin.websocket.Constant;
import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.sessionprovider.WebSocketSessionPropertyHolder;
import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.util.MemcachedUtil;
import net.spy.memcached.MemcachedClient;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by benjamin on 11/3/14.
 */
public class MemcacheSocketSessionProvider implements WebSocketSessionProvider<WebSocketSessionPropertyHolder>, Constant {

  private MemcachedClient memcachedClient = MemcachedUtil.getClientInstance();

  public MemcacheSocketSessionProvider(){
    Map<Identity, WebSocketSessionPropertyHolder> webSocketSessionMap = (Map<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    if(webSocketSessionMap == null){
      HashMap<Identity, WebSocketSession> webSocketSessionHashMap = new HashMap<>();
      memcachedClient.set(WEBSOCKET_SESSIONS, Integer.MAX_VALUE, webSocketSessionHashMap);
    }
  }

  @Override
  public boolean checkSession(Identity key) {
    Map<Identity, WebSocketSession> session = (HashMap<Identity, WebSocketSession>) memcachedClient.get(WEBSOCKET_SESSIONS);
    return session.containsKey(key);
  }

  @Override
  public void saveSession(Identity key, WebSocketSessionPropertyHolder session) {
    Map<Identity, WebSocketSessionPropertyHolder> sessions = (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    sessions.put(key, session);
    memcachedClient.set(WEBSOCKET_SESSIONS, Integer.MAX_VALUE, sessions);
  }

  @Override
  public void deleteSession(Identity key) {
    Map<Identity, WebSocketSessionPropertyHolder> sessions = (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    sessions.remove(key);
    memcachedClient.set(WEBSOCKET_SESSIONS, Integer.MAX_VALUE, sessions);
  }

  @Override
  public Identity getKeyByValue(WebSocketSessionPropertyHolder session) {
    Map<Identity, WebSocketSessionPropertyHolder> sessions = (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    Set<Identity> keySet = sessions.keySet();
    for(Identity key: keySet){
      if(sessions.get(key).equals(session)){
        return key;
      }
    }
    return null;
  }

  @Override
  public Map<Identity, WebSocketSessionPropertyHolder> getAllSession() {
    return (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
  }

  @Override
  public WebSocketSessionPropertyHolder getSession(Identity key) {
    Map<Identity, WebSocketSessionPropertyHolder> sessions = (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    return sessions.get(key);
  }

  @Override
  public int size() {
    Map<Identity, WebSocketSessionPropertyHolder> sessions = (HashMap<Identity, WebSocketSessionPropertyHolder>) memcachedClient.get(WEBSOCKET_SESSIONS);
    return sessions.size();
  }
}
