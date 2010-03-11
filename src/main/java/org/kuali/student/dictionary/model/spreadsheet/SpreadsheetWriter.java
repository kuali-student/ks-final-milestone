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
public interface SpreadsheetWriter extends SpreadsheetReader
{

 /**
  * get a writer to the worksheet with that name and column headings
  *
  * @param name of the worksheet (case ignored)
  * @param names of the column headings
  * @return WorksheetWriter
  * @throws WorksheetAlreadyExists if a worksheet already exists with the same name
  */
 public WorksheetWriter createWorksheet (String name, List<String> names)
  throws WorksheetAlreadyExistsException;

 /**
  * saves changes to the worksheet
  */
 public void save ();

}
