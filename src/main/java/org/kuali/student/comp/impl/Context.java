/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSContext;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author nwright
 */
public class Context implements KSContext
{

 public Context ()
 {
  super ();
 }

 private Map<Class, Object> registration = new Hashtable ();

 @Override
 public void register (Class infc, Object obj)
 {
  if ( ! (infc.isAssignableFrom (obj.getClass ())))
  {
   throw new IllegalArgumentException ();
  }
  registration.put (infc, obj);
 }

 @Override
 public Object getInstance (Class infc)
 {
  return registration.get (infc);
 }

}
