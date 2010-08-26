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
package org.kuali.student.dictionary.writer.dict;

import java.util.Date;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.util.DateUtility;
import org.kuali.student.dictionary.writer.XmlTypeNameBuilder;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes an object structure entity in the output XML document, either the in-line or root ones.
 * @author nwright
 */
public class DictionaryStructureWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private XmlType xmlType;
 private XmlWriter writer;
 private DictionaryStructureFieldWriter parentField;

 /**
  * construct writer for a particular type
  * @param model holds the entire spreadsheet information
  * @param xmlType data structure to write out
  * @param dictionary list of entries to write out
  * @param pwrent field if this is a sub-object
  */
 public DictionaryStructureWriter (XmlWriter writer,
                                   DictionaryModel model,
                                   XmlType xmlType,
                                   DictionaryStructureFieldWriter parentField)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.xmlType = xmlType;
  this.parentField = parentField;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  writeDefaultObjectStructure ();
 }

 protected void writeRef ()
 {
  writeObjectStructureRef ();
 }

 protected String calcObjectId ()
 {
  if (parentField != null)
  {
   return "object." + parentField.calcFieldId ();
  }
  return "object." + xmlType.getName ();
 }

 private String calcTypeId (Type type)
 {
  return "type." + calcObjectId ().substring ("object.".length ()) + "." + type.
   getName ();
 }

 private String calcStateId (Type type, State state)
 {
  return "state." + calcTypeId (type).substring ("type.".length ()) + "." + state.
   getName ();
 }

 protected String calcFieldId (Field field, Type type, State state)
 {
  String id = "field." + field.getId ();
  if (parentField != null)
  {
   id = parentField.calcFieldId () + "." + field.getShortName ();
  }
  id = id + "." + type.getName () + "." + state.getName ();
  return id;
 }

 private void writeDefaultObjectStructure ()
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

  writeTypeStructure (finder.getDefaultType (), null);
  writeStateStructure (finder.getDefaultType (), finder.getDefaultState (), null);
 }

 private void writeObjectStructureRef ()
 {
  writeRefBean ("dict:objectStructureRef", calcObjectId ());
 }

 private void writeTypeStructures ()
 {
  String parentId = calcTypeId (finder.getDefaultType ());

  for (Type type : finder.findTypes (xmlType.getName ()))
  {
   if ( ! type.getInclude ())
   {
//    System.out.println ("type flagged to not be included "
//     + xmlType.getName () + " " + type.getName ());
    continue;
   }
   writeTypeStructure (type, parentId);
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

  if (parentId != null)
  {
   writeStateStructures (type);
  }

 }

 private void writeStateStructures (Type type)
 {
  String parentId = calcStateId (type, finder.getDefaultState ());


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
                        parentId);
  }
 }

 private void writeStateStructure (Type type,
                                   State state,
                                   String parentId)
 {
  Type defaultType = finder.getDefaultType ();
  State defaultState = finder.getDefaultState ();

  String id = calcStateId (type, state);
  writer.indentPrintln ("");
  writer.writeCommentBox ("State for " + id);
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
  for (Field field : finder.findFields (xmlType.getName ()))
  {
   if (field.getXmlType ().equalsIgnoreCase ("attributeInfo"))
   {
    continue;
   }
   // always reference the default field definitions
   writeRefBean ("dict:fieldRef", calcFieldId (field, defaultType, defaultState));
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
   if (field.getXmlType ().equalsIgnoreCase ("attributeInfo"))
   {
    continue;
   }
   DictionaryStructureFieldWriter few =
    new DictionaryStructureFieldWriter (writer,
                                        model,
                                        field,
                                        type,
                                        state,
                                        this);
   few.write ();
  }
 }

//  protected XmlType getXmlType ()
// {
//  return xmlType;
// }
//
//
// protected String getMainTypeToCompare (Type type)
// {
//  if (parentFieldEntryWriter == null)
//  {
//   return type.getName ();
//  }
////  XmlType parentXmlType = finder.findXmlType (parentFieldEntryWriter.getField ().getXmlType ());
//  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getXmlType ();
//
//  if (parentXmlType.hasOwnType ())
//  {
//   type = parentFieldEntryWriter.getType ();
//  }
//  return parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getMainTypeToCompare (type);
// }
//
// protected String getMainStateToCompare (State state)
// {
//  if (parentFieldEntryWriter == null)
//  {
//   return state.getName ();
//  }
////  XmlType parentXmlType = finder.findXmlType (parentFieldEntryWriter.getField ().getXmlType ());
//  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getXmlType ();
//  if (parentXmlType.hasOwnState ())
//  {
//   state = parentFieldEntryWriter.getState ();
//  }
//  return parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getMainStateToCompare (state);
// }
//
// protected String getSubTypeToCompare (Type type)
// {
//  if (parentFieldEntryWriter == null)
//  {
//   return Type.DEFAULT;
//  }
//  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getXmlType ();
//  if (parentXmlType.hasOwnType ())
//  {
//   return parentFieldEntryWriter.getType ().getName ();
//  }
//  return parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getSubTypeToCompare (type);
// }
//
// protected String getSubStateToCompare (State state)
// {
//  if (parentFieldEntryWriter == null)
//  {
//   return State.DEFAULT;
//  }
//  XmlType parentXmlType = parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getXmlType ();
//  if (parentXmlType.hasOwnState ())
//  {
//   return parentFieldEntryWriter.getState ().getName ();
//  }
//  return parentFieldEntryWriter.getParentObjectStructureWriter ().
//   getMainStateToCompare (state);
// }
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

 private String fixupDate (Date date)
 {
  if (date == null)
  {
   return "";
  }
  return new DateUtility ().asYMD (date);
 }

}
