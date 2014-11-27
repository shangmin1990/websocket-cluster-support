package com.benjamin.websocket.exception;

import org.springframework.web.socket.server.HandshakeFailureException;

/**
 * Created by benjamin on 11/27/14.
 */
public class NoCookieFoundException extends HandshakeFailureException {
  public NoCookieFoundException(String s) {
    super(s);
  }
  public NoCookieFoundException( String message, Throwable throwable){
    super(message, throwable);
  }
}
