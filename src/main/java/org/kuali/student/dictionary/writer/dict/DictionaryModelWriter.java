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

import java.io.File;
import java.io.FileNotFoundException;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryModelValidator;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.CrossObjectConstraint;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Constraint;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * This writes out the entire dictionary xml file
 * @author nwright
 */
public class DictionaryModelWriter
{

 private DictionaryModel model;
 private ModelFinder finder;
 private String directory;
 private Set<String> dictionaryEntriesWritten = new HashSet ();
 private Set<String> crossObjectConstraintsWritten = new HashSet ();

 public DictionaryModelWriter (String directory, DictionaryModel sheet)
 {
  this.directory = directory;
  this.model = sheet;
  this.finder = new ModelFinder (sheet);
 }

 private static String CONSTRAINT_BANK_FILE_NAME =
  "constraints-dictionary-config.xml";

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  //TODO: remember to uncomment these two before going to productions just did this to speed up testing
  validate ();
  writeNamedConstraints ();

  for (String service : calcServicesThatHaveXMLTypesThatHaveOwnCreateUpdate ())
  //TODO: repace below with above once testing is done
//  for (String service : getLuServiceAsListForTesting ())
  {
   File file = new File (directory + service
    + "-dictionary-config.xml");
   PrintStream out;
   try
   {
    out = new PrintStream (file);
   }
   catch (FileNotFoundException ex)
   {
    throw new DictionaryExecutionException (ex);
   }
   XmlWriter writer = new XmlWriter (out, 0);
   try
   {
    writeHeader (writer);
    writeImport (writer, CONSTRAINT_BANK_FILE_NAME);
    writeObjectStructures (service, writer);
    writeFooter (writer);
   }
   finally
   {
    writer.getOut ().close ();
   }
  }
  checkNotWrittenDictionary ();
  //TODO: figure out why Cross-object constraints are not being writte and
  // add back in this check
  //checkNotUsedCrossObjectConstraints ();
 }

 private void validate ()
 {
  Collection<String> errors = new DictionaryModelValidator (model).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size ()
    + " errors found while validating the spreadsheet.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }
   throw new DictionaryValidationException (buf.toString ());
  }
 }

 /**
  * write out the header
  * @param out
  */
 protected void writeHeader (XmlWriter writer)
 {
  writer.indentPrintln ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  writer.indentPrintln ("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
  writer.indentPrintln ("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
  writer.indentPrintln ("xmlns:dict=\"http://student.kuali.org/xsd/dictionary-extension\"");
  writer.indentPrint ("xsi:schemaLocation=\"\nhttp://student.kuali.org/xsd/dictionary-extension ");
  //writer.indentPrintln ("dictionary-extension.xsd");
  writer.indentPrintln ("http://student.kuali.org/xsd/dictionary-extension/dictionary-extension.xsd");
  writer.indentPrintln ("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
  writer.indentPrintln ("\">");
  StringBuffer buf = new StringBuffer ();
  buf.append ("*** Automatically Generated ***");
  buf.append ("\n");
  buf.append ("by: " + this.getClass ().getName ());
  buf.append ("\n");
  String prefix = "Using:";
  for (String name : model.getSourceNames ())
  {
   buf.append (prefix + name);
   prefix = "   and: ";
  }

  buf.append ("\n");
  writer.writeComment (buf.toString ());
 }

 /**
  * write out the header
  * @param out
  */
 protected void writeImport (XmlWriter writer, String fileName)
 {
  writer.indentPrint ("<import");
  writer.writeAttribute ("resource", "classpath:" + fileName);
  writer.println ("/>");
 }

 protected void writeFooter (XmlWriter writer)
 {
  writer.indentPrintln ("</beans>");
 }

 /**
  * write out the the base fields
  * @param out
  */
 protected void writeNamedConstraints ()
 {
  System.out.println ("Writing out bank of constraints");
  File file =
   new File (directory + CONSTRAINT_BANK_FILE_NAME);
  PrintStream out;
  try
  {
   out = new PrintStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  XmlWriter writer = new XmlWriter (out, 0);
  try
  {

   writeHeader (writer);
   writer.incrementIndent ();
   for (Constraint constraint : model.getConstraints ())
   {
    ConstraintWriter fw =
     new ConstraintWriter (writer, constraint);
    fw.write ();
   }
   writer.decrementIndent ();
   writeFooter (writer);
  }
  finally
  {
   writer.getOut ().close ();
  }
 }

 /**
  * write out the the object structure
  * @param out
  */
 protected void writeObjectStructures (String service, XmlWriter writer)
 {
  for (XmlType xmlType : calcXMLTypesForServiceThatHaveOwnCreateUpdate (service))
  {
//   writeImport (writer, CONSTRAINT_BANK_FILE_NAME);
   String fileName2 = service + "-" + xmlType.getName ()
    + "-dictionary-structure-config.xml";
   String fileName3 = service + "-" + xmlType.getName ()
    + "-dictionary-override-config.xml";
   writeImport (writer, fileName3);
   File file2 = new File (directory + fileName2);
   File file3 = new File (directory + fileName3);
   PrintStream out2;
   PrintStream out3;
   try
   {
    out2 = new PrintStream (file2);
    out3 = new PrintStream (file3);
   }
   catch (FileNotFoundException ex)
   {
    throw new DictionaryExecutionException (ex);
   }
   XmlWriter writer2 = new XmlWriter (out2, 0);
   XmlWriter writer3 = new XmlWriter (out3, 0);
   try
   {
    // first write the default structure
    writeHeader (writer2);
//    writeImport (writer2, CONSTRAINT_BANK_FILE_NAME);
    DictionaryStructureWriter dsw =
     new DictionaryStructureWriter (writer2,
                                    model,
                                    xmlType,
                                    null);
    dsw.write ();

    // now do the dictinoary overrides
    writeHeader (writer3);
//    writeImport (writer3, CONSTRAINT_BANK_FILE_NAME);
    writeImport (writer3, fileName2);
    DictionaryOverrideWriter dow =
     new DictionaryOverrideWriter (writer3,
                                   model,
                                   xmlType,
                                   null,
                                   dictionaryEntriesWritten);
    dow.write ();
    writeFooter (writer2);
    writeFooter (writer3);
   }
   finally
   {
    writer2.getOut ().close ();
    writer3.getOut ().close ();
   }
  }
 }

// private List<XmlType> calcXMLTypesUsedByService (String service)
// {
//  Map<String, XmlType> map = new LinkedHashMap ();
//  for (XmlType xmlType : calcXMLTypesForServiceThatHaveOwnCreateUpdate (service))
//  {
//   if ( ! map.containsKey (xmlType.getName ().toLowerCase ()))
//   {
//    map.put (xmlType.getName ().toLowerCase (), xmlType);
//   }
//  }
//  List<XmlType> newXmlTypes = new ArrayList (map.values ());
//  while (newXmlTypes.size () > 0)
//  {
//   newXmlTypes = loadComplexSubXMLTypes (map, newXmlTypes);
//  }
//  return new ArrayList (map.values ());
// }
//
// private List<XmlType> loadComplexSubXMLTypes (Map<String, XmlType> map,
//                                               List<XmlType> xmlTypesToProcess)
// {
//  List<XmlType> newXmlTypes = new ArrayList ();
//  for (XmlType xmlType : xmlTypesToProcess)
//  {
//   for (MessageStructure ms : finder.findMessageStructures (xmlType.getName ()))
//   {
//    XmlType subType = finder.findXmlType (fixupType (ms.getType ()));
//    if (subType == null)
//    {
//     throw new DictionaryExecutionException ("Message structure " + ms.getId ()
//      + " has a type " + ms.getType () + " that does not exist");
//    }
//    if (subType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
//    {
//     if (map.put (subType.getName ().toLowerCase (), subType) == null)
//     {
//      newXmlTypes.add (xmlType);
//     }
//    }
//   }
//  }
//  return newXmlTypes;
// }
 private String fixupType (String type)
 {
  // special fix up because lists are not explicitly handled
  if (type.toLowerCase ().endsWith ("list"))
  {
   return type.substring (0, type.length () - "list".length ());
  }
  return type;
 }

 private List<String> getLuServiceAsListForTesting ()
 {
  List<String> list = new ArrayList ();
  list.add ("lu");
  return list;
 }

 private Set<String> calcServicesThatHaveXMLTypesThatHaveOwnCreateUpdate ()
 {
  Set<String> set = new LinkedHashSet ();
  for (XmlType xmlType : calcXMLTypesThatHaveOwnCreateUpdate ())
  {
   if (xmlType.getService () != null &&  ! xmlType.getService ().equals (""))
   {
    set.add (xmlType.getService ());
   }
  }
  return set;
 }
 private List<XmlType> calcXMLTypesForServiceThatHaveOwnCreateUpdate (
  String service)
 {
  List list = new ArrayList ();
  for (XmlType xmlType : calcXMLTypesThatHaveOwnCreateUpdate ())
  {
   if (xmlType.getService ().equalsIgnoreCase (service))
   {
    list.add (xmlType);
   }
  }
  return list;
 }

 private List<XmlType> xmlTypesThatHaveOwnCreateUpdate = null;

 private List<XmlType> calcXMLTypesThatHaveOwnCreateUpdate ()
 {
  if (xmlTypesThatHaveOwnCreateUpdate != null)
  {
   return xmlTypesThatHaveOwnCreateUpdate;
  }
  List list = new ArrayList ();
  for (XmlType xmlType : model.getXmlTypes ())
  {
   if (xmlType.hasOwnCreateUpdate ())
   {
    list.add (xmlType);
   }
  }
  xmlTypesThatHaveOwnCreateUpdate = list;
  return xmlTypesThatHaveOwnCreateUpdate;
 }

 private void checkNotWrittenDictionary ()
 {
  List<Dictionary> notUsed = calcNotUsedDictionary ();
  if (notUsed.size () == 0)
  {
   System.out.println ("All dictionary entries were written out");
   return;
  }
  System.out.println ("************");
  StringBuffer sb = new StringBuffer ();
  for (Dictionary dict : notUsed)
  {
   System.out.println (dict.getId ());
   sb.append ("\n");
   sb.append (dict.getId ());
   sb.append ("\n");
  }
  throw new DictionaryValidationException (notUsed.size ()
   + " dictionary entries were never written out." + sb.toString ());
 }

 private List<Dictionary> calcNotUsedDictionary ()
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : model.getDictionary ())
  {
   if ( ! dictionaryEntriesWritten.contains (dict.getId ()))
   {
    list.add (dict);
   }
  }
  return list;
 }

 private List<CrossObjectConstraint> calcNotUsedCrossObjectConstraints ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  for (CrossObjectConstraint coc : model.getCrossObjectConstraints ())
  {
   if ( ! crossObjectConstraintsWritten.contains (coc.getId ()))
   {
    if ( ! coc.getImplementation ().equals (""))
    {
     list.add (coc);
    }
   }
  }
  return list;
 }

 private void checkNotUsedCrossObjectConstraints ()
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
  throw new DictionaryValidationException (notUsed.size ()
   + " cross-object constraints were never written out.");
 }

}
