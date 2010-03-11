/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import java.io.PrintStream;
import org.kuali.student.dictionary.model.Service;

/**
 *
 * @author nwright
 */
public class ServiceDumper
{

 private Service service;
 private PrintStream out;

 public ServiceDumper (Service service, PrintStream out)
 {
  this.service = service;
  this.out = out;
 }

 public void dump ()
 {
  out.println (service.getKey () + "." + service.getName () + "(" + service.
   getVersion () + ")");
 }

 public void writeTabbedHeader ()
 {
  out.print ("Key");
  out.print ("\t");
  out.print ("Name");
  out.print ("\t");
  out.print ("Version");
  out.print ("\t");
  out.print ("url");
  out.print ("\t");
  out.print ("ImplProject");
  out.print ("\t");
  out.print ("status");
  out.print ("\t");
  out.print ("comments");
  out.println ("");
 }

 public void writeTabbedData ()
 {
  out.print (service.getKey ());
  out.print ("\t");
  out.print (service.getName ());
  out.print ("\t");
  out.print (service.getVersion ());
  out.print ("\t");
  out.print (service.getUrl ());
  out.print ("\t");
  out.print (service.getImplProject ());
  out.print ("\t");
  out.print (service.getStatus ());
  out.print ("\t");
  out.print (service.getComments ());
  out.println ("");
 }

}
