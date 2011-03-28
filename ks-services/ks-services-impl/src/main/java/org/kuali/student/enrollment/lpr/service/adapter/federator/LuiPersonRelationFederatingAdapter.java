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

import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.common.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.CriteriaInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;


/**
 * An adapter to federate internal and external services. Only one
 * service may be designated an internal service in which creates and
 * updates occur. The external services are used to bring in data from
 * outside sources. Isn't this fun?
 *
 * @Author Tom
 */

public class LuiPersonRelationFederatingAdapter
        extends LuiPersonRelationServiceAdapter
       {

    private List<LuiPersonRelationService> externalServices;


    /**
     * Gets the underlying service provider.
     *
     * @return the underlying provider
     */

    protected List<LuiPersonRelationService> getExternalProviders() {
        return (this.externalServices);
    }


    /**
     * Sets all the external providers.
     *
     * @param externalProviders a list of external service providers
     */

    protected void setExternalProviders(List<LuiPersonRelationService> externalProviders) {
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
    public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes(ContextInfo context)
            throws OperationFailedException {

        Set<LuiPersonRelationTypeInfo> lprTypes = new HashSet<LuiPersonRelationTypeInfo>();
        lprTypes.addAll(getLprService().findLuiPersonRelationTypes(context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            lprTypes.addAll(provider.findLuiPersonRelationTypes(context));
        }

        return (new ArrayList<LuiPersonRelationTypeInfo>(lprTypes));
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
    public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(ContextInfo context)
            throws OperationFailedException {

        Set<LuiPersonRelationStateInfo> lprStates = new HashSet<LuiPersonRelationStateInfo>();
        lprStates.addAll(getLprService().findLuiPersonRelationStates(context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            lprStates.addAll(provider.findLuiPersonRelationStates(context));
        }

        return (new ArrayList<LuiPersonRelationStateInfo>(lprStates));
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
    public List<LuiPersonRelationStateInfo> findAllowedRelationStates(String luiPersonRelationType, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        Set<LuiPersonRelationStateInfo> lprStates = new HashSet<LuiPersonRelationStateInfo>();
        lprStates.addAll(getLprService().findAllowedRelationStates(luiPersonRelationType, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            lprStates.addAll(provider.findAllowedRelationStates(luiPersonRelationType, context));
        }

        return (new ArrayList<LuiPersonRelationStateInfo>(lprStates));
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
    public LuiPersonRelationInfo fetchLUIPersonRelation(String luiPersonRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        try {
            return (getLprService().fetchLUIPersonRelation(luiPersonRelationId, context));
        } catch (DoesNotExistException dne) {
        }

        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprs = new ArrayList<LuiPersonRelationInfo>();

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
    public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationType, String relationState, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiIdsRelatedToPerson(personId, luiPersonRelationType, relationState, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationType, String relationState, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {


        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            ids.addAll(provider.findPersonIdsRelatedToLui(luiId, luiPersonRelationType, relationState, context));
        }

        return (new ArrayList<String>(ids));
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
    public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprs = new ArrayList<LuiPersonRelationInfo>();

        lprs.addAll(getLprService().findLuiPersonRelations(personId, luiId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIds(personId, luiId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprs = new ArrayList<LuiPersonRelationInfo>();

        lprs.addAll(getLprService().findLuiPersonRelationsForPerson(personId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIdsForPerson(personId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationInfo> lprs = new ArrayList<LuiPersonRelationInfo>();

        lprs.addAll(getLprService().findLuiPersonRelationsForLui(luiId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
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
    public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().findLuiPersonRelationIdsForLui(luiId, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            ids.addAll(provider.findLuiPersonRelationIdsForLui(luiId, context));
        }

        return (new ArrayList<String>(ids));
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
    public List<String> searchForLuiPersonRelationIds(List<CriteriaInfo> criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Set<String> ids = new HashSet<String>();

        ids.addAll(getLprService().searchForLuiPersonRelationIds(criteria, context));
        for (LuiPersonRelationService provider : getExternalProviders()) {
            ids.addAll(provider.searchForLuiPersonRelationIds(criteria, context));
        }

        return (new ArrayList<String>(ids));
    }
}