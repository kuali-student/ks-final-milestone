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
   + " - " +  xmlType.getDesc ()
   + " - Http://XXX" + xmlType.getUrl ());
 }

}
