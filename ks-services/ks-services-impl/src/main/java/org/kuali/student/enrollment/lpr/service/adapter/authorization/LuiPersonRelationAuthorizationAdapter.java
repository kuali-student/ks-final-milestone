/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.service.adapter.authorization;

import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.*;


import java.util.List;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.infc.HoldsPermissionService;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;



/**
 * A example of an authorization adapter that extends the adapter
 * template and inserts some example authorization calls.
 *
 * @Author Tom
 */

public class LuiPersonRelationAuthorizationAdapter
        extends LuiPersonRelationServiceAdapter
        implements HoldsPermissionService
{

	private PermissionService permissionService;

	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}


    /**
     * Creates relation between the specified Person and LUI.
     *
     * @param personId              Person Identifier
     * @param luiId                 LUI Identifier
     * @param luiPersonRelationType Type of LUI to Person Relation
     * @param luiPersonRelationInfo Information required to create the
     *                              LUI Person relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException      relation already exists
     * @throws DoesNotExistException       personId, luiId, relationState,
     *                                     luiPersonRelationType does not exist
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     relationState, luiPersonRelationType,
     *                                     luiPersonRelationInfo
     * @throws MissingParameterException   missing personId, luiId,
     *                                     relationState, luiPersonRelationType,
     *                                     luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "create lpr", null)) {
            return (getLprService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context));
        } else {
            throw new PermissionDeniedException("unauthorized to create LPR");
        }
    }


    /**
     * Creates bulk relationships for one specified person. This is an
     * all or nothing transaction - any error will invalidate the
     * entire transaction.
     *
     * @param personId              Identifier for Person
     * @param luiIdList             Simple list of LUI identifiers
     * @param relationState         Relation state
     * @param luiPersonRelationType Type of LUI Person relation
     * @param luiPersonRelationInfo Information required to create the
     *                              LUI Person relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException      relation already exists
     * @throws DoesNotExistException       personId, luiId, relationState,
     *                                     luiPersonRelationType does not exist
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     relationState, luiPersonRelationType,
     *                                     luiPersonRelationInfo
     * @throws MissingParameterException   missing personId, luiId,
     *                                     relationState, luiPersonRelationType,
     *                                     luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "create lpr", null)) {
            return (getLprService().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context));
        } else {
            throw new PermissionDeniedException("unauthorized to create LPR");
        }
    }


    /**
     * Update relation between Person and LUI.
     *
     * @param luiPersonRelationId   Identifier for the LUI Person
     *                              Relation
     * @param luiPersonRelationInfo Changed information about the LUI
     *                              Person Relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Updated information about the LUI Person Relation
     * @throws DoesNotExistException     luiPersonRelationId does not
     *                                   exist
     * @throws InvalidParameterException invalid luiPersonRelationId,
     *                                   luiPersonRelationInfo
     * @throws MissingParameterException missing luiPersonRelationId,
     *                                   luiPersonRelationInfo
     * @throws ReadOnlyException         attempt to update a read only attribute
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        if (isAuthorized(context.getPrincipalId(), "update lpr", luiPersonRelationId)) {
            return (getLprService().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context));
        } else {
            throw new PermissionDeniedException("unauthorized to update LPR " + luiPersonRelationId);
        }
    }


    /**
     * Deletes relation between the specified Person and LUI.
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of service
     *                            operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     luiPersonRelationId does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "delete lpr", luiPersonRelationId)) {
            return (getLprService().deleteLuiPersonRelation(luiPersonRelationId, context));
        } else {
            throw new PermissionDeniedException("unauthorized to delete LPR " + luiPersonRelationId);
        }
    }


   public static final String ENRLLMENT_NAMESPACE = "KS-Enrollment";
    /**
     * Fake authorization method.
     *
     * @param principal
     * @param permissionName  the authorization permission
     * @param qualifier an authorization qualifier
     * @return true if authorization successful
     */

	protected boolean isAuthorized(String principal, String permissionName, String qualifier) {
		AttributeSet permissionDetails = null;
		AttributeSet qualifierDetails = new AttributeSet();
		qualifierDetails.put("qualifierKey", qualifier);
		return this.permissionService.isAuthorized(principal,
				ENRLLMENT_NAMESPACE,
				permissionName,
				permissionDetails,
				qualifierDetails);
	}
}