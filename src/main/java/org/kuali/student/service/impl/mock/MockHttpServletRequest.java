/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.impl.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nwright
 */
public class MockHttpServletRequest implements HttpServletRequest
{

 @Override
 public Object getAttribute (String name)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Enumeration getAttributeNames ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getCharacterEncoding ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public int getContentLength ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getContentType ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ServletInputStream getInputStream ()
  throws IOException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }


 @Override
 public Locale getLocale ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Enumeration getLocales ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getParameter (String name)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Map getParameterMap ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Enumeration getParameterNames ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String[] getParameterValues (String name)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getProtocol ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public BufferedReader getReader ()
  throws IOException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getRealPath (String path)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getRemoteAddr ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getRemoteHost ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public RequestDispatcher getRequestDispatcher (String path)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getScheme ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getServerName ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public int getServerPort ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isSecure ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public void removeAttribute (String name)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public void setAttribute (String name, Object o)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public void setCharacterEncoding (String env)
  throws UnsupportedEncodingException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }


 @Override
 public String getAuthType ()
 {
  return BASIC_AUTH;
 }

 @Override
 public String getContextPath ()
 {
  return "/";
 }

 @Override
 public Cookie[] getCookies ()
 {
  return new Cookie[0];
 }

 @Override
 public long getDateHeader (String name)
 {
  return System.currentTimeMillis ();
 }

 @Override
 public String getHeader (String name)
 {
 return null;
 }

 @Override
 public Enumeration getHeaderNames ()
 {
  return new Vector (0).elements ();
 }

 @Override
 public Enumeration getHeaders (String name)
 {
   return new Vector (0).elements ();
 }

 @Override
 public int getIntHeader (String name)
 {
   return 0;
 }

 @Override
 public String getMethod ()
 {
  return "POST";
 }

 @Override
 public String getPathInfo ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getPathTranslated ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getQueryString ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getRemoteUser ()
 {
  return "Mock-Remote-User";
 }

 @Override
 public String getRequestURI ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StringBuffer getRequestURL ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getRequestedSessionId ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public String getServletPath ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public HttpSession getSession (boolean create)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public HttpSession getSession ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Principal getUserPrincipal ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isRequestedSessionIdFromCookie ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isRequestedSessionIdFromURL ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isRequestedSessionIdFromUrl ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isRequestedSessionIdValid ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean isUserInRole (String role)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

}
