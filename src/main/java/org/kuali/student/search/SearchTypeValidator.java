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
package org.kuali.student.search;

import java.util.ArrayList;
import java.util.Collection;
import org.kuali.student.dictionary.ModelValidator;

/**
 * This validates a single searchTypeinoary entry
 * @author nwright
 */
public class SearchTypeValidator implements ModelValidator
{

 private SearchType searchType;

 public SearchTypeValidator (SearchType searchType)
 {
  this.searchType = searchType;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  basicValidation ();
  if (searchType.getImplementation () == null)
  {
   addError ("JPQL implementation is required");
  }
  if (searchType.getSearchCriteria () == null)
  {
   addError ("Criteria is required");
  }
  ModelValidator validator =
   new SearchCriteriaValidator (searchType.getSearchCriteria (), searchType);
  errors.addAll (validator.validate ());
  if (searchType.getSearchResult () == null)
  {
   addError ("Results is required");
  }
  validator =
   new SearchResultValidator (searchType.getSearchResult (), searchType);
  errors.addAll (validator.validate ());
  return errors;
 }

 private void basicValidation ()
 {
  if (searchType.getKey ().equals (""))
  {
   addError ("search type key is required");
  }
  if ( ! searchType.getType ().equals ("Search"))
  {
   addError ("'Type' column in the search type must be 'Search'");
  }
  if (searchType.getName ().equals (""))
  {
   addError ("Name is required");
  }
  if (searchType.getDescription ().equals (""))
  {
   addError ("Description is required");
  }
  if ( ! searchType.getDataType ().equals (""))
  {
   addError ("Data Type should be blank");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in searchTypeionary entry: " + searchType.getKey () +
   " of " + searchType + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
