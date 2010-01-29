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
   messageStructure.getXmlObject () + "." +
   messageStructure.getShortName () + " - " + messageStructure.getName () +
   " - " + messageStructure.getType ()
   + " http:XXX" + messageStructure.getUrl () + " - " +
   messageStructure.getDescription ());
 }

}
