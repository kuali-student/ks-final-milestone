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

import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.DateUtility;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class FieldEntryWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Field field;
 private XmlWriter writer;
 private Type type;
 private State state;
 private ObjectStructureWriter parentObjectStructureWriter;

 public FieldEntryWriter (XmlWriter writer,
                          DictionaryModel model,
                          Field field,
                          Type type,
                          State state,
                          ObjectStructureWriter parentObjectStructureWriter)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.field = field;
  this.type = type;
  this.state = state;
  this.parentObjectStructureWriter = parentObjectStructureWriter;
 }


 protected Field getField ()
 {
  return field;
 }

 protected Type getType ()
 {
  return type;
 }

 protected State getState ()
 {
  return state;
 }


 protected ObjectStructureWriter getParentObjectStructureWriter ()
 {
  return parentObjectStructureWriter;
 }

 public void write ()
 {
  // write it out
  writeField ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
  writeSubFields ();
 }

 private void writeSubFields ()
 {
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find field's type in list of xmltypes "
    + field.getId () + "." + field.getXmlType ());
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   System.out.println ("Writing out subfield object structure for " + field.
    getId () + " " + xmlType.getName ());
   ObjectStructureWriter osw =
    new ObjectStructureWriter (writer, model, xmlType, this);
   osw.write ();
  }
 }

 protected String calcFieldId ()
 {
  return parentObjectStructureWriter.calcFieldId (field, type, state);
 }

 private String calcFieldDescriptorId ()
 {
  return calcFieldId () + ".fd";
 }

 private String calcFieldConstraintDescriptorId ()
 {
  return calcFieldId () + ".cd";
 }

 private void writeField ()
 {
  writer.indentPrintln ("");
  writer.writeComment (field.getComments ());
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writeAbstractAttributeId (calcFieldId ());
  writer.println (">");
  writeRefBean ("dict:fieldDescriptor", calcFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptor", calcFieldConstraintDescriptorId ());
  writer.writeTag ("dict:selector", calcSelector ());
  writer.writeTag ("dict:dynamic", calcDynamic ());
  writer.indentPrintln ("</dict:field>");
  // now write the non-abstract one
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldId ());
  writeParentToAbstract (calcFieldId ());
  writer.println ("/>");
 }

 private void writeFieldDescriptor ()
 {
  writer.indentPrint ("<dict:fieldDescriptor");
  writer.writeAttribute ("key", field.getShortName ());
  writeAbstractAttributeId (calcFieldDescriptorId ());
  writer.indentPrintln (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", field.getName ());
  writer.writeTag ("dict:desc", field.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());
  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:fieldDescriptor");
  writer.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldDescriptorId ());
  writeParentToAbstract (calcFieldDescriptorId ());
  writer.indentPrintln ("/>");
 }

 private void writeConstraintDescriptor ()
 {
  writer.writeComment (field.getConstraintDescription ());
  writer.indentPrint ("<dict:constraintDescriptor");
  writeAbstractAttributeId (calcFieldConstraintDescriptorId ());
  writer.indentPrintln (">");
  writer.incrementIndent ();
  // write out referenced constraints
  for (String id : field.getConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (writer, id);
   crw.write ();
  }

// write out in-line constraint
  ConstraintWriter cw =
   new ConstraintWriter (writer,
                         field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (writer,
                             field.getInlineConstraint ());
  cw.write ();
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:constraintDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:constraintDescriptor");
  writer.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldConstraintDescriptorId ());
  writeParentToAbstract (calcFieldConstraintDescriptorId ());
  writer.indentPrintln ("/>");
 }

 private String calcDataType ()
 {
  return calcDataType (field);
 }

 private String calcDataType (Field f)
 {
  if (f.getPrimitive ().equalsIgnoreCase ("complex"))
  {
   return "complex";
  }

  if (f.getPrimitive ().equalsIgnoreCase ("mapped string"))
  {
   return "string";
  }

  if (f.getPrimitive ().equalsIgnoreCase ("primitive"))
  {
   return field.getXmlType ();
  }

  throw new DictionaryValidationException ("Unexpected data value for the primitive column in field.  Found " + f.
   getPrimitive () + " on field " + f.getId ());
 }

 private String calcReadOnly ()
 {
  if (field.getConstraintIds ().contains ("read.only"))
  {
   return "true";
  }

  if (field != null)
  {
   if (field.getConstraintIds ().contains ("read.only"))
   {
    return "true";
   }

  }
  return "";
 }

 private String calcSelector ()
 {
  if (field != null)
  {
   if (field.getSelector ().equals ("true"))
   {
    return "true";
   }

  }
  return "";
 }

 private String calcDynamic ()
 {
  if (field != null)
  {
   if (field.getDynamic ().equals ("true"))
   {
    return "true";
   }

  }
  return "";
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
