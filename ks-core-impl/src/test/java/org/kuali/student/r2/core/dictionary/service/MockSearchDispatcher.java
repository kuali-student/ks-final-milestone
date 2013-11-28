package org.kuali.student.r2.core.dictionary.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

public class MockSearchDispatcher implements SearchService {

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchResultInfo result = new SearchResultInfo();
        List<SearchResultRowInfo> rows = new ArrayList ();
        SearchResultRowInfo row = new SearchResultRowInfo();
        rows.add (row);
        SearchResultCellInfo cell = new SearchResultCellInfo();
        cell.setKey ("mockKey");
        cell.setValue ("mockValue");
        List<SearchResultCellInfo> cells = new ArrayList ();
        cells.add (cell);
        row.setCells (cells);
        result.setRows (rows);
        System.out.println ("Generating mock search result for " + searchRequestInfo.getSearchKey ());
        return result;
    }
}
