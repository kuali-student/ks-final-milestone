package org.kuali.student.common.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import java.util.HashMap;

/**
 * @author Igor
 */
public class CachingSearchService implements SearchRpcServiceAsync {

    private static CachingSearchService INSTANCE = new CachingSearchService();

    private SearchRpcServiceAsync searchService = SearchServiceFactory.getSearchService();

    private HashMap<SearchRequestInfo, SearchResultInfo> cache = new HashMap<SearchRequestInfo, SearchResultInfo>();

    public static CachingSearchService getSearchService() {
        return INSTANCE;
    }

    @Override
    public void search(final SearchRequestInfo searchRequest, final AsyncCallback<SearchResultInfo> callback) {
        cachingSearch(searchRequest, callback);
    }

    @Override
    public void cachingSearch(final SearchRequestInfo searchRequest, final AsyncCallback<SearchResultInfo> callback) {
        if (cache.containsKey(searchRequest)) {
            callback.onSuccess(cache.get(searchRequest));
        } else {
            searchService.cachingSearch(searchRequest, new AsyncCallback<SearchResultInfo>() {
                @Override
                public void onFailure(Throwable caught) {
                    callback.onFailure(caught);
                }

                @Override
                public void onSuccess(SearchResultInfo searchResult) {
                    cache.put(searchRequest, searchResult);
                    callback.onSuccess(searchResult);
                }
            });
        }
    }
}
