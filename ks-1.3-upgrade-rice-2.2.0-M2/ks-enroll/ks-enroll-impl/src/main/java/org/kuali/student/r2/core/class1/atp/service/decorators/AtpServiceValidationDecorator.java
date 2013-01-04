package org.kuali.student.r2.core.class1.atp.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

public class AtpServiceValidationDecorator extends AtpServiceDecorator implements HoldsValidator, HoldsDataDictionaryService {

    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (null == atpId) {
            throw new DoesNotExistException("Null parameter in the input:atpId");
        }
        return getNextDecorator().getAtp(atpId, context);
    }

    @Override
    public List<AtpInfo> getAtpsByIds(List<String> atpIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == atpIds || atpIds.size() == 0) {
            throw new DoesNotExistException("Null parameter in the input:atpId");
        }
        return getNextDecorator().getAtpsByIds(atpIds, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == startDate || null == endDate) {
            throw new MissingParameterException("Null parameter in the input: startDate or endDate");
        }
        return getNextDecorator().getAtpsByDates(startDate, endDate, context);
    }

    @Override
    public AtpInfo createAtp(String atpTypeKey, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _atpFullValidation(atpInfo, context);

        if (atpInfo.getId() != null) {
            if (!CommonServiceConstants.isIdAllowedOnCreate(context)) {
                throw new ReadOnlyException("ID cannot be supplied when creating an ATP.");
            }
        }

        return getNextDecorator().createAtp(atpTypeKey, atpInfo, context);
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _atpFullValidation(atpInfo, context);
        try {
            return getNextDecorator().updateAtp(atpId, atpInfo, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException(e.getMessage());
        }
    }

    private void _atpFullValidation(AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateAtp(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), atpInfo.getTypeKey(), atpInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating atp", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating atp", ex);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException("Error validating milestone", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey, AtpInfo atpInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> errors;

        errors = ValidationUtils.validateInfo(validator, validationType, atpInfo, context);
        List<ValidationResultInfo> nextDecorationErrors = getNextDecorator().validateAtp(validationType, atpTypeKey, atpInfo, context);
        if (null != nextDecorationErrors) {
            errors.addAll(nextDecorationErrors);
        }

        return errors;
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId, String relatedAtpId,
            String atpAtpRelationTypeKey,
            AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _atpAtpRelationFullValidation(atpAtpRelationInfo, context);
        return getNextDecorator().createAtpAtpRelation(atpId, relatedAtpId, atpAtpRelationTypeKey, atpAtpRelationInfo, context);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _atpAtpRelationFullValidation(atpAtpRelationInfo, context);
        try {
            return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException(e.getMessage());
        }
    }

    private void _atpAtpRelationFullValidation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException,
            MissingParameterException {
//        try {
//            List<ValidationResultInfo> errors = this.validateAtpAtpRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), atpAtpRelationInfo, context);
//            if (!errors.isEmpty()) {
//                throw new DataValidationErrorException("Error(s) occurred validating atp-atp relation", errors);
//            }
//        } catch (DoesNotExistException ex) {
//            throw new OperationFailedException("Error validating atp-atp relation", ex);
//        }
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerKey,
            String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
            ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, atpAtpRelationInfo, context);
            List<ValidationResultInfo> nextDecorationErrors = getNextDecorator().validateAtpAtpRelation(validationTypeKey, atpId, atpPeerKey, atpAtpRelationTypeKey, atpAtpRelationInfo, context);
            if (null != nextDecorationErrors) {
                errors.addAll(nextDecorationErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating atp-atp relation", ex);
        }
        return errors;
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneTypeKey, MilestoneInfo milestoneInfo, ContextInfo context)
            throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _milestoneFullValidation(milestoneInfo, context);

        if (milestoneInfo.getId() != null) {
            if (!CommonServiceConstants.isIdAllowedOnCreate(context)) {
                throw new ReadOnlyException("ID cannot be populated when creating milestone.");
            }
        }

        return getNextDecorator().createMilestone(milestoneTypeKey, milestoneInfo, context);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _milestoneFullValidation(milestoneInfo, context);
        try {
            return getNextDecorator().updateMilestone(milestoneId, milestoneInfo, context);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException(e.getMessage());
        }
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (StringUtils.isEmpty(milestoneId)) {
            throw new MissingParameterException("milestoneId");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().calculateMilestone(milestoneId, contextInfo);
    }

    private void _milestoneFullValidation(MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException,
            MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateMilestone(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), milestoneInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating milestone", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating milestone", ex);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException("Error validating milestone", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, milestoneInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate milestone", ex);
        }
        return errors;
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (milestoneId == null) {

            throw new InvalidParameterException(milestoneId);
        }

        return getNextDecorator().getMilestone(milestoneId, context);

    }
}
