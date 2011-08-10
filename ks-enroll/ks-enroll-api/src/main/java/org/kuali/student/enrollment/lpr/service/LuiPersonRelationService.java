/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
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
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.service.TypeService;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

/**
 * The Lui Person Relationship (LPR) Service Maintains the relationship between
 * a Learning Unit Instance and a Person. Depending on the type this service
 * maintains relationships such as:
 * <ul>
 * <li>a student's registration in a course
 * <li>a student's enrollment in a program (major or minor)
 * <li>an instructor's assignment to teach a course
 * <li>a faculty member's assignment as an advisor for a program
 * </ul>
 * Version: 1.0 (Dev)
 * 
 * @Author Kamal
 * @Since Tue Mar 01 15:53:51 PST 2011
 * @See <a
 *      href="https://wiki.kuali.org/display/KULSTU/LUI+Person+Relation+Service"
 *      >LuiPersonRelationService</>
 */
@WebService(name = "LuiPersonRelationService", targetNamespace = LuiPersonRelationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuiPersonRelationService extends DataDictionaryService, TypeService, StateService {

    /**
     * Retrieves the Relation for the specified LUI Person Relation
     * 
     * @param luiPersonRelationId
     *            Identifier for the LUI Person Relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return LUI Person Relation information
     * @throws DoesNotExistException
     *             luiPersonRelationId not found
     * @throws InvalidParameterException
     *             invalid luiPersonRelationId
     * @throws MissingParameterException
     *             missing luiPersonRelationId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public LuiPersonRelationInfo getLuiPersonRelation(
            @WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     * 
     * @param luiPersonRelationIdList
     *            List of identifiers for LUI Person Relations
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException
     *             One or more luiPersonRelationIds not found
     * @throws InvalidParameterException
     *             One or more invalid luiPersonRelationIds
     * @throws MissingParameterException
     *             missing luiPersonRelationIdList
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiPersonRelationInfo> getLuiPersonRelationsByIdList(
            @WebParam(name = "luiPersonRelationIdList") List<String> luiPersonRelationIdList,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person related to LUI
     * 
     * @param personId
     *            Identifier for the LUI Person Relation
     * @param luiPersonRelationTypeKey
     *            Type of LUI Person Relation
     * @param relationState
     *            Relation State
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException
     *             personId, luiPersonRelationTypeKey, relationState, person to
     *             LUI relationship not found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException
     *             missing personId, luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getLuiIdsRelatedToPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI
     * 
     * @param luiId
     *            Identifier for the LUI
     * @param luiPersonRelationTypeKey
     *            Type of LUI Person Relation
     * @param relationState
     *            Relation State
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of Person Ids
     * @throws DoesNotExistException
     *             luiId, luiPersonRelationTypeKey, relationState, LUI to person
     *             relationship not found
     * @throws InvalidParameterException
     *             invalid luiId, luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException
     *             missing luiId, luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getPersonIdsRelatedToLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for LUI
     * 
     * @param personId
     *            Identifier for person
     * @param luiId
     *            Identifier for LUI
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException
     *             personId, luiId not found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiId
     * @throws MissingParameterException
     *             missing personId, luiId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiPersonRelationInfo> getLuiPersonRelations(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    public LuiPersonRelationInfo getLuiPersonRelationByState(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId, @WebParam(name = "stateKey") String stateKey,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids
     * 
     * @param personId
     *            Identifier for person
     * @param luiId
     *            Identifier for LUI
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException
     *             personId, luiId not found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiId
     * @throws MissingParameterException
     *             missing personId, luiId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getLuiPersonRelationIds(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for Person
     * 
     * @param personId
     *            Identifier for person
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException
     *             personId not found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId
     * @throws MissingParameterException
     *             missing personId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiPersonRelationInfo> getLuiPersonRelationsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for Person
     * 
     * @param personId
     *            Identifier for person
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of person relation identifiers
     * @throws DoesNotExistException
     *             personId not found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId
     * @throws MissingParameterException
     *             missing personId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getLuiPersonRelationIdsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     * 
     * @param luiId
     *            Identifier for LUI
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException
     *             luiId not found
     * @throws InvalidParameterException
     *             invalid luiId
     * @throws MissingParameterException
     *             missing luiId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiPersonRelationInfo> getLuiPersonRelationsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation for LUI
     * 
     * @param luiId
     *            Identifier for LUI
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws DoesNotExistException
     *             luiId not found
     * @throws InvalidParameterException
     *             invalid luiId
     * @throws MissingParameterException
     *             missing luiId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getLuiPersonRelationIdsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPR by person and ATP
     * 
     * @param personId
     * @param atpId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LuiPersonRelationInfo> getLuiPersonRelationsForPersonAndAtp(
            @WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI
     * 
     * @param validationType
     *            Identifier of the extent of validation
     * @param luiPersonRelationInfo
     *            LuiPersonRelation to be validated
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return list of validation results, list should be be zero length if no
     *         validation rsults are generated
     * @throws DoesNotExistException
     *             luiPersonRelationInfo not found
     * @throws InvalidParameterException
     *             invalid luiPersonRelationInfo relationState
     * @throws MissingParameterException
     *             missing luiPersonRelationInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<ValidationResultInfo> validateLuiPersonRelation(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIs for an academic time period where the specified relation
     * type and state would be valid for the specified person
     * 
     * @param personId
     *            Identifier for person
     * @param luiPersonRelationTypeKey
     *            Type of luiPersonRelationI
     * @param relationState
     *            Relation State
     * @param atpId
     *            Identifier for academic time period
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUIs
     * @throws DoesNotExistException
     *             personId, luiPersonRelationTypeKey, relationState, atpId not
     *             found
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiPersonRelationTypeKey, relationState,
     *             atpId
     * @throws MissingParameterException
     *             missing personId, luiPersonRelationTypeKey, relationState,
     *             atpId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> getAllValidLuisForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "relationState") String relationState, @WebParam(name = "atpId") String atpId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation Ids
     * 
     * @param criteria
     *            Criteria to be used for retrieval of multiple LUI Person
     *            Relation identifiers
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException
     *             invalid relation criteria
     * @throws MissingParameterException
     *             missing relation criteria
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> searchForLuiPersonRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation by search criteria
     * 
     * @param criteria
     *            Criteria to be used for retrieval of multiple LUI Person
     *            Relation identifiers
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException
     *             invalid relation criteria
     * @throws MissingParameterException
     *             missing relation criteria
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiPersonRelationInfo> searchForLuiPersonRelations(
            @WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates relation between the specified Person and LUI
     * 
     * @param personId
     *            Person Identifier
     * @param luiId
     *            LUI Identifier
     * @param luiPersonRelationTypeKey
     *            Type of LUI to Person Relation
     * @param luiPersonRelationInfo
     *            Information required to create the LUI Person relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException
     *             relation already exists
     * @throws DataValidationErrorException
     *             if luiPeronsRelationInfo is not valid
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws DoesNotExistException
     *             personId, luiId, relationState, luiPersonRelationTypeKey does
     *             not exist
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public String createLuiPersonRelation(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     * 
     * @param personId
     *            Identifier for Person
     * @param luiIdList
     *            Simple list of LUI identifiers
     * @param relationState
     *            Relation state
     * @param luiPersonRelationTypeKey
     *            Type of LUI Person relation
     * @param luiPersonRelationInfo
     *            Information required to create the LUI Person relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException
     *             relation already exists
     * @throws DataValidationErrorException
     *             if luiPeronsRelationInfo is not valid
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws DoesNotExistException
     *             personId, luiId, relationState, luiPersonRelationTypeKey does
     *             not exist
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiIdList") List<String> luiIdList,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified LUI. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     * 
     * @param luiId
     *            Identifier for Lui
     * @param personIdList
     *            Simple list of Person identifiers
     * @param relationState
     *            Relation state
     * @param luiPersonRelationTypeKey
     *            Type of LUI Person relation
     * @param luiPersonRelationInfo
     *            Information required to create the LUI Person relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException
     *             relation already exists
     * @throws DoesNotExistException
     *             personId, luiId, relationState, luiPersonRelationTypeKey does
     *             not exist
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws DataValidationErrorException
     *             errors validating luiPersonRelationInfo with all data filled
     *             in
     * @throws InvalidParameterException
     *             invalid personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "personIdList") List<String> personIdList,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     * 
     * @param luiPersonRelationId
     *            Identifier for the LUI Person Relation
     * @param luiPersonRelationInfo
     *            Changed information about the LUI Person Relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException
     *             if luiPersonRelationInfo is not valid
     * @throws DoesNotExistException
     *             luiPersonRelationId does not exist
     * @throws InvalidParameterException
     *             invalid luiPersonRelationId, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing luiPersonRelationId, luiPersonRelationInfo
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     *             if optimistic lock version ind has changed
     */
    public LuiPersonRelationInfo updateLuiPersonRelation(
            @WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes relation between the specified Person and LUI
     * 
     * @param luiPersonRelationId
     *            Identifier for the LUI Person Relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException
     *             luiPersonRelationId does not exist
     * @throws InvalidParameterException
     *             invalid luiPersonRelationId
     * @throws MissingParameterException
     *             missing luiPersonRelationId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteLuiPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     * 
     * @param luiPersonRelationId
     *            Identifier for the LUI Person Relation
     * @param luiPersonRelationInfo
     *            Changed information about the LUI Person Relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException
     *             if luiPersonRelationInfo is not valid
     * @throws DoesNotExistException
     *             luiPersonRelationId does not exist
     * @throws InvalidParameterException
     *             invalid luiPersonRelationId, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing luiPersonRelationId, luiPersonRelationInfo
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     *             if optimistic lock version ind has changed
     */
    public LprRosterInfo updateLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Creates a roster from a LuiPers
     * 
     * @param lprRosterInfo
     *            Information required to create the LUI Person relation roster
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException
     *             relation already exists
     * @throws DataValidationErrorException
     *             if luiPeronsRelationInfo is not valid
     * @throws ReadOnlyException
     *             attempt to update a read only attribute
     * @throws DoesNotExistException
     *             personId, luiId, relationState, luiPersonRelationTypeKey does
     *             not exist
     * @throws DisabledIdentifierException
     *             personId found, but has been retired
     * @throws InvalidParameterException
     *             invalid personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException
     *             missing personId, luiId, relationState,
     *             luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public String createLprRoster(@WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes the LPRRoster
     * 
     * @param luiPersonRelationId
     *            Identifier for the LUI Person Relation
     * @param context
     *            Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException
     *             lprRosterId does not exist
     * @throws InvalidParameterException
     *             invalid lprRosterId
     * @throws MissingParameterException
     *             missing lprRosterId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns all the LPR entries contained in the LPR Roster.
     * 
     * @param lprRosterId
     *            Identifier of LPR Roster
     * @param context
     * @return
     * @throws DoesNotExistException
     *             lprRosterId does not exist
     * @throws InvalidParameterException
     *             invalid lprRosterId
     * @throws MissingParameterException
     *             missing lprRosterId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LprRosterEntryInfo> getEntriesForLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of LprRosterInfo by LUI and Roster Type. A
     * type and LUI are mandatory parameters to retrieve a desired kind of
     * roster.
     * 
     * @param luiId
     *            LUI Identifier
     * @param lprRosterTypeKey
     *            LPR Roster Key
     * @param context
     * @return
     * @throws DoesNotExistException
     *             luiId or lprRosterTypeKey invalid
     * @throws InvalidParameterException
     *             Invalid luiId or lprRosterTypeKey
     * @throws MissingParameterException
     *             Missing luiId or lprRosterTypeKey
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve all LPR Rosters that are associated with a LUI.
     * 
     * @param luiId
     *            LUI Identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             luiId invalid
     * @throws InvalidParameterException
     *             Invalid luiId
     * @throws MissingParameterException
     *             Missing luiId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LprRosterInfo> getLprRostersByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a LPR Roster by id.
     * 
     * @param lprRosterId
     *            LPR Roster identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             lprRosterId invalid
     * @throws InvalidParameterException
     *             Invalid lprRosterEntryInfo
     * @throws MissingParameterException
     *             Missing lprRosterEntryInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LuiInfo> getLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a {@link LprRosterEntryInfo} relation object at the position
     * specified by the "position" field. A blank position would mean add to the
     * last position, a specified position means insert to that position.
     * 
     * @param lprRosterEntryInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     *             lprRosterEntryInfo data is not valid
     * @throws InvalidParameterException
     *             Invalid lprRosterEntryInfo
     * @throws MissingParameterException
     *             Missing lprRosterEntryInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public String createLprRosterEntry(@WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Delete the {@link LprRosterEntryInfo}
     * 
     * @param lprRosterEntryId
     * @param context
     * @return
     * @throws DoesNotExistException
     *             not a valid lprRosterEntryId
     * @throws InvalidParameterException
     *             Invalid lprRosterEntryId
     * @throws MissingParameterException
     *             Missing lprRosterEntryId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Inserts a roster entry at a particular position. Readjusts the ranks of
     * the other entries that are affected by the new entry.
     * 
     * @param lprRosterEntryId
     * @param position
     * @param context
     * @return
     * @throws DoesNotExistException
     *             One of the roster entry id in the list is not a valid id
     * @throws InvalidParameterException
     *             Invalid lprRosterEntryIds
     * @throws MissingParameterException
     *             Missing lprRosterEntryIds in the input
     * @throws OperationFailedException
     *             unable to complete request
     */
    public StatusInfo insertLprRosterEntryInPosition(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
            @WebParam(name = "position") Integer position, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException;

    /**
     * The LPR roster entries in the input list is ordered and are the first
     * entries in the roster. If a roster contains other entries, rank them from
     * end of the entries in the input list onwards.
     * 
     * @param lprRosterEntryIds
     *            ordered list of {@link LprRosterEntryInfo} ids The ordered
     * @param context
     * @return
     * @throws DoesNotExistException
     *             One of the roster entry id in the list is not a valid id
     * @throws InvalidParameterException
     *             Invalid lprRosterEntryIds
     * @throws MissingParameterException
     *             Missing lprRosterEntryIds in the input
     * @throws OperationFailedException
     *             unable to complete request
     */
    public StatusInfo reorderLprRosterEntries(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method creates a LPR transaction - validates the transaction,
     * generates a unique id for the request and persists it in the back-end.
     * 
     * @param lprTransactionInfo
     *            LPR transaction info
     * @param context
     * @return
     * @throws DataValidationErrorException
     *             if LPRTransactionInfo fields are not valid
     * @throws AlreadyExistsException
     *             LPRTransactionInfo / LPR already exists
     * @throws DoesNotExistException
     *             LUI or Person doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId, luiId, state, LPRTransactionInfo
     * @throws MissingParameterException
     *             Missing fields on LPRTransactionInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public LPRTransactionInfo createLprTransaction(
            @WebParam(name = "lprTransactionInfo") LPRTransactionInfo lprTransactionInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new {@link LPRTransactionInfo} from an existing LPR transaction
     * id.
     * 
     * @param lprTransactionId
     *            LPRTransaction identifier
     * @param context
     * @return
     * @throws DataValidationErrorException
     *             LPRTransactionInfo is not valid
     * @throws AlreadyExistsException
     *             LPRTransactionInfo / LPR already exists
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId
     * @throws MissingParameterException
     *             Missing lprTransactionId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public LPRTransactionInfo createLprTransactionFromExisting(
            @WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method persists an updated LPR Transaction; it validates the
     * {@link LPRTransactionItemInfo} object before persisting.
     * 
     * @param lprTransactionId
     *            The transaction identifier
     * @param luiPersonRelationRequestInfo
     *            The updated {@link LPRTransactionInfo}
     * @param context
     * @return
     * @throws DataValidationErrorException
     *             LPRTransactionInfo is not valid
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId or lprTransactionInfo
     * @throws MissingParameterException
     *             Missing lprTransactionId or lprTransactionInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public LPRTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "lprTransactionInfo") LPRTransactionInfo lprTransactionInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a LPR Transactions based on identifier.
     * 
     * @param lprTransactionId
     *            The transaction identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId
     * @throws MissingParameterException
     *             Missing lprTransactionId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public LPRTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions for Person by LUI Id.
     * 
     * @param personId
     *            The person identifier
     * @param luiId
     *            The LUI id
     * @param context
     * @return
     * @throws DoesNotExistException
     *             personId or luiId doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId or luiId
     * @throws MissingParameterException
     *             Missing personId or luiId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public List<LPRTransactionInfo> getLprTransactionsForPersonByLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on person id.
     * 
     * @param personId
     *            The person identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             personId doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId
     * @throws MissingParameterException
     *             Missing personId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public List<String> getLprTransactionIdsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "lprTransactionStates") List<String> lprTransactionStates,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param lprIds
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */

    public List<LPRTransactionInfo> getLprTransactionsByIdList(@WebParam(name = "lprIds") List<String> lprIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on the LPR.
     * 
     * @param lprId
     *            The person identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             personId doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId
     * @throws MissingParameterException
     *             Missing personId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public LPRTransactionInfo getLprTransactionForLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on the LUI.
     * 
     * @param luiId
     *            The LUI identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             personId doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId
     * @throws MissingParameterException
     *             Missing personId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public List<LPRTransactionInfo> getLprTransactionsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a {@link LPRTransactionInfo} based on ATP and person id.
     * 
     * @param personId
     *            The person identifier
     * @param atpKey
     *            The ATP key
     * @param context
     * @return
     * @throws DoesNotExistException
     *             atpKey or personId doesn't exist
     * @throws InvalidParameterException
     *             Invalid personId or atpKey
     * @throws MissingParameterException
     *             Missing personId or atpKey
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public List<LPRTransactionInfo> getLprTransactionsForPersonByAtp(@WebParam(name = "atpKey") String atpKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "lprTransactionStates") List<String> lprTransactionStates,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an {@link LPRTransactionInfo} based on Id.
     * 
     * @param lprTransactionId
     *            LPR Transaction identifier
     * @param context
     * @return
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId
     * @throws MissingParameterException
     *             Missing lprTransactionId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */

    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param lprTransactionId
     * @param context
     * @return
     * @throws DataValidationErrorException
     *             Invalid lprTransaction
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId
     * @throws MissingParameterException
     *             Missing lprTransactionId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public List<ValidationResultInfo> validateLprTransaction(
            @WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Submits a LPR transaction - validates the input and based on the type of
     * transaction creates, updates, cancels or removes LPRs.
     * 
     * @param lprTransactionId
     *            the id for the LPR transaction
     * @param context
     * @return
     * @throws AlreadyExistsException
     *             LPR is already present
     * @throws DataValidationErrorException
     *             Invalid lprTransaction
     * @throws DoesNotExistException
     *             lprTransactionId doesn't exist
     * @throws InvalidParameterException
     *             Invalid lprTransactionId
     * @throws MissingParameterException
     *             Missing lprTransactionId
     * @throws OperationFailedException
     *             Unable to complete request
     * @throws PermissionDeniedException
     *             Authorization failure
     */
    public LPRTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LPRTransactionInfo> searchForLprTransactions(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * 
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForLprTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LprRosterInfo> searchForLprRosters(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * This method ...
     * 
     * @param criteria
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForLprRosterIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

}
