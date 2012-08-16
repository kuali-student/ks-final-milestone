package org.kuali.student.r1.core.organizationsearch.service.impl;

import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;

@Deprecated
public interface OrganizationSearch {
    
    public SearchResult search(SearchRequest searchRequest);

}
