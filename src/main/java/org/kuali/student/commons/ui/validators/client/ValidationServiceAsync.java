package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ValidationServiceAsync {

	public void validate(String validationId, Map<String, String> data, String locale, String messageGroup, AsyncCallback callback);
	
}
