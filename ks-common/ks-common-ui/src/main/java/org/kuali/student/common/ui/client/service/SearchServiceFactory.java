package org.kuali.student.common.ui.client.service;

import com.google.gwt.core.client.GWT;

/**
 * @author Igor
 */
@Deprecated
public class SearchServiceFactory {

    private static SearchRpcServiceAsync searchService = GWT.create(SearchRpcService.class);

    public static SearchRpcServiceAsync getSearchService() {
        return searchService;
    }
}
