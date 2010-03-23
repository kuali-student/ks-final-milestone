package org.kuali.student.core.personsearch.service.impl;

import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

public interface SearchOperation {
    public SearchResult search(IdentityService identityService, SearchRequest searchRequest);
};
