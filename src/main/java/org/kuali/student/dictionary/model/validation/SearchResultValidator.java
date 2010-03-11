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

import org.kuali.student.dictionary.model.SearchResult;
import org.kuali.student.dictionary.model.SearchType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This validates a single resultinoary entry
 * @author nwright
 */
public class SearchResultValidator implements ModelValidator
{

 private SearchResult result;
 private SearchType searchType;

 public SearchResultValidator (SearchResult result, SearchType searchType)
 {
  this.result = result;
  this.searchType = searchType;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  basicValidation ();
  if (result.getResultColumns () == null)
  {
   addError ("A result column list is required");
  }
  if (result.getResultColumns ().size () == 0)
  {
   addError ("A result must have at least one column");
  }
  return errors;
 }

 private void basicValidation ()
 {
  if (result.getKey ().equals (""))
  {
   addError ("result key is required");
  }
  if ( ! result.getType ().equals ("Result"))
  {
   addError ("'Type' column in the result must be 'Result'");
  }
  if (result.getName ().equals (""))
  {
   addError ("Name is required");
  }
  if (result.getDescription ().equals (""))
  {
   addError ("Description is required");
  }
  if ( ! result.getDataType ().equals (""))
  {
   addError ("Data Type should be blank");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in result entry: " +searchType.getKey () + "." + result.getKey () +
   ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
