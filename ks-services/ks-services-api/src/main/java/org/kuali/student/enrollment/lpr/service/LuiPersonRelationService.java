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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.CriteriaInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.service.TypeService;
import org.kuali.student.datadictionary.service.DataDictionaryService;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;

/**
 * The Lui Person Relationship (LPR) Service
 *
 * Maintains the relationship between a Learning Unit Instance and a Person.
 * Depending on the type this service maintaings relationships such as:<ul>
 * <li>a student's registration in a course
 * <li>a student's enrollment in a program (major or minor)
 * <li>an instructor's assignment to teach a course
 * <li>a faculty member's assignment as an advisor for a prorgram
 *  </ul>
 * 
 * Version: 1.0 (Dev)
 *
 * @Author Kamal
 * @Since Tue Mar 01 15:53:51 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/LUI+Person+Relation+Service">LuiPersonRelationService</>
 */
@WebService(name = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/wsdl/luiPersonRelation")
// TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuiPersonRelationService extends DataDictionaryService, TypeService {

    /**
     * Retrieves the list of LUI Person Relation States
     *
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return list of relation states
     * @throws OperationFailedException unable to complete request
     */
    public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(@WebParam(name = "context") ContextInfo context) throws OperationFailedException;

    /**
     * Retrieves the list of Allowed Relation States
     *
     * @param luiPersonRelationTypeKey Type of LUI Person Relation
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return list of relationState
     * @throws DoesNotExistException     luiPersonRelationTypeKey not found
     * @throws InvalidParameterException invalid luiPersonRelationTypeKey
     * @throws MissingParameterException missing luiPersonRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     */
    public List<LuiPersonRelationStateInfo> findAllowedRelationStates(@WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the Relation for the specified LUI Person Relation
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param context             Context information containing the principalId and locale information about the caller of service operation
     * @return LUI Person Relation information
     * @throws DoesNotExistException     luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public LuiPersonRelationInfo fetchLUIPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     *
     * @param luiPersonRelationIdList List of identifiers for LUI Person Relations
     * @param context                 Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException     One or more luiPersonRelationIds not found
     * @throws InvalidParameterException One or more invalid luiPersonRelationIds
     * @throws MissingParameterException missing luiPersonRelationIdList
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(@WebParam(name = "luiPersonRelationIdList") List<String> luiPersonRelationIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person related to LUI
     *
     * @param personId              Identifier for the LUI Person Relation
     * @param luiPersonRelationTypeKey Type of LUI Person Relation
     * @param relationState         Relation State
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException       personId, luiPersonRelationTypeKey, relationState, person to LUI relationship not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException   missing personId, luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> findLuiIdsRelatedToPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey, @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI
     *
     * @param luiId                 Identifier for the LUI
     * @param luiPersonRelationTypeKey Type of LUI Person Relation
     * @param relationState         Relation State
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return Simple list of Person Ids
     * @throws DoesNotExistException     luiId, luiPersonRelationTypeKey, relationState, LUI to person relationship not found
     * @throws InvalidParameterException invalid luiId, luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException missing luiId, luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> findPersonIdsRelatedToLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey, @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for LUI
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LuiPersonRelationInfo> findLuiPersonRelations(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> findLuiPersonRelationIds(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for Person
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for Person
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId and locale information about the caller of service operation
     * @return Simple list of person relation identifiers
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> findLuiPersonRelationIdsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation for LUI
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId and locale information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> findLuiPersonRelationIdsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI
     *
     * @param validationType             Identifier of the extent of validation
     * @param luiPersonRelationInfo      LuiPersonRelation to be validated
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return list of validation results, list should be be zero length if no validation rsults are generated
     * @throws DoesNotExistException       luiPersonRelationInfo not found
     * @throws InvalidParameterException   invalid luiPersonRelationInfo relationState
     * @throws MissingParameterException   missing luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<ValidationResultInfo> validateLuiPersonRelation(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves LUIs for an academic time period where the specified relation type and state would be valid for the specified person
     *
     * @param personId              Identifier for person
     * @param luiPersonRelationTypeKey Type of luiPersonRelationI
     * @param relationState         Relation State
     * @param atpId                 Identifier for academic time period
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return List of LUIs
     * @throws DoesNotExistException       personId, luiPersonRelationTypeKey, relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiPersonRelationTypeKey, relationState, atpId
     * @throws MissingParameterException   missing personId, luiPersonRelationTypeKey, relationState, atpId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> findAllValidLuisForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey, @WebParam(name = "relationState") String relationState, @WebParam(name = "atpId") String atpId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation Ids
     *
     * @param criteria Criteria to be used for retrieval of multiple LUI Person Relation identifiers
     * @param context                   Context information containing the principalId and locale information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForLuiPersonRelationIds(@WebParam(name = "criteria") CriteriaInfo criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates relation between the specified Person and LUI
     *
     * @param personId              Person Identifier
     * @param luiId                 LUI Identifier
     * @param luiPersonRelationTypeKey Type of LUI to Person Relation
     * @param luiPersonRelationInfo Information required to create the LUI Person relation
     * @param context               Context information containing the principalId and locale information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException      relation already exists
     * @throws DataValidationErrorException if luiPeronsRelationInfo is not valid
     * @throws ReadOnlyException         attempt to update a read only attribute
     * @throws DoesNotExistException       personId, luiId, relationState, luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException   missing personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public String createLuiPersonRelation(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or nothing transaction - any error will invalidate the entire transaction.
     *
     * @param personId              Identifier for Person
     * @param luiIdList             Simple list of LUI identifiers
     * @param relationState         Relation state
     * @param luiPersonRelationTypeKey Type of LUI Person relation
     * @param luiPersonRelationInfo Information required to create the LUI Person relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException      relation already exists
     * @throws DataValidationErrorException if luiPeronsRelationInfo is not valid
     * @throws ReadOnlyException         attempt to update a read only attribute
     * @throws DoesNotExistException       personId, luiId, relationState, luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException   missing personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiIdList") List<String> luiIdList,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException,
            AlreadyExistsException,
            DoesNotExistException,
            DisabledIdentifierException,
            ReadOnlyException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified LUI. This is an all or nothing transaction - any error will invalidate the entire transaction.
     *
     * @param luiId                 Identifier for Lui
     * @param personIdList          Simple list of Person identifiers
     * @param relationState         Relation state
     * @param luiPersonRelationTypeKey Type of LUI Person relation
     * @param luiPersonRelationInfo Information required to create the LUI Person relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException      relation already exists
     * @throws DoesNotExistException       personId, luiId, relationState, luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws DataValidationErrorException errors validating luiPersonRelationInfo with all data filled in
     * @throws InvalidParameterException   invalid personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException   missing personId, luiId, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException    unable to complete request
     * @throws ReadOnlyException         attempt to update a read only attribute
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "personIdList") List<String> personIdList,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            DisabledIdentifierException,
            ReadOnlyException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     *
     * @param luiPersonRelationId   Identifier for the LUI Person Relation
     * @param luiPersonRelationInfo Changed information about the LUI Person Relation
     * @param context               Context information containing the principalId
     *                              and locale information about the caller of service
     *                              operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException if luiPersonRelationInfo is not valid
     * @throws DoesNotExistException     luiPersonRelationId does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId, luiPersonRelationInfo
     * @throws MissingParameterException missing luiPersonRelationId, luiPersonRelationInfo
     * @throws ReadOnlyException         attempt to update a read only attribute
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException if optimistic lock version ind has changed
     */
    public LuiPersonRelationInfo updateLuiPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            ReadOnlyException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException;

    /**
     * Deletes relation between the specified Person and LUI
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
    public StatusInfo deleteLuiPersonRelation(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation state
     *
     * TODO: Service Team Rewview to see if this is actually needed/desired since the state can be updated via the
     * updateLuiPersonRelation method already
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param relationState       Relation state
     * @param context             Context information containing the principalId
     *                            and locale information about the caller of service
     *                            operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     luiPersonRelationId, relationState does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId, relationState
     * @throws MissingParameterException missing luiPersonRelationId, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    @Deprecated
    public StatusInfo updateRelationState(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "relationState") LuiPersonRelationStateInfo relationState,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
