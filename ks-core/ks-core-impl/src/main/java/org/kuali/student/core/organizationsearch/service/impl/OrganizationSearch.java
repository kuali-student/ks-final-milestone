package org.kuali.student.core.organizationsearch.service.impl;

import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;

public interface OrganizationSearch {
    
    public SearchResult search(SearchRequest searchRequest);

}
