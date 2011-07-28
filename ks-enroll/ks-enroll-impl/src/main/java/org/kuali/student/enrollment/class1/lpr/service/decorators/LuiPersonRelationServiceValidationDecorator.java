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
package org.kuali.student.enrollment.class1.lpr.service.decorators;


import java.util.List;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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


/**
 * An example Validation decorator for the {@link LuiPersonRelationService}. Additional validations are performed for the validateLuiPersonRelation, createLuiPersonRelation and updateLuiPersonRelation
 * methods here
 * 
 * @author sambit
 */
public class LuiPersonRelationServiceValidationDecorator extends LuiPersonRelationServiceDecorator implements HoldsValidator{

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
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return nextDecorator.findLuiPersonRelationsForLui(luiId, context);
    }
    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
    }

	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		
	    this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), luiPersonRelationInfo, context);
		
	    return super.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
		
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context) throws
			AlreadyExistsException,
			DoesNotExistException,
			DisabledIdentifierException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			DataValidationErrorException,
			ReadOnlyException,
			PermissionDeniedException {
		List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.name(),
				luiPersonRelationInfo, context);
		if (luiPersonRelationInfo.getId() != null) {
			throw new ReadOnlyException("Id is not allowed to be supplied on a create");
		}
		if (luiPersonRelationInfo.getMeta() != null) {
			throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
		}
		if (!vris.isEmpty()) {
			throw new DataValidationErrorException("Failed validation", vris);
		}
		return super.createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context);
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	ReadOnlyException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		List<ValidationResultInfo> vris = this.validateLuiPersonRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.name(),
				luiPersonRelationInfo, context);
		LuiPersonRelationInfo orig = this.fetchLuiPersonRelation(luiPersonRelationId, context);
		
		checkReadOnly("id", orig.getId(), luiPersonRelationInfo.getId());
		checkReadOnly("type", orig.getTypeKey(), luiPersonRelationInfo.getTypeKey());
		checkReadOnly("createId", orig.getMeta().getCreateId(), luiPersonRelationInfo.getMeta().getCreateId());
		checkReadOnly("createTime", orig.getMeta().getCreateTime(), luiPersonRelationInfo.getMeta().getCreateTime());
		
		if (orig.getMeta().getVersionInd().equals(luiPersonRelationInfo.getMeta().getVersionInd())) {
			checkReadOnly("updateId", orig.getMeta().getUpdateId(), luiPersonRelationInfo.getMeta().getUpdateId());
			checkReadOnly("updateTime", orig.getMeta().getUpdateTime(), luiPersonRelationInfo.getMeta().getUpdateTime());
		}

		if (!vris.isEmpty()) {
			throw new DataValidationErrorException("Failed validation", vris);
		}
		
		return super.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context);
	}

    private void checkReadOnly(String field, Object orig, Object supplied)
    throws ReadOnlyException {
        checkReadOnly(field, orig, supplied, "" + orig, "" + supplied);
    }

    private void checkReadOnly(String field, Object orig, Object supplied, String origStr, String suppliedStr)
    throws ReadOnlyException {
        if (orig != null) {
            if (orig.equals(supplied)) {
                return;
            }
        }
        throw new ReadOnlyException(field + " is read only but the original value " + origStr + " and the supplied new=" + suppliedStr);
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#createLuiPersonRelationTransaction(org.kuali.student.enrollment.lpr.dto.LprTransactionInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo createLuiPersonRelationTransaction(
            LPRTransactionInfo lPRTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#createLuiPersonRelationTransactionFromExisting(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo createLuiPersonRelationTransactionFromExisting(
            String luiPersonRelationTransactionId, ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#updateLuiPersonRelationTransaction(java.lang.String, org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo updateLuiPersonRelationTransaction(String lprTransactionId,
            LPRTransactionItemInfo luiPersonRelationRequestInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo getLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

     
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#deleteLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public StatusInfo deleteLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

   
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByPerson(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPerson(String personId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#findLuiPersonRelationsForPersonAndAtp(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPersonAndAtp(String personId, String atpId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByAtpAndPerson(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndPerson(String atpId,
            String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByAtpAndLui(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndLui(String luiId,
            String atpId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByPersonAndLui(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPersonAndLui(String personId,
            String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#submitLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo submitLuiPersonRelationTransaction(String lprTransactionId,
            ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

       
}

