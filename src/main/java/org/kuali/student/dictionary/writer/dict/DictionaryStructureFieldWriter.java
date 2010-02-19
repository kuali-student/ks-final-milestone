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

import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class DictionaryStructureFieldWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Field field;
 private XmlWriter writer;
 private Type type;
 private State state;
 private DictionaryStructureWriter parentObject;

 public DictionaryStructureFieldWriter (XmlWriter writer,
                                        DictionaryModel model,
                                        Field field,
                                        Type type,
                                        State state,
                                        DictionaryStructureWriter parentObjectWriter)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.field = field;
  this.type = type;
  this.state = state;
  this.parentObject = parentObjectWriter;
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

 protected DictionaryStructureWriter getParentObject ()
 {
  return parentObject;
 }

 public void write ()
 {
  writeField ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
  writeSubObjectStructure ();
 }

 private void writeSubObjectStructure ()
 {
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find field's type in list of xmltypes "
    + field.getId () + "." + field.getXmlType ());
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   if (field.getXmlType ().equalsIgnoreCase ("attributeInfo"))
   {
    return;
   }
   System.out.println ("Writing out subfield object structure for " + field.
    getId () + " " + xmlType.getName ());
   DictionaryStructureWriter osw =
    new DictionaryStructureWriter (writer,
                                  model,
                                  xmlType,
                                  this);
   osw.write ();
  }
 }

 private void writeSubObjectStructureRef ()
 {
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find field's type in list of xmltypes "
    + field.getId () + "." + field.getXmlType ());
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   DictionaryStructureWriter osw =
    new DictionaryStructureWriter (writer,
                                  model,
                                  xmlType,
                                  this);
   osw.writeRef ();
  }
 }

 protected String calcFieldId ()
 {
  return parentObject.calcFieldId (field, type, state);
 }

 protected String calcFieldDescriptorId ()
 {
  return calcFieldId () + ".fd";
 }

 protected String calcFieldConstraintDescriptorId ()
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
  writeRefBean ("dict:fieldDescriptorRef", calcFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptorRef", calcFieldConstraintDescriptorId ());
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
  writeAbstractAttributeId (calcFieldDescriptorId ());
  writer.indentPrintln (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", field.getName ());
  writer.writeTag ("dict:desc", field.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());
  writeSubObjectStructureRef ();
  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:fieldDescriptor");
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
  if (field.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return XmlType.COMPLEX;
  }

  if (field.getPrimitive ().equalsIgnoreCase ("mapped string"))
  {
   return "string";
  }

  if (field.getPrimitive ().equalsIgnoreCase ("primitive"))
  {
   return field.getXmlType ();
  }

  throw new DictionaryValidationException ("Unexpected data value for the primitive column in field.  Found " + field.
   getPrimitive () + " on field " + field.getId ());
 }

 protected String calcReadOnly ()
 {
  if (field.getConstraintIds ().contains ("read.only"))
  {
   return "true";
  }
  return "";
 }

 protected String calcSelector ()
 {
  if (field != null)
  {
   if (field.isSelector ())
   {
    return "true";
   }

  }
  return "";
 }

 protected String calcDynamic ()
 {
  if (field != null)
  {
   if (field.isDynamic ())
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

}
