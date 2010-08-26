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
package org.kuali.student.dictionary.model.validation;

import org.kuali.student.dictionary.model.SearchCriteria;
import org.kuali.student.dictionary.model.SearchType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This validates a single criteriainoary entry
 * @author nwright
 */
public class SearchCriteriaValidator implements ModelValidator
{

 private SearchCriteria criteria;
 private SearchType searchType;

 public SearchCriteriaValidator (SearchCriteria criteria, SearchType searchType)
 {
  this.criteria = criteria;
  this.searchType = searchType;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  basicValidation ();
  if (criteria.getParameters () == null)
  {
   addError ("A parameter list is required even if no parameters");
  }
  return errors;
 }

 private void basicValidation ()
 {
  if (criteria.getKey ().equals (""))
  {
   addError ("criteria key is required");
  }
  if ( ! criteria.getType ().equals ("Criteria"))
  {
   addError ("'Type' column in the criteria must be 'Criteria'");
  }
  if (criteria.getName ().equals (""))
  {
   addError ("Name is required");
  }
  if (criteria.getDescription ().equals (""))
  {
   addError ("Description is required");
  }
  if ( ! criteria.getDataType ().equals (""))
  {
   addError ("Data Type should be blank");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in criteria entry: " + searchType.getKey () + "." + criteria.getKey () +
   ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
