package com.benjamin.websocket.handshake;

import com.benjamin.websocket.Constant;
import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.exception.NoSessionFoundException;
import com.benjamin.websocket.handshake.sessionfacotory.IdentityFactory;
import com.benjamin.websocket.handshake.sessionfacotory.impl.MemcachedIdentityFactory;
import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by benjamin on 11/19/14.
 * 因为我们是分布式部署,所以全局的Session需要共享，
 * 并不能通过request.getSession这种方式来获取Session,我们默认采用
 * cookie+外部nosql存储的方式来保存登录状态
 */
public class HandshakeHandlerAdapter implements org.springframework.web.socket.server.HandshakeHandler, Constant{

  private static Logger logger = Logger.getLogger(HandshakeHandlerAdapter.class);

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

  @Override
  public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
    if(request.getHeaders().containsKey("Cookie")){
      throw new NoSessionFoundException("please login first, no cookie or session found");
    };
    String cookies = request.getHeaders().get("Cookie").get(0);
    //TODO found cookie from request.
    String value = "";
    String name = null;
    try {
       name = URLDecoder.decode(value, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    if(logger.isDebugEnabled()){
      logger.debug("found cookie####"+name);
    }

    Identity identity = identityFactory.getIdentityFromGlobalSession(name);
    if( identity != null){
      attributes.put(HANDSHAKE_IDENTITY_KEY, identity);
    }
    return true;
  }
}
