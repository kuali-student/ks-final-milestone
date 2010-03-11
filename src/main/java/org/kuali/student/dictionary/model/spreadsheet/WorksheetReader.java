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

/**
 * Interface required to get data from a single tab of a spreadsheet
 * @author nwright
 */
public interface WorksheetReader
{

 /**
  * get estimated number of rows.
  * @return estimated number of rows
  */
 public int getEstimatedRows ();

 /**
  * Get the value with the specified column name
  * @param name to be compared ignoring case.
  * @return nulls converted to "" and values trimmed.
  * @throws DictionaryValidationException if name is not a valid column name
  */
 public String getValue (String name);

 /**
  * Get the index to the column with the specified column name.
  * @param name to be compared ignoring case
  * @return -1 if not found
  */
 public int getIndex (String name);

 /**
  * Move to the next row in the worksheet
  * @return false if no next row
  */
 public boolean next ();

 /**
  * Reopen the resource
  *  Note: the resource is assumed to be automatically opened when created.
  */
 public void reopen ();

 /**
  * Close and free any resources
  * Do nothing if already closed.
  */
 public void close ();

}
