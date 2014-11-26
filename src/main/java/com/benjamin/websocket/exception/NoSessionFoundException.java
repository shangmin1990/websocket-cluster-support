package com.benjamin.websocket.exception;

import org.springframework.web.socket.server.HandshakeFailureException;

/**
 * Created by benjamin on 11/26/14.
 */
public class NoSessionFoundException extends HandshakeFailureException {

  public NoSessionFoundException(String s) {
    super(s);
  }
  public NoSessionFoundException( String message, Throwable throwable){
    super(message, throwable);
  }
}
