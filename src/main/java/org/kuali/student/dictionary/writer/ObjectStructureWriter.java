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

import java.text.ParseException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.util.DateUtility;

/**
 * Writes an object structure entity in the output XML document, either the in-line or root ones.
 * @author nwright
 */
public class ObjectStructureWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private XmlType xmlType;
 private XmlWriter writer2;
 private XmlWriter writer3;
 private FieldEntryWriter parentFieldEntryWriter;
 private Set<String> dictionaryEntriesWritten;
 private Set<String> crossObjectConstraintsWritten;

 /**
  * construct writer for a particular type
  * @param model holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  * @param pwrent field if this is a sub-object
  */
 public ObjectStructureWriter (XmlWriter writer2,
                               XmlWriter writer3,
                               DictionaryModel model,
                               XmlType xmlType,
                               FieldEntryWriter parentField,
                               Set<String> dictionaryEntriesWritten,
                               Set<String> crossObjectConstraintsWritten)
 {
  this.writer2 = writer2;
  this.writer3 = writer3;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.xmlType = xmlType;
  this.parentFieldEntryWriter = parentField;
  this.dictionaryEntriesWritten = dictionaryEntriesWritten;
  this.crossObjectConstraintsWritten = crossObjectConstraintsWritten;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  writeObjectStructure ();
 }

 protected void writeRef ()
 {
  writeObjectStructureRef ();
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
  writer2.indentPrintln ("");
  String key = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
   getJavaPackage ()).build ();
  String id = calcObjectId ();
  writer2.writeCommentBox ("object structure for " + id);

  writer2.writeComment (xmlType.getDesc ());
  writer2.indentPrint ("<dict:objectStructure");
  writer2.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  writer2.println (">");
  writeRefBean ("dict:typeRef", calcTypeId (finder.getDefaultType ()));
  writer2.indentPrintln ("</dict:objectStructure>");
  // concrete version
  writer2.indentPrint ("<dict:objectStructure");
  writer2.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer2.println ("/>");

  writeTypeStructures ();
 }

 private void writeObjectStructureRef ()
 {
  writeRefBean ("dict:objectStructureRef", calcObjectId ());
 }

 private void writeTypeStructures ()
 {
  String parentId = calcTypeId (finder.getDefaultType ());
  writeTypeStructure (finder.getDefaultType (), null);

  for (Type type : finder.findTypes (xmlType.getName ()))
  {
   if ( ! type.getInclude ())
   {
//    System.out.println ("type flagged to not be included "
//     + xmlType.getName () + " " + type.getName ());
    continue;
   }
   // if no configured entries then don't write out
//   List<Dictionary> list = getMatchingDictionaryEntries (type);
//   if (list.size () == 0)
//   {
//    System.out.println ("No dictionary entries found for type "
//     + xmlType.getName () + " " + type.getName ());
//    continue;
//   }
   writeTypeStructure (type, parentId);
  }
 }

 private void writeTypeStructure (Type type, String parentId)
 {
  String id = calcTypeId (type);
  String key = type.getTypeKey ();
  System.out.println ("Writing out TYPE: " + id);
  writer2.println ("");
  writer2.writeComment (type.getComments ());
  writer2.indentPrint ("<dict:type");
  writer2.writeAttribute ("key", key);
  writeAbstractAttributeId (id);
  if (parentId != null)
  {
   writeParentToAbstract (parentId);
  }
  writer2.println (">");
  writer2.incrementIndent ();
  writer2.writeTag ("dict:name", type.getName ());
  writer2.writeTag ("dict:desc", type.getDesc ());
  try
  {
   writer2.writeTag ("dict:effectiveDate", fixupDate (type.getEffectiveDate ()));
  }
  catch (ParseException ex)
  {
   throw new DictionaryExecutionException (" State " + type.getName ()
    + " has an invalid effecitve date");
  }
  try
  {
   writer2.writeTag ("dict:expirationDate", fixupDate (type.getExpirationDate ()));
  }
  catch (ParseException ex)
  {
   throw new DictionaryExecutionException (" State " + type.getName ()
    + " has an invalid expiration date");
  }
  writer2.decrementIndent ();
  writeRefBean ("dict:stateRef",
                calcStateId (type, finder.getDefaultState ()));
  writer2.println ("</dict:type>");

  // write concrete version
  writer2.indentPrint ("<dict:type");
  writer2.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer2.println ("/>");

  // write state structures for this type
  writeStateStructures (type);
 }

 private void writeStateStructures (Type type)
 {
  String parentId = calcStateId (type, finder.getDefaultState ());
  writeStateStructure (type,
                       finder.getDefaultState (),
                       null,
                       getMatchingDictionaryEntries (type, finder.
   getDefaultState ()));

  for (State state : finder.findStates (xmlType.getName ()))
  {
   if ( ! state.getInclude ())
   {
//    System.out.println ("State not included " + xmlType.getName () + " state= " + state.
//     getName ());
    continue;
   }
   writeStateStructure (type,
                        state,
                        parentId,
                        getMatchingDictionaryEntries (type, state));
  }
 }

// private List<Dictionary> getMatchingDictionaryEntries (Type type)
// {
//  List<Dictionary> list = getMatchingDictionaryEntries (type, finder.
//   getDefaultState ());
//  for (State state : finder.findStates (xmlType.getName ()))
//  {
//   list.addAll (getMatchingDictionaryEntries (type, state));
//  }
//  return list;
// }
 private List<Dictionary> getMatchingDictionaryEntries (Type type, State state)
 {
  List<Dictionary> list = new ArrayList ();
  String mainType = getMainTypeToCompare (type);
  String mainState = getMainStateToCompare (state);
  String subType = getSubTypeToCompare (type);
  String subState = getSubStateToCompare (state);
  for (Dictionary dict : finder.findDictionaryEntriees (xmlType.getName ()))
  {
   if (dict.getType ().equalsIgnoreCase (mainType))
   {
    if (dict.getState ().equalsIgnoreCase (mainState))
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

 private void writeStateStructure (Type type,
                                   State state,
                                   String parentId,
                                   List<Dictionary> dicts)
 {
  if ( ! state.equals (finder.getDefaultState ()))
  {
   if (dicts.size () == 0)
   {
    return;
   }
  }
  String id = calcStateId (type, state);
  writer2.indentPrintln ("");
  writer2.writeCommentBox ("fields for " + id);
  //System.out.println ("Writing out State: " + id);
  String key = state.getStateKey ();
  writer2.indentPrintln ("");
  writer2.writeComment (state.getComments ());
  writer2.indentPrint ("<dict:state");
  writer2.writeAttribute ("key", key);
  if (parentId != null)
  {
   writeParentToAbstract (parentId);
  }
  writeAbstractAttributeId (id);
  writer2.println (">");
//  writer2.incrementIndent ();
//  writer2.writeTag ("dict:name", state.getName ());
//  writer2.writeTag ("dict:desc", state.getDesc ());
//  try
//  {
//   writer2.writeTag ("dict:effectiveDate", fixupDate (state.getEffectiveDate ()));
//  }
//  catch (ParseException ex)
//  {
//   throw new DictionaryExecutionException (" State " + state.getName ()
//    + " has an invalid effective date");
//  }
//  try
//  {
//   writer2.writeTag ("dict:expirationDate", fixupDate (state.getExpirationDate ()));
//  }
//  catch (ParseException ex)
//  {
//   throw new DictionaryExecutionException (" State " + state.getName ()
//    + " has an invalid expiration date");
//  }
//  writer2.decrementIndent ();
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   writeRefBean ("dict:fieldRef", calcFieldId (field, type, state));
  }
  writer2.indentPrintln ("</dict:state>");

// write concrete version
  writer2.indentPrint ("<dict:state");
  writer2.writeAttribute ("key", key);
  writeAttributeId (id);
  writeParentToAbstract (id);
  writer2.println ("/>");

  writeFields (type, state, dicts);
 }

 private void writeFields (Type type, State state, List<Dictionary> dicts)
 {
  writer2.indentPrintln ("");
  // no write out each field
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   FieldEntryWriter few =
    new FieldEntryWriter (writer2,
                          writer3,
                          model,
                          field,
                          type,
                          state,
                          this,
                          dictionaryEntriesWritten,
                          crossObjectConstraintsWritten);
   few.write ();
   for (Dictionary dict : dicts)
   {
    if (dict.getShortName ().equalsIgnoreCase (field.getShortName ()))
    {
     DictionaryEntryWriter dew =
      new DictionaryEntryWriter (writer3, model, dict, field, few, crossObjectConstraintsWritten);
     dew.write ();
     dictionaryEntriesWritten.add (dict.getId ());
    }
   }
  }
 }

// private List<Dictionary> getDefaultDictionaryEntries ()
// {
//  List<Dictionary> list =
//   finder.findDefaultDictionaryEntriees (xmlType.getName ());
//// TODO: uncomment this when/if all substructures are used OR check if substructure is ever used
////  if (list.size () == 0)
////  {
////   throw new DictionaryValidationException ("No default dictionary entries found for " + xmlType.
////    getName ());
////  }
//  return list;
// }
//
// private void writeDictionaryOverrides ()
// {
//  writer2.indentPrintln ("");
//  writer2.writeCommentBox ("dictionary override of object structure for " + xmlType.
//   getName ());
//  String key = new XmlTypeNameBuilder (xmlType.getName (), xmlType.
//   getJavaPackage ()).build ();
//  String id = calcObjectId ();
//  String typeId = calcTypeId (finder.getDefaultType ());
//
//  writer2.writeComment (xmlType.getDesc ());
//  writer2.indentPrint ("<dict:objectStructure");
//  writer2.writeAttribute ("key", key);
//  writeAbstractAttributeId (id);
//  writer2.println (">");
//  writeRefBean ("dict:typeRef", typeId);
//  writer2.indentPrintln ("</dict:objectStructure>");
//  // concrete version
//  writer2.indentPrint ("<dict:objectStructure");
//  writer2.writeAttribute ("key", key);
//  writeAttributeId (id);
//  writeParentToAbstract (id);
//  writer2.println ("/>");
//
//  writer2.indentPrintln ("");
//  writer2.indentPrint ("<dict:state");
//  writer2.writeAttribute ("key", "(default)");
//  writeAbstractAttributeId (xmlType.getName () + ".type");
//  writer2.println ("/>");
//  writer2.incrementIndent ();
//
//  for (Type type : filterTypes ())
//  {
//   writer2.indent (System.out, ' ');
//   System.out.println ("Writing out TYPE: " + xmlType.getName () + "." + type.
//    getTypeKey ());
//
//   writer2.indentPrintln ("");
//   writer2.writeComment (type.getDesc ());
//   writer2.writeComment (type.getAliases ());
//   writer2.writeComment (type.getComments ());
//   writer2.indentPrint ("<dict:type");
//   writer2.writeAttribute ("key", type.getTypeKey ());
//   writeAbstractAttributeId (xmlType.getName () + "." + type.getTypeKey ());
//   writer2.println ("/>");
//   writer2.indentPrintln ("</dict:type>");
//
//   for (State state : filterStates ())
//   {
//    writer2.writeComment (state.getDesc ());
//    writer2.writeComment (state.getComments ());
//    writer2.indentPrint ("<dict:state");
//    writer2.writeAttribute ("key", state.getName ());
////    writer.indent (System.out, ' ');
////    System.out.println ("Writing out STATE: " + xmlType.getName () + "." + state.
////     getName ());
//    writer2.println (">");
//
//    for (Dictionary dict : getDefaultDictionaryEntries ())
//    {
//     if (matchesType (dict, type))
//     {
//      DictionaryEntryWriter dew =
//       new DictionaryEntryWriter (writer2,
//                                  model,
//                                  dict,
//                                  this);
//      dew.write ();
//     }
//    }
//    writer2.indentPrintln ("</dict:state>");
//   }
//  }
// }
//
// private boolean matchesType (Dictionary dict, Type type)
// {
//  if (parentFieldEntryWriter != null)
//  {
//   if (dict.getSubType ().equals (type.getName ()))
//   {
//    return true;
//   }
//   return false;
//  }
//  if (dict.getMainType ().equals (type.getName ()))
//  {
//   return true;
//  }
//  return false;
// }
//
// private List<Type> filterTypes ()
// {
//  if (parentFieldEntryWriter != null)
//  {
//   return filterSubTypes ();
//  }
//  return filterMainTypes ();
// }
//
// private List<Type> filterMainTypes ()
// {
//  List<Type> list = new ArrayList ();
//  HashSet names = new HashSet ();
//
//  for (Dictionary d : getDefaultDictionaryEntries ())
//  {
//   if (names.contains (d.getMainType ()))
//   {
//    continue;
//   }
//   names.add (d.getMainType ());
//   list.add (getType (xmlType.getName (), d.getMainType ()));
//  }
//  if (list.size () == 0)
//  {
//   throw new DictionaryValidationException ("No main types found in dictionary for " + xmlType.
//    getName ());
//  }
//  return list;
// }
//
// private List<Type> filterSubTypes ()
// {
//  List<Type> list = new ArrayList ();
//  HashSet names = new HashSet ();
//
//  for (Dictionary d : getDefaultDictionaryEntries ())
//  {
//   if (names.contains (d.getSubType ()))
//   {
//    continue;
//   }
//   names.add (d.getSubType ());
//   list.add (getType (xmlType.getName (), d.getSubType ()));
//  }
//  if (list.size () == 0)
//  {
//   throw new DictionaryValidationException ("No sub-types found in dictionary for " + xmlType.
//    getName ());
//  }
//  return list;
// }
//
// private Type getType (String xmlObject, String name)
// {
//  Type type = finder.findType (xmlObject, name);
//  if (type == null)
//  {
//   throw new DictionaryValidationException ("Could not find type for: "
//    + xmlObject + "." + name);
//  }
//  return type;
// }
//
// private List<State> filterStates ()
// {
//  List list = new ArrayList ();
//  for (State state : model.getStates ())
//  {
//   if (state.getXmlObject ().equals (xmlType.getName ()))
//   {
//    list.add (state);
//   }
//  }
//  if (list.size () == 0)
//  {
//   throw new DictionaryValidationException ("No states found for " + xmlType.
//    getName ());
//  }
//  return list;
// }
 private void writeAbstractAttributeId (String id)
 {
  new AttributeIdUtil ().writeAbstractAttribute (writer2, id);
 }

 private void writeParentToAbstract (String id)
 {
  new AttributeIdUtil ().writeParentToAbstract (writer2, id);
 }

 private void writeAttributeId (String id)
 {
  new AttributeIdUtil ().writeAttribute (writer2, id);
 }

 private void writeRefBean (String refType, String id)
 {
  new AttributeIdUtil ().writeRefBean (writer2, refType, id);
 }

 private String fixupDate (String date)
  throws ParseException
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
