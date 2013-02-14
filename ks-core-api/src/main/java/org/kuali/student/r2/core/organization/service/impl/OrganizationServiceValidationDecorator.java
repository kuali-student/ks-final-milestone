/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.organization.service.impl;


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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.service.OrganizationServiceDecorator;
import org.kuali.student.r2.core.organization.service.impl.lib.ValidationUtils;

import java.util.List;


public class OrganizationServiceValidationDecorator extends OrganizationServiceDecorator {
    // validator property w/getter & setter
    private DataDictionaryValidator validator;

    public DataDictionaryValidator getValidator() {
        return validator;
    }

    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateOrgHierarchy(String validationTypeKey, String orgHierarchyTypeKey, OrgHierarchyInfo orgHierarchyInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, orgHierarchyInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateOrgHierarchy(validationTypeKey, orgHierarchyTypeKey, orgHierarchyInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public OrgHierarchyInfo createOrgHierarchy(String orgHierarchyTypeKey, OrgHierarchyInfo orgHierarchyInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgHierarchy(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgHierarchyTypeKey, orgHierarchyInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createOrgHierarchy(orgHierarchyTypeKey, orgHierarchyInfo, contextInfo);
    }

    @Override
    public OrgHierarchyInfo updateOrgHierarchy(String orgHierarchyId, OrgHierarchyInfo orgHierarchyInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgHierarchy(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgHierarchyId, orgHierarchyInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateOrgHierarchy(orgHierarchyId, orgHierarchyInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateOrg(String validationTypeKey, String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, orgInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateOrg(validationTypeKey, orgTypeKey, orgInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public OrgInfo createOrg(String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrg(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgTypeKey, orgInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createOrg(orgTypeKey, orgInfo, contextInfo);
    }

    @Override
    public OrgInfo updateOrg(String orgId, OrgInfo orgInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrg(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgId, orgInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateOrg(orgId, orgInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateOrgOrgRelation(String validationTypeKey, String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, orgOrgRelationInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateOrgOrgRelation(validationTypeKey, orgId, orgPeerId, orgOrgRelationTypeKey, orgOrgRelationInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgOrgRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgId, orgPeerId, orgOrgRelationTypeKey, orgOrgRelationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createOrgOrgRelation(orgId, orgPeerId, orgOrgRelationTypeKey, orgOrgRelationInfo, contextInfo);
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgOrgRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(), orgOrgRelationInfo.getTypeKey(), orgOrgRelationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateOrgOrgRelation(orgOrgRelationId, orgOrgRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateOrgPersonRelation(String validationTypeKey, String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, orgPersonRelationInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateOrgPersonRelation(validationTypeKey, orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo, contextInfo);
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgPersonRelationInfo.getOrgId(), orgPersonRelationInfo.getPersonId(), orgPersonRelationInfo.getTypeKey(), orgPersonRelationInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateOrgPersonRelation(orgPersonRelationId, orgPersonRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateOrgPositionRestriction(String validationTypeKey, String orgId, String orgPersonRelationTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, orgPositionRestrictionInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateOrgPositionRestriction(validationTypeKey, orgId, orgPersonRelationTypeKey, orgPositionRestrictionInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public OrgPositionRestrictionInfo createOrgPositionRestriction(String orgId, String orgPersonRelationTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo)
            throws AlreadyExistsException
            , DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // create
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgPositionRestriction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgId, orgPersonRelationTypeKey, orgPositionRestrictionInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().createOrgPositionRestriction(orgId, orgPersonRelationTypeKey, orgPositionRestrictionInfo, contextInfo);
    }

    @Override
    public OrgPositionRestrictionInfo updateOrgPositionRestriction(String orgPositionRestrictionId, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // update
        try {
            List<ValidationResultInfo> errors =
                    this.validateOrgPositionRestriction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo, contextInfo);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return getNextDecorator().updateOrgPositionRestriction(orgPositionRestrictionId, orgPositionRestrictionInfo, contextInfo);
    }
}

