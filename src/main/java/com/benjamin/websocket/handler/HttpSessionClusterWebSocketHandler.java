package com.benjamin.websocket.handler;

import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.handler.ClusterWebSocketHandlerAdapter;
import com.benjamin.websocket.sessionprovider.WebSocketSessionPropertyHolder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by benjamin on 11/26/14.
 * 利用HTTPsession中的对象 将client与websocketSession对象建立对应关系
 */
public class HttpSessionClusterWebSocketHandler extends ClusterWebSocketHandlerAdapter {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    Identity identity = getIdentityFromWebSocketSessionHandshake(session);
    //将session与对象建立对应关系
    //全局session
    //供websocketSession对象定位和消息转发使用
    String ip = getRealIp();
    WebSocketSessionPropertyHolder webSocketSessionPropertyHolder = new WebSocketSessionPropertyHolder();
    webSocketSessionPropertyHolder.setIdentity(identity);
    webSocketSessionPropertyHolder.setHost(ip);
    webSocketSessionPropertyHolder.setPort(session.getLocalAddress().getPort());
    webSocketSessionPropertyHolderProvider.saveSession(identity, webSocketSessionPropertyHolder);
    //局部session存储，供检索websocketSession对像使用
    localWebSocketSessionProvider.saveSession(identity,session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    super.afterConnectionClosed(session, closeStatus);
    Identity identity = getIdentityFromWebSocketSessionHandshake(session);
    //删除对应关系
    String ip = getRealIp();
    WebSocketSessionPropertyHolder webSocketSessionPropertyHolder = new WebSocketSessionPropertyHolder();
    webSocketSessionPropertyHolder.setIdentity(identity);
    webSocketSessionPropertyHolder.setHost(ip);
    webSocketSessionPropertyHolder.setPort(session.getLocalAddress().getPort());
    webSocketSessionPropertyHolderProvider.deleteSession(identity);
    //局部session删除，
    localWebSocketSessionProvider.deleteSession(identity);
  }
  private Identity getIdentityFromWebSocketSessionHandshake(WebSocketSession session){
    //首先获取自定义握手所存储的数据,包括httpSession 及 httpSession中存储对象使用的key(由用户定义,通过Map传递到handler中)
    Map<String, Object> map = session.getAttributes();
    //获取httpSession;
    HttpSession httpSession = (HttpSession) map.get(WEBSOCKET_HTTPSESSION_KEY);
    //获取httpSession中存储对象所使用的key
    String key = (String) map.get(HTTP_SESSION_KEY_VALUE);
    //从httpSession中拿到实际的存储对象。
    Identity identity = (Identity) httpSession.getAttribute(key);
    return identity;
  }
}
