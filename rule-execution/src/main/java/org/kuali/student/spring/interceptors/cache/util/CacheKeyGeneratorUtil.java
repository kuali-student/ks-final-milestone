package org.kuali.student.spring.interceptors.cache.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ObjectUtils;
import org.springmodules.cache.key.HashCodeCacheKeyGenerator;

public class CacheKeyGeneratorUtil
{
  private final static Map idClassMap = new HashMap();
  
  //Object.class
  private static final Class[] DEFAULT_CLASS = 
    new Class[]{ 
      String[].class, 
      int[].class, 
      Map.class, 
      Collection.class, 
      String.class, 
      Number.class, 
      boolean.class, Boolean.class,
      byte.class, Byte.class,
      char.class, Character.class,
      double.class, Double.class,
      float.class, Float.class,
      int.class, Integer.class,
      long.class, Long.class,
      short.class, Short.class };
  
  private static final String[] classMethod = new String[] { "toString" };

  /**
   * Creates a cache key for a method.
   * 
   * @param methodInvocation Method to create a key for
   * @return Serializable cache key
   * @exception Exception if generating cache key fails
   */
  public final static Serializable generateMethodKey( MethodInvocation methodInvocation )
      throws Exception
  {
      if ( idClassMap == null || idClassMap.size() == 0 )
      {
        for ( int i = 0; i < DEFAULT_CLASS.length; i++ )
        {
          idClassMap.put( DEFAULT_CLASS[i], classMethod );
        }
      }
      
      Object target = methodInvocation.getThis();
      Object[] arguments = methodInvocation.getArguments();
      Class[] argumentClasses = methodInvocation.getMethod().getParameterTypes();
      
      StringBuffer result = new StringBuffer();

      // Add the identity hash code for the object
      result.append( ObjectUtils.getIdentityHexString( target ) );

      if ( arguments != null )
      {
          result.append( "::" );
          for ( int i = 0; i < arguments.length; i++ )
          {
              // A separator between the keys for each argument
              if ( i > 0 )
              {
                  result.append( "-" );
              }

              // Specify the type so method overloading is supported.
              // Otherwise two methods like getObject(Long) and
              // getObject(String) would have the same cache key.
              result.append( argumentClasses[i].getName() );
              result.append( "#" );
              result.append( generateKey( arguments[i] ) );
          }
      }

      return result.toString();
  }

  /**
   * Creates a key from the method parameter <code>object</code>.
   * 
   * @param object Object to create key from
   * @return A key
   * @throws Exception if generating key fails
   */
  private final static String generateKey( Object object ) throws Exception
  {
      if ( object == null )
          return "";

      StringBuffer result = new StringBuffer();

      if ( idClassMap != null )
      {
          boolean keyFound = false;
          Iterator cacheIterator = idClassMap.entrySet().iterator();
          while ( cacheIterator.hasNext() && !keyFound )
          {
              Map.Entry entry = ( Map.Entry ) cacheIterator.next();
              Class clazz = ( Class ) entry.getKey();

              if ( clazz.isAssignableFrom( object.getClass() ) )
              {
                  String[] method = ( String[] ) entry.getValue();
                  Object key = null;
                  for( int i=0; i<method.length; i++ )
                  {
                      try
                      {
                          Method methodObj = object.getClass().getMethod( method[i], null );
                          key = methodObj.invoke( object, null );
                          keyFound = true;
                          break;
                      }
                      catch( NoSuchMethodException e )
                      {
                        keyFound = false;
                      }
                      catch ( Exception e )
                      {
                          throw new Exception( "Could not invoke method '"
                                  + method + "' " + "in class '"
                                  + clazz.getName() + "' without parameters", e );
                      }
                  }
                  result.append( key );
              }
          }
          if ( !keyFound ) 
          {
            result.append( object.toString() );
          }
      }

      return result.toString();
  }

  /**
   * Creates a hash code cache key for a method.
   * 
   * @param methodInvocation Method to create a key for
   * @return Serializable cache key
   */
  public final static Serializable generateHashCodeMethodKey( MethodInvocation methodInvocation )
  {
      HashCodeCacheKeyGenerator keyGenerator = new HashCodeCacheKeyGenerator();
      keyGenerator.setGenerateArgumentHashCode( true );
      return keyGenerator.generateKey( methodInvocation );
  }
}
