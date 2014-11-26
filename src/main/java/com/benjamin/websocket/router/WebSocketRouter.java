package com.benjamin.websocket.router;

import com.benjamin.websocket.entity.Identity;
import net.sf.json.JSONObject;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Collection;

/**
 * Created by benjamin on 11/18/14.
 */
public interface WebSocketRouter {
  /**
   * 在集群中分发此请求
   * @param identify
   * @param webSocketMessage
   * @return
   */
  public JSONObject dispatcherMessage(Identity identify, WebSocketMessage webSocketMessage);

  /**
   * 在集群中分发此请求
   * @param identify
   * @param webSocketMessage
   * @return
   */
  public JSONObject dispatcherMessage(String identify, String webSocketMessage);

  /**
   * 获取集群中各主机的IP和端口
   * @return
   */
  public Collection<String> getCluster();
}
