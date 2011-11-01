package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import org.kuali.student.enrollment.class2.courseregistration.service.assembler.CourseRegistrationAssembler;
import org.kuali.student.enrollment.class2.courseregistration.service.assembler.RegRequestAssembler;
import org.kuali.student.enrollment.class2.courseregistration.service.assembler.RegResponseAssembler;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationServiceDecorator;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.lum.lrc.service.LrcService;
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
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationServiceValidationDecorator extends CourseRegistrationServiceDecorator {

    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;
    private LuiPersonRelationService lprService;
    private CourseOfferingService courseOfferingService;
    private LRCService lrcService;

       public LRCService getLrcService() {
           return lrcService;
       }

       public void setLrcService(LRCService lrcService) {
           this.lrcService = lrcService;
       }

       public LuiPersonRelationService getLprService() {
           return lprService;
       }

       public void setLprService(LuiPersonRelationService lprService) {
           this.lprService = lprService;
       }

       public CourseOfferingService getCourseOfferingService() {
           return courseOfferingService;
       }

       public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
           this.courseOfferingService = courseOfferingService;
       }


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
        LprTransactionInfo lprIn = lprService.getLprTransaction(regRequestId, context);
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
        if(lprIn.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY)|| lprIn.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SUBMITTED_STATE_KEY)||
                lprIn.getStateKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY)) {
            ValidationResultInfo validationResultInfo = ValidationResultInfo.newInstance()  ;
            validationResultInfo.setMessage("Validation of state failed. Reg Request state is: " +lprIn.getStateKey());
            validationResults.add(validationResultInfo);

        }

        if(validationResults.size()>0){
            throw new DataValidationErrorException("Submit reg request validation failed:", validationResults );
        }
        return getNextDecorator().submitRegRequest(regRequestId, context);
    }
}
