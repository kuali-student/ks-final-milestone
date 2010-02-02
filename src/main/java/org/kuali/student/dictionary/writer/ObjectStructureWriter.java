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
package org.kuali.student.dictionary.writer;

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Writes an object structure entity in the output XML document, either the in-line or root ones.
 * @author nwright
 */
public class ObjectStructureWriter
{

 private DictionaryModel spreadsheet;
 private ModelFinder finder;
 private XmlType xmlType;
 private List<Dictionary> dictionary;
 private boolean inline;
 private State mainState;
 private XmlWriter writer;

 /**
  * construct writer for a particular type
  * @param spreadsheet holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  */
 public ObjectStructureWriter (PrintStream out, int indent,
                               DictionaryModel spreadsheet, XmlType xmlType,
                               List<Dictionary> dictionary, boolean inline,
                               State mainState)
 {
  this.writer = new XmlWriter (out, indent);
  this.spreadsheet = spreadsheet;
  this.finder = new ModelFinder (spreadsheet);
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
  writer.indentPrintln ("");
  writer.indentPrint ("<dict:objectStructure");
  String fullName = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  writer.writeAttribute ("key", fullName);
  writer.println (">");
  writer.incrementIndent ();
  writer.writeComment (xmlType.getDesc ());
  for (Type type : filterTypes ())
  {
   writer.indentPrint ("<dict:type");
   writer.writeAttribute ("key", type.getTypeKey ());
   writer.indent (System.out, ' ');
   System.out.println ("Writing out TYPE: " + xmlType.getName () + "." + type.
    getTypeKey ());
   writer.println (">");
   writer.writeComment (type.getDesc ());
   writer.writeComment (type.getAliases ());
   writer.writeComment (type.getComments ());
   writer.incrementIndent ();
   for (State state : filterStates ())
   {
    if ( ! inline)
    {
     mainState = state;
    }
    writer.indentPrint ("<dict:state");
    writer.writeAttribute ("key", state.getName ());
    writer.indent (System.out, ' ');
    System.out.println ("Writing out STATE: " + xmlType.getName () + "." + state.
     getName ());
    writer.println (">");
    writer.writeComment (state.getDesc ());
    writer.writeComment (state.getComments ());
    for (Dictionary dict : dictionary)
    {
     if (matchesType (dict, type))
     {
      FieldWriter fw =
       new FieldWriter (writer.getOut (), writer.getIndent () + 1, spreadsheet, dict, mainState, inline);
      fw.write ();
     }
    }
    writer.indentPrintln ("</dict:state>");
   }
   writer.decrementIndent ();
   writer.indentPrintln ("</dict:type>");
  }
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:objectStructure>");
 }

 private boolean matchesType (Dictionary dict, Type type)
 {
  if (inline)
  {
   if (dict.getSubType ().equals (type.getName ()))
   {
    return true;
   }
   return false;
  }
  if (dict.getMainType ().equals (type.getName ()))
  {
   return true;
  }
  return false;
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
   throw new DictionaryValidationException ("No main types found in dictionary for " + xmlType.
    getName ());
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
   throw new DictionaryValidationException ("No sub-types found in dictionary for " + xmlType.
    getName ());
  }
  return list;
 }

 private Type getType (String xmlObject, String name)
 {
  Type type = finder.findType (xmlObject, name);
  if (type == null)
  {
   throw new DictionaryValidationException ("Could not find type for: "
    + xmlObject + "." + name);
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
   throw new DictionaryValidationException ("No states found for " + xmlType.
    getName ());
  }
  return list;
 }

}
