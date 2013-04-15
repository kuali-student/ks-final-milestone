package org.kuali.student.r1.common.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r1.common.search.service.impl.CrossSearchManager;
import org.kuali.student.r1.common.search.service.impl.SearchDispatcherImpl;
import org.kuali.student.r1.common.search.service.impl.SearchManagerImpl;

public class MockSearch implements SearchService {
	SearchManagerImpl sm;
	public MockSearch(){
		sm = new SearchManagerImpl("classpath:test-cross-search.xml");
		CrossSearchManager csm = new CrossSearchManager();
		sm.setCrossSearchManager(csm);
		SearchDispatcherImpl sd = new SearchDispatcherImpl();
		List<SearchService> services = new ArrayList<SearchService>();
		services.add(this);
		sd.setServices(services);
		csm.setSearchDispatcher(sd);
	}
	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return sm.getSearchTypes();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return sm.getSearchType(searchTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchTypesByResult(searchResultTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return sm.getSearchResultTypes();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return sm.getSearchResultType(searchResultTypeKey);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return sm.getSearchCriteriaTypes();
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return sm.getSearchCriteriaType(searchCriteriaTypeKey);
	}

	@Override
	public SearchResult search(SearchRequest searchRequest)
			throws MissingParameterException {
		if("test.search1".equals(searchRequest.getSearchKey())){
			SearchResult searchResult = new SearchResult();
			SearchResultRow row = new SearchResultRow();
			SearchResultCell cell = new SearchResultCell();
			cell.setKey("col1");
			cell.setValue("value1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("col2");
			cell.setValue("value2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRow();
			cell = new SearchResultCell();
			cell.setKey("col1");
			cell.setValue("value1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("col2");
			cell.setValue("value2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}
		
		if("test.search2".equals(searchRequest.getSearchKey())){
			SearchResult searchResult = new SearchResult();
			SearchResultRow row = new SearchResultRow();
			SearchResultCell cell = new SearchResultCell();
			cell.setKey("C-A");
			cell.setValue("Avalue1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("C-B");
			cell.setValue("Avalue2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRow();
			cell = new SearchResultCell();
			cell.setKey("C-A");
			cell.setValue("Avalue1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCell();
			cell.setKey("C-B");
			cell.setValue("Avalue2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}else if("test.crossSearch".equals(searchRequest.getSearchKey())){
			return sm.search(searchRequest, null);
		}
		return null;
	}

}
