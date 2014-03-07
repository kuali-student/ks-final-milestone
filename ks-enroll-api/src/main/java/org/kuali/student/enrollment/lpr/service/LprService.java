/*
 * Copyright 2009 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.*;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * The Lui Person Relationship (LPR) Service Maintains the relationship between
 * a Learning Unit Instance and a Person. Depending on the type this service
 * maintains relationships such as:
 * <ul>
 *     <li>a student's registration in a course
 *     <li>a student's enrollment in a program (major or minor)
 *     <li>an instructor's assignment to teach a course
 *     <li>a faculty member's assignment as an advisor for a program
 * </ul>
 * @version 0.0.7
 *
 * @Author Kamal
 * @Since Tue Mar 01 15:53:51 PST 2011
 */
@WebService(name = "LprService", targetNamespace = LprServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface LprService {

    /**
     * Retrieves a single LPR by an LPR Id.
     *
     * @param lprId the identifier for the LPR to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the LPR requested
     * @throws DoesNotExistException lprId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprInfo getLpr(@WebParam(name = "lprId") String lprId,
                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException;

    /**
     * Retrieve a list of LPRs from a list of LPR Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a
     * unique set may or may not ber returned.
     *
     * @param lprIds a list of LPR identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list if LPRs
     * @throws DoesNotExistException an LPR Id in the list was not
     *         found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprIdsIds, an Id in lprIds,
     *         or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByIds(@WebParam(name = "lprIds") List<String> lprIds,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, 
                   InvalidParameterException, 
                   MissingParameterException,
                   OperationFailedException, 
                   PermissionDeniedException;


    /**
     * Retrieve a list of LPR Ids by LPR Type.
     *
     * @param lprTypeKey an identifier for an
     *        LPR Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LPRs identifiers matching lprTypeKey or an
     *         empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTypeKey or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLprIdsByType(@WebParam(name = "lprTypeKey") String lprTypeKey,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;
    
    /**
     * Gets a list of LPRs for a LUI.
     * 
     * @param luiId an identifier for LUI
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of LPRs associated with the given LUI or an empty
     *         list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException luiId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByLui(@WebParam(name = "luiId") String luiId,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of LPRs for a Person.
     * 
     * @param personId an identifier for Person
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of LPRs associated with the given Person or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByPerson(@WebParam(name = "personId") String personId,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets a list of LPRs for a LUI and a Person.
     * (NOTE: the parameters are swapped from the general pattern).
     *
     * @param personId an identifier for Person
     * @param luiId an identifier for LUI
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of LPRs associated with the given LUI and Person
     *         or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException luiId, personId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByPersonAndLui(@WebParam(name = "personId") String personId,
                                               @WebParam(name = "luiId") String luiId,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person, type and state.
     * 
     * Example Use Case: This would allow you to get all the active
     * (state) courses (type=registration) for a student.
     *
     * @param personId                 Identifier for the Person 
     * @param lprTypeKey Type of LUI Person Relation
     * @param relationState            Relation State
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException       personId, lprTypeKey,
     *                                     relationState, person to LUI relationship not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException   missing personId,
     *                                     lprTypeKey, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated this should be pulling the LPRs
     */
    public List<String> getLuiIdsByPersonAndTypeAndState(@WebParam(name = "personId") String personId,
                @WebParam(name = "lprTypeKey") String lprTypeKey,
                @WebParam(name = "relationState") String relationState,
                @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI, type and state
     * 
     * This would allow you to get a list of people who are active (state) students (type=registration) 
     * for a particular course (luiId) 
     * 
     * Example Use Case: get all students in a course.
     * 
     * @param luiId                    Identifier for the LUI
     * @param lprTypeKey               Type of LUI Person Relation
     * @param relationState            Relation State
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Simple list of Person Ids
     * @throws DoesNotExistException     luiId, lprTypeKey,
     *                                   relationState, LUI to person relationship not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing luiId,
     *                                   lprTypeKey, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated this should be pulling the LPRs
     */
    public List<String> getPersonIdsByLuiAndTypeAndState(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves LUI Person Relations for a list of Lui ids
     * 
     * Example Use Case: Can be used to get all the people and their
     * relationships to a particular list of course offering's.
     *
     * @param luiIds   Identifiers for LUI's that we want to load the related LPR's 
     * @param contextInfo Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     one or more of the provided luiId's are not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException one or more missing parameters.
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated ???
     */
    public List<LprInfo> getLprsByLuis(@WebParam (name="luiIds")List<String>luiIds, 
    								   @WebParam(name = "contextInfo") ContextInfo contextInfo) 
    										   throws DoesNotExistException, 
    										          InvalidParameterException,
    										          MissingParameterException,
    										          OperationFailedException, 
    										          PermissionDeniedException;
    
    /**
     * Retrieves LUI Person Relation for a particular type and specified LUI
     *
     * Example: Can be used to get all the people with a particular relationship to a
     * specified course offering.
     * 
     * @param lprTypeKey LUI person relation type key
     * @param luiId         Identifier for LUI
     * @param contextInfo  Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated the new LPR Types should handle this
     */
    public List<LprInfo> getLprsByLuiAndType (@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPRs by person and ATP. 
     *
     * Example Use Case: Can be used to get a list of registrations or
     * instructor assignments for a person and a term.
     * 
     * @param personId an identifier of a Person
     * @param atpId an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of LPRs or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId, atpId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByPersonAndAtp(@WebParam(name = "personId") String personId,
                                               @WebParam(name = "atpId") String atpId,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves a list of LPRs for a person and particular ATP by the
     * type of LPR.
     *
     * Example Use Case: Can be used to get a list of registrations
     * for a person and a relation type but making sure to exclude
     * other types of relations the student may have during that term
     * such as also being an teaching assistant for a course.
     *
     * @param lprTypeKey an LPR Type
     * @param personId an identifier for a person
     * @param atpId an identifier for an ATP
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of LPRs or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTypeKey, personId, atpId,
     *         or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> getLprsByTypeAndPersonAndAtp(@WebParam(name = "lprTypeKey") String lprTypeKey,
                                                      @WebParam(name = "personId") String personId,
                                                      @WebParam(name = "atpId") String atpId,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Gets the LPRs for a person and the type Lui.
     * 
     * Example Use Case: Can be used to get a list of all the relations a person may have to a course
     * offering (which is a lui type).
     *
     * @param personId   person identifier
     * @param luiTypeKey type of the LUI
     * @param contextInfo  Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated the new LPR Types should handle this
     */
    public List<LprInfo> getLprsByPersonAndLuiType(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the the LPRs for a person and atp and Lui type.
     * 
     * Example Use Case: Can be used to get the relations a person may
     * have to a course offering (which is a lui type) and a
     * particular term (atpId)
     *
     * @param personId   person identifier
     * @param atpId academic time period identifier
     * @param luiTypeKey type of the LUI
     * @param contextInfo       Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return a List of Lui Person Relation Info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated the new LPR Types should handle this
     */
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Search for LPRs that meet the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of LPR identifiers that meet the search criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLprIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException;

    /**
     * Search for LPRs that meet the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of LPRs that meet the search criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprInfo> searchForLprs(@WebParam(name = "criteria") QueryByCriteria criteria,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Validates an LPR. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * LPR and its directly contained sub-objects or expanded to
     * perform all tests related to this LPR. If an identifier is
     * present for the LPR (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation
     * checks if the LPR can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the LPR with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param luiId the identifier of the LUI
     * @param personId the identifier of the Person
     * @param lprTypeKey       the key to the type of the relationship
     * @param lprInfo the LPR to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey, luiId,
     *         personId, or luiTypeKey not found
     * @throws InvalidParameterException contextInfo or lprInfo is not valid 
     * @throws MissingParameterException validationTypeKey, luiId,
     *         personId, lprTypeKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLpr(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                  @WebParam (name="luiId") String luiId,
                                                  @WebParam (name="personId") String personId,
                                                  @WebParam (name="lprTypeKey")  String lprTypeKey,
                                                  @WebParam(name = "lprInfo") LprInfo lprInfo,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, 
                   InvalidParameterException,
                   MissingParameterException, 
                   OperationFailedException, 
                   PermissionDeniedException;


    /**
     * Creates a new LPR. The LPR Id, Type, and Meta information may
     * not be set in the supplied data object.
     *
     * @param luiId an identifier of a LUI
     * @param person Id an identifier for a Person
     * @param lprTypeKey the identifier for the Type of LPR to be
     *        created
     * @param lprInfo the data with which to create the LPR
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new LPR
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprTypeKey, luiId, or personId
     *         does not exist or is not supported
     * @throws InvalidParameterException lprInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException lprTypeKey, luiId, personId,
     *         lprInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public LprInfo createLpr(@WebParam(name = "personId") String personId,
                             @WebParam(name = "luiId") String luiId,
                             @WebParam(name = "lprTypeKey") String lprTypeKey,
                             @WebParam(name = "lprInfo") LprInfo lprInfo,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException,
               DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException,
               PermissionDeniedException, 
               ReadOnlyException;

    /**
     * Creates multiple LPRs for one specified person. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * NOTE: should revisit bulk pattern -- variable luiId contrary to
     * the single create -- and should we do bulk underneath
     * LprTransaction submission
     *
     * @param personId                 Identifier for Person
     * @param lprTypeKey                Type of LUI Person relation
     * @param lprInfos                  List of Information required to create the LUI
     *                                 Person relation
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing status and LUI Person relation identifiers and message
     * @throws DataValidationErrorException if luiPersonsRelationInfo is not
     *                                      valid
     * @throws DoesNotExistException        personId, lprTypeKey does not exist
     * @throws InvalidParameterException    invalid personId, lprTypeKey, lprInfo
     * @throws MissingParameterException    missing personId, lprTypeKey, lprInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException            attempt to update a read only attribute
     */
    public List<BulkStatusInfo> createLprsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "lprInfos") List<LprInfo> lprInfos,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException;

    /**
     * Creates multiple LPRs for one specified LUI. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * NOTE: should revisit bulk pattern -- variable personId contrary
     * to the single create -- and should we do bulk underneath
     * LprTransaction submission
     *
     * @param luiId                    Identifier for Lui
     * @param lprTypeKey                Type of LUI Person relation
     * @param lprInfos                  List of Information required to create the LUI
     *                                 Person relation
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing status and LUI Person relation identifiers and message
     * @throws DataValidationErrorException if luiPersonsRelationInfo is not
     *                                      valid
     * @throws DoesNotExistException        luiId, lprTypeKey does not exist
     * @throws InvalidParameterException    invalid luiId, lprTypeKey, lprInfos
     * @throws MissingParameterException    missing luiId, lprTypeKey, lprInfos
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException            attempt to update a read only attribute
     */
    public List<BulkStatusInfo> createLprsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "lprInfos") List<LprInfo> lprInfos,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing LPR. The LPR Id, Type, LUI Id, Person Id,
     * and Meta information may not be changed.
     *
     * @param lprId the identifier for the LPR to be updated
     * @param lprInfo the new data for the LPR
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated LPR
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprId is not found
     * @throws InvalidParameterException lprInfo or contextInfo is not
     *         valid
     * @throws MissingParameterException lprId, lprInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LprInfo updateLpr(@WebParam(name = "lprId") String lprId,
                             @WebParam(name = "lprInfo") LprInfo lprInfo,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               DataValidationErrorException, 
               InvalidParameterException,
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException, 
               ReadOnlyException, 
               VersionMismatchException;

    /**
     * Updates the state of an existing Lpr to another state
     * provided that it is valid to do so.
     *
     * @param lprId identifier of the Lpr to be updated
     * @param nextStateKey the State Key into which the identified Lpr
     *        will be placed if the operation succeeds
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the operation. This value must be true.
     * @throws DoesNotExistException lprId not found
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException lprId, nextStateKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeLprState(@WebParam(name = "lprId") String lprId, 
                                     @WebParam(name = "nextStateKey") String nextStateKey, 
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Deletes an existing LPR.
     *
     * @param lprId the identifier for the LPR to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException lprId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprId or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLpr(@WebParam(name = "lprId") String lprId,
                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Retrieves a single LprTransaction by a LprTransaction Id.
     *
     * @param lprTransactionId the identifier for the
     *        LprTransaction to be retrieved
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the LprTransaction requested
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve a list of LprTransactions from a list of
     * LprTransaction Ids. The returned list may be in any order
     * and if duplicate Ids are supplied, a unique set may or may not
     * ber returned.
     *
     * @param lprTransactionIds a list of LprTransaction identifiers
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprTransactions
     * @throws DoesNotExistException a lprTransactionId in the list
     *         was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionIds, an Id in
     *         lprTransactionIds, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprTransactionInfo> getLprTransactionsByIds(@WebParam(name = "lprTransactionIds") List<String> lprTransactionIds,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Retrieve a list of LprTransactionIds by LprTransaction
     * Type.
     *
     * @param lprTransactionTypeKey an identifier for an
     *        LprTransaction Type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprTransactions identifiers matching
     *         lprTransactionTypeKey or an empty list of none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionTypeKey
     *         or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLprTransactionIdsByType(@WebParam(name = "lprTransactionTypeKey") String lprTransactionTypeKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Retrieves LPR Transactions with an item by Person and LUI
     * 
     * Selects all transactions that have at least one item that
     * matches the specified person and either the existing or new
     * lui.
     * 
     * Note: this matches the person on the item not the person
     * requesting the transaction which is on the transaction.
     *
     * @param personId The person identifier
     * @param luiId    The LUI id
     * @param contextInfo the context information
     * @throws DoesNotExistException     personId or luiId doesn't exist
     * @throws InvalidParameterException Invalid personId or luiId
     * @throws MissingParameterException Missing personId or luiId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated revisit this operation
     */
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;



    /**
     * Retrieves LPR Transaction Items based on the resulting LPR.
     *
     * @param lprId   The resulting lpr
     * @param contextInfo the context info
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated revisit this operation
     */
    public List<LprTransactionItemInfo> getLprTransactionItemsByResultingLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transaction Items based on an item with the specified LUI.
     *
     * Selects all transaction items that where the specified LUI matches either
     * the existing or new lui
     *
     * @param luiId   The LUI identifier
     * @param contextInfo the  ontext info
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
     * @deprecated revisit this operation
     */
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Searches for LprTransactions that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprTransaction identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLprTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Searches for LprTransactions that meet the given search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of LprTransactions matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *         not valid
     * @throws MissingParameterException criteria or or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LprTransactionInfo> searchForLprTransactions(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Validates a LprTrsnaction. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current LprTrsnaction and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * LprTrsnaction. If an identifier is present for the
     * LprTrsnaction (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation
     * checks if the LprTrsnaction can be updated to the new
     * values. If an identifier is not present or a record does not
     * exist, the validation checks if the LprTrsnaction with
     * the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param lprTransactionTypeKey the identifier for the
     *        LprTrsnaction Type to be validated
     * @param lprTransactionInfo the LprTrsnaction to be validated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException validationTypeKey or
     *         lprTransactionTypeKey is not found
     * @throws InvalidParameterException lprTransactionInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException validationTypeKey,
     *         lprTransactionTypeKey, lprTransactionInfo, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLprTransaction(@WebParam(name = "validationType") String validationType,
                                                             @WebParam(name = "lprTransactionType") String lprTransactionType,
                                                             @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, 
                   InvalidParameterException,
                   MissingParameterException, 
                   OperationFailedException, 
                   PermissionDeniedException;

    /**
     * Creates a new LprTransaction. The LprTransaction Id,
     * Type, and Meta information may not be set in the supplied data
     * object.
     *
     * @param lprTransactionTypeKey the identifier for the Type
     *        of LprTransaction to be created
     * @param lprTransactionInfo the data with which to create
     *        the LprTransaction
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the new LprTransaction
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprTransactionTypeKey does
     *         not exist or is not supported
     * @throws InvalidParameterException lprTransactionInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException lprTransactionTypeKey,
     *         lprTransactionInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     */
    public LprTransactionInfo createLprTransaction(@WebParam(name = "lprTransactionType") String lprTransactionType,
                                                   @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException, 
               DoesNotExistException, 
               InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Creates a new Lpr Transaction from an existing LPR transaction.
     *
     * Copies the transaction's Items as well.
     * 
     * The new transaction has the same type as the existing
     * transaction.  Since transactions can only be processed once
     * this method was intended to allow the application to easily
     * create a new transaction with all of it's items that the user
     * be able to fix and update any problems that resulted from
     * processing the existing transaction.
     * 
     * @param lprTransactionId an existing LprTransaction identifier
     * @param contextInfo the context information
     * @throws DoesNotExistException lprTransactionId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprTransactionInfo createLprTransactionFromExisting(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Updates an existing LprTransaction. The
     * LprTransaction Id, Type, and Meta information may not be
     * changed.
     *
     * @param lprTransactionId the identifier for the
     *        LprTransaction to be updated
     * @param lprTransactionInfo the new data for the LprTransaction
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated LprTransaction
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException lprTransactionInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException lprTransactionId,
     *         lprTransactionInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LprTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                   @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException, 
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException, 
               VersionMismatchException;

    /**
     * Updates an existing LprTransactionItem. The
     * LprTransactionItem Id, Type, State and Meta information may not be
     * changed.
     *
     * @param lprTransactionItemId the identifier for the
     *        LprTransaction to be updated
     * @param lprTransactionItemInfo the new data for the LprTransaction
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the updated LprTransactionItem
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException lprTransactionInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException lprTransactionId,
     *         lprTransactionInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public LprTransactionItemInfo updateLprTransactionItem(@WebParam(name = "lprTransactionItemId") String lprTransactionItemId,
                                                   @WebParam(name = "lprTransactionItemInfo") LprTransactionItemInfo lprTransactionItemInfo,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;
    /**
     * Updates the state of an existing LprTransactionItem to another state
     * provided that it is valid to do so.
     *
     * @param lprTransactionItemId the identifier of the LprTransactionItem to
     *        be updated
     * @param nextStateKey the State Key into which the identified
     *        lprTransactionItem will be placed if the operation succeeds
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the operation. This must be true.
     * @throws DoesNotExistException lprTransatcionId is not found
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException lprTransactionId,
     *         nextStateKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeLprTransactionItemState(@WebParam(name = "lprTransactionItemId") String lprTransactionItemId,
                                                @WebParam(name = "nextStateKey") String nextStateKey,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Updates the state of an existing LprTransaction to another state
     * provided that it is valid to do so.
     *
     * @param lprTransactionId the identifier of the LprTransaction to
     *        be updated
     * @param nextStateKey the State Key into which the identified
     *        prTransaction will be placed if the operation succeeds
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return status of the operation. This must be true.
     * @throws DoesNotExistException lprTransatcionId is not found
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException lprTransactionId,
     *         nextStateKey, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo changeLprTransactionState(@WebParam(name = "lprTransactionId") String lprTransactionId, 
                                                @WebParam(name = "nextStateKey") String nextStateKey, 
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Deletes an existing LprTransaction.
     *
     * @param lprTransactionId the identifier for the
     *        LprTransaction to be deleted
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Verify the LPR Transaction for submission. This method performs
     * additional evaluations beyond simple persistence.
     *
     * @param lprTransactionId the Id of the LprTransaction to be
     * verified
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return a list of validation results or empty if verification suceeded
     *         note: it may want to return waitlist info
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionId or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> verifyLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException,
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Submits a LPR transaction for processing.
     *
     * @param lprTransactionId the ideentifier for the LPR transaction
     *        to be processed
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @throws AlreadyExistsException the LpsTransatcion was already processed
     * @throws DoesNotExistException lprTransactionId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException lprTransactionId or
     *         contextInfo is missing or null
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LprTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws AlreadyExistsException,
               DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;


    /**
     * Retrieves transactions for the requesting person and the ATP.
     *
     * Unsubmitted transactions are those that have not been submitted
     * (processed) yet. They can be found by checking the state of
     * transactions where state is "New" (see wiki).
     *
     * @param requestingPersonId The person identifier
     * @param atpId   The ATP Id
     * @param contextInfo the context info
     * @throws InvalidParameterException Invalid personId or atpId
     * @throws MissingParameterException Missing personId or atpId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @return
     */
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(
                                                                                          @WebParam(name = "personId") String requestingPersonId,
                                                                                          @WebParam(name = "atpId") String atpId,
                                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException,
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

}