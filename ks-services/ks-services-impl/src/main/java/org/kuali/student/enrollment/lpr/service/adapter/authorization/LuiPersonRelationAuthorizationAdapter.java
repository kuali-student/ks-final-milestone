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

import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

import java.util.List;


/**
 * A example of an authorization adapter that extends the adapter
 * template and inserts some example authorization calls.
 *
 * @Author Tom
 */

public class LuiPersonRelationAuthorizationAdapter
        extends LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc {


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
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "create lpr", null)) {
            return (getProvider().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context));
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
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "create lpr", null)) {
            return (getProvider().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context));
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
    public LuiPersonRelationInfc updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        if (isAuthorized(context.getPrincipalId(), "update lpr", luiPersonRelationId)) {
            return (getProvider().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context));
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
    public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (isAuthorized(context.getPrincipalId(), "delete lpr", luiPersonRelationId)) {
            return (getProvider().deleteLuiPersonRelation(luiPersonRelationId, context));
        } else {
            throw new PermissionDeniedException("unauthorized to delete LPR " + luiPersonRelationId);
        }
    }


    /**
     * Update relation state.
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param relationState       Relation state
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of service
     *                            operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     luiPersonRelationId,
     *                                   relationState does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId,
     *                                   relationState
     * @throws MissingParameterException missing luiPersonRelationId,
     *                                   relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public StatusInfc updateRelationState(String luiPersonRelationId, LuiPersonRelationStateInfc relationState, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if (isAuthorized(context.getPrincipalId(), "update lpr", luiPersonRelationId)) {
            return (getProvider().updateRelationState(luiPersonRelationId, relationState, context));
        } else {
            throw new PermissionDeniedException("unauthorized to update LPR state for " + luiPersonRelationId);
        }
    }


    /**
     * Fake authorization method.
     *
     * @param principal
     * @param function  the authorization permission
     * @param qualifier an authorization qualifier
     * @return true if authorization successful
     */

    protected boolean isAuthorized(String principal, String function, String qualifier)
            throws OperationFailedException {

        if ("destroy".equals(function) && "system".equals(qualifier)) {
            return (false);
        } else {
            return (true);
        }
    }
}