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

import java.util.List;

/**
 *
 * @author nwright
 */
public class CompositeWorksheetReader implements WorksheetReader
{

 private List<WorksheetReader> readers;
 private int currentReader = 0;
 private String name;

 public CompositeWorksheetReader (List<WorksheetReader> readers, String name)
 {
  this.readers = readers;
  this.name = name;
 }

 public void close ()
 {
  for (WorksheetReader reader : readers)
  {
   reader.close ();
  }
 }

 public int getEstimatedRows ()
 {
  int estRows = 0;
  for (WorksheetReader reader : readers)
  {
   estRows = estRows + reader.getEstimatedRows ();
  }
  return estRows;
 }

 public int getIndex (String name)
 {
  return readers.get (currentReader).getIndex (name);
 }

 public String getValue (String name)
 {
  return readers.get (currentReader).getValue (name);
 }

 public boolean next ()
 {
  if (readers.get (currentReader).next ())
  {
   return true;
  }
  currentReader++;
  if (currentReader < readers.size ())
  {
   return this.next ();
  }
  return false;
 }

 public void reopen ()
 {
  this.close ();
  this.currentReader = 0;
  for (WorksheetReader reader : readers)
  {
   reader.reopen ();
  }
 }

}
