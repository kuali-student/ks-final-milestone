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
package org.kuali.student.search;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.SpreadsheetReader;
import org.kuali.student.dictionary.WorksheetReader;

/**
 * Loads a spreadsheet using either a google or excel reader
 * @author nwright
 */
public class SpreadsheetLoader implements Spreadsheet
{

 private SpreadsheetReader spreadsheetReader;

 public SpreadsheetLoader (SpreadsheetReader spreadsheetReader)
 {
  this.spreadsheetReader = spreadsheetReader;
 }

 public List<SearchRow> getSearchRows ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Searches");
  List list = new ArrayList (worksheetReader.getEstimatedRows ());
  while (worksheetReader.next ())
  {
   SearchRow baseSearch = new SearchRow ();
   baseSearch.setKey (getFixup (worksheetReader, "Key"));
   baseSearch.setType (getFixup (worksheetReader, "Type"));
   baseSearch.setName (getFixup (worksheetReader, "Name"));
   baseSearch.setDescription (getFixup (worksheetReader, "Description"));
   baseSearch.setDataType (getFixup (worksheetReader, "DataType"));
   baseSearch.setStatus (getFixup (worksheetReader, "Status"));
   baseSearch.setComments (getFixup (worksheetReader, "Comments"));
   list.add (baseSearch);
  }
  return list;
 }

 private String get (WorksheetReader worksheetReader, String colName)
 {
  String value = worksheetReader.getValue (colName);
  if (value == null)
  {
   return "";
  }
  value = value.trim ();
  return value;
 }

 private String getFixup (WorksheetReader worksheetReader, String colName)
 {
  return fixup (get (worksheetReader, colName));
 }

 private String fixup (String str)
 {
  if (str.equals ("FALSE"))
  {
   return "false";
  }
  if (str.equals ("TRUE"))
  {
   return "true";
  }
  return str;
 }

}
