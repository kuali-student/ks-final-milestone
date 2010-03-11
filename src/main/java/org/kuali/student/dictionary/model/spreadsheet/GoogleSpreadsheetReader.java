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

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 * Reads an on-line google spreadsheet using the gdata API
 * @author nwright
 */
public class GoogleSpreadsheetReader implements SpreadsheetReader
{

 private String application = "kuali-mit-1";
 private String userId = null;
 private String password = null;
 private String spreadsheetTitle = null;
 private String key = null;
 private String visibility = null;
 private String projection = null;

 public GoogleSpreadsheetReader ()
 {
  super ();
 }

 public void setUserCredentials (String userId, String password)
 {
  this.userId = userId;
  this.password = password;
 }

 public void setSpreadsheetTitle (String spreadsheetTitle)
 {
  this.spreadsheetTitle = spreadsheetTitle;
 }

 public void setKey (String key)
 {
  this.key = key;
 }

 public void setVisibility (String visibility)
 {
  this.visibility = visibility;
 }

 public void setProjection (String projection)
 {
  this.projection = projection;
 }

 @Override
 public List<String> getWorksheetNames ()
 {
  List<String> names = new ArrayList ();
  for (WorksheetEntry sheet : getWorksheetEntries ())
  {
   names.add (sheet.getTitle ().getPlainText ().toLowerCase ());
  }
  return names;
 }

 @Override
 public WorksheetReader getWorksheetReader (String name)
  throws WorksheetNotFoundException
 {
  name = name.toLowerCase ();
  if ( ! getWorksheetNames ().contains (name))
  {
   StringBuffer buf = new StringBuffer ();
   String comma = "";
   for (String nme : getWorksheetNames ())
   {
    buf.append (comma);
    buf.append (nme);
    comma = ", ";
   }
   throw new WorksheetNotFoundException (name + " not in " + buf.toString ());
  }
  return new GoogleWorksheetReader (this, name);
 }

 private transient SpreadsheetService service = null;

 protected SpreadsheetService getService ()
 {
  if (this.service != null)
  {
   return this.service;
  }
  this.service = new SpreadsheetService (application);
  //TODO: figure out why I have to setUserCredientials on a published public spreadsheet
  if (userId != null)
  {
   try
   {
    service.setUserCredentials (userId, password);
   }
   catch (AuthenticationException e)
   {
    throw new DictionaryExecutionException (e);
   }
  }
  return service;
 }

 public static String FEEDS_URL =
  "http://spreadsheets.google.com/feeds";

 protected WorksheetEntry getWorksheetEntry (String worksheetName)
 {
  List<WorksheetEntry> sheets = getWorksheetEntries ();
  for (WorksheetEntry sheet : sheets)
  {
   if (sheet.getTitle ().getPlainText ().equals (worksheetName))
   {
    return sheet;
   }
  }
  return null;
 }

 private List<WorksheetEntry> getWorksheetEntries ()
 {
  List<WorksheetEntry> sheets = null;
  if (key == null)
  {
   sheets = getWorksheetEntriesByUserId ();
  }
  else
  {
   sheets = getWorksheetEntriesByKey ();
  }
  return sheets;
 }

 private List<WorksheetEntry> getWorksheetEntriesByUserId ()
 {
  SpreadsheetEntry entry = getSpreadsheetEntryByUserId ();
  List<WorksheetEntry> sheets = null;
  try
  {
   sheets = entry.getWorksheets ();
  }
  catch (IOException e)
  {
   throw new DictionaryExecutionException (e);
  }
  catch (ServiceException e)
  {
   throw new DictionaryExecutionException (e);
  }
  return sheets;

 }

 private SpreadsheetEntry getSpreadsheetEntryByUserId ()
 {
  SpreadsheetFeed feed = null;
  try
  {
   URL metafeedUrl =
    new URL (FEEDS_URL + "/spreadsheets/" + visibility + "/" + projection);
   feed = getService ().getFeed (metafeedUrl, SpreadsheetFeed.class);
  }
  catch (IOException e)
  {
   throw new DictionaryExecutionException (e);
  }
  catch (ServiceException e)
  {
   throw new DictionaryExecutionException (e);
  }

  // search by name
  List<SpreadsheetEntry> sheets = feed.getEntries ();
  for (SpreadsheetEntry sheet : sheets)
  {
   //System.out.println ("\t" + sheet.getTitle ().getPlainText ());
   if (sheet.getTitle ().getPlainText ().equals (spreadsheetTitle))
   {
    return sheet;
   }
  }
  return null;
 }

 private List<WorksheetEntry> getWorksheetEntriesByKey ()
 {
  WorksheetFeed feed = null;
  try
  {
   URL url =
    new URL (FEEDS_URL + "/worksheets/" + key + "/" + visibility + "/" +
    projection);
   feed = getService ().getFeed (url, WorksheetFeed.class);
  }
  catch (IOException e)
  {
   throw new DictionaryExecutionException (e);
  }
  catch (ServiceException e)
  {
   throw new DictionaryExecutionException (e);
  }
  List<WorksheetEntry> worksheets = feed.getEntries ();
  for (WorksheetEntry sheet : worksheets)
  {
   System.out.println ("\t" + sheet.getTitle ().getPlainText ());
  }
  return worksheets;
 }

 @Override
 public void close ()
 {
  if (service == null)
  {
   return;
  }
  // there is no explicit close but this should free up memory
  service = null;
 }

 public String getSourceName ()
 {
  return "Google Spreadsheet " + spreadsheetTitle;
 }

}
