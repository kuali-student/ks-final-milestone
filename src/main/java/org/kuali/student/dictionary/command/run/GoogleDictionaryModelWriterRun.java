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

import org.kuali.student.dictionary.model.impl.DictionaryModelCache;
import org.kuali.student.dictionary.model.impl.DictionaryModelLoader;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.spreadsheet.GoogleSpreadsheetReader;
import org.kuali.student.dictionary.writer.dict.DictionaryModelWriter;

/**
 *
 * @author nwright
 */
public class GoogleDictionaryModelWriterRun implements RunConstants
{

 public GoogleDictionaryModelWriterRun ()
 {
 }

 

 //private static String USER_ID = "nwright@mit.edu";
 //private static String PASSWORD = "xxxxx";
 private static String SPREADSHEET_KEY = "tSdKqlIJ1piKNBie4_H0hoA";

 public static void main (String[] args)
 {
  System.out.println ("writeGoogleDictionary");
  
  GoogleSpreadsheetReader reader = new GoogleSpreadsheetReader ();
  // don't need user name and password if spreadsheet is published
  //reader.setUserCredentials (USER_ID, PASSWORD);
  reader.setKey (SPREADSHEET_KEY);
  reader.setVisibility ("public");
  reader.setProjection ("values");
  DictionaryModel spreadsheet = new DictionaryModelLoader (reader);
  spreadsheet = new DictionaryModelCache (spreadsheet);
  DictionaryModelWriter instance = new DictionaryModelWriter (RESOURCES_DIRECTORY, spreadsheet);
  instance.write ();
 }

}