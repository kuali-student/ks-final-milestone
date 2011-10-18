/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.decorators;

import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.service.util.ValidationUtils;

/**
 * An example Validation decorator for the {@link LuiPersonRelationService}.
 * Additional validations are performed for the validateLuiPersonRelation,
 * createLuiPersonRelation and updateLuiPersonRelation methods here
 * 
 * @author sambit
 */
public class LuiPersonRelationServiceValidationDecorator extends LuiPersonRelationServiceDecorator implements
        HoldsValidator {

    private DataDictionaryValidator validator;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, luiPersonRelationInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateLpr(validationType,
                    luiPersonRelationInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate lui-person relation", ex);
        }
        return errors;
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (lprTransactionInfo.getId() == null) {
            throw new DataValidationErrorException("Id is mandatory on an update");
        }
        if(lprTransactionId==null || !lprTransactionId.equals(lprTransactionInfo.getId()))
            throw new DataValidationErrorException("Ids are not same in the update parameter");

        return getNextDecorator().updateLprTransaction(lprTransactionId, lprTransactionInfo, context);
    }

    @Override
    public String createLpr(String personId, String luiId, String luiPersonRelationType,
            LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, DataValidationErrorException, ReadOnlyException, PermissionDeniedException {
        if (luiPersonRelationInfo.getId() != null) {
            throw new ReadOnlyException("Id is not allowed to be supplied on a create");
        }
        if (luiPersonRelationInfo.getMeta() != null) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        _luiPersonRelationFullValidation(luiPersonRelationInfo, context);
        return getNextDecorator().createLpr(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
    }

    @Override
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        _luiPersonRelationFullValidation(luiPersonRelationInfo, context);
        LuiPersonRelationInfo orig = this.getLpr(luiPersonRelationId, context);

        checkReadOnly("id", orig.getId(), luiPersonRelationInfo.getId());
        checkReadOnly("type", orig.getTypeKey(), luiPersonRelationInfo.getTypeKey());
        checkReadOnly("createId", orig.getMeta().getCreateId(), luiPersonRelationInfo.getMeta().getCreateId());
        checkReadOnly("createTime", orig.getMeta().getCreateTime(), luiPersonRelationInfo.getMeta().getCreateTime());

        if (orig.getMeta().getVersionInd().equals(luiPersonRelationInfo.getMeta().getVersionInd())) {
            checkReadOnly("updateId", orig.getMeta().getUpdateId(), luiPersonRelationInfo.getMeta().getUpdateId());
            checkReadOnly("updateTime", orig.getMeta().getUpdateTime(), luiPersonRelationInfo.getMeta().getUpdateTime());
        }

        return getNextDecorator().updateLpr(luiPersonRelationId, luiPersonRelationInfo, context);
    }

    @Override
    public LprTransactionInfo createLprTransaction(
            @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (lprTransactionInfo.getId() != null) {
            throw new DataValidationErrorException("Id is not allowed to be supplied on a create");
        }

        return getNextDecorator().createLprTransaction(lprTransactionInfo, context);
    }

    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (lprTransactionId == null) {
            throw new InvalidParameterException("Id is mandatory to be supplied on a get*");
        }

        return getNextDecorator().getLprTransaction(lprTransactionId, context);
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList,
                String relationState, String luiPersonRelationTypeKey,
                LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DisabledIdentifierException,
                DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if (null == personId || personId.isEmpty()) {
            throw new MissingParameterException("Person ID is required");
        }
        if (null == luiIdList || luiIdList.isEmpty()) {
            throw new MissingParameterException("A list of LUI's is required");
        }
        if (null == luiPersonRelationTypeKey || luiPersonRelationTypeKey.isEmpty()) {
            throw new MissingParameterException("Relation state is required");
        }
        if (null == relationState || relationState.isEmpty()) {
            throw new MissingParameterException("LUI-Person relation type key is required");
        }
        if (null == luiPersonRelationInfo) {
            throw new MissingParameterException("LUI-Person relation info is required");
        }

        return getNextDecorator().createBulkRelationshipsForPerson(
                personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }



    private void _luiPersonRelationFullValidation(LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateLpr(
                    DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiPersonRelationInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating lui-person relation", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating lui-person relation", ex);
        }
    }

    private void checkReadOnly(String field, Object orig, Object supplied) throws ReadOnlyException {
        checkReadOnly(field, orig, supplied, "" + orig, "" + supplied);
    }

    private void checkReadOnly(String field, Object orig, Object supplied, String origStr, String suppliedStr)
            throws ReadOnlyException {
        if (orig != null) {
            if (orig.equals(supplied)) {
                return;
            }
        }
        throw new ReadOnlyException(field + " is read only but the original value " + origStr
                + " and the supplied new=" + suppliedStr);
    }

}
