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
package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.*;

import java.util.List;

public interface LuiPersonRelationServiceInfc {

    /**
     * Retrieves the list of LUIPersonRelation types
     *
     * @param context - ContextInfo - Context information containing the principalId
     *                and locale information about the caller of service opperation
     * @return list of luiPersonRelationTypes
     */
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(ContextInfc context)
            throws OperationFailedException;

    /**
     * Retrieves the list of LUI Person Relation States
     *
     * @param context - ContextInfo - Context information containing the principalId
     *                and locale information about the caller of service opperation
     * @return list of relation states
     */
    public List<LuiPersonRelationStateInfc> findLuiPersonRelationStates(ContextInfc context)
            throws OperationFailedException;

    /**
     * Retrieves the list of Allowed Relation States
     *
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return list of relationState
     */
    public List<LuiPersonRelationStateInfc> findAllowedRelationStates(String luiPersonRelationType, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the Relation for the specified LUI Person Relation
     *
     * @param luiPersonRelationId - String - Identifier for the LUI Person Relation
     * @param context             - ContextInfo - Context information containing the principalId
     *                            and locale information about the caller of service opperation
     * @return LUI Person Relation information
     */
    public LuiPersonRelationInfc fetchLUIPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     *
     * @param luiPersonRelationIdList - StringList - List of identifiers for LUI Person
     *                                Relations
     * @param context                 - ContextInfo - Context information containing the principalId
     *                                and locale information about the caller of service opperation
     * @return List of LUI Person Relation information
     */
    public List<LuiPersonRelationInfc> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person related to LUI
     *
     * @param personId              - String - Identifier for the LUI Person Relation
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param relationState         - String - Relation State
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return Simple list of LUI Ids
     */
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI
     *
     * @param luiId                 - String - Identifier for the LUI
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param relationState         - String - Relation State
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return Simple list of Person Ids
     */
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation Types for LUI
     *
     * @param personId      - String - Identifier for person
     * @param luiId         - String - Identifier for LUI
     * @param relationState - String - Relation State
     * @param context       - ContextInfo - Context information containing the principalId
     *                      and locale information about the caller of service opperation
     * @return Simple list of LUI Person Relation Types
     */
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypesForLuiPersonRelation(String personId, String luiId, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for LUI
     *
     * @param personId - String - Identifier for person
     * @param luiId    - String - Identifier for LUI
     * @param context  - ContextInfo - Context information containing the principalId
     *                 and locale information about the caller of service opperation
     * @return List of LUI Person Relation info
     */
    public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId, String luiId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids
     *
     * @param personId - String - Identifier for person
     * @param luiId    - String - Identifier for LUI
     * @param context  - ContextInfo - Context information containing the principalId
     *                 and locale information about the caller of service opperation
     * @return List of LUI Person Relation display info
     */
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for Person
     *
     * @param personId - String - Identifier for person
     * @param context  - ContextInfo - Context information containing the principalId
     *                 and locale information about the caller of service opperation
     * @return List of LUI Person Relation info
     */
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForPerson(String personId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for Person
     *
     * @param personId - String - Identifier for person
     * @param context  - ContextInfo - Context information containing the principalId
     *                 and locale information about the caller of service opperation
     * @return Simple list of person relation identifiers
     */
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     *
     * @param luiId   - String - Identifier for LUI
     * @param context - ContextInfo - Context information containing the principalId
     *                and locale information about the caller of service opperation
     * @return List of LUI Person Relation info
     */
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForLui(String luiId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation for LUI
     *
     * @param luiId   - String - Identifier for LUI
     * @param context - ContextInfo - Context information containing the principalId
     *                and locale information about the caller of service opperation
     * @return Simple list of LUI Person Relation identifiers
     */
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves valid Relation States for LUI Person relation
     *
     * @param personId              - String - Identifier for person
     * @param luiId                 - String - Identifier for LUI
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return list of valid relation states
     */
    public List<LuiPersonRelationStateInfc> findValidRelationStatesForLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks to see if it's ok to create a particular type and state of a relation
     * between Person and LUI
     *
     * @param personId              - String - Identifier for person
     * @param luiId                 - String - Identifier for LUI
     * @param luiPersonRelationType - String - Type of LUI Person relation
     * @param relationState         - String - Relation state
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return true if relation of specified type and state is ok to create between
     *         person and lui
     */
    public Boolean isValidLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Checks if a Person and LUI have a particular relation in a particular state
     *
     * @param personId              - String - Identifier for person
     * @param luiId                 - String - Identifier for LUI
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param relationState         - String - Relation state
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return true if relation of specified type and state exists between person and
     *         lui
     */
    public Boolean isRelated(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI
     *
     * @param personId              - String - Identifier for person
     * @param luiId                 - String - Identifier for LUI
     * @param luiPersonRelationType - String - Type of luiPersonRelation
     * @param relationState         - String - Relation State
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return result from validation operation not sure of specifics
     */
    public ValidationResultInfc validateLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIs for an academic time period where the specified relation type and
     * state would be valid for the specified person
     *
     * @param personId              - String - Identifier for person
     * @param luiPersonRelationType - String - Type of luiPersonRelationI
     * @param relationState         - String - Relation State
     * @param atpId                 - String - Identifier for academic time period
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return List of LUIs
     */
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationType, String relationState, String atpId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of people where the specified relation type and state would
     * be valid for the specified LUI
     *
     * @param luiId                 - String - Identifier for LUI
     * @param luiPersonRelationType - String - Type of LUI Person Relation
     * @param relationState         - String - Relation state
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return List of people that could have a particular relation with a LUI
     */
    public List<String> findAllValidPeopleForLui(String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves an ordered list of the "history" of a particular LUI Person Relation,
     * including state and date of change
     *
     * @param luiPersonRelationId - String - Identifier for LUI Person Relation
     * @param context             - ContextInfo - Context information containing the principalId
     *                            and locale information about the caller of service opperation
     * @return List of LUI Person Relation info Andy Bucior This might need to be
     *         slightly different from the normal luiPersonRelation display listing, since it
     *         is dealing with a relationship history type concept.
     */
    public List<LuiPersonRelationInfc> findOrderedRelationStatesForLuiPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation Ids
     *
     * @param luiPersonRelationCriteria - LuiPersonRelationCriteria - Criteria to be
     *                                  used for retrieval of multiple LUI Person Relation identifiers
     * @param context                   - ContextInfo - Context information containing the principalId
     *                                  and locale information about the caller of service opperation
     * @return Simple list of LUI Person Relation identifiers
     */
    public List<String> searchForLuiPersonRelationIds(LuiPersonRelationCriteriaInfc luiPersonRelationCriteria, ContextInfc context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates relation between the specified Person and LUI
     *
     * @param personId              - String - Person Identifier
     * @param luiId                 - String - LUI Identifier
     * @param luiPersonRelationType - String - Type of LUI to Person Relation
     * @param luiPersonRelationInfo - LuiPersonRelationInfo - Information required to
     *                              create the LUI Person relation
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return Structure containing LUI Person relation identifiers
     */
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or nothing
     * transaction - any error will invalidate the entire transaction.
     *
     * @param personId              - String - Identifier for Person
     * @param luiIdList             - StringList - Simple list of LUI identifiers
     * @param relationState         - String - Relation state
     * @param luiPersonRelationType - String - Type of LUI Person relation
     * @param luiPersonRelationInfo - LuiPersonRelationInfo - Information required to
     *                              create the LUI Person relation
     * @param context               - ContextInfo - Context information containing the principalId
     *                              and locale information about the caller of service opperation
     * @return Structure containing LUI Person relation identifiers
     */
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     *
     * @param luiPersonRelationId   - String - Identifier for the LUI Person Relation
     * @param luiPersonRelationInfo - LuiPersonRelationInfo - Changed information about
     *                              the LUI Person Relation
     * @return Updated information about the LUI Person Relation
     */
    public LuiPersonRelationInfc updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes relation between the specified Person and LUI
     *
     * @param luiPersonRelationId - String - Identifier for the LUI Person Relation
     * @return status of the operation (success, failed)
     */
    public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation state
     *
     * @param luiPersonRelationId - String - Identifier for the LUI Person Relation
     * @param relationState       - LuiPersonRelationStateInfo - Relation state
     * @return status of the operation (success or failure)
     */
    public StatusInfc updateRelationState(String luiPersonRelationId, LuiPersonRelationStateInfc relationState, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;
}

