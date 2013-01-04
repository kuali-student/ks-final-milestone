package org.kuali.student.r2.common.search;

import org.kuali.student.r2.common.class1.search.CrossSearchManager;
import org.kuali.student.r2.common.class1.search.SearchManagerImpl;
import org.kuali.student.r2.common.class1.search.SearchServiceDispatcherImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

public class MockSearch implements SearchService {
	SearchManagerImpl sm;
	public MockSearch(){
		sm = new SearchManagerImpl("classpath:test-cross-search.xml");
		CrossSearchManager csm = new CrossSearchManager();
		sm.setCrossSearchManager(csm);
		SearchServiceDispatcherImpl sd = new SearchServiceDispatcherImpl();
		List<SearchService> services = new ArrayList<SearchService>();
		services.add(this);
		sd.setSearchServices(services);
		csm.setSearchDispatcher(sd);
	}

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(@WebParam(name = "searchResultTypeKey") String searchResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(@WebParam(name = "searchCriteriaTypeKey") String searchCriteriaTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchResultTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return sm.getSearchCriteriaTypes(contextInfo);
    }

	@Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
		if("test.search1".equals(searchRequestInfo.getSearchKey())){
			SearchResultInfo searchResult = new SearchResultInfo();
			SearchResultRowInfo row = new SearchResultRowInfo();
			SearchResultCellInfo cell = new SearchResultCellInfo();
			cell.setKey("col1");
			cell.setValue("value1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCellInfo();
			cell.setKey("col2");
			cell.setValue("value2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRowInfo();
			cell = new SearchResultCellInfo();
			cell.setKey("col1");
			cell.setValue("value1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCellInfo();
			cell.setKey("col2");
			cell.setValue("value2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}
		
		if("test.search2".equals(searchRequestInfo.getSearchKey())){
			SearchResultInfo searchResult = new SearchResultInfo();
			SearchResultRowInfo row = new SearchResultRowInfo();
			SearchResultCellInfo cell = new SearchResultCellInfo();
			cell.setKey("C-A");
			cell.setValue("Avalue1-1");
			row.getCells().add(cell);
			
			cell = new SearchResultCellInfo();
			cell.setKey("C-B");
			cell.setValue("Avalue2-1");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
		
			row = new SearchResultRowInfo();
			cell = new SearchResultCellInfo();
			cell.setKey("C-A");
			cell.setValue("Avalue1-2");
			row.getCells().add(cell);
			
			cell = new SearchResultCellInfo();
			cell.setKey("C-B");
			cell.setValue("Avalue2-2");
			row.getCells().add(cell);
			
			searchResult.getRows().add(row);
			return searchResult;
		}else if("test.crossSearch".equals(searchRequestInfo.getSearchKey())){
			return sm.search(searchRequestInfo, null);
		}
		return null;
	}


}
