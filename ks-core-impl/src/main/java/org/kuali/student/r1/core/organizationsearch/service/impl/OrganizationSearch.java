package org.kuali.student.r1.core.organizationsearch.service.impl;

import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

public interface OrganizationSearch {
    
    public SearchResultInfo search(SearchRequestInfo searchRequest);

}
