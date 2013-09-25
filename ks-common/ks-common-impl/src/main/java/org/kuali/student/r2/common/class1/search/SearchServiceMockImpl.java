package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of SearchService which can be configured by providing a
 * SearchRequestInfo and the desired SearchResultsInfo via addSearchResult().
 *
 * This is for use in unit tests.
 */
public class SearchServiceMockImpl implements SearchService {

    /*
     *  Storage for search results. Key is build from search key plus concatenated list of params and values.
     */
    private Map<String, SearchResultInfo> searchResults = new HashMap<String, SearchResultInfo>();

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        String searchResultsKey = makeSearchResultsKey(searchRequestInfo);
        return searchResults.get(searchResultsKey);
    }

    /**
     * Data loader method for mock implementation.
     * @param searchRequestInfo
     * @param searchResultInfo
     */
    public void addSearchResult(SearchRequestInfo searchRequestInfo, SearchResultInfo searchResultInfo) {
        String key = makeSearchResultsKey(searchRequestInfo);
        searchResults.put(key, searchResultInfo);
    }

    /**
     * Build a search results key from a SearchRequestInfo in the
     * format: "searchKey[searchParam[value,...], searchParam[value,...]]" where params are in alphabetical order.
     * @param searchRequestInfo The search request.
     * @return A key derived from the SearchRequestInfo.
     */
    private String makeSearchResultsKey(SearchRequestInfo searchRequestInfo) {
        List<String> paramsAndValues = new ArrayList<String>();

        for (SearchParamInfo param : searchRequestInfo.getParams()) {
            paramsAndValues.add(param.getKey() + "" + param.getValues().toString());
        }

        Collections.sort(paramsAndValues);
        return String.format("%s%s", searchRequestInfo.getSearchKey(), paramsAndValues);
    }
}
