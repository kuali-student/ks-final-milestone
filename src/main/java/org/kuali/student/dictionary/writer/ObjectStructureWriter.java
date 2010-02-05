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
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.util.DateUtility;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;

/**
 * Writes an object structure entity in the output XML document, either the in-line or root ones.
 * @author nwright
 */
public class ObjectStructureWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private XmlType xmlType;
 private List<Dictionary> dictionary;
 private boolean inline;
 private State mainState;
 private XmlWriter writer;

 /**
  * construct writer for a particular type
  * @param model holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  */
 public ObjectStructureWriter (PrintStream out, int indent,
                               DictionaryModel model, XmlType xmlType,
                               List<Dictionary> dictionary, boolean inline,
                               State mainState)
 {
  this.writer = new XmlWriter (out, indent);
  this.model = model;
  this.finder = new ModelFinder (model);
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
  writeObjectStructure ();
  writeTypeStructure ();
  writeStateStructure ();
  writeFields ();
 }

 private void writeObjectStructure ()
 {
  String fullName = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  writer.writeComment (xmlType.getDesc ());
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", fullName);
  writeAttributeId ("object." + xmlType.getName () + ".abstract");
  writer.writeAttribute ("abstract", "true");
  writer.println (">");
  writer.incrementIndent ();
  writer.indentPrint ("<dict:typeRef");
  writer.writeAttribute ("bean", "object." + xmlType.getName () + ".type");
  writer.println ("/>");
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:objectStructure>");
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", fullName);
  writeAttributeId ("object." + xmlType.getName ());
  writer.writeAttribute ("parent", "object." + xmlType.getName () + ".abstract");
  writer.println ("/>");
 }

 private void writeTypeStructure ()
 {
  // now write out the default for the type
  writer.indentPrintln ("");
  writer.indentPrint ("<dict:type");
  writer.writeAttribute ("key", "(default)");
  writeAttributeId ("object." + xmlType.getName () + ".type.abstract");
  writer.writeAttribute ("abstract", "true");
  writer.println (">");
  writer.incrementIndent ();
  writer.indentPrint ("<dict:stateRef");
  writer.writeAttribute ("bean", "object." + xmlType.getName () + ".state");
  writer.println ("/>");
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:type>");

  for (Type type : finder.findTypes (xmlType.getName ()))
  {
   writer.indentPrint ("<dict:type");
   writer.writeAttribute ("key", type.getTypeKey ());
   writeAttributeId ("object." + xmlType.getName () + ".type."
    + type.getTypeKey ());
   writer.writeAttribute ("parent", "object." + xmlType.getName ()
    + ".type.abstract");
   writer.println (">");
   writer.incrementIndent ();
   writer.writeTag ("dict:name", type.getName ());
   writer.writeTag ("dict:desc", type.getDesc ());
   writer.writeTag ("dict:effectiveDate", fixupDate (type.getEffectiveDate ()));
   writer.writeTag ("dict:expirationDate", fixupDate (type.getExpirationDate ()));
   writer.decrementIndent ();
   writer.println ("</dict:type>");
  }
 }

 private String fixupDate (String date)
 {
  if (date == null)
  {
   return "";
  }
  if (date.equals (""))
  {
   return "";
  }
  return new DateUtility ().asYMD (date);
 }

 private void writeStateStructure ()
 {
  // now write out the default for the state
  writer.indentPrintln ("");
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", "(default)");
  writeAttributeId ("object." + xmlType.getName () + ".state.abstract");
  writer.writeAttribute ("abstract", "true");
  writer.println (">");
  writer.incrementIndent ();
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   writer.indentPrint ("<dict:fieldRef");
   writer.writeAttribute ("bean", "field." + field.getId ());
   writer.println ("/>");
  }
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:state>");



  for (State state : finder.findStates (xmlType.getName ()))
  {
   writer.indentPrint ("<dict:state");
   writer.writeAttribute ("key", state.getStateKey ());
   writeAttributeId ("object." + xmlType.getName () + ".state." + state.
    getStateKey ());
   writer.writeAttribute ("parent", "object." + xmlType.getName ()
    + ".state.abstract");
   writer.println ("/>");
//   writer.println (">");
//   writer.incrementIndent ();
//   writer.writeTag ("dict:name", state.getName ());
//   writer.writeTag ("dict:desc", state.getDesc ());
//   writer.writeTag ("dict:effectiveDate", fixupDate (state.getEffectiveDate ()));
//   writer.writeTag ("dict:expirationDate", fixupDate (state.getExpirationDate ()));
//   writer.decrementIndent ();
//   writer.println ("</dict:state>");
  }
 }

 private void writeAttributeId (String id)
 {
  writer.writeAttribute ("id", fixId (id));
 }

 private String fixId (String id)
 {
  id = id.replace ('*', '_');
  id = id.replace (',', '_');
  id = id.replace ("(n/a)", "NA");
  return id;
 }

 private void writeFields ()
 {
  // no write out each field
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   FieldEntryWriter few = new FieldEntryWriter (writer, model, field);
   few.write ();
  }
 }

 private void writeRestOfObjectStructure ()
 {
  writer.indentPrintln ("");
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", "(default)");
  writeAttributeId (xmlType.getName () + ".type");
  writer.println ("/>");
  writer.incrementIndent ();




  for (Type type : filterTypes ())
  {
   writer.indent (System.out, ' ');
   System.out.println ("Writing out TYPE: " + xmlType.getName () + "." + type.
    getTypeKey ());

   writer.indentPrintln ("");
   writer.writeComment (type.getDesc ());
   writer.writeComment (type.getAliases ());
   writer.writeComment (type.getComments ());
   writer.indentPrint ("<dict:type");
   writer.writeAttribute ("key", type.getTypeKey ());
   writeAttributeId (xmlType.getName () + "." + type.getTypeKey ());
   writer.println ("/>");
   writer.indentPrintln ("</dict:type>");



   for (State state : filterStates ())
   {
    if ( ! inline)
    {
     mainState = state;


    }
    writer.writeComment (state.getDesc ());
    writer.writeComment (state.getComments ());
    writer.indentPrint ("<dict:state");
    writer.writeAttribute ("key", state.getName ());
    writer.indent (System.out, ' ');
    System.out.println ("Writing out STATE: " + xmlType.getName () + "." + state.
     getName ());
    writer.println (">");


    for (Dictionary dict : dictionary)
    {
     if (matchesType (dict, type))
     {
      DictionaryEntryWriter fw =
       new DictionaryEntryWriter (writer.getOut (), writer.getIndent () + 1, model, dict, mainState, inline);
      fw.write ();


     }
    }
    writer.indentPrintln ("</dict:state>");


   }
  }
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


  for (State state : model.getStates ())
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
