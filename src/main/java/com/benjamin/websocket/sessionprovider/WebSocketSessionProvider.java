package com.benjamin.websocket.sessionprovider;

import com.benjamin.websocket.entity.Identity;

import java.util.Map;

/**
 * Created by benjamin on 9/28/14.
 * MAC地址与WebSocket的对应关系
 * T 如果是本服务器上的数据,则为websocketSession对象
 *   如果是共享数据,则为websocketSessionPropertyHolder对象
 *   解决websocketSession无法序列化的问题
 */
public interface WebSocketSessionProvider<T> {

  /**
   * 是否具有该MAC地址所对应的设备的Websocket链接
   * @param key
   * @return
   */
  boolean checkSession(Identity key);

  /**
   * 将MAC地址与设备的(WebsocketSessionPropertyHolder or websocketSession)链接建立对应关系
   * @param key
   * @param object
   */
  void saveSession(Identity key, T object);

  /**
   * 删除一个设备的链接
   * @param key
   */
  void deleteSession(Identity key);

  /**
   * 通过值获取key
   * @param object
   * @return
   */
  Identity getKeyByValue(T object);

  /**
   * 获取所有的链接信息
   */
  Map<Identity, T> getAllSession();

  /**
   * @param key
   * @return
   */
  T getSession(Identity key);

  /**
   * 获取链接数量
   * @return
   */
  int size();
}
