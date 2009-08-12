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
package org.kuali.student.dictionary;

/**
 * Interface required to get data from a single tab of a spreadsheet
 * @author nwright
 */
public interface WorksheetReader
{

 public int getEstimatedRows ();

 /**
  * Get the value with the specified column name
  * @param name
  * @return nulls converted to "" and values trimmed.
  */
 public String getValue (String name);

 public boolean next ();

}
