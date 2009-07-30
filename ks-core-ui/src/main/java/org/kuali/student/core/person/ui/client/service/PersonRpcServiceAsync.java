package org.kuali.student.core.person.ui.client.service;

import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PersonRpcServiceAsync {
    public void searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues, AsyncCallback<List<Result>> callback);
    public void fetchPerson(String personId, AsyncCallback<PersonInfo> callback);
}
