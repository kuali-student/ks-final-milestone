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

package org.kuali.student.common.search.service;

import java.util.List;

import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;

public interface SearchManager {

	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException;

	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException;

	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException;

	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;
	
	public SearchResult search(SearchRequest searchRequest, SearchableDao dao) throws MissingParameterException;
}
