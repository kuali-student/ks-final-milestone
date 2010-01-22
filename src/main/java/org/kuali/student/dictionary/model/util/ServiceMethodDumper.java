/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import java.io.PrintStream;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceMethodParameter;

/**
 *
 * @author nwright
 */
public class ServiceMethodDumper
{

 private ServiceMethod method;
 private PrintStream out;

 public ServiceMethodDumper (ServiceMethod method, PrintStream out)
 {
  this.method = method;
  this.out = out;
 }

 public void dump ()
 {
  out.println (method.getName () + " - " + method.getDescription ());
  for (ServiceMethodParameter param : method.getParameters ())
  {
   out.println (" Param: " + param.getName () + " (" + param.getType () + ") " +
    param.getDescription ());
  }
  for (ServiceMethodError param : method.getErrors ())
  {
   out.println (" Error: " + param.getType () + " - " +
    param.getDescription ());
  }
  out.println (" return: " + method.getReturnValue ().getType () + " - " + method.getReturnValue ().
   getDescription ());
 }

}
