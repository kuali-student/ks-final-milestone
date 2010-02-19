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
package org.kuali.student.dictionary.command;

import org.kuali.student.dictionary.writer.dict.DictionaryModelWriter;
import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class DictionaryGeneratorFromCommandLine
{

 /**
  * @param args the command line arguments
  */
 public static void main (String[] args)
 {
  DictionaryGeneratorFromCommandLine instance =
   new DictionaryGeneratorFromCommandLine ();
  instance.generate (args);

 }

 private void displayVersion ()
 {
  displayVersion (System.out);
 }

 public void displayVersion (PrintStream out)
 {
  //TODO: figure out how to get the version from the Maven property
  out.println ("Kuali Student Dictionary Geneator: Version 0.5");
  out.println ("             Built on Tuesday, September 9, 2009");
 }

 private void displayParameters (String inFile, String outFile)
 {
  displayParameters (System.out, inFile, outFile);
 }

 public void displayParameters (PrintStream out, String inFile, String outFile)
 {
  out.println ("Reading: " + inFile);
  out.println ("Generating: " + outFile);
 }

 private void displayUsage ()
 {
  displayUsage (System.out);
 }

 public void displayUsage (PrintStream out)
 {
  out.println ("Usage: java -jar kuali-dictionary-generator.jar <inputExcel> <outputXMLDirectory>");
  out.println ("\t@param inputExcel the fully qualified file name for the input excel file");
  out.println ("\t@param outputXMLDirectory the fully qualified directory name where the output xml files should be written");
  out.println ("\t@note both / and \\ are allowed in directory path name");
  out.println ("ex: java -jar kuali-dictionary-generator.jar mydir/org.kuali.student.dictionary.xls mydir\\");
 }

 private void generate (String[] args)
 {
  displayVersion ();
  if (args == null)
  {
   displayUsage ();
   throw new DictionaryExecutionException ("args is null");
  }
  if (args.length == 0)
  {
   displayUsage ();
   throw new DictionaryExecutionException ("no args specified");
  }
  if (args.length == 1)
  {
   displayUsage ();
   throw new DictionaryExecutionException ("no output file specified");
  }
  String in = args[0];
  String out = args[1];
  generate (in, out);
 }

 protected void generate (String inFile, String outDir)
 {
  displayParameters (inFile, outDir);
  SpreadsheetReader reader = null;

  try
  {
   reader = new ExcelSpreadsheetReader (inFile);
   DictionaryModelLoader loader = new DictionaryModelLoader (reader);
   DictionaryModel cache = new DictionaryModelCache (loader);
   DictionaryModelWriter instance = new DictionaryModelWriter (outDir, cache);
   instance.write ();
  }
  finally
  {
   if (reader != null)
   {
    reader.close ();
   }
  }
 }

}
