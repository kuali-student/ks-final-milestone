package org.kuali.student.commons.ui.validators.server.gwt;

import java.util.Map;

import org.kuali.student.commons.ui.validators.client.ValidatorDefinition;
import org.kuali.student.commons.ui.validators.client.ValidatorService;
import org.kuali.student.commons.ui.validators.server.impl.ValidatorServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * GWT remote service servlet provides a wrapper for the Validator service
 */
public class ValidatorServiceGWT extends RemoteServiceServlet implements ValidatorService {
    private static final long serialVersionUID = 1L;
    ValidatorService impl = new ValidatorServiceImpl();

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#createValidator(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Boolean createValidator(String id, String type, String script) {
        return impl.createValidator(id, type, script);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#deleteValidator(java.lang.String)
     */
    @Override
    public Boolean deleteValidator(String id) {
        return impl.deleteValidator(id);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#getValidatorDefinition(java.lang.String)
     */
    @Override
    public ValidatorDefinition getValidatorDefinition(String id) {
        return impl.getValidatorDefinition(id);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#getValidatorDefinitions(java.lang.String)
     */
    @Override
    public Map<String, ValidatorDefinition> getValidatorDefinitions(String type) {
        return impl.getValidatorDefinitions(type);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#updateValidator(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Boolean updateValidator(String id, String type, String script) {
        return impl.updateValidator(id, type, script);
    }

}
