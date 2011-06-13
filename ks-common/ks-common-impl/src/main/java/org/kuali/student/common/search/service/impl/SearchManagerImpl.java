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

package org.kuali.student.common.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.search.dto.CrossSearchTypeInfo;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.search.service.SearchManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Loads all search info for a service into memory
 *
 */
public class SearchManagerImpl implements SearchManager{

	final Logger logger = Logger.getLogger(SearchManagerImpl.class);
	
	private String searchContextFile;
	private Map<String, SearchTypeInfo> searchInfoTypeMap;
	private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
	private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
	private Map<String, String> queryMap;
	
	private CrossSearchManager crossSearchManager;
	
	@SuppressWarnings("unchecked")
	private void init() {
		ApplicationContext ac = new FileSystemXmlApplicationContext(searchContextFile);
		searchInfoTypeMap = ac.getBeansOfType(SearchTypeInfo.class);
		searchCriteriaTypeMap = ac.getBeansOfType(SearchCriteriaTypeInfo.class);
		searchResultTypeInfoMap = ac.getBeansOfType(SearchResultTypeInfo.class);
		queryMap = (Map<String, String>) ac.getBean("queryMap");
	}

	public SearchManagerImpl(String searchContextFile) {
		super();
		this.searchContextFile = searchContextFile;
		init();
	}

	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return searchCriteriaTypeMap.get(searchCriteriaTypeKey);
	}

	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return new ArrayList<SearchCriteriaTypeInfo>(searchCriteriaTypeMap
				.values());
	}

	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchResultTypeInfoMap.get(searchResultTypeKey);
	}

	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return new ArrayList<SearchResultTypeInfo>(searchResultTypeInfoMap
				.values());
	}

	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return searchInfoTypeMap.get(searchTypeKey);
	}

	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return new ArrayList<SearchTypeInfo>(searchInfoTypeMap.values());
	}

	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<SearchTypeInfo> searchTypesByCriteria = new ArrayList<SearchTypeInfo>();
		for (SearchTypeInfo searchTypeInfo : searchInfoTypeMap.values()) {
			if (searchCriteriaTypeKey.equals(searchTypeInfo
					.getSearchCriteriaTypeInfo().getKey())) {
				searchTypesByCriteria.add(searchTypeInfo);
			}
		}
		return searchTypesByCriteria;
	}

	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<SearchTypeInfo> searchTypesByResult = new ArrayList<SearchTypeInfo>();
		for (SearchTypeInfo searchTypeInfo : searchInfoTypeMap.values()) {
			if (searchResultTypeKey.equals(searchTypeInfo
					.getSearchResultTypeInfo().getKey())) {
				searchTypesByResult.add(searchTypeInfo);
			}
		}
		return searchTypesByResult;
	}

	public String getSearchContextFile() {
		return searchContextFile;
	}

	public void setSearchContext(String searchContextFile) {
		this.searchContextFile = searchContextFile;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest, SearchableDao dao) throws MissingParameterException {
		if(searchRequest == null){
			throw new MissingParameterException("Search Request can not be null.");
		}
		
		String searchKey = searchRequest.getSearchKey();

		SearchTypeInfo searchType = searchInfoTypeMap.get(searchKey);
		
		//If no type was found for the key, try to dispatch the search
		if(searchType == null){
			if(crossSearchManager!=null && crossSearchManager.getSearchDispatcher()!=null){
				logger.info("Search type '"+searchKey+"' is not known to this service's search manager, attempting to dispatch search.");
				return crossSearchManager.getSearchDispatcher().dispatchSearch(searchRequest);
			}else{
				logger.error("Search type '"+searchKey+"' is not known to this service's search manager.");
				throw new RuntimeException("Search type '"+searchKey+"' is not known to this service's search manager.");
			}
		}

		//Check if the search is a cross search
		if(searchType instanceof CrossSearchTypeInfo){
			if(crossSearchManager == null){
				//FIXME should we change these to Operation Failed Exceptions? also we need to handle invalid parameters.
				throw new RuntimeException("Requested cross service search:"+searchKey+", but no cross service search manager was defined.");
			}
			return crossSearchManager.doCrossSearch(searchRequest, (CrossSearchTypeInfo) searchType);
		}
		

		try{
			return dao.search(searchRequest, queryMap, searchType);
		}catch (Exception e){
			logger.error("Search Failed for searchKey:"+searchKey,e);
			throw new RuntimeException("Search Failed for searchKey:"+searchKey, e);
		}
	}

	public void setCrossSearchManager(CrossSearchManager crossSearchManager) {
		this.crossSearchManager = crossSearchManager;
	}

}
