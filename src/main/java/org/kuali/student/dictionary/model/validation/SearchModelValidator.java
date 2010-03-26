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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.model.SearchCriteria;
import org.kuali.student.dictionary.model.SearchCriteriaParameter;
import org.kuali.student.dictionary.model.SearchModel;
import org.kuali.student.dictionary.model.SearchResult;
import org.kuali.student.dictionary.model.SearchResultColumn;
import org.kuali.student.dictionary.model.SearchType;
import org.kuali.student.dictionary.model.util.ModelFinder;

/**
 * Validates the entire spreadsheet model
 * @author nwright
 */
public class SearchModelValidator implements ModelValidator
{

 private SearchModel model;

 public SearchModelValidator (SearchModel model)
 {
  this.model = model;
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
  if (model.getSearchTypes ().size () == 0)
  {
   addError ("No search types found");
  }
  validateForDuplicates ();
  for (SearchType st : model.getSearchTypes ())
  {
   SearchTypeValidator stv = new SearchTypeValidator (st, model);
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
  for (SearchType st : model.getSearchTypes ())
  {
   if ( ! keys.add (st.getKey ()))
   {
    SearchType st2 = new ModelFinder (model).findSearchType (st.getKey ());
    List<String> differences = findDifferences (st, st2);
    if (differences.size () > 0)
    {
     StringBuffer buf = new StringBuffer ();
     String comma = "";
     for (String val : differences)
     {
      buf.append (comma);
      buf.append (val);
      comma = ", ";
     }
     addError ("Search type [" + st.getKey () +
      "] is duplicated but differences were found: " + buf);
    }
   }
  }
 }

 private List<String> findDifferences (SearchType st1, SearchType st2)
 {
  List<String> differences = new ArrayList ();
  addIfDifferent (differences, st1.getKey (), st2.getKey (), "key");
  addIfDifferent (differences, st1.getName (), st2.getName (), "name");
  addIfDifferent (differences, st1.getDescription (), st2.getDescription (), "Description");
  addIfDifferent (differences, st1.getService (), st2.getService (), "Service");

  SearchCriteria sc1 = st1.getSearchCriteria ();
  SearchCriteria sc2 = st2.getSearchCriteria ();
  addIfDifferent (differences, sc1.getKey (), sc2.getKey (), "key");
  addIfDifferent (differences, sc1.getName (), sc2.getName (), "name");
  addIfDifferent (differences, sc1.getDescription (), sc2.getDescription (), "Description");

  List<SearchCriteriaParameter> scp1s = sc1.getParameters ();
  List<SearchCriteriaParameter> scp2s = sc2.getParameters ();
  addIfDifferent (differences, scp1s.size (), scp2s.size (), "# of parameters");

  if (scp1s.size () == scp2s.size ())
  {
   for (int i = 0; i < scp1s.size (); i ++)
   {
    SearchCriteriaParameter scp1 = scp1s.get (i);
    SearchCriteriaParameter scp2 = scp2s.get (i);
    addIfDifferent (differences, scp1.getKey (), scp2.getKey (), "key");
    addIfDifferent (differences, scp1.getName (), scp2.getName (), "name");
    addIfDifferent (differences, scp1.getDescription (), scp2.getDescription (), "Description");
    addIfDifferent (differences, scp1.getDataType (), scp2.getDataType (), "Datatype");
    addIfDifferent (differences, scp1.getOptional (), scp2.getOptional (), "Optional");
    addIfDifferent (differences, scp1.getCaseSensitive (), scp2.getCaseSensitive (), "case");
   }
  }


  SearchResult sr1 = st1.getSearchResult ();
  SearchResult sr2 = st2.getSearchResult ();
  addIfDifferent (differences, sr1.getKey (), sr2.getKey (), "key");
  addIfDifferent (differences, sr1.getName (), sr2.getName (), "name");
  addIfDifferent (differences, sr1.getDescription (), sr2.getDescription (), "Description");
  List<SearchResultColumn> src1s = sr1.getResultColumns ();
  List<SearchResultColumn> src2s = sr2.getResultColumns ();
  addIfDifferent (differences, src1s.size (), src2s.size (), "# of result columns");
  if (src1s.size () == src2s.size ())
  {
   for (int i = 0; i < src1s.size (); i ++)
   {
    SearchResultColumn src1 = src1s.get (i);
    SearchResultColumn src2 = src2s.get (i);
    addIfDifferent (differences, src1.getKey (), src2.getKey (), "key");
    addIfDifferent (differences, src1.getName (), src2.getName (), "name");
    addIfDifferent (differences, src1.getDescription (), src2.getDescription (), "Description");
    addIfDifferent (differences, src1.getDataType (), src2.getDataType (), "Datatype");
    addIfDifferent (differences, src1.getOptional (), src2.getOptional (), "Optional");
    addIfDifferent (differences, src1.getCaseSensitive (), src2.getCaseSensitive (), "case");
   }
  }
  return differences;
 }

 private void addIfDifferent (List<String> differences, Object val1, Object val2,
                              String difference)
 {
  if (val1 == null && val2 == null)
  {
   return;
  }
  if (val1 == null)
  {
   differences.add (difference);
   return;
  }
  if ( ! val1.equals (val2))
  {
   differences.add (difference);
  }
 }

 private List<SearchResult> getValidateSearchResults (boolean validate)
 {
  List<SearchResult> list = new ArrayList ();
  Set<String> keys = new HashSet ();
  for (SearchType st : model.getSearchTypes ())
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
  for (SearchType st : model.getSearchTypes ())
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
