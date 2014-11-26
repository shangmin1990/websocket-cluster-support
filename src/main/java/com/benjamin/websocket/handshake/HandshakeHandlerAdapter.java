package com.benjamin.websocket.handshake;

import com.benjamin.websocket.Constant;
import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.exception.NoSessionFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by benjamin on 11/19/14.
 */
public class HandshakeHandlerAdapter implements org.springframework.web.socket.server.HandshakeHandler, Constant{

  private static Logger logger = Logger.getLogger(HandshakeHandlerAdapter.class);

  private String sessionKey;

  @Override
  public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpSession httpSession = ((HttpServletRequest) request).getSession(false);
    if(httpSession == null){
      throw new NoSessionFoundException("No session found");
    }
    Object sessionValue = httpSession.getAttribute(sessionKey);
    if( sessionValue != null){
      if(!(sessionValue instanceof Identity)){
        logger.error("this object must be a type of Identity");
        return false;
      }else{
        attributes.put(WEBSOCKET_HTTPSESSION_KEY, httpSession);
        attributes.put(HTTP_SESSION_KEY_VALUE, sessionValue);
      }
    }else{
      throw new NoSessionFoundException("No value found for key : "+ sessionKey);
    }
    return true;
  }
}
