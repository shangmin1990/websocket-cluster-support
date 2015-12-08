package com.benjamin.websocket.router;

import com.benjamin.websocket.Constant;
import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.entity.StringIdentity;
import com.benjamin.websocket.sessionprovider.WebSocketSessionPropertyHolder;
import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.factory.WebSocketSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.factory.impl.DefaultWebSocketSessionProviderFactory;
import com.benjamin.websocket.sessionprovider.impl.MemcacheSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.impl.SimpWebSocketSessionProvider;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by benjamin on 11/19/14.
 */
public abstract class AbstractWebSocketRouter implements WebSocketRouter, Constant {

  private static final Logger logger = Logger.getLogger(AbstractWebSocketRouter.class.getName());

  protected List<String> cluster;

  protected ServletContext servletContext;

//  /**
//   * static的属性无法注入
//   * 保证此实例scope为singleton
//   * 在 AbstractWebsocketRouter的三个子类中达到数据共享的目的
//   */
//  @Resource(name = "localWebSocketSessionProvider")
  protected WebSocketSessionProvider simpWebSocketSessionProvider;

//  @Resource(name = "memcachedWebSocketSessionPropertyHolderProvider")
  protected WebSocketSessionProvider memcacheSocketSessionProvider;

  protected WebSocketSessionProviderFactory webSocketSessionProviderFactory;

  public AbstractWebSocketRouter(){
    this.webSocketSessionProviderFactory = new DefaultWebSocketSessionProviderFactory();
  }

  public void setCluster(List cluster) {
    this.cluster = cluster;
  }

  @Override
  public Collection<String> getCluster() {
    return cluster;
  }

  public void setServletContext(ServletContext servletContext) {
    logger.info("servletContext init success:-----------"+ servletContext.toString());
    this.servletContext = servletContext;
  }

  public ServletContext getServletContext(){
    return this.servletContext;
  }

  @Override
  public JSONObject dispatcherMessage(Identity identify, WebSocketMessage webSocketMessage){
    providerInit();
    return dispatcher(identify, webSocketMessage);
  }

  private void providerInit(){
    this.simpWebSocketSessionProvider = webSocketSessionProviderFactory.getWebSocketSessionProvider(false);
    this.memcacheSocketSessionProvider = webSocketSessionProviderFactory.getWebSocketSessionProvider(true);
  };

  @Override
  public JSONObject dispatcherMessage(String identify, String webSocketMessage){
    StringIdentity stringIdentity = new StringIdentity(identify);
    TextMessage textMessage = new TextMessage(webSocketMessage);
    return dispatcherMessage(stringIdentity, textMessage);
  };

  protected abstract JSONObject dispatcher(Identity identify, WebSocketMessage webSocketMessage);

}
