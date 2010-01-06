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
package org.kuali.student.dictionary.model.spreadsheet;

import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 * Reads a single tab of a google spreadsheet using the Gdata API
 * @author nwright
 */
public class GoogleWorksheetReader implements WorksheetReader
{

 private GoogleSpreadsheetReader googleSpreadsheetReader;
 private String worksheetName;
 private WorksheetEntry worksheetEntry;
 private ListFeed listFeed;
 private List<ListEntry> listEntries;
 private Iterator<ListEntry> it;
 private ListEntry currentListEntry;
 private CustomElementCollection currentCustomElementCollection;

 public GoogleWorksheetReader (GoogleSpreadsheetReader googleSpreadsheetReader,
                               String worksheetName)
 {
  this.googleSpreadsheetReader = googleSpreadsheetReader;
  this.worksheetName = worksheetName;
  reopen ();
 }

 @Override
 public void reopen ()
 {
  worksheetEntry = googleSpreadsheetReader.getWorksheetEntry (worksheetName);
  URL url = worksheetEntry.getListFeedUrl ();
  try
  {
   listFeed =
    this.googleSpreadsheetReader.getService ().getFeed (url, ListFeed.class);
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  catch (ServiceException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  this.listEntries = listFeed.getEntries ();
  this.it = this.listEntries.iterator ();
 }

 @Override
 public int getIndex (String name)
 {
  name = name.toLowerCase ().trim ();
  int col = 0;
  for (String tag : currentCustomElementCollection.getTags ())
  {
   if (tag.equals (name))
   {
    return col;
   }
   col ++;
  }
  return -1;
 }

 @Override
 public String getValue (String name)
 {
  name = name.toLowerCase ().trim ();
  if ( ! currentCustomElementCollection.getTags ().contains (name))
  {
   throw new DictionaryValidationException ("ColName=" + name +
    " does not exist in " +
    currentCustomElementCollection.getTags ());
  }
  String value = currentCustomElementCollection.getValue (name);
  if (value == null)
  {
   return "";
  }
  return value.trim ();
 }

 @Override
 public boolean next ()
 {
  try
  {
   currentListEntry = it.next ();
   currentCustomElementCollection = currentListEntry.getCustomElements ();
   return true;
  }
  catch (NoSuchElementException e)
  {
   return false;
  }

 }

 @Override
 public int getEstimatedRows ()
 {
  return this.worksheetEntry.getRowCount ();
 }

 @Override
 public void close ()
 {
  worksheetEntry = null;
  listFeed = null;
  listEntries = null;
  it = null;
  currentListEntry = null;
  currentCustomElementCollection = null;
 }

}
