package com.benjamin.websocket.handshake.sessionfacotory;

import com.benjamin.websocket.entity.Identity;

/**
 * Created by benjamin on 11/27/14.
 * 因为实现session共享的所样性,
 * 此模式采用的cookie+全局存储的方式
 * 我们采用的Memcache+Tomcat实现的session共享
 * 可以实现此接口，并注入到HandshakeHandlerAdapter
 */
public interface IdentityFactory {
  /**
   * 获取globalHttpSession中存储的对象
   * @param name 为浏览器中存储的cookie的值
   *             用来从"GlobalHttpSession"寻找对象的键
   * @return
   */
  Identity getIdentityFromGlobalSession(String name);
}
