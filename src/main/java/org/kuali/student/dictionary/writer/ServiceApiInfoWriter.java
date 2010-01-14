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

import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.ModelFinder;

/**
 *
 * @author nwright
 */
public class ServiceApiInfoWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.service";
 private Service service;
 private XmlType xmlType;

 public ServiceApiInfoWriter (DictionaryModel model,
                              String directory,
                              Service service,
                              XmlType xmlType)
 {
  super (directory, calcPackage (service), calcClassName (xmlType.getName ()));
  this.model = model;
  this.directory = directory;
  this.service = service;
  this.xmlType = xmlType;
 }

 private static String calcPackage (Service service)
 {
  return ServiceApiMethodsWriter.calcPackage (service);
 }

 public static String calcClassName (String name)
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public interface " + calcClassName (xmlType.getName ()));
  openBrace ();

  List<MessageStructure> list =
   new ModelFinder (model).findMessageStructures (xmlType.getName ());
  if (list.size () == 0)
  {
   throw new DictionaryExecutionException ("xmlType " + xmlType.getName () +
    " has no fields defined in the message structure tab");
  }
  for (MessageStructure ms : list)
  {
   String type = this.calcFieldTypeToUse (ms.getType ());
   indentPrintln ("");
   indentPrintln ("/**");
   indentPrintln ("* Set " + ms.getName ());
   indentPrintln ("*");
   indentPrintln ("* " + ms.getDescription ());
   indentPrintln ("*/");
   indentPrintln ("public void " + calcSetter (ms) + "(" + type + " " + ms.
    getShortName () + ");");


   indentPrintln ("");
   indentPrintln ("/**");
   indentPrintln ("* Get " + ms.getName ());
   indentPrintln ("*");
   indentPrintln ("* " + ms.getDescription ());
   indentPrintln ("*/");
   indentPrintln ("public " + type + " " + calcGetter (ms) + "();");
   indentPrintln ("");

   indentPrintln ("");
  }
  indentPrintln ("");
  closeBrace ();

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private String calcGetter (MessageStructure ms)
 {
  if (calcFieldTypeToUse (ms.getType ()).equals ("Boolean"))
  {
   return "is" + calcProperName (ms.getShortName ());
  }
  return "get" + calcProperName (ms.getShortName ());
 }

 private String calcSetter (MessageStructure ms)
 {
  return "set" + calcProperName (ms.getShortName ());
 }

 private String calcProperName (String name)
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 private String calcFieldTypeToUse (String type)
 {
  return ServiceMessageStructureTypeCalculator.calculate (this, model, type);
 }

}
