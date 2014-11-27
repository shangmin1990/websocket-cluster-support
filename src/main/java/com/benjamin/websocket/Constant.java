package com.benjamin.websocket;

/**
 * Created by benjamin on 11/3/14.
 */
public interface Constant {
  String WEBSOCKET_SESSIONS = "websocket_sessions";
  String SCHEMA = "http://";
  String INTERNAL_SERVLET_PATH = "internalRequest";
  String URL_DELIMITER = "/";
  String INTERNAL_REQUEST_PARAM_MAC = "mac";
  String INTERNAL_REQUEST_PARAM_MESSAGE = "message";
  String HOST_STR = "host";
  String PORT_STR = "port";
  String MEMCACHE_HOST = "memcached.host";
  String MEMCACHE_PORT = "memcached.port";
  int MEMCACHE_PORT_DEFAULT = 11211;
  String MEMCACHE_HOST_DEFAULT = "localhost";
  //WebsocketSession握手中传递的值
  String WEBSOCKET_HTTPSESSION_KEY = "websocket_httpsession_key";
  //httpSession中存储Identity对象使用的键名
  String HTTP_SESSION_KEY_VALUE = "httpsession_key_value";

  String HANDSHAKE_IDENTITY_KEY = "handshake_identity_key";

}
