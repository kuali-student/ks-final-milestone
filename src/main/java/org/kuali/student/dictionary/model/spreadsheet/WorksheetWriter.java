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
public interface WorksheetWriter extends WorksheetReader
{


 /**
  * set the value with the specified column name.
  * 
  * @param name of the column heading to be compared to ignoring case.
  * @param value value to set
  * @throws DictionaryValidationException if name is not a valid column name
  */
 public void setValue (String name, String value);

 /**
  * insert a new row after the current row and make that row current
  */
 public void insert ();

 
}
