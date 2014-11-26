package com.benjamin.websocket.util;


import com.benjamin.websocket.Constant;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * Created by leiguorui on 10/11/14.
 */
public class MemcachedUtil {

  private static Logger logger = Logger.getLogger(MemcachedUtil.class);

  private static MemcachedClient client;

  private static Properties properties = new Properties();
  //This class is not a spring bean so it can't use
  //IOC
//  @Value("memcached.host")
  private static String host;
//  @Value("memcached.port")
  private static int port;
  static {
    try {
      InputStream inputStream = MemcachedUtil.class.getClassLoader().getResourceAsStream("/config/memcache.properties");
      if(inputStream == null){
        host = Constant.MEMCACHE_HOST_DEFAULT;
        port = Constant.MEMCACHE_PORT_DEFAULT;
      }else{
        properties.load(inputStream);
        host = properties.getProperty(Constant.MEMCACHE_HOST);
        try{
          port = Integer.parseInt(properties.getProperty(Constant.MEMCACHE_PORT));
        }catch (NumberFormatException e){
          port = Constant.MEMCACHE_PORT_DEFAULT;
        }
      }
      logger.info("Memcached host is " + host);
      logger.info("Memcached port is "+port);
      client = new MemcachedClient(new InetSocketAddress(host, port));
    } catch (IOException e) {
      System.out.println("MemcachedClient建立异常");
      e.printStackTrace();
    }
  }


  public static MemcachedClient getClientInstance(){
    return client;
  }

}
