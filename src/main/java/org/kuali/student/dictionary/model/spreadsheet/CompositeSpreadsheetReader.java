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
package org.kuali.student.dictionary.model.spreadsheet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nwright
 */
public class CompositeSpreadsheetReader implements SpreadsheetReader
{

 private List<SpreadsheetReader> readers;

 public CompositeSpreadsheetReader (List<SpreadsheetReader> readers)
 {
  this.readers = readers;
 }

 @Override
 public void close ()
 {
  for (SpreadsheetReader reader : readers)
  {
   reader.close ();
  }
 }

 @Override
 public String getSourceName ()
 {
  StringBuffer buf = new StringBuffer ();
  String comma = "";
  for (SpreadsheetReader reader : readers)
  {
   buf.append (comma);
   comma = ", ";
   buf.append (reader.getSourceName ());
  }
  return buf.toString ();
 }

 private transient List<String> worksheetNames = null;
 private transient Set<String> lowercaseNames = null;

 @Override
 public List<String> getWorksheetNames ()
 {
  if (worksheetNames != null)
  {
   return worksheetNames;
  }
  worksheetNames = new ArrayList ();
  lowercaseNames = new HashSet ();
  for (SpreadsheetReader reader : readers)
  {
   for (String name : reader.getWorksheetNames ())
   {
    name = name.toLowerCase ();
    if (lowercaseNames.add (name))
    {
     worksheetNames.add (name);
    }
   }
  }
  return worksheetNames;
 }

 @Override
 public WorksheetReader getWorksheetReader (String name)
  throws WorksheetNotFoundException
 {
  name = name.toLowerCase ();
  getWorksheetNames (); // make sure lowercaseNames is initialized
  if ( ! lowercaseNames.contains (name))
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
  List<WorksheetReader> worksheetReaders = new ArrayList ();
  for (SpreadsheetReader reader : readers)
  {
   if (reader.getWorksheetNames ().contains (name))
   {
    WorksheetReader worksheetReader = reader.getWorksheetReader (name);
    worksheetReaders.add (worksheetReader);
   }
  }
  return new CompositeWorksheetReader (worksheetReaders, name);
 }

}
