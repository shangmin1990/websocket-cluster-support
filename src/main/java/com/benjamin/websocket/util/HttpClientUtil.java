package com.benjamin.websocket.util;

import com.benjamin.websocket.Constant;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by benjamin on 11/3/14.
 */
public class HttpClientUtil implements Constant {

  private static byte[] bytes = new byte[2048];

  private static Logger logger = Logger.getLogger(HttpClientUtil.class);

  public static JSONObject dispatcherRequest(String urlString, String method, Map<String, String> header){
    HttpURLConnection httpURLConnection = prepareURLConnection(urlString);
    if(method.toUpperCase().equals("GET")){
      return doGetRequest(httpURLConnection);
    }else{
      //doPostRequest();
      return null;
    }
  }

  private static JSONObject doGetRequest(HttpURLConnection httpURLConnection) {
    try {
      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.connect();
      if(logger.isDebugEnabled()){
        logger.debug("转发请求结果#######"+httpURLConnection.getResponseCode());
      }
      if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
        InputStream in = httpURLConnection.getInputStream();
        String jsonString = resolveStream(in);
        if(!"".equals(jsonString)){
          JSONObject result = JSONObject.fromObject(jsonString);
          return result;
        }
      }
    } catch (ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String resolveStream(InputStream in) {
    StringBuffer stringBuffer = new StringBuffer();
    int count;
    try {
      while ((count = in.read(bytes)) != -1){
        stringBuffer.append(new String(bytes, 0, count, Charset.defaultCharset()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

  private static HttpURLConnection prepareURLConnection(String urlString){
    try {
      URL url = new URL(urlString);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setConnectTimeout(3000);
      urlConnection.setDefaultUseCaches(false);
      urlConnection.setReadTimeout(5000);
      urlConnection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
      return urlConnection;
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static JSONObject dispatcherRequestInternal(String hostAndPort, String contextPath, String servletPath,  String method, Map<String, String> header, String mac, String message){
//    String urlString = com.krx.Constant.SCHEMA + hostAndPort ++ contextPath +"/"+servletPath+"?mac="+mac+"&message="+message;
    StringBuilder urlBuild = new StringBuilder(SCHEMA);
    urlBuild.append(hostAndPort);
    urlBuild.append(contextPath);
    urlBuild.append(URL_DELIMITER);
    urlBuild.append(servletPath);
    urlBuild.append("?");
    urlBuild.append(INTERNAL_REQUEST_PARAM_MAC);
    urlBuild.append("=");
    urlBuild.append(mac);
    urlBuild.append("&");
    urlBuild.append(INTERNAL_REQUEST_PARAM_MESSAGE);
    urlBuild.append("=");
    urlBuild.append(message);
    String urlString = urlBuild.toString();
    if(logger.isDebugEnabled()){
      logger.debug("转发请求:"+urlString);
    }
    HttpURLConnection httpURLConnection = prepareURLConnection(urlString);
    if(method.toUpperCase().equals("GET")){
      return doGetRequest(httpURLConnection);
    }else{
      //doPostRequest();
      return null;
    }
  }


}
