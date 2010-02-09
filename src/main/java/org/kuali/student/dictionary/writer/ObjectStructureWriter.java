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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
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
 private XmlWriter writer;
 private FieldEntryWriter parentFieldEntryWriter;

 /**
  * construct writer for a particular type
  * @param model holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  * @param pwrent field if this is a sub-object
  */
 public ObjectStructureWriter (XmlWriter writer,
                               DictionaryModel model,
                               XmlType xmlType,
                               FieldEntryWriter parentField)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.xmlType = xmlType;
  this.parentFieldEntryWriter = parentField;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  writeObjectStructure ();
 }

 protected String calcObjectId ()
 {
  String id = "object";
  if (parentFieldEntryWriter != null)
  {
   id = parentFieldEntryWriter.calcFieldId ();
  }
  return id + "." + xmlType.getName ();
 }

 private String calcTypeId (Type type)
 {
  return calcObjectId () + ".type." + type.getName ();
 }

 private String calcStateId (Type type, State state)
 {
  return calcTypeId (type) + ".state." + state.getName ();
 }

 protected String calcFieldId (Field field, Type type, State state)
 {
  String id = "field";
  if (parentFieldEntryWriter != null)
  {
   id = parentFieldEntryWriter.calcFieldId ();
  }
  id = id + "." + field.getId () + ".type." + type.getName () + ".state." + state.
   getName ();
  return id;
 }

 private void writeObjectStructure ()
 {
  writer.indentPrintln ("");
  String key = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  String id = calcObjectId ();
  writer.writeCommentBox ("object structure for " + id);

  writer.writeComment (xmlType.getDesc ());
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  writer.println (">");
  writeRefBean ("dict:typeRef", calcTypeId (finder.getDefaultType ()));
  writer.indentPrintln ("</dict:objectStructure>");
  // concrete version
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer.println ("/>");

  writeTypeStructures ();
 }

 private void writeTypeStructures ()
 {
  String parentId = calcTypeId (finder.getDefaultType ());
   writeTypeStructure (finder.getDefaultType (), null);

  for (Type type : finder.findTypes (xmlType.getName ()))
  {
   if (type.getInclude ().equalsIgnoreCase ("true"))
   {
    // if no configured entries then don't write out
    List<Dictionary> list = getMatchingDictionaryEntries (type);
    if (list.size () != 0)
    {
     writeTypeStructure (type, parentId);
    }
   }
  }
 }

 private void writeTypeStructure (Type type, String parentId)
 {
  String id = calcTypeId (type);
  String key = type.getTypeKey ();
  System.out.println ("Writing out TYPE: " + id);
  writer.println ("");
  writer.writeComment (type.getComments ());
  writer.indentPrint ("<dict:type");
  writer.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  if (parentId != null)
  {
   writeParentToAbstract (parentId);
  }
  writer.println (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", type.getName ());
  writer.writeTag ("dict:desc", type.getDesc ());
  writer.writeTag ("dict:effectiveDate", fixupDate (type.getEffectiveDate ()));
  writer.writeTag ("dict:expirationDate", fixupDate (type.getExpirationDate ()));
  writer.decrementIndent ();
  writeRefBean ("dict:stateRef",
                calcStateId (type, finder.getDefaultState ()));
  writer.println ("</dict:type>");

  // write concrete version
  writer.indentPrint ("<dict:type");
  writer.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer.println ("/>");

  // write state structures for this type
  writeStateStructures (type);
 }

 private void writeStateStructures (Type type)
 {
  String parentId = calcStateId (type, finder.getDefaultState ());
   writeStateStructure (type, finder.getDefaultState (), null);  

  for (State state : finder.findStates (xmlType.getName ()))
  {
   if (state.getInclude ().equalsIgnoreCase ("true"))
   {
    List<Dictionary> list = getMatchingDictionaryEntries (type, state);
    if (list.size () != 0)
    {
     writeStateStructure (type, state, parentId);
    }
   }
  }
 }

 private List<Dictionary> getMatchingDictionaryEntries (Type type)
 {
  List<Dictionary> list = new ArrayList ();
  String mainType = getMainTypeToCompare (type);
  String subType = getSubTypeToCompare (type);
  for (Dictionary dict : finder.findDictionaryEntriees (xmlType.getName ()))
  {
   if (dict.getMainType ().equalsIgnoreCase (mainType))
   {
    if (dict.getSubType ().equalsIgnoreCase (subType))
    {
     list.add (dict);
    }
   }
  }
  return list;
 }

 private List<Dictionary> getMatchingDictionaryEntries (Type type, State state)
 {
  List<Dictionary> list = new ArrayList ();
  String mainType = getMainTypeToCompare (type);
  String mainState = getMainStateToCompare (state);
  String subType = getSubTypeToCompare (type);
  String subState = getSubStateToCompare (state);
  for (Dictionary dict : finder.findDictionaryEntriees (xmlType.getName ()))
  {
   if (dict.getMainType ().equalsIgnoreCase (mainType))
   {
    if (dict.getMainState ().equalsIgnoreCase (mainState))
    {
     if (dict.getSubType ().equalsIgnoreCase (subType))
     {
      if (dict.getSubState ().equalsIgnoreCase (subState))
      {
       list.add (dict);
      }
     }
    }
   }
  }
  return list;
 }

 protected XmlType getXmlType ()
 {
  return xmlType;
 }

 protected String getMainTypeToCompare (Type type)
 {
  if (parentFieldEntryWriter == null)
  {
   return type.getName ();
  }
//  XmlType parentXmlType = finder.findXmlType (parentFieldEntryWriter.getField ().getXmlType ());
  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
   getXmlType ();

  if (parentXmlType.hasOwnType ())
  {
   type = parentFieldEntryWriter.getType ();
  }
  return parentFieldEntryWriter.getParentObjectStructureWriter ().
   getMainTypeToCompare (type);
 }

 protected String getMainStateToCompare (State state)
 {
  if (parentFieldEntryWriter == null)
  {
   return state.getName ();
  }
//  XmlType parentXmlType = finder.findXmlType (parentFieldEntryWriter.getField ().getXmlType ());
  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
   getXmlType ();
  if (parentXmlType.hasOwnState ())
  {
   state = parentFieldEntryWriter.getState ();
  }
  return parentFieldEntryWriter.getParentObjectStructureWriter ().
   getMainStateToCompare (state);
 }

 protected String getSubTypeToCompare (Type type)
 {
  if (parentFieldEntryWriter == null)
  {
   return Type.NA;
  }
  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
   getXmlType ();
  if (parentXmlType.hasOwnType ())
  {
   return parentFieldEntryWriter.getType ().getName ();
  }
  return parentFieldEntryWriter.getParentObjectStructureWriter ().
   getSubTypeToCompare (type);
 }

 protected String getSubStateToCompare (State state)
 {
  if (parentFieldEntryWriter == null)
  {
   return State.NA;
  }
  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
   getXmlType ();
  if (parentXmlType.hasOwnState ())
  {
   return parentFieldEntryWriter.getState ().getName ();
  }
  return parentFieldEntryWriter.getParentObjectStructureWriter ().
   getMainStateToCompare (state);
 }

 private void writeStateStructure (Type type, State state, String parentId)
 {
  String id = calcStateId (type, state);
  writer.indentPrintln ("");
  writer.writeCommentBox ("fields for " + id);
  //System.out.println ("Writing out State: " + id);
  String key = state.getStateKey ();
  writer.indentPrintln ("");
  writer.writeComment (state.getComments ());
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", key);
  if (parentId != null)
  {
   writeParentToAbstract (parentId);
  }
  writeAbstractAttributeId (id);
  writer.println (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", state.getName ());
  writer.writeTag ("dict:desc", state.getDesc ());
  writer.writeTag ("dict:effectiveDate", fixupDate (state.getEffectiveDate ()));
  writer.writeTag ("dict:expirationDate", fixupDate (state.getExpirationDate ()));
  writer.decrementIndent ();
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   writeRefBean ("dict:fieldRef", calcFieldId (field, type, state));
  }
  writer.indentPrintln ("</dict:state>");

// write concrete version
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer.println ("/>");

  writeFields (type, state);
 }

 private void writeFields (Type type, State state)
 {
  writer.indentPrintln ("");
  // no write out each field
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   FieldEntryWriter few =
    new FieldEntryWriter (writer, model, field, type, state, this);
   few.write ();
  }
 }

 private List<Dictionary> getDefaultDictionaryEntries ()
 {
  List<Dictionary> list =
   finder.findDefaultDictionaryEntriees (xmlType.getName ());
// TODO: uncomment this when/if all substructures are used OR check if substructure is ever used
//  if (list.size () == 0)
//  {
//   throw new DictionaryValidationException ("No default dictionary entries found for " + xmlType.
//    getName ());
//  }
  return list;
 }

 private void writeDictionaryOverrides ()
 {
  writer.indentPrintln ("");
  writer.writeCommentBox ("dictionary override of object structure for " + xmlType.
   getName ());
  String key = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  String id = calcObjectId ();
  String typeId = calcTypeId (finder.getDefaultType ());

  writer.writeComment (xmlType.getDesc ());
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  writer.println (">");
  writeRefBean ("dict:typeRef", typeId);
  writer.indentPrintln ("</dict:objectStructure>");
  // concrete version
  writer.indentPrint ("<dict:objectStructure");
  writer.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer.println ("/>");

  writer.indentPrintln ("");
  writer.indentPrint ("<dict:state");
  writer.writeAttribute ("key", "(default)");
  writeAbstractAttributeId (xmlType.getName () + ".type");
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
   writeAbstractAttributeId (xmlType.getName () + "." + type.getTypeKey ());
   writer.println ("/>");
   writer.indentPrintln ("</dict:type>");

   for (State state : filterStates ())
   {
    writer.writeComment (state.getDesc ());
    writer.writeComment (state.getComments ());
    writer.indentPrint ("<dict:state");
    writer.writeAttribute ("key", state.getName ());
//    writer.indent (System.out, ' ');
//    System.out.println ("Writing out STATE: " + xmlType.getName () + "." + state.
//     getName ());
    writer.println (">");

    for (Dictionary dict : getDefaultDictionaryEntries ())
    {
     if (matchesType (dict, type))
     {
      DictionaryEntryWriter fw =
       new DictionaryEntryWriter (writer,
                                  model,
                                  dict,
                                  this);
      fw.write ();
     }
    }
    writer.indentPrintln ("</dict:state>");
   }
  }
 }

 private boolean matchesType (Dictionary dict, Type type)
 {
  if (parentFieldEntryWriter != null)
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
  if (parentFieldEntryWriter != null)
  {
   return filterSubTypes ();
  }
  return filterMainTypes ();
 }

 private List<Type> filterMainTypes ()
 {
  List<Type> list = new ArrayList ();
  HashSet names = new HashSet ();

  for (Dictionary d : getDefaultDictionaryEntries ())
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

  for (Dictionary d : getDefaultDictionaryEntries ())
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

 private void writeAbstractAttributeId (String id)
 {
  new AttributeIdUtil ().writeAbstractAttribute (writer, id);
 }

 private void writeParentToAbstract (String id)
 {
  new AttributeIdUtil ().writeParentToAbstract (writer, id);
 }

 private void writeAttributeId (String id)
 {
  new AttributeIdUtil ().writeAttribute (writer, id);
 }

 private void writeRefBean (String refType, String id)
 {
  new AttributeIdUtil ().writeRefBean (writer, refType, id);
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

}
