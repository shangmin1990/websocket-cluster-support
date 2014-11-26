package com.benjamin.websocket.router.impl;


import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.router.AbstractWebSocketRouter;
import com.benjamin.websocket.sessionprovider.WebSocketSessionPropertyHolder;
import com.benjamin.websocket.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by benjamin on 11/3/14.
 */
public class HttpWebSocketRouter extends AbstractWebSocketRouter {

  private static final Logger logger = Logger.getLogger(HttpWebSocketRouter.class.getName());
  /**
   * 推送消息
   * 当在本机内找到链接,直接使用此链接推送消息
   * 如果没有找到,从全局中查找此链接所在服务器,如果找到,请求转发给对应的服务器
   * 如果从全局中也没有找到,则没有此台设备的websocket链接
   * @param identify
   * @param webSocketMessage
   * @return
   */
  @Override
  public JSONObject dispatcher(Identity identify, WebSocketMessage webSocketMessage) {
    JSONObject jsonObject = new JSONObject();
    WebSocketSession webSocketSession = (WebSocketSession) simpWebSocketSessionProvider.getSession(identify);
    //从本机获取websocket链接
    //如果不为空则本机处理
    if(webSocketSession != null){
      if(webSocketSession.isOpen()){
        try {
          webSocketSession.sendMessage(webSocketMessage);
          jsonObject.put("status", true);
          return jsonObject;
        } catch (IOException e) {
          e.printStackTrace();
          jsonObject.put("status",false);
          jsonObject.put("reason", e.getClass().getName()+"\r\n"+e.getMessage());
          return jsonObject;
        }
      }else {
        jsonObject.put("status", false);
        jsonObject.put("reason", "websocket connection has closed");
      }
    }else{
      //转发到存在socket链接的服务器;
      WebSocketSessionPropertyHolder propertyHolder = (WebSocketSessionPropertyHolder) memcacheSocketSessionProvider.getSession(identify);
      //如果不存在propertyHolder则没有链接
      if(propertyHolder != null){
        String hostAndPort = propertyHolder.getHost()+":"+propertyHolder.getPort();
        JSONObject result =
            HttpClientUtil.dispatcherRequestInternal(
                hostAndPort, servletContext.getContextPath(), INTERNAL_SERVLET_PATH, "GET",
                null, (String) identify.getIdentify(), ((TextMessage) webSocketMessage).getPayload()
            );
        return result;
      }else {
        jsonObject.put("status", false);
        jsonObject.put("reason", "Can't not found a connection for device "+identify.getIdentify());
      }
    }
    jsonObject.put("status", false);
    return jsonObject;
  }
}
