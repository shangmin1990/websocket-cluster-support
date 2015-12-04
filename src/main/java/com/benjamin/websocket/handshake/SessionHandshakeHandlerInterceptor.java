package com.benjamin.websocket.handshake;

import com.benjamin.websocket.Constant;
import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.exception.NoCookieFoundException;
import com.benjamin.websocket.exception.NoSessionFoundException;
import com.benjamin.websocket.handshake.sessionfacotory.IdentityFactory;
import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by benjamin on 11/19/14.
 * 因为我们是分布式部署,所以全局的Session需要共享，
 * 并不能通过request.getSession这种方式来获取Session,我们默认采用
 * cookie+外部nosql存储的方式来保存登录状态
 * 当我们需要自动的从session中建立联系关系的时候使用此handshakeInterceptor
 */
public class SessionHandshakeHandlerInterceptor implements HandshakeInterceptor, Constant{

  private static Logger logger = Logger.getLogger(SessionHandshakeHandlerInterceptor.class);

  private IdentityFactory identityFactory;

  private String sessionKey;

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public IdentityFactory getHttpSessionFactory() {
    return identityFactory;
  }

  public void setHttpSessionFactory(IdentityFactory httpSessionFactory) {
    this.identityFactory = httpSessionFactory;
  }

  private String resolveCookie(String sessionKey, String cookies) throws NoCookieFoundException{
    if(cookies == null || cookies.length() == 0){
      throw new NoCookieFoundException("The request has no cookie information found");
    }
    int index = cookies.indexOf(';');
    if(index > 0){
      String[] cookies_array = cookies.split(";");
      for(String cookie: cookies_array){
        String cookieValue = foundCookie(sessionKey, cookie);
        if(cookieValue != null){
          return cookieValue;
        }
      }
    }
    return foundCookie(sessionKey, cookies);
  }

  private String foundCookie(String sessionKey, String cookie) {
    String[] cookie_key_value = cookie.split("=");
    if(cookie_key_value[0].trim().equals(sessionKey))
      return cookie_key_value[1];
    else
      return null;
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    if(!request.getHeaders().containsKey(HTTP_HEADER_COOKIE)){
      throw new NoSessionFoundException("please login first, no cookie or session found");
    };
    String cookies = request.getHeaders().get(HTTP_HEADER_COOKIE).get(0);
    //TODO found cookie from request.
    String value = resolveCookie(sessionKey, cookies);
    if(value == null){
      throw new NoCookieFoundException("No cookie found for key "+ sessionKey );
    }
    String name = null;
    try {
      name = URLDecoder.decode(value.trim(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    if(logger.isDebugEnabled()){
      logger.debug("found cookie####"+name);
    }

    Identity identity = identityFactory.getIdentityFromGlobalSession(name);
    if( identity != null){
      attributes.put(HANDSHAKE_IDENTITY_KEY, identity);
    } else {
      throw new NoSessionFoundException("no session found for name "+ name);
    }
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

  }
}
