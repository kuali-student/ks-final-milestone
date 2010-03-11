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

import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;

/**
 * Creates the dictionary XML file from the Excel source file
 * <p>
 * This is also
 * @goal excelToXML
 */
public class DictionaryGeneratorFromExcelMojo extends AbstractMojo
{

 public static String DIRECTORY = "src/test/resources/dictionary/";
 public static String DICTIONARY_INPUT_FILE = "type-state configuration.xls";
 public static String ORCHESTRATION_INPUT_FILE = "Orchestration Dictionary.xls";
 public static String SERVICE_METHODS_INPUT_FILE = "service methods.xls";

 public DictionaryGeneratorFromExcelMojo ()
 {
  this.dictionaryInputFile = null;
  this.orchestrationInputFile = null;
  this.serviceMethodsInputFile = null;
  this.xmlOutputDirectory = null;
 }

 /**
  * Path to excel file.
  *
  * @parameter
  */
 private File dictionaryInputFile;
 /**
  * Path to orchestration excel file.
  *
  * @parameter
  */
 private File orchestrationInputFile;
 /**
  * Path to orchestration excel file.
  *
  * @parameter
  */
 private File serviceMethodsInputFile;
 /**
  * Path to the XML file
  *
  * @parameter
  */
 private File xmlOutputDirectory;

 @Override
 public void execute ()
  throws MojoExecutionException,
         MojoFailureException
 {
  try
  {
   List<SpreadsheetReader> readers = new ArrayList ();
   readers.add (new ExcelSpreadsheetReader (dictionaryInputFile.getCanonicalPath ()));
   readers.add (new ExcelSpreadsheetReader (orchestrationInputFile.
    getCanonicalPath ()));
   readers.add (new ExcelSpreadsheetReader (serviceMethodsInputFile.
    getCanonicalPath ()));
   SpreadsheetReader reader = new CompositeSpreadsheetReader (readers);
   DictionaryModelLoader loader =
    new DictionaryModelLoader (reader);
   DictionaryModel cache = new DictionaryModelCache (loader);
   DictionaryModelWriter instance = new DictionaryModelWriter (xmlOutputDirectory.
    getCanonicalPath (), cache);
   instance.write ();
  }
  catch (DictionaryValidationException ex)
  {
   throw new MojoFailureException (ex.getMessage ());
  }
  catch (DictionaryExecutionException ex)
  {
   throw new MojoExecutionException ("Error generating dictionary", ex);
  }
  catch (Exception ex)
  {
   throw new MojoExecutionException ("Unexpected Exception while trying to generate the dictionary", ex);
  }

 }

 public File getDictionaryInputFile ()
 {
  return dictionaryInputFile;


 }

 public void setDictionaryInputlFile (File file)
 {
  this.dictionaryInputFile = file;


 }

 public File getOrchestrationInputFile ()
 {
  return orchestrationInputFile;


 }

 public void setOrchestrationInputlFile (File file)
 {
  this.orchestrationInputFile = file;


 }

 public File getServiceMethodsInputFile ()
 {
  return serviceMethodsInputFile;


 }

 public void setServiceMethosInputlFile (File file)
 {
  this.serviceMethodsInputFile = file;


 }

 public File getXmlOutputDirectory ()
 {
  return xmlOutputDirectory;


 }

 public void setXmlOutputDirectory (File xmlFile)
 {
  this.xmlOutputDirectory = xmlFile;

 }

}
