package com.benjamin.websocket.handshake.sessionfacotory.impl;

import com.benjamin.websocket.entity.Identity;
import com.benjamin.websocket.handshake.sessionfacotory.IdentityFactory;
import com.benjamin.websocket.util.MemcachedUtil;
import net.spy.memcached.MemcachedClient;

/**
 * Created by benjamin on 11/27/14.
 */
public class MemcachedIdentityFactory implements IdentityFactory {

  private MemcachedClient memcachedClient = MemcachedUtil.getClientInstance();
  @Override
  public Identity getIdentityFromGlobalSession(String name) {
    return (Identity) memcachedClient.get(name);
  }
}
