/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.exemption.service;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionRequestInfo;

/**
 *
 * @author nwright
 */
public class ExemptionServiceDecorator implements ExemptionService {
    
    private ExemptionService nextDecorator;

    public ExemptionService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(ExemptionService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateExemptionRequest(String validationTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateExemptionRequest(validationTypeKey, exemptionRequestInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateExemption(String validationTypeKey, ExemptionInfo exemptionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateExemption(validationTypeKey, exemptionInfo, context);
    }

    @Override
    public ExemptionRequestInfo updateExemptionRequest(String exemptionRequestId, ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateExemptionRequest(exemptionRequestId, exemptionRequestInfo, context);
    }

    @Override
    public ExemptionInfo updateExemption(String exemptionId, ExemptionInfo exemptionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateExemption(exemptionId, exemptionInfo, context);
    }

    @Override
    public ExemptionInfo retrieveMilestoneExemption(String checkKey, String personId, String milestoneId, String qualifierTypeKey, String qualifierId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().retrieveMilestoneExemption(checkKey, personId, milestoneId, qualifierTypeKey, qualifierId, context);
    }

    @Override
    public ExemptionInfo retrieveDateExemption(String checkKey, String personId, String milestoneId, String qualifierTypeKey, String qualifierId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().retrieveDateExemption(checkKey, personId, milestoneId, qualifierTypeKey, qualifierId, context);
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRequestsForPerson(personId, context);
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getRequestsByTypeForPerson(typeKey, personId, context);
    }

    @Override
    public List<ExemptionInfo> getExemptionsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionsForPerson(personId, context);
    }

    @Override
    public List<ExemptionInfo> getExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionsByTypeForPerson(typeKey, personId, context);
    }

    @Override
    public List<ExemptionInfo> getExemptionsByIds(List<String> exemptionIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionsByIds(exemptionIds, context);
    }

    @Override
    public List<ExemptionRequestInfo> getExemptionRequestsByIds(List<String> exemptionRequestIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionRequestsByIds(exemptionRequestIds, context);
    }

    @Override
    public List<String> getExemptionRequestIdsByType(String exemptionRequestTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionRequestIdsByType(exemptionRequestTypeKey, context);
    }

    @Override
    public List<String> getExemptionRequestIdsByCheck(String checkKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionRequestIdsByCheck(checkKey, context);
    }

    @Override
    public ExemptionRequestInfo getExemptionRequest(String exemptionRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionRequest(exemptionRequestId, context);
    }

    @Override
    public List<String> getExemptionIdsByType(String exemptionTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemptionIdsByType(exemptionTypeKey, context);
    }

    @Override
    public ExemptionInfo getExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExemption(exemptionId, context);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveExemptionsForPerson(personId, context);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveExemptionsByTypeForPerson(typeKey, personId, context);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeProcessAndCheckForPerson(String typeKey, String processKey, String checkKey, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getActiveExemptionsByTypeProcessAndCheckForPerson(typeKey, processKey, checkKey, personId, context);
    }

    @Override
    public StatusInfo deleteExemptionRequest(String exemptionRequestId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteExemptionRequest(exemptionRequestId, context);
    }

    @Override
    public StatusInfo deleteExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteExemption(exemptionId, context);
    }

    @Override
    public ExemptionRequestInfo createExemptionRequest(ExemptionRequestInfo exemptionRequestInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createExemptionRequest(exemptionRequestInfo, context);
    }

    @Override
    public ExemptionInfo createExemption(String exemptionRequestId, ExemptionInfo exemptionInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createExemption(exemptionRequestId, exemptionInfo, context);
    }

    @Override
    public StatusInfo addUseToExemption(String exemptionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addUseToExemption(exemptionId, context);
    }

    
    
}
