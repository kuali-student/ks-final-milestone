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
package org.kuali.student.dictionary.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class DateUtility
{

 public String asYMD (String date)
 {
  if (date == null)
  {
   return null;
  }
  return asYMD (asDate (date));
 }

 public Date asDate (String date)
 {
  if (date == null)
  {
   return null;
  }
  String[] formats =
  {
   "MM/dd/yyyy",
   "yyyy-MM-dd"
  };
  for (int i = 0; i < formats.length; i ++)
  {
   DateFormat df = new SimpleDateFormat (formats[i]);
   try
   {
    return df.parse (date);
   }
   catch (ParseException e)
   {
    // ignore
   }
  }
  throw new DictionaryExecutionException ("Could not parse as a date ["
   + date
   + "]");
 }

 public String asYMD (Date date)
 {
  if (date == null)
  {
   return null;
  }
  DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
  return df.format (date);
 }

}
