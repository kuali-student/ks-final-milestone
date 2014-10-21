package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.class1.search.SearchServiceMockImpl;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  Base class for creating data loaders for SearchServiceMockImpl.
 */
public abstract class SearchServiceMockImplAbstractDataLoader {
    protected SearchServiceMockImpl searchServiceMock;

    public SearchServiceMockImplAbstractDataLoader(SearchServiceMockImpl searchServiceMock) {
        this.searchServiceMock = searchServiceMock;
    }

    protected abstract void loadData();

    /**
     * Constructs a SearchRequestInfo from given data.
     * @param searchKey The search key as defined in the search service.
     * @param params A Map containing the parameters. A key and a list of values (because SearchParamInfo can
     *               have multiple values)
     * @return
     */
    protected SearchRequestInfo makeSearchRequestInfo(String searchKey, Map<String, String[]> params) {
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo();
        searchRequestInfo.setSearchKey(searchKey);

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        for (Map.Entry<String, String[]> p : params.entrySet()) {
            SearchParamInfo searchParam = new SearchParamInfo(p.getKey(), Arrays.asList(p.getValue()));
            searchParams.add(searchParam);
        }
        searchRequestInfo.setParams(searchParams);
        return searchRequestInfo;
    }

    /**
     * Constructs a SearchResultInfo from given data.
     * @param rows A List<Map<String,String>>() (as List<Map<paramKey, paramValue>)
     * @return A SearchResultInfo.
     */
    protected SearchResultInfo makeSearchResultInfo(List<Map<String, String>> rows) {
        SearchResultInfo searchResultInfo = new SearchResultInfo();
        List<SearchResultRowInfo> searchResultRowInfos = new ArrayList<SearchResultRowInfo>();
        for (Map<String, String> r : rows) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            for (Map.Entry<String, String> c : r.entrySet()) {
                row.addCell(c.getKey(), c.getValue());
            }
            searchResultRowInfos.add(row);
        }
        searchResultInfo.setRows(searchResultRowInfos);
        return searchResultInfo;
    }
}
