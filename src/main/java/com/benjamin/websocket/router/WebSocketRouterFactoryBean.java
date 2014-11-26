package com.benjamin.websocket.router;

import com.benjamin.websocket.router.impl.HttpWebSocketRouter;
import com.benjamin.websocket.router.impl.TcpWebSocketRouter;
import com.benjamin.websocket.router.impl.UdpWebSocketRouter;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by benjamin on 11/19/14.
 */
public class WebSocketRouterFactoryBean implements FactoryBean<WebSocketRouter> {

  private static final String HTTP ="http";
  private static final String TCP = "tcp";
  private static final String UDP = "udp";

  private List<Collection> cluster;
  private String protocol = "HTTP";

  public List<Collection> getCluster() {
    return cluster;
  }

  public void setCluster(List<Collection> cluster) {
    this.cluster = cluster;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  @Override
  public WebSocketRouter getObject() throws Exception {
    return WebSocketRouterFactory.getInstance(protocol);
  }

  @Override
  public Class<?> getObjectType() {
    Type type=  this.getClass().getGenericInterfaces()[0];
    Type[] params = ((ParameterizedType) type).getActualTypeArguments();
    return (Class) params[0];
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  private static final class WebSocketRouterFactory {

    private static WebSocketRouter httpWebSocketRouter = new HttpWebSocketRouter();

    private static WebSocketRouter tcpWebSocketRouter = new TcpWebSocketRouter();

    private static WebSocketRouter udpWebSocketRouter = new UdpWebSocketRouter();

    public static WebSocketRouter getInstance(String type){
      if(HTTP.equals(type)){
        return httpWebSocketRouter;
      }else if(TCP.equals(type)){
        return tcpWebSocketRouter;
      }else if(UDP.equals(type)){
        return udpWebSocketRouter;
      }else{
        return httpWebSocketRouter;
      }
    }
  }
}
