package org.kuali.student.core.person.ui.client.service;

import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

public interface PersonServiceClient {
    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues);
    public PersonInfo fetchPerson(String personId);

}
