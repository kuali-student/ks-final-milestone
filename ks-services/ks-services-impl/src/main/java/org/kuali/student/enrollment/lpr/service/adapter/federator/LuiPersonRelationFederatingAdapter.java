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

package org.kuali.student.enrollment.lpr.service.adapter.federator;

import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.core.exceptions.*;
import org.kuali.student.enrollment.lpr.infc.*;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.common.infc.CriteriaInfc;


/**
 * An adapter to federate internal and external services. Only one
 * service may be designated an internal service in which creates and
 * updates occur. The external services are used to bring in data from
 * outside sources. Isn't this fun?
 *
 * @Author Tom
 */

public class LuiPersonRelationFederatingAdapter
        extends LuiPersonRelationAdapter
        implements LuiPersonRelationServiceInfc {

    private List<LuiPersonRelationServiceInfc> externalServices;


    /**
     * Gets the underlying service provider.
     *
     * @return the underlying provider
     */

    protected List<LuiPersonRelationServiceInfc> getExternalProviders() {
        return (this.externalServices);
    }


    /**
     * Sets all the external providers.
     *
     * @param externalProviders a list of external service providers
     */

    protected void setExternalProviders(List<LuiPersonRelationServiceInfc> externalProviders) {
        this.externalServices = externalProviders;
        return;
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

        Set<LuiPersonRelationTypeInfc> lprTypes = new HashSet<LuiPersonRelationTypeInfc>();
        lprTypes.addAll(getLprService().findLuiPersonRelationTypes(context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprTypes.addAll(provider.findLuiPersonRelationTypes(context));
        }

        return (new ArrayList<LuiPersonRelationTypeInfc>(lprTypes));
    }


    /**
     * Retrieves the list of LUI Person Relation States
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

        Set<LuiPersonRelationStateInfc> lprStates = new HashSet<LuiPersonRelationStateInfc>();
        lprStates.addAll(getLprService().findLuiPersonRelationStates(context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprStates.addAll(provider.findLuiPersonRelationStates(context));
        }

        return (new ArrayList<LuiPersonRelationStateInfc>(lprStates));
    }


    /**
     * Retrieves the list of Allowed Relation States
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

        Set<LuiPersonRelationStateInfc> lprStates = new HashSet<LuiPersonRelationStateInfc>();
        lprStates.addAll(getLprService().findAllowedRelationStates(luiPersonRelationType, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprStates.addAll(provider.findAllowedRelationStates(luiPersonRelationType, context));
        }

        return (new ArrayList<LuiPersonRelationStateInfc>(lprStates));
    }


    /**
     * Retrieves the Relation for the specified LUI Person Relation
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

        try {
            return (getLprService().fetchLUIPersonRelation(luiPersonRelationId, context));
        } catch (DoesNotExistException dne) {
        }

        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            try {
                return (getLprService().fetchLUIPersonRelation(luiPersonRelationId, context));
            } catch (DoesNotExistException dne) {
            }
        }

        throw new DoesNotExistException(luiPersonRelationId + " not found");
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

        List<LuiPersonRelationInfc> lprs = new ArrayList<LuiPersonRelationInfc>();

        for (String id : luiPersonRelationIdList) {
            lprs.add(fetchLUIPersonRelation(id, context));
        }

        return (lprs);
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

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context));
        }

        return (new ArrayList<String>(ids));
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


        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context));
        }

        return (new ArrayList<String>(ids));
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

        Set<LuiPersonRelationTypeInfc> lprTypes = new HashSet<LuiPersonRelationTypeInfc>();

        lprTypes.addAll(getLprService().findLuiPersonRelationTypesForLuiPersonRelation(personId, luiId, relationState, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprTypes.addAll(provider.findLuiPersonRelationTypesForLuiPersonRelation(personId, luiId, relationState, context));
        }

        return (new ArrayList<LuiPersonRelationTypeInfc>(lprTypes));
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

        List<LuiPersonRelationInfc> lprs = new ArrayList<LuiPersonRelationInfc>();

        lprs.addAll(getLprService().findLuiPersonRelations(personId, luiId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprs.addAll(provider.findLuiPersonRelations(personId, luiId, context));
        }

        return (lprs);
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

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIds(personId, luiId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.findLuiPersonRelationIds(personId, luiId, context));
        }

        return (new ArrayList<String>(ids));
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

        List<LuiPersonRelationInfc> lprs = new ArrayList<LuiPersonRelationInfc>();

        lprs.addAll(getLprService().findLuiPersonRelationsForPerson(personId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprs.addAll(provider.findLuiPersonRelationsForPerson(personId, context));
        }

        return (lprs);
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

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIdsForPerson(personId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.findLuiPersonRelationIdsForPerson(personId, context));
        }

        return (new ArrayList<String>(ids));
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

        List<LuiPersonRelationInfc> lprs = new ArrayList<LuiPersonRelationInfc>();

        lprs.addAll(getLprService().findLuiPersonRelationsForLui(luiId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprs.addAll(provider.findLuiPersonRelationsForLui(luiId, context));
        }

        return (lprs);
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

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIdsForLui(luiId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.findLuiPersonRelationIdsForLui(luiId, context));
        }

        return (new ArrayList<String>(ids));
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

        if (getLprService().isRelated(personId, luiId, luiPersonRelationType, relationState, context)) {
            return (true);
        }

        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            if (provider.isRelated(personId, luiId, luiPersonRelationType, relationState, context)) {
                return (true);
            }
        }

        return (false);
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

        List<LuiPersonRelationInfc> lprs = new ArrayList<LuiPersonRelationInfc>();

        lprs.addAll(getLprService().findOrderedRelationStatesForLuiPersonRelation(luiPersonRelationId, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            lprs.addAll(provider.findOrderedRelationStatesForLuiPersonRelation(luiPersonRelationId, context));
        }

        return (lprs);
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

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().searchForLuiPersonRelationIds(criteria, context));
        for (LuiPersonRelationServiceInfc provider : getExternalProviders()) {
            ids.addAll(provider.searchForLuiPersonRelationIds(criteria, context));
        }

        return (new ArrayList<String>(ids));
    }
}