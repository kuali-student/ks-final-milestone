/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.model.util;

import java.io.PrintStream;
import org.kuali.student.dictionary.model.MessageStructure;

/**
 *
 * @author nwright
 */
public class MessageStructureDumper
{

 private MessageStructure messageStructure;
 private PrintStream out;

 public MessageStructureDumper (MessageStructure messageStructure,
                                PrintStream out)
 {
  this.messageStructure = messageStructure;
  this.out = out;
 }

 public void dump ()
 {
  out.println (
   messageStructure.getXmlObject () + "." + messageStructure.getShortName ()
   + " - " + messageStructure.getName () + " - " + messageStructure.getType ()
   + " http:XXX" + messageStructure.getUrl () + " - " + messageStructure.
   getDescription ());
 }

 public void writeTabbedHeader ()
 {
  out.print ("id");
  out.print ("\t");
  out.print ("Action");
  out.print ("\t");
  out.print ("xmlObject");
  out.print ("\t");
  out.print ("ShortName");
  out.print ("\t");
  out.print ("Name");
  out.print ("\t");
  out.print ("Type");
  out.print ("\t");
  out.print ("Description");
  out.print ("\t");
  out.print ("Required");
  out.print ("\t");
  out.print ("Cardinality");
  out.print ("\t");
  out.print ("XMLAttribute");
  out.print ("\t");
  out.print ("Status");
  out.print ("\t");
  out.print ("Feedback");
  out.println ("");
 }

 public void writeTabbedData ()
 {
  out.print (messageStructure.getId ());
  out.print ("\t");
  out.print ("");
  out.print ("\t");
  out.print (messageStructure.getXmlObject ());
  out.print ("\t");
  out.print (messageStructure.getShortName ());
  out.print ("\t");
  out.print (messageStructure.getName ());
  out.print ("\t");
  out.print (messageStructure.getType ());
  out.print ("\t");
  out.print (messageStructure.getDescription ());
  out.print ("\t");
  out.print (messageStructure.getRequired ());
  out.print ("\t");
  out.print (messageStructure.getCardinality ());
  out.print ("\t");
  out.print (messageStructure.getXmlAttribute ());
  out.print ("\t");
  out.print (messageStructure.getStatus ());
  out.print ("\t");
  out.print (messageStructure.getFeedback ());
  out.println ("");
 }

}
