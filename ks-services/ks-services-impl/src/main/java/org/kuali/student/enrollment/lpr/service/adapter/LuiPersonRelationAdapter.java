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

package org.kuali.student.enrollment.lpr.service.adapter;

import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.infc.*;

import java.util.List;
import org.kuali.student.common.infc.CriteriaInfc;
import org.kuali.student.common.infc.HoldsLprServiceInfc;


/**
 * A example of a generic adapter that does absolutely nothing.
 *
 * @Author Tom
 */

public class LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc, HoldsLprServiceInfc {

    private LuiPersonRelationServiceInfc lprService;

	@Override
    public LuiPersonRelationServiceInfc getLprService() {
        return this.lprService;
    }


@Override
    public void setLprService(LuiPersonRelationServiceInfc lprService) {
        this.lprService = lprService;
    }


    /**
     * Retrieves the list of LUIPersonRelation types.
     *
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of luiPersonRelationTypes
     * @throws OperationFailedException unable to complete request
     */

    @Override
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(ContextInfc context)
            throws OperationFailedException {

        return (getLprService().findLuiPersonRelationTypes(context));
    }


    /**
     * Retrieves the list of LUI Person Relation States.
     *
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of relation states
     * @throws OperationFailedException unable to complete request
     */

    @Override
    public List<LuiPersonRelationStateInfc> findLuiPersonRelationStates(ContextInfc context)
            throws OperationFailedException {

        return (getLprService().findLuiPersonRelationStates(context));
    }


    /**
     * Retrieves the list of Allowed Relation States.
     *
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of relationState
     * @throws DoesNotExistException     luiPersonRelationType not found
     * @throws InvalidParameterException invalid luiPersonRelationType
     * @throws MissingParameterException missing luiPersonRelationType
     * @throws OperationFailedException  unable to complete request
     */

    @Override
    public List<LuiPersonRelationStateInfc> findAllowedRelationStates(String luiPersonRelationType, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        return (this.lprService.findAllowedRelationStates(luiPersonRelationType, context));
    }


    /**
     * Retrieves the Relation for the specified LUI Person Relation.
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of service
     *                            operation
     * @return LUI Person Relation information
     * @throws DoesNotExistException     luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public LuiPersonRelationInfc fetchLUIPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().fetchLUIPersonRelation(luiPersonRelationId, context));
    }


    /**
     * Retrieves the Relation for the specified list of LUI Person
     * Relation Ids.
     *
     * @param luiPersonRelationIdList List of identifiers for LUI
     *                                Person Relations
     * @param context                 Context information containing the principalId
     *                                and locale information about the caller of service
     *                                operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException     One or more luiPersonRelationIds not found
     * @throws InvalidParameterException One or more invalid
     *                                   luiPersonRelationIds
     * @throws MissingParameterException missing luiPersonRelationIdList
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationsByIdList(luiPersonRelationIdList, context));
    }


    /**
     * Retrieves the LUI Ids for Person related to LUI.
     *
     * @param personId              Identifier for the LUI Person Relation
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState         Relation State
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException       personId, luiPersonRelationType,
     *                                     relationState, person to LUI relationship not found
     * @throws DisabledIdentifierException personId found, but has been
     *                                     retired
     * @throws InvalidParameterException   invalid personId,
     *                                     luiPersonRelationType, relationState
     * @throws MissingParameterException   missing personId,
     *                                     luiPersonRelationType, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context));
    }


    /**
     * Retrieves Person Ids related to the specified LUI.
     *
     * @param luiId                 Identifier for the LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState         Relation State
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Simple list of Person Ids
     * @throws DoesNotExistException     luiId, luiPersonRelationType,
     *                                   relationState, LUI to person relationship not found
     * @throws InvalidParameterException invalid luiId,
     *                                   luiPersonRelationType, relationState
     * @throws MissingParameterException missing luiId,
     *                                   luiPersonRelationType, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context));
    }


    /**
     * Retrieves Person Relation Types for LUI.
     *
     * @param personId      Identifier for person
     * @param luiId         Identifier for LUI
     * @param relationState Relation State
     * @param context       Context information containing the principalId
     *                      and locale information about the caller of service
     *                      operation
     * @return Simple list of LUI Person Relation Types
     * @throws DoesNotExistException       personId, luiId, relationState not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId, relationState
     * @throws MissingParameterException   missing personId, luiId, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypesForLuiPersonRelation(String personId, String luiId, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationTypesForLuiPersonRelation(personId, luiId, relationState, context));
    }


    /**
     * Retrieves Person Relation for LUI.
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId, String luiId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelations(personId, luiId, context));
    }


    /**
     * Retrieves LUI Person Relation Ids.
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationIds(personId, luiId, context));
    }


    /**
     * Retrieves LUI Person Relation for Person.
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForPerson(String personId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationsForPerson(personId, context));
    }


    /**
     * Retrieves LUI Person Relation Ids for Person.
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return Simple list of person relation identifiers
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationIdsForPerson(personId, context));
    }


    /**
     * Retrieves LUI Person Relation for a specified LUI.
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForLui(String luiId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationsForLui(luiId, context));
    }


    /**
     * Retrieves LUIPersonRelation for LUI.
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findLuiPersonRelationIdsForLui(luiId, context));
    }


    /**
     * Retrieves valid Relation States for LUI Person relation.
     *
     * @param personId              Identifier for person
     * @param luiId                 Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return list of valid relation states
     * @throws DoesNotExistException       personId, luiId,
     *                                     luiPersonRelationType not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     luiPersonRelationType
     * @throws MissingParameterException   missing personId, luiId,
     *                                     luiPersonRelationType
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<LuiPersonRelationStateInfc> findValidRelationStatesForLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findValidRelationStatesForLuiPersonRelation(personId, luiId, luiPersonRelationType, context));
    }


    /**
     * Checks to see if it's ok to create a particular type and state
     * of a relation between Person and LUI.
     *
     * @param personId              Identifier for person
     * @param luiId                 Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person relation
     * @param relationState         Relation state
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return true if relation of specified type and state is ok to
     *         create between person and lui
     * @throws DoesNotExistException       personId, luiId,
     *                                     luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws MissingParameterException   missing personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public Boolean isValidLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().isValidLuiPersonRelation(personId, luiId, luiPersonRelationType, relationState, context));
    }


    /**
     * Checks if a Person and LUI have a particular relation in a
     * particular state.
     *
     * @param personId              Identifier for person
     * @param luiId                 Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState         Relation state
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return true if relation of specified type and state exists
     *         between person and lui
     * @throws DoesNotExistException       personId, luiId,
     *                                     luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws MissingParameterException   missing personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public Boolean isRelated(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().isRelated(personId, luiId, luiPersonRelationType, relationState, context));
    }


    /**
     * Validates the particular relation in a state for a Person and
     * LUI.
     *
     * @param personId              Identifier for person
     * @param luiId                 Identifier for LUI
     * @param luiPersonRelationType Type of luiPersonRelation
     * @param relationState         Relation State
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return result from validation operation not sure of specifics
     * @throws DoesNotExistException       personId, luiId,
     *                                     luiPersonRelationType, relationState not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws MissingParameterException   missing personId, luiId,
     *                                     luiPersonRelationType, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public ValidationResultInfc validateLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().validateLuiPersonRelation(personId, luiId, luiPersonRelationType, relationState, context));
    }


    /**
     * Retrieves LUIs for an academic time period where the specified
     * relation type and state would be valid for the specified
     * person.
     *
     * @param personId              Identifier for person
     * @param luiPersonRelationType Type of luiPersonRelationI
     * @param relationState         Relation State
     * @param atpId                 Identifier for academic time period
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return List of LUIs
     * @throws DoesNotExistException       personId, luiPersonRelationType,
     *                                     relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid personId,
     *                                     luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException   missing personId,
     *                                     luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationType, String relationState, String atpId, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findAllValidLuisForPerson(personId, luiPersonRelationType, relationState, atpId, context));
    }


    /**
     * Retrieves the list of people where the specified relation type
     * and state would be valid for the specified LUI.
     *
     * @param luiId                 Identifier for LUI
     * @param luiPersonRelationType Type of LUI Person Relation
     * @param relationState         Relation state
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return List of people that could have a particular relation
     *         with a LUI
     * @throws DoesNotExistException       personId, luiPersonRelationType,
     *                                     relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has
     *                                     been retired
     * @throws InvalidParameterException   invalid luiId,
     *                                     luiPersonRelationType, relationState, atpId
     * @throws MissingParameterException   missing luiId,
     *                                     luiPersonRelationType, relationState, atpId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */

    @Override
    public List<String> findAllValidPeopleForLui(String luiId, String luiPersonRelationType, String relationState, ContextInfc context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findAllValidPeopleForLui(luiId, luiPersonRelationType, relationState, context));
    }


    /**
     * Retrieves an ordered list of the "history" of a particular LUI
     * Person Relation, including state and date of change.
     *
     * @param luiPersonRelationId Identifier for LUI Person Relation
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of service
     *                            operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<LuiPersonRelationInfc> findOrderedRelationStatesForLuiPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().findOrderedRelationStatesForLuiPersonRelation(luiPersonRelationId, context));
    }


    /**
     * Retrieves detail of LUI Person Relation Ids.
     *
     * @param luiPersonRelationCriteria Criteria to be used for
     *                                  retrieval of multiple LUI Person Relation identifiers
     * @param context                   Context information containing the principalId
     *                                  and locale information about the caller of service
     *                                  operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    @Override
    public List<String> searchForLuiPersonRelationIds(List<CriteriaInfc> criteria, ContextInfc context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().searchForLuiPersonRelationIds(criteria, context));
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
    public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return (getLprService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, luiPersonRelationInfo, context));
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

        return (getLprService().createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationType, luiPersonRelationInfo, context));
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

        return (getLprService().updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationInfo, context));
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

        return (getLprService().deleteLuiPersonRelation(luiPersonRelationId, context));
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

        return (getLprService().updateRelationState(luiPersonRelationId, relationState, context));
    }
}