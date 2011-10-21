package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationServiceDecorator;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationServiceValidationDecorator extends CourseRegistrationServiceDecorator {

    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;

    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    private LuiPersonRelationService lprService;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    @Override
    public RegRequestInfo createRegRequest(RegRequestInfo regRequestInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (regRequestInfo.getRequestorId() == null)
            throw new DataValidationErrorException("Null field requestorId");
        if (regRequestInfo.getRegRequestItems() == null)
            throw new DataValidationErrorException("Not a valid request, missing request items");

        return getNextDecorator().createRegRequest(regRequestInfo, context);
    }
    
    @Override
    public RegResponseInfo submitRegRequest(String regRequestId, ContextInfo context) throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
    DataValidationErrorException, AlreadyExistsException {

        LprTransactionInfo storedLprTransaction = lprService.getLprTransaction(regRequestId, context);
        List<ValidationResultInfo> validationErrors = new ArrayList<ValidationResultInfo>();

         if(storedLprTransaction.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY)||
                storedLprTransaction.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_DISCARDED_STATE_KEY)||
                 storedLprTransaction.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_FAILED_STATE_KEY)){

             throw new DataValidationErrorException("The state key validation failed", validationErrors);
         }
        return getNextDecorator().submitRegRequest(regRequestId, context);
    }
}
