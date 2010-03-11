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
package org.kuali.student.dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public class SearchResult extends SearchRow
{

 public SearchResult ()
 {
 }

 private List<SearchResultColumn> resultColumns = new ArrayList ();

 /**
  * Get the value of resultColumns
  *
  * @return the value of resultColumns
  */
 public List<SearchResultColumn> getResultColumns ()
 {
  return resultColumns;
 }

 /**
  * Set the value of resultColumns
  *
  * @param resultColumns new value of resultColumns
  */
 public void setResultColumns (List<SearchResultColumn> resultColumns)
 {
  this.resultColumns = resultColumns;
 }



}
