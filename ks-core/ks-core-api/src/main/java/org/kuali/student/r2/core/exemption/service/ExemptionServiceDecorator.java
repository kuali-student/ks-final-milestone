/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.exemption.service;

import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
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
    public List<ValidationResultInfo> validateExemptionRequest(String validationTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().validateExemptionRequest(validationTypeKey, exemptionRequestInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateExemption(String validationTypeKey, ExemptionInfo exemptionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator ().validateExemption(validationTypeKey, exemptionInfo, contextInfo);
    }

    @Override
    public ExemptionRequestInfo updateExemptionRequest(String exemptionRequestId, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator ().updateExemptionRequest(exemptionRequestId, exemptionRequestInfo, contextInfo);
    }

    @Override
    public ExemptionInfo updateExemption(String exemptionId, ExemptionInfo exemptionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator ().updateExemption(exemptionId, exemptionInfo, contextInfo);
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getRequestsByPerson(personId, contextInfo);
    }

    public List<ExemptionRequestInfo> getRequestsByRequester(String requesterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getRequestsByRequester(requesterId, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getExemptionsForRequest(String requestId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionsForRequest(requestId, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getExemptionsForPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionsForPerson(personId, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionsByTypeForPerson(typeKey, personId, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getExemptionsByIds(List<String> exemptionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionsByIds(exemptionIds, contextInfo);
    }

    @Override
    public List<ExemptionRequestInfo> getExemptionRequestsByIds(List<String> exemptionRequestIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionRequestsByIds(exemptionRequestIds, contextInfo);
    }

    @Override
    public List<String> getExemptionRequestIdsByType(String exemptionRequestTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionRequestIdsByType(exemptionRequestTypeKey, contextInfo);
    }

    @Override
    public ExemptionRequestInfo getExemptionRequest(String exemptionRequestId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionRequest(exemptionRequestId, contextInfo);
    }

    @Override
    public List<String> getExemptionIdsByType(String exemptionTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemptionIdsByType(exemptionTypeKey, contextInfo);
    }

    @Override
    public ExemptionInfo getExemption(String exemptionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getExemption(exemptionId, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsForPerson(String personId, Date asOfDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getActiveExemptionsForPerson(personId, asOfDate, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeProcessAndCheckForPerson(String typeKey, String processKey, String checkId, String personId, Date asOfDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getActiveExemptionsByTypeProcessAndCheckForPerson(typeKey, processKey, checkId, personId, asOfDate, contextInfo);
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(String typeKey, String personId, Date asOfDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getActiveExemptionsByTypeForPerson(typeKey, personId, asOfDate, contextInfo);
    }

    @Override
    public StatusInfo deleteExemptionRequest(String exemptionRequestId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().deleteExemptionRequest(exemptionRequestId, contextInfo);
    }

    @Override
    public StatusInfo deleteExemption(String exemptionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().deleteExemption(exemptionId, contextInfo);
    }

    @Override
    public ExemptionRequestInfo createExemptionRequest(String personId, String exemptionRequestTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().createExemptionRequest(personId, exemptionRequestTypeKey, exemptionRequestInfo, contextInfo);
    }

    @Override
    public ExemptionInfo createExemption(String exemptionRequestId, String exemptionTypeKey, ExemptionInfo exemptionInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().createExemption(exemptionRequestId, exemptionTypeKey, exemptionInfo, contextInfo);
    }

    @Override
    public StatusInfo addUseToExemption(String exemptionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().addUseToExemption(exemptionId, contextInfo);
    }
}
