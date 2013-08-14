package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

//import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationServiceDecorator;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;

public class CourseRegistrationServiceValidationDecorator
        extends CourseRegistrationServiceDecorator {

    private DataDictionaryValidator validator;

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }
    private LprService lprService;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public RegistrationResponseInfo submitRegistrationRequest(String registrationRequestId,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        LprTransactionInfo storedLprTransaction = lprService.getLprTransaction(registrationRequestId, contextInfo);
        List<ValidationResultInfo> validationErrors = new ArrayList<ValidationResultInfo>();

        if (storedLprTransaction.getStateKey().equals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY) ||
                storedLprTransaction.getStateKey().equals(LprServiceConstants.LPRTRANS_DISCARDED_STATE_KEY) ||
                storedLprTransaction.getStateKey().equals(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY)) {

            throw new OperationFailedException("The state key validation failed", new DataValidationErrorException(
                    "The state key validation failed", validationErrors));
        }
        return getNextDecorator().submitRegistrationRequest(registrationRequestId, contextInfo);
    }
}
