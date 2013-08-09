/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r1.core.personsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.r2.core.search.dto.*;

public class QuickViewByGivenNameSearchTypeCreator
{

 public SearchTypeInfo get ()
 {
  SearchTypeInfo st = new SearchTypeInfo ();
  st.setKey (QuickViewByGivenName.SEARCH_TYPE);
  st.setName ("Quick View by given Name");
  st.setDesc (
    "Do a quick search using a given name returning just key info needed");

  SearchCriteriaTypeInfo crit = new SearchCriteriaTypeInfo ();
  st.setSearchCriteriaTypeInfo (crit);
  crit.setKey (QuickViewByGivenName.CRITERIA_TYPE);
  crit.setName ("Given Name Search");
  crit.setDesc ("Search using given name and affiliation");
  List<QueryParamInfo> parms = new ArrayList ();
  crit.setQueryParams (parms);

  QueryParamInfo parm = null;
  FieldDescriptor field = null;

  parm = new QueryParamInfo ();
  parms.add (parm);
  parm.setKey (QuickViewByGivenName.NAME_PARAM);
  field = new FieldDescriptor ();
  parm.setFieldDescriptor (field);
  field.setDataType ("string");
  field.setName ("Given name");
  field.setDesc ("Given name, i.e. Smith, John");

  parm = new QueryParamInfo ();
  parms.add (parm);
  parm.setKey (QuickViewByGivenName.ID_PARAM);
  field = new FieldDescriptor ();
  parm.setFieldDescriptor (field);
  field.setDataType ("string");
  field.setName ("ID");
  field.setDesc ("University Id");

  parm = new QueryParamInfo ();
  parms.add (parm);
  parm.setKey (QuickViewByGivenName.AFFILIATION_PARAM);
  field = new FieldDescriptor ();
  parm.setFieldDescriptor (field);
  field.setDataType ("string");
  field.setName ("Affiliation");
  field.setDesc ("The person's primary affiliation with the school");

  SearchResultTypeInfo result = new SearchResultTypeInfo ();
  st.setSearchResultTypeInfo (result);

  result.setKey (QuickViewByGivenName.RESULT_TYPE);
  result.setName ("Given Name Search");
  result.setDesc("Search using given name and affiliation");
  List<ResultColumnInfo> rcs = new ArrayList ();
  result.setResultColumns (rcs);

  ResultColumnInfo rc = null;

  rc = new ResultColumnInfo ();
  rcs.add (rc);
  rc.setKey (QuickViewByGivenName.ENTITY_ID_RESULT);
  rc.setDataType ("string");
  rc.setName ("Entity Id");
  rc.setDesc ("Internal id assigned to person");

  rc = new ResultColumnInfo ();
  rcs.add (rc);
  rc.setKey (QuickViewByGivenName.PERSON_ID_RESULT);
  rc.setDataType ("string");
  rc.setName ("Person ID");
  rc.setDesc ("The Person's ID");

  rc = new ResultColumnInfo ();
  rcs.add (rc);
  rc.setKey (QuickViewByGivenName.PRINCIPAL_NAME_RESULT);
  rc.setDataType ("string");
  rc.setName ("Principalname");
  rc.setDesc ("Principal name");

  rc = new ResultColumnInfo ();
  rcs.add (rc);
  rc.setKey (QuickViewByGivenName.GIVEN_NAME_RESULT);
  rc.setDataType ("string");
  rc.setName ("Given name");
  rc.setDesc ("Given name, i.e. Smith, John");
  rc = new ResultColumnInfo ();

  rcs.add (rc);
  rc.setKey (QuickViewByGivenName.DISPLAY_NAME_RESULT);
  rc.setDataType ("string");
  rc.setName ("Display name");
  rc.setDesc ("Display name, i.e. John Smith");

  return st;
 }
}
