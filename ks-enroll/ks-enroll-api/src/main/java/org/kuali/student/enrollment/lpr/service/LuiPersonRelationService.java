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
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
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
 * href="https://wiki.kuali.org/display/KULSTU/LUI+Person+Relation+Service"
 * >LuiPersonRelationService</>
 */
@WebService(name = "LuiPersonRelationService", targetNamespace = LuiPersonRelationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuiPersonRelationService  {

    /**
     * Retrieves the Relation for the specified LUI Person Relation
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return LUI Person Relation information
     * @throws DoesNotExistException     luiPersonRelationId not found
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public LuiPersonRelationInfo getLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     *
     * @param luiPersonRelationIdList List of identifiers for LUI Person
     *                                Relations
     * @param context                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException     One or more luiPersonRelationIds not found
     * @throws InvalidParameterException One or more invalid
     *                                   luiPersonRelationIds
     * @throws MissingParameterException missing luiPersonRelationIdList
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> getLprsByIdList(@WebParam(name = "luiPersonRelationIdList") List<String> luiPersonRelationIdList, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person related to LUI
     *
     * @param personId                 Identifier for the LUI Person Relation
     * @param luiPersonRelationTypeKey Type of LUI Person Relation
     * @param relationState            Relation State
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException       personId, luiPersonRelationTypeKey,
     *                                     relationState, person to LUI relationship not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId,
     *                                     luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException   missing personId,
     *                                     luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> getLuiIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
                                          @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI
     *
     * @param luiId                    Identifier for the LUI
     * @param luiPersonRelationTypeKey Type of LUI Person Relation
     * @param relationState            Relation State
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Simple list of Person Ids
     * @throws DoesNotExistException     luiId, luiPersonRelationTypeKey,
     *                                   relationState, LUI to person relationship not found
     * @throws InvalidParameterException invalid luiId,
     *                                   luiPersonRelationTypeKey, relationState
     * @throws MissingParameterException missing luiId,
     *                                   luiPersonRelationTypeKey, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getPersonIdsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
                                          @WebParam(name = "relationState") String relationState, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for LUI
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return List of LUI Person Relation display info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> getLprIdsByLuiAndPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for Person
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LuiPersonRelationInfo> getLprsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for Person
     *
     * @param personId Identifier for person
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return Simple list of person relation identifiers
     * @throws DoesNotExistException       personId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<String> getLprIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> getLprsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     *
     * @param typeKey
     * @param luiId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LuiPersonRelationInfo> getLprsByTypeAndLui(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation for LUI
     *
     * @param luiId   Identifier for LUI
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getLprIdsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
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
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPR for a person and particular ATP by the type of
     * LPR.
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
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId, @WebParam(name = "typeKey") String typeKey,
                                                                    @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Returns the {@link LuiPersonRelationInfo} for a person and the type of a {@link LuiInfo}.
     *
     * @param personId   person identifier
     * @param luiTypeKey type of the LUI
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LuiPersonRelationInfo> getLprsByPersonAndLuiType(@WebParam(name = "personId") String personId,
                                                                 @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     *
     * @param personId
     * @param atpId
     * @param luiTypeKey
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId,
                                                                       @WebParam(name = "luiTypeKey") String luiTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     *
     * @param personId
     * @param atpId
     * @param personTypeKey
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(@WebParam(name = "personId") String personId, @WebParam(name = "atpId") String atpId,
                                                                          @WebParam(name = "personTypeKey") String personTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI
     *
     * @param validationType        Identifier of the extent of validation
     * @param luiPersonRelationInfo LuiPersonRelation to be validated
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return list of validation results, list should be be zero length if no
     *         validation rsults are generated
     * @throws DoesNotExistException     luiPersonRelationInfo not found
     * @throws InvalidParameterException invalid luiPersonRelationInfo
     *                                   relationState
     * @throws MissingParameterException missing luiPersonRelationInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateLpr(@WebParam(name = "validationType") String validationType, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
                                                  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIs for an academic time period where the specified relation
     * type and state would be valid for the specified person
     *
     * @param personId Identifier for person
     * @param luiPersonRelationTypeKey Type of luiPersonRelationI
     * @param relationState Relation State
     * @param atpId Identifier for academic time period
     * @param context Context information containing the principalId and locale
     *            information about the caller of service operation
     * @return List of LUIs
     * @throws DoesNotExistException personId, luiPersonRelationTypeKey,
     *             relationState, atpId not found
     * @throws DisabledIdentifierException personId found, but has been retired
     * @throws InvalidParameterException invalid personId,
     *             luiPersonRelationTypeKey, relationState, atpId
     * @throws MissingParameterException missing personId,
     *             luiPersonRelationTypeKey, relationState, atpId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    // TODO check the need of such a method in class 2, if none exist delete
    // this.
    // public List<String> getAllValidLuisForPerson(@WebParam(name = "personId")
    // String personId, @WebParam(name = "luiPersonRelationTypeKey") String
    // luiPersonRelationTypeKey, @WebParam(name = "relationState") String
    // relationState, @WebParam(name = "atpId") String atpId, @WebParam(name =
    // "context") ContextInfo context) throws DoesNotExistException,
    // DisabledIdentifierException, InvalidParameterException,
    // MissingParameterException, OperationFailedException,
    // PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation Ids
     *
     * @param criteria Criteria to be used for retrieval of multiple LUI Person
     *                 Relation identifiers
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForLprIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves detail of LUI Person Relation by search criteria
     *
     * @param criteria Criteria to be used for retrieval of multiple LUI Person
     *                 Relation identifiers
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> searchForLprs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates relation between the specified Person and LUI
     *
     * @param personId                 Person Identifier
     * @param luiId                    LUI Identifier
     * @param luiPersonRelationTypeKey Type of LUI to Person Relation
     * @param luiPersonRelationInfo    Information required to create the LUI
     *                                 Person relation
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException       relation already exists
     * @throws DataValidationErrorException if luiPeronsRelationInfo is not
     *                                      valid
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws DoesNotExistException        personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException  personId found, but has been retired
     * @throws InvalidParameterException    invalid personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException    missing personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public String createLpr(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
                            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * @param personId                 Identifier for Person
     * @param luiIdList                Simple list of LUI identifiers
     * @param relationState            Relation state
     * @param luiPersonRelationTypeKey Type of LUI Person relation
     * @param luiPersonRelationInfo    Information required to create the LUI
     *                                 Person relation
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException       relation already exists
     * @throws DataValidationErrorException if luiPeronsRelationInfo is not
     *                                      valid
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws DoesNotExistException        personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException  personId found, but has been retired
     * @throws InvalidParameterException    invalid personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException    missing personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "luiIdList") List<String> luiIdList,
                                                         @WebParam(name = "relationState") String relationState, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
                                                         @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified LUI. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * @param luiId                    Identifier for Lui
     * @param personIdList             Simple list of Person identifiers
     * @param relationState            Relation state
     * @param luiPersonRelationTypeKey Type of LUI Person relation
     * @param luiPersonRelationInfo    Information required to create the LUI
     *                                 Person relation
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException       relation already exists
     * @throws DoesNotExistException        personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException  personId found, but has been retired
     * @throws DataValidationErrorException errors validating
     *                                      luiPersonRelationInfo with all data filled in
     * @throws InvalidParameterException    invalid personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException    missing personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException     unable to complete request
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws PermissionDeniedException    authorization failure
     */
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "personIdList") List<String> personIdList,
                                                      @WebParam(name = "relationState") String relationState, @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
                                                      @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     *
     * @param luiPersonRelationId   Identifier for the LUI Person Relation
     * @param luiPersonRelationInfo Changed information about the LUI Person
     *                              Relation
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException if luiPersonRelationInfo is not
     *                                      valid
     * @throws DoesNotExistException        luiPersonRelationId does not exist
     * @throws InvalidParameterException    invalid luiPersonRelationId,
     *                                      luiPersonRelationInfo
     * @throws MissingParameterException    missing luiPersonRelationId,
     *                                      luiPersonRelationInfo
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     if optimistic lock version ind has
     *                                      changed
     */
    public LuiPersonRelationInfo updateLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
                                           @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Deletes relation between the specified Person and LUI
     *
     * @param luiPersonRelationId Identifier for the LUI Person Relation
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     luiPersonRelationId does not exist
     * @throws InvalidParameterException invalid luiPersonRelationId
     * @throws MissingParameterException missing luiPersonRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update relation between Person and LUI
     *
     * @param lprRosterId   Identifier for the LUI Person Relation
     * @param lprRosterInfo Changed information about the LUI Person
     *                      Relation
     * @param context       Context information containing the principalId and locale
     *                      information about the caller of service operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException if luiPersonRelationInfo is not
     *                                      valid
     * @throws DoesNotExistException        luiPersonRelationId does not exist
     * @throws InvalidParameterException    invalid luiPersonRelationId,
     *                                      luiPersonRelationInfo
     * @throws MissingParameterException    missing luiPersonRelationId,
     *                                      luiPersonRelationInfo
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     if optimistic lock version ind has
     *                                      changed
     */
    public LprRosterInfo updateLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
                                         @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Creates a roster from a LuiPers
     *
     * @param lprRosterInfo Information required to create the LUI Person
     *                      relation roster
     * @param context       Context information containing the principalId and locale
     *                      information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws AlreadyExistsException       relation already exists
     * @throws DataValidationErrorException if luiPeronsRelationInfo is not
     *                                      valid
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws DoesNotExistException        personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey does not exist
     * @throws DisabledIdentifierException  personId found, but has been retired
     * @throws InvalidParameterException    invalid personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws MissingParameterException    missing personId, luiId, relationState,
     *                                      luiPersonRelationTypeKey, luiPersonRelationInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public String createLprRoster(@WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Deletes the LPRRoster
     *
     * @param lprRosterId Identifier for the LUI Person Relation
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     lprRosterId does not exist
     * @throws InvalidParameterException invalid lprRosterId
     * @throws MissingParameterException missing lprRosterId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns all the LPR entries contained in the LPR Roster.
     *
     * @param lprRosterId Identifier of LPR Roster
     * @param context
     * @return
     * @throws DoesNotExistException     lprRosterId does not exist
     * @throws InvalidParameterException invalid lprRosterId
     * @throws MissingParameterException missing lprRosterId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterEntryInfo> getEntriesForLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LPR Roster Entries for the specified list of LPR Roster Entry Ids
     *
     * @param lprRosterEntryIdList List of identifiers for LPR Roster Entries
     * @param context
     * @return List of LPR Roster Entry information
     * @throws DoesNotExistException     One or more lprRosterEntryIds not found
     * @throws InvalidParameterException One or more invalid lprRosterEntryIds
     * @throws MissingParameterException missing lprRosterEntryIdList
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByIdList(
            @WebParam(name = "lprRosterEntryIdList") List<String> lprRosterEntryIdList,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of LprRosterInfo by LUI and Roster Type. A
     * type and LUI are mandatory parameters to retrieve a desired kind of
     * roster.
     *
     * @param luiId            LUI Identifier
     * @param lprRosterTypeKey LPR Roster Key
     * @param context
     * @return
     * @throws DoesNotExistException     luiId or lprRosterTypeKey invalid
     * @throws InvalidParameterException Invalid luiId or lprRosterTypeKey
     * @throws MissingParameterException Missing luiId or lprRosterTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(@WebParam(name = "luiId") String luiId, @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
                                                               @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve all LPR Rosters that are associated with a LUI.
     *
     * @param luiId   LUI Identifier
     * @param context
     * @return
     * @throws DoesNotExistException     luiId invalid
     * @throws InvalidParameterException Invalid luiId
     * @throws MissingParameterException Missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterInfo> getLprRostersByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a LPR Roster by id.
     *
     * @param lprRosterId LPR Roster identifier
     * @param context
     * @return
     * @throws DoesNotExistException     lprRosterId invalid
     * @throws InvalidParameterException Invalid lprRosterEntryInfo
     * @throws MissingParameterException Missing lprRosterEntryInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public LprRosterInfo getLprRoster(@WebParam(name = "lprRosterId") String lprRosterId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a {@link LprRosterEntryInfo} relation object at the position
     * specified by the "position" field. A blank position would mean add to the
     * last position, a specified position means insert to that position.
     *
     * @param lprRosterEntryInfo
     * @param context
     * @return
     * @throws DataValidationErrorException lprRosterEntryInfo data is not valid
     * @throws InvalidParameterException    Invalid lprRosterEntryInfo
     * @throws MissingParameterException    Missing lprRosterEntryInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public String createLprRosterEntry(@WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Delete the {@link LprRosterEntryInfo}
     *
     * @param lprRosterEntryId
     * @param context
     * @return
     * @throws DoesNotExistException     not a valid lprRosterEntryId
     * @throws InvalidParameterException Invalid lprRosterEntryId
     * @throws MissingParameterException Missing lprRosterEntryId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Inserts a roster entry at a particular position. Readjusts the ranks of
     * the other entries that are affected by the new entry.
     *
     * @param lprRosterEntryId
     * @param position
     * @param context
     * @return
     * @throws DoesNotExistException     One of the roster entry id in the list is
     *                                   not a valid id
     * @throws InvalidParameterException Invalid lprRosterEntryIds
     * @throws MissingParameterException Missing lprRosterEntryIds in the input
     * @throws OperationFailedException  unable to complete request
     */
    public StatusInfo insertLprRosterEntryInPosition(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId, @WebParam(name = "position") Integer position,
                                                     @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * The LPR roster entries in the input list is ordered and are the first
     * entries in the roster. If a roster contains other entries, rank them from
     * end of the entries in the input list onwards.
     *
     * @param lprRosterEntryIds ordered list of {@link LprRosterEntryInfo} ids
     *                          The ordered
     * @param context
     * @return
     * @throws DoesNotExistException     One of the roster entry id in the list is
     *                                   not a valid id
     * @throws InvalidParameterException Invalid lprRosterEntryIds
     * @throws MissingParameterException Missing lprRosterEntryIds in the input
     * @throws OperationFailedException  unable to complete request
     */
    public StatusInfo reorderLprRosterEntries(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method creates a LPR transaction - validates the transaction,
     * generates a unique id for the request and persists it in the back-end.
     *
     * @param lprTransactionInfo LPR transaction info
     * @param context
     * @return
     * @throws DataValidationErrorException if LprTransactionInfo fields are not
     *                                      valid
     * @throws AlreadyExistsException       LprTransactionInfo / LPR already exists
     * @throws DoesNotExistException        LUI or Person doesn't exist
     * @throws InvalidParameterException    Invalid personId, luiId, state,
     *                                      LprTransactionInfo
     * @throws MissingParameterException    Missing fields on LprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransaction(@WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo, @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new {@link LprTransactionInfo} from an existing LPR transaction
     * id.
     *
     * @param lprTransactionId LprTransaction identifier
     * @param context
     * @return
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws AlreadyExistsException       LprTransactionInfo / LPR already exists
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransactionFromExisting(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method persists an updated LPR Transaction; it validates the
     * {@link LprTransactionItemInfo} object before persisting.
     *
     * @param lprTransactionId   The transaction identifier
     * @param lprTransactionInfo The updated
     *                           {@link LprTransactionInfo}
     * @param context
     * @return
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId or
     *                                      lprTransactionInfo
     * @throws MissingParameterException    Missing lprTransactionId or
     *                                      lprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
                                                   @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a LPR Transactions based on identifier.
     *
     * @param lprTransactionId The transaction identifier
     * @param context
     * @return
     * @throws DoesNotExistException     lprTransactionId doesn't exist
     * @throws InvalidParameterException Invalid lprTransactionId
     * @throws MissingParameterException Missing lprTransactionId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public LprTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions for Person by LUI Id.
     *
     * @param personId The person identifier
     * @param luiId    The LUI id
     * @param context
     * @return
     * @throws DoesNotExistException     personId or luiId doesn't exist
     * @throws InvalidParameterException Invalid personId or luiId
     * @throws MissingParameterException Missing personId or luiId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsForPersonByLui(@WebParam(name = "personId") String personId, @WebParam(name = "luiId") String luiId,
                                                                     @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on person id.
     *
     * @param personId The person identifier
     * @param context
     * @return
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<String> getLprTransactionIdsForPerson(@WebParam(name = "personId") String personId, @WebParam(name = "lprTransactionStates") List<String> lprTransactionStates,
                                                      @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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

    public List<LprTransactionInfo> getLprTransactionsByIdList(@WebParam(name = "lprIds") List<String> lprIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on the LPR.
     *
     * @param lprId   The person identifier
     * @param context
     * @return
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsForLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on the LUI.
     *
     * @param luiId   The LUI identifier
     * @param context
     * @return
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsForLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a {@link LprTransactionInfo} based on ATP and person id.
     *
     * @param personId The person identifier
     * @param atpId   The ATP Id
     * @param context
     * @return
     * @throws DoesNotExistException     atpId or personId doesn't exist
     * @throws InvalidParameterException Invalid personId or atpId
     * @throws MissingParameterException Missing personId or atpId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsForPersonByAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "personId") String personId,
                                                                     @WebParam(name = "lprTransactionStates") List<String> lprTransactionStates, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an {@link LprTransactionInfo} based on Id.
     *
     * @param lprTransactionId LPR Transaction identifier
     * @param context
     * @return
     * @throws DoesNotExistException     lprTransactionId doesn't exist
     * @throws InvalidParameterException Invalid lprTransactionId
     * @throws MissingParameterException Missing lprTransactionId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */

    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     *
     * @param lprTransactionId
     * @param context
     * @return
     * @throws DataValidationErrorException Invalid lprTransaction
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException    Authorization failure
     */
    public List<ValidationResultInfo> validateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Submits a LPR transaction - validates the input and based on the type of
     * transaction creates, updates, cancels or removes LPRs.
     *
     * @param lprTransactionId the id for the LPR transaction
     * @param context
     * @return
     * @throws AlreadyExistsException       LPR is already present
     * @throws DataValidationErrorException Invalid lprTransaction
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException    Authorization failure
     */
    public LprTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<LprTransactionInfo> searchForLprTransactions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<String> searchForLprTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<LprRosterInfo> searchForLprRosters(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<String> searchForLprRosterIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

}