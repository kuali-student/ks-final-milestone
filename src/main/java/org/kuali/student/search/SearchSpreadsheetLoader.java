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
public class SearchSpreadsheetLoader implements SearchSpreadsheet
{

 private SpreadsheetReader spreadsheetReader;

 public SearchSpreadsheetLoader (SpreadsheetReader spreadsheetReader)
 {
  this.spreadsheetReader = spreadsheetReader;
 }

 public List<SearchType> getSearchTypes ()
 {
  WorksheetReader worksheetReader =
   spreadsheetReader.getWorksheetReader ("Searches");
  List<SearchType> list = new ArrayList (worksheetReader.getEstimatedRows ());
  SearchType searchType = null;
  int rowNumber = 1;
  while (worksheetReader.next ())
  {
   rowNumber++;
   String type = getFixup (worksheetReader, "Type");
   if (type.equals ("Search"))
   {
    searchType = new SearchType ();
    loadRow (worksheetReader, searchType, rowNumber);
    list.add (searchType);
   }
   else if (type.equals ("JPQL"))
   {
    SearchImplementation impl = new SearchImplementation ();
    loadRow (worksheetReader, impl, rowNumber);
    //TODO: Fix this hack that gets the key set to the search key
    impl.setDescription (impl.getKey ());
    impl.setKey (searchType.getKey ());
    searchType.setImplementation (impl);
   }
   else if (type.equals ("Criteria"))
   {
    SearchCriteria criteria = new SearchCriteria ();
    searchType.setCriteria (criteria);
    loadRow (worksheetReader, criteria, rowNumber);
   }
   else if (type.equals ("Param"))
   {
    SearchCriteriaParameter param = new SearchCriteriaParameter ();
    searchType.getCriteria ().getParameters ().add (param);
    loadRow (worksheetReader, param, rowNumber);
   }
   else if (type.equals ("Result"))
   {
    SearchResult result = new SearchResult ();
    searchType.setResults (result);
    loadRow (worksheetReader, result, rowNumber);
   }
   else if (type.equals ("Column"))
   {
    SearchResultColumn col = new SearchResultColumn ();
    searchType.getResults ().getResultColumns ().add (col);
    loadRow (worksheetReader, col, rowNumber);
   }
   else
   {
    throw new SearchValidationException ("Spreadsheet row #" + rowNumber + " has an unknown type,[" + type + "]");
   }
  }
  return list;
 }

 private void loadRow (WorksheetReader worksheetReader, SearchRow row, int rowNumber)
 {
  row.setRowNumber (rowNumber);
  row.setKey (getFixup (worksheetReader, "Key"));
  row.setType (getFixup (worksheetReader, "Type"));
  row.setName (getFixup (worksheetReader, "Name"));
  row.setDescription (getFixup (worksheetReader, "Description"));
  row.setDataType (getFixup (worksheetReader, "DataType"));
  row.setStatus (getFixup (worksheetReader, "Status"));
  row.setComments (getFixup (worksheetReader, "Comments"));
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

 public String getSourceName ()
 {
  return spreadsheetReader.getSourceName ();
 }
}
