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

/**
 *
 * @author nwright
 */
public class SearchType extends SearchRow
{

 public SearchType ()
 {
  super ();
 }

 protected SearchSql sql;

 /**
  * Get the value of sql
  *
  * @return the value of sql
  */
 public SearchSql getSql ()
 {
  return sql;
 }

 /**
  * Set the value of sql
  *
  * @param sql new value of sql
  */
 public void setSql (SearchSql sql)
 {
  this.sql = sql;
 }

 protected SearchCriteria criteria;

 /**
  * Get the value of criteria
  *
  * @return the value of criteria
  */
 public SearchCriteria getCriteria ()
 {
  return criteria;
 }

 /**
  * Set the value of criteria
  *
  * @param criteria new value of criteria
  */
 public void setCriteria (SearchCriteria criteria)
 {
  this.criteria = criteria;
 }

 protected SearchResults results;

 /**
  * Get the value of results
  *
  * @return the value of results
  */
 public SearchResults getResults ()
 {
  return results;
 }

 /**
  * Set the value of results
  *
  * @param results new value of results
  */
 public void setResults (SearchResults results)
 {
  this.results = results;
 }


}
