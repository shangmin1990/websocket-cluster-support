package com.benjamin.websocket.handler;


import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.entity.StringIdentity;
import com.benjamin.websocket.sessionprovider.WebSocketSessionPropertyHolder;
import org.apache.log4j.Logger;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by benjamin on 9/25/14.
 * 通过发送消息的方式将client与websocketSession 建立链接关系
 */
public class SendIdentityClusterWebSocketHandler extends ClusterWebSocketHandlerAdapter {

  private static Logger logger = Logger.getLogger(SendIdentityClusterWebSocketHandler.class.getName());

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    String messageBody = ((TextMessage) message).getPayload();
    if(messageBody.indexOf("DISCONNECT") >= 0){
      final String mac = messageBody.split("\n")[1];
      Identity identity = new StringIdentity(mac);
      localWebSocketSessionProvider.deleteSession(identity);
      webSocketSessionPropertyHolderProvider.deleteSession(identity);
    }else if (messageBody.indexOf("CONNECT") >= 0){
      final String mac = messageBody.split("\n")[1];
      Identity<String> identity = new StringIdentity(mac);
      if(logger.isDebugEnabled()){
        Identity<String> identity1 = new StringIdentity(mac);
        Identity<String> identity2 = new StringIdentity(mac);
        logger.debug("Identity1 的hashCode#########" +identity1.hashCode());
        logger.debug("Identity2 的hashCode#########" +identity2.hashCode());
        logger.debug("Identity1 equals identity2########"+identity1.equals(identity2));
        String hostName = session.getLocalAddress().getHostName();
        int port = session.getLocalAddress().getPort();
        logger.debug(hostName);
        logger.debug(port);
        logger.debug("此链接将信息发送到了服务器:"+hostName+" : "+port + "信息为:"+mac);
      }
      WebSocketSessionPropertyHolder webSocketSessionPropertyHolder = new WebSocketSessionPropertyHolder();
      Map<String, String> hostAndPort = resolveHostAndPort(session);
      webSocketSessionPropertyHolder.setHost(hostAndPort.get(HOST_STR));
      webSocketSessionPropertyHolder.setPort(Integer.valueOf(hostAndPort.get(PORT_STR)));
      webSocketSessionPropertyHolder.setIdentity(identity);
      //将propertyHolder保存memcache中共享
      webSocketSessionPropertyHolderProvider.saveSession(identity, webSocketSessionPropertyHolder);
      //将websocketSession对象保存在本机供查找
      localWebSocketSessionProvider.saveSession(identity, session);
      if(logger.isDebugEnabled()){
        int size  = localWebSocketSessionProvider.getAllSession().size();
        logger.debug("存储之后的本机websocketSession数量为~~~~~~~~~~~~"+size);
        Set<Identity> identitySet = localWebSocketSessionProvider.getAllSession().keySet();
        Identity abc = null;
        for(Identity identity123: identitySet){
          if(abc != null && identity123 != null){
            logger.debug("equals###########"+identity123.equals(abc));
          }
          logger.debug("HashCode###########"+identity123.hashCode());
          abc = identity123;
        }
      }
    }
  }

}
