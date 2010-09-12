package org.kuali.student.core.personsearch.service.impl;

import java.util.List;

import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

public interface SearchOperation {
    public SearchResult search(IdentityService identityService, SearchRequest searchRequest);

    public List<Result> searchForResults(IdentityService identityService, String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
};
