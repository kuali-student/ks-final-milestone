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
package org.kuali.student.dictionary.model.impl;

import org.kuali.student.dictionary.model.*;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetNotFoundException;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.spreadsheet.SpreadsheetReader;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetReader;

/**
 * Loads a spreadsheet using either a google or excel reader
 * @author nwright
 */
public class SearchModelLoader implements SearchModel
{

 private SpreadsheetReader reader;

 public SearchModelLoader (SpreadsheetReader reader)
 {
  this.reader = reader;
 }

 public List<SearchType> getSearchTypes ()
 {
  WorksheetReader worksheetReader;
  try
  {
   worksheetReader = reader.getWorksheetReader ("Searches");
  }
  catch (WorksheetNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  List<SearchType> list = new ArrayList ();
  SearchType searchType = null;
  int rowNumber = 1;
  String lastLookupKey = null;
  int lastLookupKeySequence = 0;
  boolean firstSearchFound = false;
  while (worksheetReader.next ())
  {
   rowNumber ++;
   String type = getFixup (worksheetReader, "Type");
   if (type.equalsIgnoreCase ("Lookup"))
   {
    SearchRow searchRow = new SearchRow ();
    loadRow (worksheetReader, searchRow, rowNumber);
    lastLookupKey = searchRow.getKey ();
    lastLookupKeySequence = 0;
    firstSearchFound = false;
   }
   else if (type.equalsIgnoreCase ("Search"))
   {
    searchType = new SearchType ();
    loadRow (worksheetReader, searchType, rowNumber);
    // give the non-default lookups a unique key
    if ( ! firstSearchFound)
    {
     searchType.setLookupKey (lastLookupKey);
     firstSearchFound = true;
    }
    else
    {
     lastLookupKeySequence ++;
     searchType.setLookupKey (lastLookupKey + ".additional." +
      lastLookupKeySequence);
    }
    list.add (searchType);
   }
   else if (type.equalsIgnoreCase ("JPQL") || type.equalsIgnoreCase ("SPECIAL"))
   {
    SearchImplementation impl = new SearchImplementation ();
    loadRow (worksheetReader, impl, rowNumber);
    //TODO: Fix this hack that gets the key set to the search key
    impl.setType (type);
    impl.setDescription (impl.getKey ());
    impl.setKey (searchType.getKey ());
    searchType.setImplementation (impl);
   }
   else if (type.equalsIgnoreCase ("Criteria"))
   {
    SearchCriteria criteria = new SearchCriteria ();
    searchType.setSearchCriteria (criteria);
    loadRow (worksheetReader, criteria, rowNumber);
   }
   else if (type.equals ("Param"))
   {
    SearchCriteriaParameter param = new SearchCriteriaParameter ();
    searchType.getSearchCriteria ().getParameters ().add (param);
    loadRow (worksheetReader, param, rowNumber);
   }
   else if (type.equalsIgnoreCase ("Result"))
   {
    SearchResult result = new SearchResult ();
    searchType.setSearchResult (result);
    loadRow (worksheetReader, result, rowNumber);
   }
   else if (type.equalsIgnoreCase ("Column"))
   {
    SearchResultColumn col = new SearchResultColumn ();
    searchType.getSearchResult ().getResultColumns ().add (col);
    loadRow (worksheetReader, col, rowNumber);
   }
   else if (type.equalsIgnoreCase (""))
   {
    continue;
   }
   else
   {
    throw new DictionaryValidationException ("Spreadsheet row #" + rowNumber +
     " has an unknown type,[" + type + "]");
   }
  }
  return list;
 }

 private void loadRow (WorksheetReader worksheetReader, SearchRow row,
                       int rowNumber)
 {
  row.setRowNumber (rowNumber);
  // trying to reuse same code for two slightly different spreadsheets
  row.setKey (getFixup (worksheetReader, "Key"));
  row.setType (getFixup (worksheetReader, "Type"));
  row.setName (getFixup (worksheetReader, "Name"));
  row.setDescription (getFixup (worksheetReader, "Description"));
  row.setDataType (getFixup (worksheetReader, "DataType"));
  row.setStatus (getFixup (worksheetReader, "Status"));
  row.setComments (getFixup (worksheetReader, "Comments"));
  row.setCaseSensitive (getFixup (worksheetReader, "Case?"));
  row.setOptional (getFixup (worksheetReader, "Optional"));
  row.setDefaultValue (getFixup (worksheetReader, "Default", "DefaultValue"));
  if (worksheetReader.getIndex ("Service") != -1)
  {
   row.setService (getFixup (worksheetReader, "Service"));
  }
  else
  {
   row.setService ("");
  }
  // default for spreadsheet that does not have these fields
  if (worksheetReader.getIndex ("WriteAccess") == -1)
  {
   row.setWriteAccess ("Always");
   row.setChildLookup ("");
   row.setHidden ("");
   row.setUsage ("");
   row.setWidget ("");
  }
  else
  {
   row.setWriteAccess (getFixup (worksheetReader, "WriteAccess"));
   row.setChildLookup (getFixup (worksheetReader, "ChildLookup"));
   row.setHidden (getFixup (worksheetReader, "Hidden"));
   row.setUsage (getFixup (worksheetReader, "Usage"));
   row.setWidget (getFixup (worksheetReader, "Widget"));
  }
 }

 private boolean isRowBlank (SearchRow row)
 {
  // TODO: uncomment the key
  if (row.getKey ().equals (""))
  {
   return true;
  }
  //if ( ! row.getLookupKey ().equals ("")) {return false;}
  if ( ! row.getType ().equals (""))
  {
   return false;
  }
  //TODO: check more fields
  return true;
 }

 private String get (WorksheetReader worksheetReader, String colName)
 {
  return get (worksheetReader, colName, null);
 }

 private String get (WorksheetReader worksheetReader,
                     String colName,
                     String colName2)
 {
  while (true)
  {
   if (worksheetReader.getIndex (colName) == -1)
   {
    if (colName2 != null)
    {
     colName = colName2;
    }
   }
   String value = worksheetReader.getValue (colName);
   if (value == null)
   {
    return "";
   }
   value = value.trim ();
   return value;
  }
 }

 private String getFixup (WorksheetReader worksheetReader, String colName)
 {
  return fixup (get (worksheetReader, colName));
 }

 private String getFixup (WorksheetReader worksheetReader, String colName,
                          String colName2)
 {
  return fixup (get (worksheetReader, colName, colName2));
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

 @Override
 public List<String> getSourceNames ()
 {
  return Arrays.asList (reader.getSourceName ());
 }

}
