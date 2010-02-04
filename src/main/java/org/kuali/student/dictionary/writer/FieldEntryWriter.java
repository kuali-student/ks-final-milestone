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
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class FieldEntryWriter
{

 private DictionaryModel spreadsheet;
 private ModelFinder finder;
 private Field field;
 private XmlWriter writer;

 public FieldEntryWriter (XmlWriter writer,
                          DictionaryModel spreadsheet,
                          Field field)
 {
  this.writer = writer;
  this.spreadsheet = spreadsheet;
  this.finder = new ModelFinder (spreadsheet);
  this.field = field;
 }

 public void write ()
 {

  // write it out
  writeFieldHeader ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
  writeSelector ();
  writeDynamic ();
  writeFieldFooter ();
 }

 private void writeFieldHeader ()
 {
  writer.indentPrintln ("");
  writer.writeComment (field.getComments ());
  writer.writeComment (field.getComments ());
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writer.writeAttribute ("id", "field." + field.getId () + ".abstract");
  writer.writeAttribute ("abstract", "true");
  writer.println (">");
  writer.incrementIndent ();
 }

 private void writeFieldFooter ()
 {
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:field>");
  // now write the non-abstract one
  writer.indentPrint ("<dict:field");
  writer.writeAttribute ("key", field.getShortName ());
  writer.writeAttribute ("id", "field." + field.getId ());
  writer.writeAttribute ("parent", "field." + field.getId () + ".abstract");
  writer.println (">");
  writer.indentPrintln ("</dict:field>");
 }

 private void writeFieldDescriptor ()
 {
  writer.indentPrintln ("<dict:fieldDescriptor>");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", field.getName ());
  writer.writeTag ("dict:desc", field.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());

  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
 }

 private void writeSelector ()
 {
  writer.writeTag ("dict:selector", calcSelector ());
 }

 private void writeDynamic ()
 {
  writer.writeTag ("dict:dynamic", calcDynamic ());
 }

 private void writeConstraintDescriptor ()
 {
  writer.indentPrintln ("<dict:constraintDescriptor>");
  writer.incrementIndent ();
  // write out referenced constraints
  for (String id : field.getConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (writer.getOut (), writer.
    getIndent () + 1, id);
   crw.write ();
  }

// write out in-line constraint
  ConstraintWriter cw =
   new ConstraintWriter (writer.getOut (),
                         writer.getIndent () + 1,
                         field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (writer.getOut (),
                             writer.getIndent () + 1,
                             field.getInlineConstraint ());
  cw.write ();

  writer.decrementIndent ();
  writer.indentPrintln ("</dict:constraintDescriptor>");
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

}
