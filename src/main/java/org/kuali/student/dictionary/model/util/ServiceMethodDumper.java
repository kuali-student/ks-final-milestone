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
  out.println (method.getService () + "." + method.getName () + " - " + method.
   getDescription ());
  for (ServiceMethodParameter param : method.getParameters ())
  {
   out.println (" Param: " + param.getName () + " (" + param.getType () + ") " + param.
    getDescription () + " http://XXX" + param.getUrl ());
  }
  for (ServiceMethodError param : method.getErrors ())
  {
   out.println (" Error: " + param.getType () + " - " + param.getDescription ());
  }
  out.println (" return: " + method.getReturnValue ().getType () + " - " + method.
   getReturnValue ().
   getDescription () + " http://XXX" + method.getReturnValue ().getUrl ());
 }

 public void writeTabbedHeader ()
 {
  out.print ("Service");
  out.print ("\t");
  out.print ("Key");
  out.print ("\t");
  out.print ("ShortName");
  out.print ("\t");
  out.print ("LongName");
  out.print ("\t");
  out.print ("Description");
  out.print ("\t");
  out.print ("url");
  out.println ("");
 }

 public void writeTabbedData ()
 {
  out.print (method.getService ());
  out.print ("\t");
  out.print ("Method");
  out.print ("\t");
  out.print (method.getName ());
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print (method.getUrl ());
  out.println ();

  out.print (method.getService ());
  out.print ("\t");
  out.print ("Description");
  out.print ("\t");
  out.print (method.getDescription ());
  out.print ("\t");
  out.print ("");
  out.println ();
  if (method.getParameters ().size () == 0)
  {
   out.print (method.getService ());
   out.print ("\t");
   out.print ("Parameters");
   out.print ("\t");
   out.print ("None");
   out.print ("\t");
   out.print ("None");
   out.print ("\t");
   out.print ("No parameters");
   out.print ("\t");
   out.print ("");
   out.println ();
  }
  else
  {
   String parameters = "Parameters";
   for (ServiceMethodParameter param : method.getParameters ())
   {
    out.print (method.getService ());
    out.print ("\t");
    out.print (parameters);
    parameters = "";
    out.print ("\t");
    out.print (param.getType ());
    out.print ("\t");
    out.print (param.getName ());
    out.print ("\t");
    out.print (param.getDescription ());
    out.print ("\t");
    out.print (param.getUrl ());
    out.println ();
   }
  }

  out.print (method.getService ());
  out.print ("\t");
  out.print ("Return");
  out.print ("\t");
  out.print (method.getReturnValue ().getType ());
  out.print ("\t");
  out.print (method.getReturnValue ().getDescription ());
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print (method.getReturnValue ().getUrl ());
  out.println ();

  String errors = "Errors";
  for (ServiceMethodError error : method.getErrors ())
  {
   out.print (method.getService ());
   out.print ("\t");
   out.print (errors);
   errors = "";
   out.print ("\t");
   out.print (error.getType ());
   out.print ("\t");
   out.print (error.getDescription ());
   out.print ("\t");
   out.print ("");
   out.print ("\t");
   out.print ("");
   out.println ();
  }

  out.print (method.getService ());
  out.print ("\t");
  out.print ("Capabilities");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.println ();

  out.print (method.getService ());
  out.print ("\t");
  out.print ("Use Cases");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.println ();

    out.print (method.getService ());
  out.print ("\t");
  out.print ("Comments/Feedback");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print ("");
  out.println ();
 }

}
