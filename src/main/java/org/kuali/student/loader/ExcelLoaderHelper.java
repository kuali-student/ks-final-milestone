/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.loader;

import java.text.ParseException;
import java.util.Date;
import org.kuali.student.dictionary.model.spreadsheet.WorksheetReader;


/**
 *
 * @author nwright
 */
public class ExcelLoaderHelper
{

 private int maxStringSize = 255;
 private WorksheetReader reader;

 public ExcelLoaderHelper (WorksheetReader reader)
 {
  this.reader = reader;
 }

 public int getMaxStringSize ()
 {
  return maxStringSize;
 }

 public void setMaxStringSize (int maxStringSize)
 {
  this.maxStringSize = maxStringSize;
 }

 public Date getFixupDate (String name)
 {
  String dateStr = getFixup (name);
  if (dateStr == null)
  {
   return null;
  }
  if (dateStr.equals (""))
  {
   return null;
  }
  // for some reason jexcel api is loading dates as 2009--07-21 with 2 dashes!??
  dateStr = dateStr.replaceAll ("--", "-");
//  System.out.println (new Date () + " trying to parse date " + dateStr);
  return new DateHelper ().asDate (dateStr);

 }

 public String get (String colName)
 {
  int index = reader.getIndex (colName);
  if (index == -1)
  {
   return null;
  }
  String value = reader.getValue (colName);
  if (value == null)
  {
   return "";
  }
  value = value.trim ();
  return value;
 }

 public String getFixup (String colName)
 {
  return fixup (get (colName));
 }

 public String truncate (String value, int maxLength)
 {
  if (value == null)
  {
   return null;
  }
  if (value.length () <= maxLength)
  {
   return value;
  }
  return value.substring (0, maxLength);
 }

 public String fixup (String str)
 {
  if (str == null)
  {
   return null;
  }
  if (str.trim ().equals (""))
  {
   return null;
  }

  // TODO: figure out why I am getting an unprintable character that gets translated to a 63 which is a ?
  //       when there is a calculation that returns null
  if (str.length () == 1)
  {
   byte[] bytes = str.getBytes ();
   if (bytes[0] == 63)
   {
    return null;
   }
  }
  if (str.equalsIgnoreCase ("FALSE"))
  {
   return "false";
  }
  if (str.equalsIgnoreCase ("TRUE"))
  {
   return "true";
  }
  return truncate (str, maxStringSize);
 }

 public boolean getFixupBoolean (String name)
 {
  String str = getFixup (name);
  if (str == null)
  {
   return false;
  }
  if (str.equals (""))
  {
   return false;
  }
  if (str.equalsIgnoreCase ("true"))
  {
   return true;
  }
  if (str.equalsIgnoreCase ("false"))
  {
   return false;
  }
  throw new IllegalArgumentException ("Could not parse " + name
                                      + " as a boolean");
 }

 public Integer getFixupInteger (String name)
 {
  String str = getFixup (name);
  if (str == null)
  {
   return null;
  }
  if (str.equals (""))
  {
   return null;
  }
  try
  {
   return Integer.parseInt (str);
  }
  catch (NumberFormatException ex)
  {
   throw new IllegalArgumentException ("Could not parse " + name
                                       + " as an integer");
  }
 }

}
