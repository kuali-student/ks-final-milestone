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
package org.kuali.student.dictionary;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This writes out the entire dictionary xml file
 * @author nwright
 */
public class DictionaryWriter extends XmlWriter
{

 private Spreadsheet sheet;
 private SpreadsheetFinder finder;

 public DictionaryWriter (PrintStream out, Spreadsheet sheet)
 {
  super (out, 0);
  this.sheet = sheet;
  this.finder = new SpreadsheetFinder (sheet);
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  Collection<String> errors = new SpreadsheetValidator (sheet).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size () +
    " errors found while validating the spreadsheet.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }
   throw new DictionaryValidationException (buf.toString ());
  }
  sheet = new ExpandedSpreadsheet (sheet, new DictionaryMainTypeExpander (sheet));
  sheet = new ExpandedSpreadsheet (sheet, new DictionarySubTypeExpander (sheet));
  this.finder = new SpreadsheetFinder (sheet);
  for (Dictionary d : sheet.getDictionary ())
  {
   if (d.getMainType ().endsWith ("*"))
   {
    throw new DictionaryValidationException ("Main type was not expanded " + d.getId () + ":" + d.getMainType ());
   }
  }
  writeHeader ();
  writeNamedConstraints ();
  writeObjectStructures ();
  writeFooter ();
  writeNotUsedDictionary ();
  writeNotUsedCrossObjectConstraints ();
 }

 /**
  * write out the header
  * @param out
  */
 protected void writeHeader ()
 {
  indentPrintln ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  indentPrintln ("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
  indentPrintln ("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
  indentPrintln ("xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\"");
  indentPrint ("xsi:schemaLocation=\"\nhttp://student.kuali.org/xsd/dictionary-extension ");
  indentPrintln ("dictionary-extension.xsd");
  //indentPrintln ("https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev/ks-common-impl/src/main/resources/dictionary-extension.xsd");
  indentPrintln ("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
  indentPrintln ("\">");
 }

 protected void writeFooter ()
 {
  indentPrintln ("</beans>");
 }

 /**
  * write out the the base fields
  * @param out
  */
 protected void writeNamedConstraints ()
 {
  for (Constraint constraint : sheet.getConstraints ())
  {
   ConstraintWriter fw =
    new ConstraintWriter (getOut (), getIndent () + 1, constraint);
   fw.write ();
  }
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeObjectStructures ()
 {
  for (XmlType xmlType : calcXMLTypesThatHaveOwnCreateUpdate ())
  {
   ObjectStructureWriter writer =
    new ObjectStructureWriter (getOut (), getIndent () + 1, sheet, xmlType, getFields (xmlType), false, null);
   writer.write ();
  }
 }

 private List<XmlType> calcXMLTypesThatHaveOwnCreateUpdate ()
 {
  List list = new ArrayList ();
  for (XmlType xmlType : sheet.getXmlTypes ())
  {
   if (xmlType.getHasOwnCreateUpdate ().equals ("true"))
   {
    list.add (xmlType);
   }
  }
  return list;
 }

 private List<Dictionary> getFields (XmlType xmlType)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : finder.findDefaultDictionary ())
  {
   if (dict.getXmlObject ().equals (xmlType.getName ()))
   {
    list.add (dict);
   }
  }
  if (list.size () == 0)
  {
   throw new DictionaryValidationException ("No default dictionary entries found for " +
    xmlType.getName ());
  }
  return list;
 }

 private void writeNotUsedDictionary ()
 {
  List<Dictionary> notUsed = calcNotUsedDictionary ();
  if (notUsed.size () == 0)
  {
   System.out.println ("All dictionary entries were written out");
   return;
  }
  System.out.println ("************");
  for (Dictionary dict : notUsed)
  {
   System.out.println (dict.getId ());
  }
  throw new DictionaryValidationException (notUsed.size () +
   " dictionary entries were never written out.");
 }

 private List<Dictionary> calcNotUsedDictionary ()
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : sheet.getDictionary ())
  {
   if ( ! FieldWriter.getDictionaryEntriesWritten ().contains (dict.getId ()))
   {
    list.add (dict);
   }
  }
  return list;
 }

 private List<CrossObjectConstraint> calcNotUsedCrossObjectConstraints ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  for (CrossObjectConstraint coc : sheet.getCrossObjectConstraints ())
  {
   if ( ! FieldWriter.getCrossObjectConstraintsWritten ().contains (coc.getId ()))
   {
    if ( ! coc.getImplementation ().equals (""))
    {
     list.add (coc);
    }
   }
  }
  return list;
 }

 private void writeNotUsedCrossObjectConstraints ()
 {
  List<CrossObjectConstraint> notUsed = calcNotUsedCrossObjectConstraints ();
  if (notUsed.size () == 0)
  {
   System.out.println ("All cross-object constraints were written out");
   return;
  }
  System.out.println ("************");
  for (CrossObjectConstraint coc : notUsed)
  {
   System.out.println (coc.getId ());
  }
  throw new DictionaryValidationException (notUsed.size () +
   " cross-object constraints were never written out.");
 }

}
