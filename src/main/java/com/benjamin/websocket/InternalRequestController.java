package com.benjamin.websocket;


import com.benjamin.websocket.entity.StringIdentity;
import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.factory.impl.DefaultLocalSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.factory.impl.DefaultWebSocketSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.impl.SimpWebSocketSessionProvider;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by benjamin on 11/4/14.
 */
@Controller
public class InternalRequestController {

  private static Logger logger = Logger.getLogger(InternalRequestController.class);

  @Resource(name = "defaultWebSocketSessionProviderFactory")
  private DefaultWebSocketSessionProviderFactory defaultWebSocketSessionProviderFactory;

  private WebSocketSessionProvider<WebSocketSession> simpWebSocketSessionProvider;

  @PostConstruct
  private void postConstruct(){
    logger.debug("POST construct ---- set simpleWebSocketSessionProvider ");
    simpWebSocketSessionProvider = defaultWebSocketSessionProviderFactory.getWebSocketSessionProvider(false);
  }

  @RequestMapping("/internalRequest")
  @ResponseBody
  public JSONObject handleInternalRequest(@RequestParam("mac") String identify, @RequestParam("message") String message){
    JSONObject result = new JSONObject();
    WebSocketSession webSocketSession = simpWebSocketSessionProvider.getSession(new StringIdentity(identify));
    Assert.notNull(webSocketSession);
    Assert.isTrue(webSocketSession.isOpen());
    if(webSocketSession !=null && webSocketSession.isOpen()){
      if(logger.isDebugEnabled()){
        logger.debug("处理请求的WebSocketSessionId为#######"+webSocketSession.getId());
        logger.debug(webSocketSession.isOpen());
      }
      try {
        webSocketSession.sendMessage(new TextMessage(message));
        result.put("status", true);
      } catch (IOException e) {
        e.printStackTrace();
        result.put("status", false);
        result.put("reason", e.getClass().getName()+ "\r\n"+ e.getMessage());
      }
    }else{
      result.put("status", false);
      result.put("reason", "can't find session or websocketSession has already closed");
    }
    return result;
  };
}
