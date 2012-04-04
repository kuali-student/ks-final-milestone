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
public interface LuiPersonRelationService {

    /**
     * Retrieves the Relation for the specified LUI Person Relation id
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
    public LuiPersonRelationInfo getLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     *
     * @param luiPersonRelationIds List of identifiers for LUI Person
     *                                Relations
     * @param context                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException     One or more luiPersonRelationIds not found
     * @throws InvalidParameterException One or more invalid
     *                                   luiPersonRelationIds
     * @throws MissingParameterException missing luiPersonRelationIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LuiPersonRelationInfo> getLprsByIds(@WebParam(name = "luiPersonRelationIds") List<String> luiPersonRelationIds,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person, type and state.
     * 
     * This would allow you to get all the active (state) courses 
     * (type=registration) for a student.
     *
     * @param personId                 Identifier for the Person 
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
    public List<String> getLuiIdsByPersonAndTypeAndState(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Ids related to the specified LUI, type and state
     * 
     * This would allow you to get a list of people who are active (state) students (type=registration) 
     * for a particular course (luiId) 
     * 
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
    public List<String> getPersonIdsByLuiAndTypeAndState(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
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
    public List<LuiPersonRelationInfo> getLprsByPersonAndLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for a person and LuiId
     * 
     * Can be used to get course registrations for a person in a particular course.
     * Although this would typically only return one registration for each set of values, but
     * if a person adds a course then drops it then adds it again you could get multiple registrations.
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
    public List<String> getLprIdsByPersonAndLui (@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relations for Person
     * 
     * Can be used to get all the course registrations for a person for as long as 
     * they have been at the school, but please note it could include student registrations
     * in things that are not courses OR student's acting as teaching assistants
     * to courses.
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
    public List<LuiPersonRelationInfo> getLprsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation Ids for a Person
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
    public List<String> getLprIdsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     * 
     * Can be used to get all the people and their relationship to a particular
     * course offering.
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
    public List<LuiPersonRelationInfo> getLprsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUIPersonRelation Ids for LUI
     * 
     * Can be used to get all the people and their relationship to a particular
     * course offering.
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
    public List<String> getLprIdsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a particular type and specified LUI
     *
     * Can be used to get all the people with a particular relationship to a 
     * specified course offering.
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
    public List<LuiPersonRelationInfo> getLprsByLuiAndType (@WebParam(name = "luiId") String luiId,
            @WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPRs by person and ATP
     *
     * Can be used to get a list of registrations or instructor assignments for a person and a term
     * 
     * Note: the ATP is not stored on the LPR but on the Lui so the ATP specified is used
     * to select or filter the Lui ids that can appear on the LPRs that are returned.
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
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPRs for a person and particular ATP by the type of
     * LPR.
     *
     * Can be used to get a list of registrations for a person and a person 
     * but making sure to exclude other types of relations the student may have 
     * during that term such as also being an teaching assistant for a course.
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
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "typeKey") String typeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the LPRs for a person and the type Lui.
     * 
     * Can be used to get a list of all the relations a person may have to a course 
     * offering (which is a lui type).
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
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the the LPRs for a person and atp and Lui type.
     * 
     * Can be used to get the relations a person may have to a course offering
     * (which is a lui type) and a particular term (atpId)
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
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

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
    public List<ValidationResultInfo> validateLpr(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public List<String> searchForLprIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
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
    public List<LuiPersonRelationInfo> searchForLprs(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException,
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
    public String createLpr(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified person. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * @param personId                 Identifier for Person
     * @param luiIds                Simple list of LUI identifiers
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
    public List<String> createBulkRelationshipsForPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiIds") List<String> luiIds,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates bulk relationships for one specified LUI. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
     *
     * @param luiId                    Identifier for Lui
     * @param personIds             Simple list of Person identifiers
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
    public List<String> createBulkRelationshipsForLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "personIds") List<String> personIds,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "luiPersonRelationTypeKey") String luiPersonRelationTypeKey,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DisabledIdentifierException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public LuiPersonRelationInfo updateLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "luiPersonRelationInfo") LuiPersonRelationInfo luiPersonRelationInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException,
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
    public StatusInfo deleteLpr(@WebParam(name = "luiPersonRelationId") String luiPersonRelationId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
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
    public LprRosterInfo updateLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Creates a roster
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
    public String createLprRoster(@WebParam(name = "lprRosterInfo") LprRosterInfo lprRosterInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public StatusInfo deleteLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns all the LPR roster entries contained in the LPR Roster.
     *
     * @param lprRosterId Identifier of LPR Roster
     * @param context
     * @throws DoesNotExistException     lprRosterId does not exist
     * @throws InvalidParameterException invalid lprRosterId
     * @throws MissingParameterException missing lprRosterId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesForRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the LPR Roster Entries for the specified list of LPR Roster Entry Ids
     *
     * @param lprRosterEntryIds List of identifiers for LPR Roster Entries
     * @param context
     * @return List of LPR Roster Entry information
     * @throws DoesNotExistException     One or more lprRosterEntryIds not found
     * @throws InvalidParameterException One or more invalid lprRosterEntryIds
     * @throws MissingParameterException missing lprRosterEntryIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprRosterEntryInfo> getLprRosterEntriesByIds(
            @WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a list of Lpr Rosters by LUI and Type. 
     * 
     * Can be used to get the waitlist (type) roster for a particular course offering (lui).
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
    public List<LprRosterInfo> getLprRostersByLuiAndType(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprRosterTypeKey") String lprRosterTypeKey,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve all LPR Rosters that are associated with a LUI.
     * 
     * Can be used to get all the rosters, waitlists, hold until lists, grade sheets, etc 
     * for a particular course offering.
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
    public List<LprRosterInfo> getLprRostersByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns a LPR Roster by it's id.
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
    public LprRosterInfo getLprRoster(@WebParam(name = "lprRosterId") String lprRosterId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates an Lpr Roster Entry at the position specified by the value of the 
     * relative position field inside the roster info. 
     * 
     * An empty position would mean add to the last position, a specified position 
     * means insert to that position.
     * 
     *
     * @param lprRosterEntryInfo
     * @param context
     * @throws DataValidationErrorException lprRosterEntryInfo data is not valid
     * @throws InvalidParameterException    Invalid lprRosterEntryInfo
     * @throws MissingParameterException    Missing lprRosterEntryInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public String createLprRosterEntry(@WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update The roster entry
     *
     * @param lprRosterEntryId   Identifier for the roster entry
     * @param lprRosterEntryInfo Changed information about the roster entry
     * @param context       Context information containing the principalId and locale
     *                      information about the caller of service operation
     * @return Updated information about the roster entry
     * @throws DataValidationErrorException if lprRosterEntryInfo is not
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
    public LprRosterInfo updateLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
            @WebParam(name = "lprRosterEntryInfo") LprRosterEntryInfo lprRosterEntryInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Delete the Lpr Roster Entry
     *
     * @param lprRosterEntryId
     * @param context
     * @throws DoesNotExistException     not a valid lprRosterEntryId
     * @throws InvalidParameterException Invalid lprRosterEntryId
     * @throws MissingParameterException Missing lprRosterEntryId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLprRosterEntry(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Inserts an existing roster entry at a particular position on the roster.
     * 
     * If another roster entry already exists at that particular position within 
     * the roster then this method "bumps down" the rest of the roster entries 
     * until there is an open position.
     * 
     * @param lprRosterEntryId
     * @param absolutePosition the position the person would be in if they 
     * @param context
     * @throws DoesNotExistException     One of the roster entry id in the list is
     *                                   not a valid id
     * @throws InvalidParameterException Invalid lprRosterEntryIds
     * @throws MissingParameterException Missing lprRosterEntryIds in the input
     * @throws OperationFailedException  unable to complete request
     */
    public StatusInfo insertLprRosterEntryInPosition(@WebParam(name = "lprRosterEntryId") String lprRosterEntryId,
            @WebParam(name = "absolutePosition") Integer absolutePosition,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Reorders all the roster entries setting their position to match the order
     * within the specified list of LPR roster entry ids.
     * 
     * This is a bulk method to reset the positions all of the entries in the roster.
     * 
     * Any entries in the roster that are not contained in the list are ordered 
     * by their existing position and placed at the end of the entries in the
     * specified list.
     *
     * @param lprRosterEntryIds ordered list of LPR roster entry Ids
     * @param context
     * @throws DoesNotExistException     One of the roster entry id in the list is
     *                                   not a valid roster entry id
     * @throws InvalidParameterException Invalid lprRosterEntryIds 
     * @throws MissingParameterException Missing lprRosterEntryIds in the input
     * @throws OperationFailedException  unable to complete request
     */
    public StatusInfo reorderLprRosterEntries(@WebParam(name = "lprRosterEntryIds") List<String> lprRosterEntryIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method creates a LPR transaction of the specified type
     * 
     * Validates the transaction generates a unique id for the request and 
     * persists it in the back-end.
     * 
     * TODO: Add requesting person Id and ATP id to these signature
     * 
     * @param lprTransactionInfo LPR transaction info
     * @param lprTransactionType LPR transaction type
     * @param context
     * @throws DataValidationErrorException if LprTransactionInfo fields are not
     *                                      valid
     * @throws AlreadyExistsException       LprTransactionInfo / LPR already exists
     * @throws DoesNotExistException        LUI or Person doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionType, lprTransaction
     * @throws MissingParameterException    Missing fields on LprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransaction(@WebParam(name = "lprTransactionType") String lprTransactionType,
            @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Lpr Transaction from an existing LPR transaction
     *
     * Copies the transaction's Items as well.
     * 
     * The new transaction has the same type as the existing transaction.
     * Since transactions can only be processed once this method was intended
     * to allow the application to easily create a new transaction with all of it's items 
     * that the user be able to fix and update any problems that resulted from 
     * processing the existing transaction. 
     * 
     * @param lprTransactionId LprTransaction identifier
     * @param context
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws AlreadyExistsException       LprTransactionInfo / LPR already exists
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransactionFromExisting(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * This method updates an LPR Transaction and all of it's items.
     *
     * @param lprTransactionId   The transaction identifier
     * @param lprTransactionInfo The updated Lpr Transaction
     * @param context
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId or
     *                                      lprTransactionInfo
     * @throws MissingParameterException    Missing lprTransactionId or
     *                                      lprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
            @WebParam(name = "context") ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LPR Transactions based on it's identifier.
     *
     * @param lprTransactionId The transaction identifier
     * @param context
     * @throws DoesNotExistException     lprTransactionId doesn't exist
     * @throws InvalidParameterException Invalid lprTransactionId
     * @throws MissingParameterException Missing lprTransactionId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public LprTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions with an item by Person and LUI
     * 
     * Selects all transactions that have at least one item that matches the specified 
     * person and either the existing or new lui.
     * 
     * Note: this matches the person on the item not the person requesting the transaction 
     * which is on the transaction.
     * 
     * TODO: Think about recasting this to return Transaction Items instead, that is
     * what we really want here and if we have to track back to the Transaction 
     * then put the transaction id on the item so that can be done.
     *
     * @param personId The person identifier
     * @param luiId    The LUI id
     * @param context
     * @throws DoesNotExistException     personId or luiId doesn't exist
     * @throws InvalidParameterException Invalid personId or luiId
     * @throws MissingParameterException Missing personId or luiId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsWithItemsByPersonAndLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions with an item by Person and transaction states
     * 
     * Note: this matches the person on the item not the person requesting the transaction 
     * which is on the transaction.
     * 
     * TODO: Think about recasting this to return Transaction Items instead, that is
     * what we really want here and if we have to track back to the Transaction 
     * then put the transaction id on the item so that can be done.
     *
     * @param personId The person identifier
     * @param context
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<String> getLprTransactionIdsByStateWithItemsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "lprTransactionStates") List<String> lprTransactionStates,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Get lpr transactions for the specified list of ids.
     *
     * @param lprIds
     * @param context
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LprTransactionInfo> getLprTransactionsByIds(@WebParam(name = "lprIds") List<String> lprIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on the resulting LPR.
     * 
     * Selects all transactions that have at least one item who's resulting lpr matches 
     * the specified lpr.
     * 
     * TODO: Think about recasting this to return Transaction Items instead, that is
     * what we really want here and if we have to track back to the Transaction 
     * then put the transaction id on the item so that can be done.
     * 
     * @param lprId   The resulting lpr
     * @param context
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsWithItemsByResultingLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transactions based on an item with the specified LUI.
     *
     * Selects all transactions that have at least one item with the specified LUI.
     * 
     * TODO: Think about recasting this to return Transaction Items instead, that is
     * what we really want here and if we have to track back to the Transaction 
     * then put the transaction id on the item so that can be done.
    
     * @param luiId   The LUI identifier
     * @param context
     * @throws DoesNotExistException     personId doesn't exist
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsWithItemsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves transactions for the requesting person and the ATP.
     * 
     * TODO: This method originally also additionally filtered on transaction state 
     * but other evidence had it by lpr type consider adding back in so I took it 
     * out.  Perhaps it should be added back in.
     * 
     * @param requestingPersonId The person identifier
     * @param atpId   The ATP Id
     * @param context
     * @throws DoesNotExistException     atpId or personId doesn't exist
     * @throws InvalidParameterException Invalid personId or atpId
     * @throws MissingParameterException Missing personId or atpId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsByRequestingPersonAndAtp(
            @WebParam(name = "personId") String requestingPersonId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an lpr Transaction based on Id.
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
    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate the LPR Transaction
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
    public List<ValidationResultInfo> validateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Submits a LPR transaction - validates the input and based on the type of
     * transaction creates, updates, cancels or removes LPRs.
     *
     * @param lprTransactionId the id for the LPR transaction
     * @param context
     * @throws AlreadyExistsException       LPR is already present
     * @throws DataValidationErrorException Invalid lprTransaction
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException    Authorization failure
     */
    public LprTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * search for matching LPR transactions
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
     * Search for matching LPR transactions returning their ids
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
     * Search for matching LPR rosters
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
     * Search for matching LPR rosters returning the ids
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