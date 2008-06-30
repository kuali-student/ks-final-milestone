package org.kuali.student.commons.ui.validators.server.gwt;

import java.util.Map;

import org.kuali.student.commons.ui.validators.client.ValidatorDefinition;
import org.kuali.student.commons.ui.validators.client.ValidatorService;
import org.kuali.student.commons.ui.validators.server.impl.ValidatorServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ValidatorServiceGWT extends RemoteServiceServlet implements ValidatorService {
	private static final long serialVersionUID = 1L;
	ValidatorService impl = new ValidatorServiceImpl();
	
	@Override
	public Boolean createValidator(String id, String type, String script) {
		return impl.createValidator(id, type, script);
	}

	@Override
	public Boolean deleteValidator(String id, String type, String script) {
		return impl.deleteValidator(id, type, script);
	}

	@Override
	public ValidatorDefinition getValidatorDefinition(String id) {
		return impl.getValidatorDefinition(id);
	}

	@Override
	public Map<String, ValidatorDefinition> getValidatorDefinitions(String type) {
		return impl.getValidatorDefinitions(type);
	}

	@Override
	public Boolean updateValidator(String id, String type, String script) {
		return impl.updateValidator(id, type, script);
	}

}
