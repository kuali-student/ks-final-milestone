package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ValidationService extends RemoteService {

	public static final String SERVICE_URI = "/ValidationService";

	public static class Util {

		public static ValidationServiceAsync getInstance() {

			ValidationServiceAsync instance = (ValidationServiceAsync) GWT
					.create(ValidationService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}
	
	public ValidationResult validate(String validationId, Map<String, String> data, String locale, String messageGroup);
	
}
