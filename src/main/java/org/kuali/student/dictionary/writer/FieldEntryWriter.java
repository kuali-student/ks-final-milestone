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

import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.XmlType;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class FieldEntryWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Field field;
 private XmlWriter writer2;
 private XmlWriter writer3;
 private Type type;
 private State state;
 private ObjectStructureWriter parentObjectStructureWriter;
 private Set<String> dictionaryEntriesWritten;
 private Set<String> crossObjectConstraintsWritten;

 public FieldEntryWriter (XmlWriter writer2,
                          XmlWriter writer3,
                          DictionaryModel model,
                          Field field,
                          Type type,
                          State state,
                          ObjectStructureWriter parentObjectStructureWriter,
                          Set<String> dictionaryEntriesWritten,
                          Set<String> crossObjectConstraintsWritten)
 {
  this.writer2 = writer2;
  this.writer3 = writer3;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.field = field;
  this.type = type;
  this.state = state;
  this.parentObjectStructureWriter = parentObjectStructureWriter;
  this.dictionaryEntriesWritten = dictionaryEntriesWritten;
  this.crossObjectConstraintsWritten = crossObjectConstraintsWritten;
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
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   System.out.println ("Writing out subfield object structure for " + field.
    getId () + " " + xmlType.getName ());
   ObjectStructureWriter osw =
    new ObjectStructureWriter (writer2,
                               writer3,
                               model,
                               xmlType,
                               this,
                               dictionaryEntriesWritten,
                               crossObjectConstraintsWritten);
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
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   ObjectStructureWriter osw =
    new ObjectStructureWriter (writer2,
                               writer3,
                               model,
                               xmlType,
                               this,
                               dictionaryEntriesWritten,
                               crossObjectConstraintsWritten);
   osw.writeRef ();
  }
 }

 protected String calcFieldId ()
 {
  return parentObjectStructureWriter.calcFieldId (field, type, state);
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
  writer2.indentPrintln ("");
  writer2.writeComment (field.getComments ());
  writer2.indentPrint ("<dict:field");
  writer2.writeAttribute ("key", field.getShortName ());
  writeAbstractAttributeId (calcFieldId ());
  writer2.println (">");
  writeRefBean ("dict:fieldDescriptorRef", calcFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptorRef", calcFieldConstraintDescriptorId ());
  writer2.writeTag ("dict:selector", calcSelector ());
  writer2.writeTag ("dict:dynamic", calcDynamic ());
  writer2.indentPrintln ("</dict:field>");
  // now write the non-abstract one
  writer2.indentPrint ("<dict:field");
  writer2.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldId ());
  writeParentToAbstract (calcFieldId ());
  writer2.println ("/>");
 }

 private void writeFieldDescriptor ()
 {
  writer2.indentPrint ("<dict:fieldDescriptor");
  writer2.writeAttribute ("key", field.getShortName ());
  writeAbstractAttributeId (calcFieldDescriptorId ());
  writer2.indentPrintln (">");
  writer2.incrementIndent ();
  writer2.writeTag ("dict:name", field.getName ());
  writer2.writeTag ("dict:desc", field.getDesc ());
  writer2.writeTag ("dict:dataType", calcDataType ());
  writeSubObjectStructureRef ();
  writer2.writeTag ("dict:readOnly", calcReadOnly ());
  writer2.decrementIndent ();
  writer2.indentPrintln ("</dict:fieldDescriptor>");
  // concrete one
  writer2.indentPrint ("<dict:fieldDescriptor");
  writer2.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldDescriptorId ());
  writeParentToAbstract (calcFieldDescriptorId ());
  writer2.indentPrintln ("/>");
 }

 private void writeConstraintDescriptor ()
 {
  writer2.writeComment (field.getConstraintDescription ());
  writer2.indentPrint ("<dict:constraintDescriptor");
  writeAbstractAttributeId (calcFieldConstraintDescriptorId ());
  writer2.indentPrintln (">");
  writer2.incrementIndent ();
  // write out referenced constraints
  for (String id : field.getConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (writer2, id);
   crw.write ();
  }

// write out in-line constraint
  ConstraintWriter cw =
   new ConstraintWriter (writer2,
                         field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (writer2,
                             field.getInlineConstraint ());
  cw.write ();
  writer2.decrementIndent ();
  writer2.indentPrintln ("</dict:constraintDescriptor>");
  // concrete one
  writer2.indentPrint ("<dict:constraintDescriptor");
  writer2.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldConstraintDescriptorId ());
  writeParentToAbstract (calcFieldConstraintDescriptorId ());
  writer2.indentPrintln ("/>");
 }

 protected String calcDataType ()
 {
  return calcDataType (field);
 }

 protected String calcDataType (Field f)
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

 protected String calcReadOnly ()
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

 protected String calcSelector ()
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

 protected String calcDynamic ()
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

}
