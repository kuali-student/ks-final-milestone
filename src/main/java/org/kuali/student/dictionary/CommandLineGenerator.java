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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author nwright
 */
public class CommandLineGenerator
{

 /**
  * @param args the command line arguments
  */
 public static void main (String[] args)
 {
  CommandLineGenerator instance = new CommandLineGenerator ();
  instance.generate (args);

 }

 private void displayUsage ()
 {
 }

 public void displayUsage (PrintStream out)
 {
  out.println ("Usage: java -jar dictionary.jar <inputExcel> <outputXML>");
  out.println ("\t@param inputExcel the fully qualified file name for the input excel file");
  out.println ("\t@param outputXML the fully qualified file name for the output xml file");
  out.println ("\t@note both / and \\ are allowed in directory path name");
  out.println ("ex: java mydir/org.kuali.student.dictionary.xls mydir\\lu-dictionary-config.xml ");
 }

 private void generate (String[] args)
 {
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

 protected void generate (String inFile, String outFile)
 {
  System.out.println ("Reading " + inFile + " and generating " + outFile);
  File file = new File (outFile);
  PrintStream out;
  try
  {
   out = new PrintStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new RuntimeException (ex);
  }

  SpreadsheetReader reader = null;

  try
  {
   reader = new ExcelSpreadsheetReader (inFile);
   SpreadsheetLoader loader = new SpreadsheetLoader (reader);
   Spreadsheet cache = new SpreadsheetCache (loader);
   DictionaryWriter instance = new DictionaryWriter (out, cache);
   instance.write ();
  }
  finally
  {
   out.close ();
   if (reader != null)
   {
    reader.close ();
   }
  }
 }

}
