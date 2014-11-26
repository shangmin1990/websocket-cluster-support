package com.benjamin.websocket.interceptor;

import com.benjamin.websocket.router.WebSocketRouter;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by benjamin on 11/3/14.
 * 确保请求是由tomcat集群中的任意一台发送的请求,而不是其他的client发来的请求
 * Http 与 webSocket握手拦截
 */
public class WebsocketInternalRequestInterceptor extends HandlerInterceptorAdapter {

  private static Logger logger = Logger.getLogger(WebsocketInternalRequestInterceptor.class);

  @Resource
  private WebSocketRouter webSocketRouter;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Collection<String> cluster = webSocketRouter.getCluster();
    //请求的ip
    String host = request.getRemoteHost();
//    int port = request.getLocalPort();
//    String remoteHostAndPort = host+":"+port;

//    if(logger.isDebugEnabled()){
//      logger.debug(remoteHostAndPort);
//    }

    for(String hostAndPort: cluster){
      String[] clusterMachineHostAndPort = hostAndPort.split(":");
      if(host.equals(clusterMachineHostAndPort[0])){
        return super.preHandle(request, response, handler);
      }
    }

    if(logger.isDebugEnabled()){
      logger.debug("拦截了tomcat 内部请求,请求源地址为"+host);
    }

    response.setStatus(401);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", false);
    jsonObject.put("reason", "you can't send message use this interface");
    PrintWriter out = response.getWriter();
    out.write(jsonObject.toString());
    out.flush();
    out.close();
    return false;
  }
}
