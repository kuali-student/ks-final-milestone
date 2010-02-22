/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import java.io.PrintStream;
import org.kuali.student.dictionary.model.XmlType;

/**
 *
 * @author nwright
 */
public class XmlTypeDumper
{

 private XmlType xmlType;
 private PrintStream out;

 public XmlTypeDumper (XmlType xmlType,
                       PrintStream out)
 {
  this.xmlType = xmlType;
  this.out = out;
 }

 public void dump ()
 {
  out.println (xmlType.getName ()
   + " - " + xmlType.getPrimitive ()
   + " - " + xmlType.getDesc ()
   + " - Http://XXX" + xmlType.getUrl ());
 }

 public void writeTabbedHeader ()
 {
  out.print ("name");
  out.print ("\t");
  out.print ("desc");
  out.print ("\t");
  out.print ("primitive");
  out.print ("\t");
  out.print ("hasOwnType");
  out.print ("\t");
  out.print ("hasOwnState");
  out.print ("\t");
  out.print ("hasOwnCreateUpdate");
  out.print ("\t");
  out.print ("Service");
  out.print ("\t");
  out.print ("examples");
  out.print ("\t");
  out.print ("javaPackage");
  out.print ("\t");
  out.print ("comments");

  out.println ("");
 }

 public void writeTabbedData ()
 {
  out.print (xmlType.getName ());
  out.print ("\t");
  out.print (xmlType.getDesc ());
  out.print ("\t");
  out.print (xmlType.getPrimitive ());
  out.print ("\t");
  out.print (xmlType.hasOwnType ());
  out.print ("\t");
  out.print (xmlType.hasOwnState ());
  out.print ("\t");
  out.print (xmlType.hasOwnCreateUpdate ());
  out.print ("\t");
  out.print (xmlType.getService ());
  out.print ("\t");
  out.print (xmlType.getExamples ());
  out.print ("\t");
  out.print (xmlType.getJavaPackage ());
  out.print ("\t");
  out.print (xmlType.getComments ());
  out.println ("");
 }

}
