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

package org.kuali.student.common.ui.server.gwt;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.r1.common.assembly.transform.IdTranslatorFilter;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.service.SearchDispatcher;
import org.springframework.beans.factory.InitializingBean;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchDispatchRpcGwtServlet extends RemoteServiceServlet implements SearchRpcService, InitializingBean {

    private static final long serialVersionUID = 1L;

    private IdTranslatorFilter idTranslatorFilter;

    private SearchDispatcher searchDispatcher;

    protected boolean cachingEnabled = false;
	protected int searchCacheMaxSize = 20;
	protected int searchCacheMaxAgeSeconds = 90;
	protected Map<String,MaxAgeSoftReference<SearchResult>> searchCache;
	
    
    public SearchDispatchRpcGwtServlet() {
        super();
    }

    /**
     * Delegates to the service responsible for the given search type key
     *
     * @param searchRequest
     * @return The searchResult from the delegated search or null
     * @throws MissingParameterException
     */
    @Override
    public SearchResult search(SearchRequest searchRequest) {
        try
        {
            SearchResult searchResult = searchDispatcher.dispatchSearch(searchRequest);
            List<SearchParam> params = searchRequest.getParams();
            if (params != null && params.size() > 0) {
                SearchParam firstParam = params.get(0);
                if (firstParam.getKey().equals("lu.queryParam.cluVersionIndId")) {//FIXME can this special case be handled after this call?
                    doIdTranslation(searchResult);
                }
            }
            return searchResult;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SearchResult cachingSearch(SearchRequest searchRequest) {
        try
        {
            String cacheKey = searchRequest.toString();
            if (cachingEnabled) {

                //Get From Cache
                MaxAgeSoftReference<SearchResult> ref = searchCache.get(cacheKey);
                if (ref != null) {
                    SearchResult cachedSearchResult = ref.get();
                    if (cachedSearchResult != null) {
                        return cachedSearchResult;
                    }
                }
            }

            //Perform the actual Search
            SearchResult searchResult = search(searchRequest);

            if (cachingEnabled) {
                //Store to cache
                searchCache
                        .put(cacheKey, new MaxAgeSoftReference<SearchResult>(searchCacheMaxAgeSeconds, searchResult));
            }

            return searchResult;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void doIdTranslation(SearchResult searchResult) {
        for (SearchResultRow searchResultRow : searchResult.getRows()) {
            for (SearchResultCell searchResultCell : searchResultRow.getCells()) {
                String value = searchResultCell.getValue();
                if (value != null && value.startsWith("kuali.atp")) {
                    String newValue = idTranslatorFilter.getTranslationForAtp(value);
                    if (newValue != null) {
                        searchResultCell.setValue(newValue);
                    }
                }
            }
        }
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		if(cachingEnabled){
			searchCache = Collections.synchronizedMap( new MaxSizeMap<String,MaxAgeSoftReference<SearchResult>>( searchCacheMaxSize ) );
		}
	}
    
    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    public void setIdTranslatorFilter(IdTranslatorFilter idTranslatorFilter) {
        this.idTranslatorFilter = idTranslatorFilter;
    }

	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}

	public void setSearchCacheMaxSize(int searchCacheMaxSize) {
		this.searchCacheMaxSize = searchCacheMaxSize;
	}

	public void setSearchCacheMaxAgeSeconds(int searchCacheMaxAgeSeconds) {
		this.searchCacheMaxAgeSeconds = searchCacheMaxAgeSeconds;
	}
	
	
	//Added as inner classes to avoid huge dependency on rice-impl
	/**
	 * An extension to SoftReference that stores an expiration time for the 
	 * value stored in the SoftReference. If no expiration time is passed in
	 * the value will never be cached.  
	 */
	public class MaxAgeSoftReference<T> extends SoftReference<T> {
		
		private long expires;

		public MaxAgeSoftReference(long expires, T referent) {
			super(referent);
			this.expires = System.currentTimeMillis() + expires * 1000;
		}
		
		public boolean isValid() {
			return System.currentTimeMillis() < expires;
		}
		
		public T get() {			
			return isValid() ? super.get() : null;
		}		
		
	}
	/**
	 * This class acts like an LRU cache, automatically purging contents when it gets above a certain size. 
	 * 
	 * @author Kuali Rice Team (rice.collab@kuali.org)
	 */
	public class MaxSizeMap<K,V> extends LinkedHashMap<K,V> {
		private static final long serialVersionUID = -5354227348838839919L;

		private int maxSize;
		
		/**
		 * @param maxSize
		 */
		public MaxSizeMap( int maxSize  ) {
			this( maxSize, false );
		}
		/**
		 * @param maxSize
		 * @param accessOrder Whether to sort in the order accessed rather than the order inserted.
		 */
		public MaxSizeMap( int maxSize, boolean accessOrder ) {
			super( maxSize / 2, 0.75f, accessOrder );
			this.maxSize = maxSize;
		}
		
		/**
		 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
		 */
		@Override
		protected boolean removeEldestEntry(Entry<K,V> eldest) {
			return size() > maxSize;
		}
		
	}
}
