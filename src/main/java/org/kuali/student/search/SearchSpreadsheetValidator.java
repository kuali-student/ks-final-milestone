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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.ModelValidator;

/**
 * Validates the entire spreadsheet model
 * @author nwright
 */
public class SearchSpreadsheetValidator implements ModelValidator
{

 private SearchSpreadsheet sheet;

 public SearchSpreadsheetValidator (SearchSpreadsheet sheet)
 {
  this.sheet = sheet;
 }

 List<String> errors = new ArrayList ();

 @Override
 public Collection<String> validate ()
 {
  errors = new ArrayList ();
  validateSearchTypes ();
  return errors;
 }

 private void validateSearchTypes ()
 {
  if (sheet.getSearchTypes ().size () == 0)
  {
   addError ("No search types found");
  }
  validateForDuplicates ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   SearchTypeValidator stv = new SearchTypeValidator (st);
   errors.addAll (stv.validate ());
  }
 }

 private void validateForDuplicates ()
 {
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   if ( ! keys.add (st.getKey ()))
   {
    addError ("Search type [" + st.getKey () + "] is duplicated");
   }
  }
 }

 private void addError (String msg)
 {
  String error = "Error in overall spreadsheet: " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
