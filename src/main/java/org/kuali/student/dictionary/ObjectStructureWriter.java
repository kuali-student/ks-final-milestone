/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author nwright
 */
public class ObjectStructureWriter extends XmlWriter
{

 private Spreadsheet spreadsheet;
 private SpreadsheetFinder finder;
 private XmlType xmlType;
 private List<Dictionary> dictionary;
 private boolean inline;
 private State mainState;

 /**
  * construct writer for a particular type
  * @param spreadsheet holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  */
 public ObjectStructureWriter (PrintStream out, int indent,
                               Spreadsheet spreadsheet, XmlType xmlType,
                               List<Dictionary> dictionary, boolean inline,
                               State mainState)
 {
  super (out, indent);
  this.spreadsheet = spreadsheet;
  this.finder = new SpreadsheetFinder (spreadsheet);
  this.xmlType = xmlType;
  this.dictionary = dictionary;
  this.inline = inline;
  this.mainState = mainState;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("");
  indentPrint ("<dict:objectStructure");
  writeAttribute ("key", xmlType.getName ());
  println (">");
  writeComment (xmlType.getDesc ());
  for (Type type : filterTypes ())
  {
   indentPrint ("<dict:type");
   writeAttribute ("key", type.getTypeKey ());
   indent (System.out, ' ');
   System.out.println ("Writing out TYPE: " + xmlType.getName () + "." + type.
    getTypeKey ());
   println (">");
   writeComment (type.getDesc ());
   writeComment (type.getAliases ());
   writeComment (type.getComments ());
   for (State state : filterStates ())
   {
    if ( ! inline)
    {
     mainState = state;
    }
    indentPrint ("<dict:state");
    writeAttribute ("key", state.getName ());
    indent (System.out, ' ');
    System.out.println ("Writing out STATE: " + xmlType.getName () + "." +
     state.getName ());
    println (">");
    writeComment (state.getDesc ());
    writeComment (state.getComments ());
    for (Dictionary dict : dictionary)
    {
     FieldWriter fw =
      new FieldWriter (getOut (), getIndent () + 1, spreadsheet, dict, mainState, inline);
     fw.write ();
    }
    indentPrintln ("</dict:state>");
   }
   indentPrintln ("</dict:type>");
  }
  indentPrintln ("</dict:objectStructure>");
 }

 private List<Type> filterTypes ()
 {
  if (inline)
  {
   return filterSubTypes ();
  }
  return filterMainTypes ();
 }

 private List<Type> filterMainTypes ()
 {
  List<Type> list = new ArrayList ();
  HashSet names = new HashSet ();
  for (Dictionary d : dictionary)
  {
   if (names.contains (d.getMainType ()))
   {
    continue;
   }
   names.add (d.getMainType ());
   list.add (getType (xmlType.getName (), d.getMainType ()));
  }
  if (list.size () == 0)
  {
   throw new RuntimeException ("No main types found in dictionary for " +
    xmlType.getName ());
  }
  return list;
 }

 private List<Type> filterSubTypes ()
 {
  List<Type> list = new ArrayList ();
  HashSet names = new HashSet ();
  for (Dictionary d : dictionary)
  {
   if (names.contains (d.getSubType ()))
   {
    continue;
   }
   names.add (d.getSubType ());
   list.add (getType (xmlType.getName (), d.getSubType ()));
  }
  if (list.size () == 0)
  {
   throw new RuntimeException ("No sub-types found in dictionary for " +
    xmlType.getName ());
  }
  return list;
 }

 private Type getType (String xmlObject, String name)
 {
  Type type = finder.findType (xmlObject, name);
  if (type == null)
  {
   throw new RuntimeException ("Could not find type for: " + xmlObject + "." +
   name);
  }
  return type;
 }

 private List<State> filterStates ()
 {
  List list = new ArrayList ();
  for (State state : spreadsheet.getStates ())
  {
   if (state.getXmlObject ().equals (xmlType.getName ()))
   {
    list.add (state);
   }
  }
  if (list.size () == 0)
  {
   throw new RuntimeException ("No states found for " + xmlType.getName ());
  }
  return list;
 }

}
