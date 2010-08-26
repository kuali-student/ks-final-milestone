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
import org.kuali.student.dictionary.writer.XmlWriter;

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
  this.field = finder.findField (dict);
  this.type = type;
  this.state = state;
  this.parentObject = parentObject;
  this.dictionaryEntriesWritten = dictionaryEntriesWritten;
 }

 protected Dictionary getDict ()
 {
  return dict;
 }

 protected Type getMainType ()
 {
  if (this.parentObject.getParentField () == null)
  {
   return type;
  }
  return this.parentObject.getParentField ().getMainType ();
 }

 protected State getMainState ()
 {
  if (this.parentObject.getParentField () == null)
  {
   return state;
  }
  return this.parentObject.getParentField ().getMainState ();
 }

 protected Type getSubType ()
 {
  if (this.parentObject.getParentField () == null)
  {
   return finder.getDefaultType ();
  }
  XmlType xmlType = finder.findXmlType (field.getXmlObject ());
  if (xmlType.hasOwnType ())
  {
   return type;
  }
  return this.parentObject.getParentField ().getSubType ();
 }

 protected State getSubState ()
 {
  if (this.parentObject.getParentField () == null)
  {
   return finder.getDefaultState ();
  }
  XmlType xmlType = finder.findXmlType (field.getXmlObject ());
  if (xmlType.hasOwnState ())
  {
   return state;
  }
  return this.parentObject.getParentField ().getSubState ();
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

 public void writeFieldStructure ()
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

 private void writeSubObjectStructureRefIfNeeded ()
 {
  if (dict.isDynamic ())
  {
   return;
  }
  if ( ! dict.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return;
  }
  if (isNotUsed ())
  {
   return;
  }
  XmlType xmlType = finder.findXmlType (field.getXmlType ());
  if (xmlType == null)
  {
   throw new DictionaryExecutionException ("Could not find dictionary field's type in list of xmltypes "
    + dict.getId () + "." + field.getXmlType ());
  }
  DictionaryOverrideWriter osw =
   new DictionaryOverrideWriter (writer,
                                 model,
                                 xmlType,
                                 this,
                                 dictionaryEntriesWritten);
  osw.writeRef ();
 }

 private boolean isNotUsed ()
 {
  if (dict.getAdditionalConstraintIds ().contains ("not.used"))
  {
   return true;
  }
  return false;
 }

 private String calcDefaultFieldId ()
 {
  return parentObject.calcFieldId (field, finder.getDefaultType (), finder.
   getDefaultState ());
 }

 protected String calcFieldId ()
 {
  return parentObject.calcFieldId (field, type, state);
 }

 private String calcDictionaryFieldId ()
 {
  return parentObject.calcDictionaryFieldId (dict);
 }

 private String calcDictionaryFieldDescriptorId ()
 {
  return calcDictionaryFieldId () + ".fd";
 }

 private String calcDictinoaryFieldConstraintDescriptorId ()
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
  //writeParentToAbstract (calcDefaultFieldId ());
  writeAbstractAttributeId (calcDictionaryFieldId ());
  writer.println (">");
  writeRefBean ("dict:fieldDescriptorRef", calcDictionaryFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptorRef", calcDictinoaryFieldConstraintDescriptorId ());
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
  writeAbstractAttributeId (calcDictionaryFieldDescriptorId ());
  writer.indentPrintln (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", dict.getName ());
  writer.writeTag ("dict:desc", dict.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());
  writeSubObjectStructureRefIfNeeded ();
  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:fieldDescriptor");
  writeAttributeId (calcDictionaryFieldDescriptorId ());
  writeParentToAbstract (calcDictionaryFieldDescriptorId ());
  writer.indentPrintln ("/>");
 }

 private void writeConstraintDescriptor ()
 {
  writer.writeComment (dict.getCombinedConstraintDescription ());
  writer.indentPrint ("<dict:constraintDescriptor");
  writeAbstractAttributeId (calcDictinoaryFieldConstraintDescriptorId ());
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
  writeAttributeId (calcDictinoaryFieldConstraintDescriptorId ());
  writeParentToAbstract (calcDictinoaryFieldConstraintDescriptorId ());
  writer.indentPrintln ("/>");
 }

 private String calcDataType ()
 {
  if (dict.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return XmlType.COMPLEX;
  }
  if (dict.isDynamic ())
  {
   return "string";
  }

  if (dict.getPrimitive ().equalsIgnoreCase ("mapped string"))
  {
   return "string";
  }

  if (dict.getPrimitive ().equalsIgnoreCase ("primitive"))
  {
   return field.getXmlType ();
  }

  throw new DictionaryValidationException ("Unexpected data value for the primitive column in field.  Found " + dict.
   getPrimitive () + " on dictionary entry " + dict.getId ());
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
  if (dict.isSelector ())
  {
   return "true";
  }
  return "";
 }

 protected String calcDynamic ()
 {
  if (dict.isDynamic ())
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
