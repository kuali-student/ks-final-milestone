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
public class SearchModelValidator implements ModelValidator
{

 private SearchModel sheet;

 public SearchModelValidator (SearchModel sheet)
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
  getValidateSearchCriteria (true);
  getValidateSearchCriteriaParameters (true);
  getValidateSearchResults (true);
  getValidateSearchResultColumns (true);
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

 private List<SearchResult> getValidateSearchResults (boolean validate)
 {
  List<SearchResult> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   if (keys.add (st.getSearchResult ().getKey ()))
   {
    list.add (st.getSearchResult ());
   }
   else
   {
    if (validate)
    {
     for (SearchResult result : list)
     {
      if (result.getKey ().equals (st.getSearchResult ().getKey ()))
      {
       compareSearchResults (result, st.getSearchResult ());
      }
     }
    }
   }
  }
  return list;
 }

 private void compareSearchResults (SearchResult result1, SearchResult result2)
 {
  assert result1.getKey ().equals (result2.getKey ());
  if ( ! result1.getName ().equals (result2.getName ()))
  {
   addError ("two results with the same key have different names, one is on row " +
    result1.getRowNumber () + " the other is on row " + result2.getRowNumber ());
  }
  if ( ! result1.getDescription ().equals (result2.getDescription ()))
  {
   addError ("Two results with the same key have different descriptions, one is on row " +
    result1.getRowNumber () + " the other is on row " + result2.getRowNumber ());
  }
  if (result1.getResultColumns ().size () != result2.getResultColumns ().size ())
  {
   addError ("two results with the same key have different number of result columns, one is on row " +
    result1.getRowNumber () + " the other is on row " + result2.getRowNumber ());
  }
  int i = 0;
  for (SearchResultColumn col : result1.getResultColumns ())
  {
   if ( ! col.getKey ().equals (result2.getResultColumns ().get (i).getKey ()))
   {
    addError ("two results with the same key have different result columns, one is on row " +
     result1.getRowNumber () + " the other is on row " + result2.getRowNumber () +
     " the result columns that are different are on row " + col.getRowNumber ());
   }
   i ++;
  }
 }

 private List<SearchResultColumn> getValidateSearchResultColumns (
  boolean validate)
 {
  List<SearchResultColumn> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchResult searchResult : getValidateSearchResults (false))
  {
   for (SearchResultColumn col : searchResult.getResultColumns ())
   {
    if (keys.add (col.getKey ()))
    {
     list.add (col);
    }
    else
    {
     if (validate)
     {
      for (SearchResultColumn resultCol : list)
      {
       if (resultCol.getKey ().equals (col.getKey ()))
       {
        compareSearchResultColumns (resultCol, col);
       }
      }
     }
    }
   }
  }
  return list;
 }

 private void compareSearchResultColumns (SearchResultColumn col1,
                                         SearchResultColumn col2)
 {
  assert col1.getKey ().equals (col2.getKey ());
  if ( ! col1.getName ().equals (col2.getName ()))
  {
   addError ("two result columns with the same key have different names, one is on row " +
    col1.getRowNumber () + " the other is on row " +
    col2.getRowNumber ());
  }
  if ( ! col1.getDescription ().equals (col2.getDescription ()))
  {
   addError ("Two result columns with the same key have different descriptions, one is on row " +
    col1.getRowNumber () + " the other is on row " +
    col2.getRowNumber ());
  }
  if ( ! col1.getDataType ().equals (col2.getDataType ()))
  {
   addError ("Two result columns with the same key have different data type, one is on row " +
    col1.getRowNumber () + " the other is on row " +
    col2.getRowNumber ());
  }
 }

 private List<SearchCriteria> getValidateSearchCriteria (boolean validate)
 {
  List<SearchCriteria> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : sheet.getSearchTypes ())
  {
   if (keys.add (st.getSearchCriteria ().getKey ()))
   {
    list.add (st.getSearchCriteria ());
   }
   else
   {
    if (validate)
    {
     for (SearchCriteria criteria : list)
     {
      if (criteria.getKey ().equals (st.getSearchCriteria ().getKey ()))
      {
       compareSearchCriteria (criteria, st.getSearchCriteria ());
      }
     }
    }
   }
  }
  return list;
 }

 private void compareSearchCriteria (SearchCriteria criteria1,
                                     SearchCriteria criteria2)
 {
  assert criteria1.getKey ().equals (criteria2.getKey ());
  if ( ! criteria1.getName ().equals (criteria2.getName ()))
  {
   addError ("two criteria with the same key have different names, one is on row " +
    criteria1.getRowNumber () + " the other is on row " +
    criteria2.getRowNumber ());
  }
  if ( ! criteria1.getDescription ().equals (criteria2.getDescription ()))
  {
   addError ("Two criteria with the same key have different descriptions, one is on row " +
    criteria1.getRowNumber () + " the other is on row " +
    criteria2.getRowNumber ());
  }
  if (criteria1.getParameters ().size () != criteria2.getParameters ().size ())
  {
   addError ("two results with the same key have different number of result columns, one is on row " +
    criteria1.getRowNumber () + " the other is on row " +
    criteria2.getRowNumber ());
  }
  int i = 0;
  for (SearchCriteriaParameter param : criteria1.getParameters ())
  {
   if ( ! param.getKey ().equals (criteria2.getParameters ().get (i).getKey ()))
   {
    addError ("two criteria with the same key have different parameters, one is on row " +
     criteria1.getRowNumber () + " the other is on row " + criteria2.
     getRowNumber () +
     " the parameters that are different are on row " + param.getRowNumber ());
   }
   i ++;
  }
 }

 private List<SearchCriteriaParameter> getValidateSearchCriteriaParameters (
  boolean validate)
 {
  List<SearchCriteriaParameter> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchCriteria criteria : getValidateSearchCriteria (false))
  {
   for (SearchCriteriaParameter parm : criteria.getParameters ())
   {
    if (keys.add (parm.getKey ()))
    {
     list.add (parm);
    }
    else
    {
     if (validate)
     {
      for (SearchCriteriaParameter param : list)
      {
       if (param.getKey ().equals (parm.getKey ()))
       {
        compareSearchCriteriaParameter (param, parm);
       }
      }
     }
    }
   }
  }
  return list;
 }

 private void compareSearchCriteriaParameter (SearchCriteriaParameter param1,
                                              SearchCriteriaParameter param2)
 {
  assert param1.getKey ().equals (param2.getKey ());
  if ( ! param1.getName ().equals (param2.getName ()))
  {
   addError ("two criteria parameters with the same key have different names, one is on row " +
    param1.getRowNumber () + " the other is on row " +
    param2.getRowNumber ());
  }
  if ( ! param1.getDescription ().equals (param2.getDescription ()))
  {
   addError ("Two criteria parameters with the same key have different descriptions, one is on row " +
    param1.getRowNumber () + " the other is on row " +
    param2.getRowNumber ());
  }
  if ( ! param1.getDataType ().equals (param2.getDataType ()))
  {
   addError ("Two criteria parameters with the same key have different data type, one is on row " +
    param1.getRowNumber () + " the other is on row " +
    param2.getRowNumber ());
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
