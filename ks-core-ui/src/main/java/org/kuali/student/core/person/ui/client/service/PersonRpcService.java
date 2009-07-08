package org.kuali.student.core.person.ui.client.service;

import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
@RemoteServiceRelativePath("PersonRpcService")
public interface PersonRpcService extends RemoteService {
	
	public static final String SERVICE_URI = "PersonRpcService";

	public static class Util {

		public static PersonRpcServiceAsync getInstance() {

			PersonRpcServiceAsync instance = (PersonRpcServiceAsync) GWT
					.create(PersonRpcService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}
	
    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues);
    public PersonInfo fetchPerson(String personId);
}