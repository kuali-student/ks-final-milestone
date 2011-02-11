package org.kuali.student.common.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

import java.util.HashMap;

/**
 * @author Igor
 */
public class CachingSearchService implements SearchRpcServiceAsync {

    private static CachingSearchService INSTANCE = new CachingSearchService();

    private SearchRpcServiceAsync searchService = SearchServiceFactory.getSearchService();

    private HashMap<SearchRequest, SearchResult> cache = new HashMap<SearchRequest, SearchResult>();

    public static CachingSearchService getSearchService() {
        return INSTANCE;
    }

    @Override
    public void search(final SearchRequest searchRequest, final AsyncCallback<SearchResult> callback) {
        cachingSearch(searchRequest, callback);
    }

    @Override
    public void cachingSearch(final SearchRequest searchRequest, final AsyncCallback<SearchResult> callback) {
        if (cache.containsKey(searchRequest)) {
            callback.onSuccess(cache.get(searchRequest));
        } else {
            searchService.cachingSearch(searchRequest, new AsyncCallback<SearchResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    callback.onFailure(caught);
                }

                @Override
                public void onSuccess(SearchResult searchResult) {
                    cache.put(searchRequest, searchResult);
                    callback.onSuccess(searchResult);
                }
            });
        }
    }
}
