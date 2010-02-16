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
import org.kuali.student.dictionary.model.Dictionary;
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
public class DictionaryOverrideFieldWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Dictionary dict;
 private Field field;
 private XmlWriter writer;
 private Type type;
 private State state;
 private DictionaryOverrideWriter parentObject;
 private Set<String> dictionaryEntriesWritten;

 public DictionaryOverrideFieldWriter (XmlWriter writer,
                                       DictionaryModel model,
                                       Dictionary dict,
                                       Type type,
                                       State state,
                                       DictionaryOverrideWriter parentObject,
                                       Set<String> dictionaryEntriesWritten)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.dict = dict;
  this.field = finder.findField (dict.getXmlObject (), dict.getShortName ());
  this.type = type;
  this.state = state;
  this.parentObject = parentObject;
  this.dictionaryEntriesWritten = dictionaryEntriesWritten;
 }

 protected Dictionary getDict ()
 {
  return dict;
 }

 protected Type getType ()
 {
  return type;
 }

 protected State getState ()
 {
  return state;
 }

 protected DictionaryOverrideWriter getParentObject ()
 {
  return parentObject;
 }

 public void write ()
 {
  writeField ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
 }


 protected void writeSubObjectStructure ()
 {
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find dictionary field's type in list of xmltypes "
    + dict.getId () + "." + field.getXmlType ());
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   System.out.println ("Writing out subfield object structure for "
    + dict.getId () + " " + xmlType.getName ());
   DictionaryOverrideWriter osw =
    new DictionaryOverrideWriter (writer,
                                  model,
                                  xmlType,
                                  this,
                                  dictionaryEntriesWritten);
   osw.write ();
  }
 }

 private void writeSubObjectStructureRef ()
 {
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find dictionary field's type in list of xmltypes "
    + dict.getId () + "." + field.getXmlType ());
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   DictionaryOverrideWriter osw =
    new DictionaryOverrideWriter (writer,
                                  model,
                                  xmlType,
                                  this,
                                  dictionaryEntriesWritten);
   osw.writeRef ();
  }
 }

 protected String calcDefaultFieldId ()
 {
  return parentObject.calcFieldId (field, finder.getDefaultType (), finder.getDefaultState ());
 }

 protected String calcFieldId ()
 {
  return parentObject.calcFieldId (field, type, state);
 }


 protected String calcDictionaryFieldId ()
 {
  return parentObject.calcFieldId (dict);
 }

 protected String calcFieldDescriptorId ()
 {
  return calcDictionaryFieldId () + ".fd";
 }

 protected String calcFieldConstraintDescriptorId ()
 {
  return calcDictionaryFieldId () + ".cd";
 }

 private void writeField ()
 {
  writer.indentPrintln ("");
  writer.writeCommentBox (calcDictionaryFieldId ());
  writer.writeComment (dict.getComments ());
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", dict.getShortName ());
  writeParentToAbstract (calcDefaultFieldId ());
  writeAbstractAttributeId (calcDictionaryFieldId ());
  writer.println (">");
  writeRefBean ("dict:fieldDescriptorRef", calcFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptorRef", calcFieldConstraintDescriptorId ());
  writer.writeTag ("dict:selector", calcSelector ());
  writer.writeTag ("dict:dynamic", calcDynamic ());
  writer.indentPrintln ("</dict:field>");
  // now write the non-abstract one
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", dict.getShortName ());
  writeAttributeId (calcDictionaryFieldId ());
  writeParentToAbstract (calcDictionaryFieldId ());
  writer.println ("/>");
 }

 private void writeFieldDescriptor ()
 {
  writer.indentPrint ("<dict:fieldDescriptor");
  writer.writeAttribute ("key", dict.getShortName ());
  writeAbstractAttributeId (calcFieldDescriptorId ());
  writer.indentPrintln (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", dict.getName ());
  writer.writeTag ("dict:desc", dict.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());
  writeSubObjectStructureRef ();
  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:fieldDescriptor");
  writer.writeAttribute ("key", dict.getShortName ());
  writeAttributeId (calcFieldDescriptorId ());
  writeParentToAbstract (calcFieldDescriptorId ());
  writer.indentPrintln ("/>");
 }

 private void writeConstraintDescriptor ()
 {
  writer.writeComment (dict.getCombinedConstraintDescription ());
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
  for (String id : dict.getAdditionalConstraintIds ())
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
                             dict.getInlineConstraint ());
  cw.write ();
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:constraintDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:constraintDescriptor");
  writer.writeAttribute ("key", dict.getShortName ());
  writeAttributeId (calcFieldConstraintDescriptorId ());
  writeParentToAbstract (calcFieldConstraintDescriptorId ());
  writer.indentPrintln ("/>");
 }

 protected String calcDataType ()
 {
  return calcDataType (field);
 }

 protected String calcDataType (Field f)
 {
  if (f.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return XmlType.COMPLEX;
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

  if (dict.getAdditionalConstraintIds ().contains ("read.only"))
  {
   return "true";
  }
  return "";
 }

 protected String calcSelector ()
 {
  if (dict.getSelector ().equals ("true"))
  {
   return "true";
  }
  return "";
 }

 protected String calcDynamic ()
 {
  if (field.getDynamic ().equals ("true"))
  {
   return "true";
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
