package org.kuali.student.core.person.ui.client.service;

import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("/rpcservices/PersonRpcService")
public interface PersonRpcService extends RemoteService {
	

    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues);
    public PersonInfo fetchPerson(String personId);
}