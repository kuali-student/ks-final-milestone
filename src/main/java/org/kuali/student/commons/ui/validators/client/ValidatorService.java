package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ValidatorService extends RemoteService {

	public static final String SERVICE_URI = "/ValidatorService";

	public static class Util {

		public static ValidatorServiceAsync getInstance() {

			ValidatorServiceAsync instance = (ValidatorServiceAsync) GWT
					.create(ValidatorService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}

	public ValidatorDefinition getValidatorDefinition(String id);
	public Map<String, ValidatorDefinition> getValidatorDefinitions(String type);
	public Boolean createValidator(String id, String type, String script);
	public Boolean updateValidator(String id, String type, String script);
	public Boolean deleteValidator(String id, String type, String script);
	
}
