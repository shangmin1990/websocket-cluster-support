package com.benjamin.websocket.handler;

import com.benjamin.websocket.Constant;
import com.benjamin.websocket.sessionprovider.WebSocketSessionProvider;
import com.benjamin.websocket.sessionprovider.factory.WebSocketSessionProviderFactory;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by benjamin on 11/26/14.
 */
public class ClusterWebSocketHandlerAdapter implements WebSocketHandler, Constant {

  private static final Logger logger = Logger.getLogger(ClusterWebSocketHandlerAdapter.class);

//  @Resource(name = "memcachedWebSocketSessionPropertyHolderProvider")
  protected WebSocketSessionProvider webSocketSessionPropertyHolderProvider;

//  @Resource(name = "localWebSocketSessionProvider")
  protected WebSocketSessionProvider localWebSocketSessionProvider;

  protected WebSocketSessionProviderFactory webSocketSessionProviderFactory;

  public WebSocketSessionProviderFactory getWebSocketSessionProviderFactory() {
    return webSocketSessionProviderFactory;
  }

  public void setWebSocketSessionProviderFactory(WebSocketSessionProviderFactory webSocketSessionProviderFactory) {
    this.webSocketSessionProviderFactory = webSocketSessionProviderFactory;
  }

  protected  Map<String, String> resolveHostAndPort(WebSocketSession webSocketSession) {
    Map<String ,String> map = new HashMap<>();
    HttpHeaders httpHeaders = webSocketSession.getHandshakeHeaders();
    String hostAndPort = httpHeaders.get(HOST_STR).get(0);
    if(logger.isDebugEnabled()){
      logger.debug("此链接的请求头host的值为++++"+hostAndPort);
    }
    try {
      String realIp = getRealIp();
      if (logger.isDebugEnabled()){
        logger.debug("本机的真实外网IP为++++"+realIp);
      }
      map.put(HOST_STR,realIp);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    String[] hostPortArray = hostAndPort.split(":");
    //包含port信息
    if(hostPortArray.length>1){
      map.put(PORT_STR, hostPortArray[1]);
    }else{
      map.put(PORT_STR, webSocketSession.getLocalAddress().getPort()+"");
    }
    return map;
  }

  protected String getRealIp() throws SocketException {
    String localip = null;// 本地IP，如果没有配置外网IP则返回它
    String netip = null;// 外网IP

    Enumeration<NetworkInterface> netInterfaces =
        NetworkInterface.getNetworkInterfaces();
    InetAddress ip = null;
    boolean find = false;// 是否找到外网IP
    while (netInterfaces.hasMoreElements() && !find) {
      NetworkInterface ni = netInterfaces.nextElement();
      Enumeration<InetAddress> address = ni.getInetAddresses();
      while (address.hasMoreElements()) {
        ip = address.nextElement();
        if (!ip.isSiteLocalAddress()
            && !ip.isLoopbackAddress()
            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
          netip = ip.getHostAddress();
          find = true;
          break;
        } else if (ip.isSiteLocalAddress()
            && !ip.isLoopbackAddress()
            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
          localip = ip.getHostAddress();
        }
      }
    }

    if (netip != null && !"".equals(netip)) {
      return netip;
    } else {
      return localip;
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    webSocketSessionPropertyHolderProvider = webSocketSessionProviderFactory.getWebSocketSessionProvider(true);
    localWebSocketSessionProvider = webSocketSessionProviderFactory.getWebSocketSessionProvider(false);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}
