package org.kuali.student.commons.ui.validators.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ValidatorServiceAsync {
	public void getValidatorDefinition(String id, AsyncCallback<ValidatorDefinition> callback);
	public void getValidatorDefinitions(String type, AsyncCallback<Map<String, ValidatorDefinition>> callback);
	public void createValidator(String id, String type, String script, AsyncCallback<Boolean> callback);
	public void updateValidator(String id, String type, String script, AsyncCallback<Boolean> callback);
	public void deleteValidator(String id, String type, String script, AsyncCallback<Boolean> callback);
}
