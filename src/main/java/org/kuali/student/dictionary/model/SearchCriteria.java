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
public class SearchCriteria extends SearchRow
{

 public SearchCriteria ()
 {
 }

 protected List<SearchCriteriaParameter> parameters = new ArrayList ();

 /**
  * Get the value of parameters
  *
  * @return the value of parameters
  */
 public List<SearchCriteriaParameter> getParameters ()
 {
  return parameters;
 }

 /**
  * Set the value of parameters
  *
  * @param parameters new value of parameters
  */
 public void setParameters (List<SearchCriteriaParameter> parameters)
 {
  this.parameters = parameters;
 }

 
}
