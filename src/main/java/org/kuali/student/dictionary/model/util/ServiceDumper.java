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

}
