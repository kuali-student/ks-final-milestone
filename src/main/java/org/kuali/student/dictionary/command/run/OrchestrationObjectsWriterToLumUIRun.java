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
package org.kuali.student.dictionary.command.run;

import org.kuali.student.dictionary.writer.search.SearchModelWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.spreadsheet.ExcelSpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.CompositeSpreadsheetReader;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.writer.orch.OrchestrationObjectsWriter;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectsWriterToLumUIRun implements RunConstants
{

 public OrchestrationObjectsWriterToLumUIRun ()
 {
 }

  public static void main (String[] args)
 {
  System.out.println ("write");
  List<SpreadsheetReader> readers = new ArrayList ();
  readers.add (new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE));
  readers.add (new ExcelSpreadsheetReader (ORCHESTRATION_DICTIONARY_EXCEL_FILE));
  readers.add (new ExcelSpreadsheetReader (SERVICE_METHODS_EXCEL_FILE));
  readers.add (new ExcelSpreadsheetReader (SERVICES_EXCEL_FILE));
  SpreadsheetReader reader = new CompositeSpreadsheetReader (readers);
  try
  {
   DictionaryModelLoader loader =
    new DictionaryModelLoader (reader);
   DictionaryModel model = new DictionaryModelCache (loader);
   OrchestrationObjectsWriter instance =
    new OrchestrationObjectsWriter (model,
                                    LUM_UI_DIRECTORY_TO_WRITE_JAVA,
                                    LUM_UI_ROOT_PACKAGE);
   instance.write ();
   File file =
    new File (LUM_UI_DIRECTORY_TO_WRITE_RESOURCES + "/" +
    "orchestration-search-config-generated-excel.xml");
   PrintStream out;
   try
   {
    out = new PrintStream (file);
   }
   catch (FileNotFoundException ex)
   {
    throw new DictionaryValidationException (ex);
   }
   try
   {
    SearchModelWriter searchWriter = new SearchModelWriter (out, model);
    searchWriter.write ();
   }
   finally
   {
    out.close ();
   }
  }
  finally
  {
   reader.close ();
  }
 }

}
