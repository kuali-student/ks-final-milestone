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

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.TypeStateCaseConstraint;
import org.kuali.student.dictionary.model.CrossObjectConstraint;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class DictionaryEntryWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Dictionary dict;
 private Field field;
 private DictionaryStructureFieldWriter few;
 private XmlWriter writer;
 private Set<String> crossObjectConstraintsWritten;

 public DictionaryEntryWriter (XmlWriter writer,
                               DictionaryModel model,
                               Dictionary dict,
                               Field field,
                               DictionaryStructureFieldWriter few,
                               Set<String> crossObjectConstraintsWritten)
 {
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.dict = dict;
  this.few = few;
  this.field = field;
  this.crossObjectConstraintsWritten = crossObjectConstraintsWritten;
 }

 public void write ()
 {
  writeField ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
 }

 protected String calcFieldId ()
 {
  return dict.getId ();
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
  writer.writeComment (dict.getComments ());
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writeAbstractAttributeId (calcFieldId ());
  writeParentToAbstract (few.calcFieldId ());
  writer.println (">");
  writeRefBean ("dict:fieldDescriptorRef", calcFieldDescriptorId ());
  writeRefBean ("dict:constraintDescriptorRef", calcFieldConstraintDescriptorId ());
  writer.writeTag ("dict:selector", calcSelector ());
  writer.writeTag ("dict:dynamic", calcDynamic ());
  writer.indentPrint ("</dict:field>");
  // now write the non-abstract one
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldId ());
  writeParentToAbstract (calcFieldId ());
  writer.println ("/>");
 }

 private void writeFieldDescriptor ()
 {
  writer.indentPrintln ("<dict:fieldDescriptor");
  writeParentToAbstract (few.calcFieldDescriptorId ());
  writeAbstractAttributeId (calcFieldDescriptorId ());
  writer.println (">");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", dict.getName ());
  writer.writeTag ("dict:desc", dict.getDesc ());
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

  // write out referenced constraints
  for (String id : dict.getAdditionalConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (writer, id);
   crw.write ();
  }

// write out in-line constraints
  ConstraintWriter cw =
   new ConstraintWriter (writer,
                         field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (writer,
                             dict.getInlineConstraint ());
  cw.write ();
  // write out cross-object constraints tha are tied to this field
  TypeStateCaseConstraint tscc =
   new TypeStateCaseConstraint (getTypeStateWhens ());
  writer.incrementIndent ();
  TypeStateCaseConstraintWriter tsccw =
   new TypeStateCaseConstraintWriter (writer, tscc, model);
  tsccw.write ();
  writer.decrementIndent ();
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:constraintDescriptor>");
  // concrete one
  writer.indentPrint ("<dict:constraintDescriptor");
  writer.writeAttribute ("key", field.getShortName ());
  writeAttributeId (calcFieldConstraintDescriptorId ());
  writeParentToAbstract (calcFieldConstraintDescriptorId ());
  writer.indentPrintln ("/>");
  writer.decrementIndent ();
 }

 private String calcReadOnly ()
 {
  if (field.getConstraintIds ().contains ("read.only"))
  {
   return "true";
  }

  if (dict != null)
  {
   if (dict.getAdditionalConstraintIds ().contains ("read.only"))
   {
    return "true";
   }

  }
  return "";
 }

 private String calcSelector ()
 {
  if (dict != null)
  {
   if (dict.isSelector ())
   {
    return "true";
   }
  }
  return "";
 }

 private String calcDynamic ()
 {
  if (dict != null)
  {
   if (dict.isDynamic ())
   {
    return "true";
   }
  }
  return "";
 }

 private List<CrossObjectConstraint> getTypeStateWhens ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  for (CrossObjectConstraint coc : model.getCrossObjectConstraints ())
  {
   if (coc.getDictionaryId ().equalsIgnoreCase (dict.getId ()))
   {
    if (coc.getImplementation ().equalsIgnoreCase (CrossObjectConstraint.IMPLEMENTATION_TYPE_STATE_WHEN))
    {
     if (coc.getType1 ().equalsIgnoreCase (dict.getType ()))
     {
      if (coc.getState1 ().equalsIgnoreCase (dict.getState ())
       || coc.getState1 ().equalsIgnoreCase (State.DEFAULT))
      {
       list.add (coc);
       crossObjectConstraintsWritten.add (coc.getId ());
      }
     }
    }
   }
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

}
