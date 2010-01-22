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

import java.util.List;

/**
 * interface that can be implemented for both google and excel spreadsheets
 * @author nwright
 */
public interface SpreadsheetReader
{

 /**
  * get names of all the worksheets in this spreadsheet
  * @return names mapped to lower case.
  */
 public List<String> getWorksheetNames ();

 /**
  * get a reader to the worksheet with that name
  * @param name of the worksheet (case ignored)
  * @return
  */
 public WorksheetReader getWorksheetReader (String name)
  throws WorksheetNotFoundException;

 /**
  * Close and free any resources
  * Do nothing if already closed.
  */
 public void close ();

 /**
  * get Human readable Information about this spreadsheet.
  * For example the list of file names or Urls holding these worksheets
  * @return source of the spreadsheet
  */
 public String getSourceName ();

}
