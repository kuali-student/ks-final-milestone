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
 * <li>a student's registration in a course
 * <li>a student's enrollment in a program (major or minor)
 * <li>an instructor's assignment to teach a course
 * <li>a faculty member's assignment as an advisor for a program
 * </ul>
 * @Version 1.0 (Dev)
 *
 * @Author Kamal
 * @Since Tue Mar 01 15:53:51 PST 2011
 */
@WebService(name = "LprService", serviceName = "LprService", portName = "LprService",targetNamespace = LprServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LprService {

    /**
     * Retrieves the Relation for the specified LUI Person Relation id
     *
     * @param lprId Identifier for the LUI Person Relation
     * @param contextInfo             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return LUI Person Relation information
     * @throws DoesNotExistException     lprId not found
     * @throws InvalidParameterException invalid lprId
     * @throws MissingParameterException missing lprId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public LprInfo getLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the Relation for the specified list of LUI Person Relation Ids
     *
     * @param lprIds List of identifiers for LUI Person
     *                                Relations
     * @param contextInfo                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @return List of LUI Person Relation information
     * @throws DoesNotExistException     One or more lprIds not found
     * @throws InvalidParameterException One or more invalid
     *                                   lprIds
     * @throws MissingParameterException missing lprIds
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByIds(@WebParam(name = "lprIds") List<String> lprIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the LUI Ids for Person, type and state.
     * 
     * Example Use Case: This would allow you to get all the active (state) courses 
     * (type=registration) for a student.
     *
     * @param personId                 Identifier for the Person 
     * @param lprTypeKey Type of LUI Person Relation
     * @param relationState            Relation State
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Simple list of LUI Ids
     * @throws DoesNotExistException       personId, lprTypeKey,
     *                                     relationState, person to LUI relationship not found
     * @throws InvalidParameterException   invalid personId,
     *                                     lprTypeKey, relationState
     * @throws MissingParameterException   missing personId,
     *                                     lprTypeKey, relationState
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
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
     * @throws InvalidParameterException invalid luiId,
     *                                   lprTypeKey, relationState
     * @throws MissingParameterException missing luiId,
     *                                   lprTypeKey, relationState
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getPersonIdsByLuiAndTypeAndState(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "relationState") String relationState,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves Person Relation for person and LUI
     *
     * Example Use Case: Can be used to get course registrations for a person in a particular course.
     * Although this would typically only return one registration for each set of values, but
     * if a person adds a course then drops it then adds it again you could get multiple registrations.
     *
     * @param personId Identifier for person
     * @param luiId    Identifier for LUI
     * @param contextInfo  Context information containing the principalId and locale
     *                     information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId, luiId not found
     * @throws InvalidParameterException   invalid personId, luiId
     * @throws MissingParameterException   missing personId, luiId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LprInfo> getLprsByPersonAndLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relations for Person
     * 
     * Example Use Case: Can be used to get all the course registrations for a person for as long as
     * they have been at the school, but please note it could include student registrations
     * in things that are not courses OR student's acting as teaching assistants
     * to courses.
     * 
     * @param personId Identifier for person
     * @param contextInfo  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException       personId not found
     * @throws InvalidParameterException   invalid personId
     * @throws MissingParameterException   missing personId
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   authorization failure
     */
    public List<LprInfo> getLprsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LUI Person Relation for a specified LUI
     * 
     * Example Use Case: Can be used to get all the people and their relationships to a particular
     * course offering.
     *
     * @param luiId   Identifier for LUI
     * @param contextInfo Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves LUI Person Relations for a list of Lui ids
     * 
     * Example Use Case: Can be used to get all the people and their relationships to a particular
     * list of course offering's.
     *
     * @param luiIds   Identifiers for LUI's that we want to load the related LPR's 
     * @param contextInfo Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     one or more of the provided luiId's are not found
     * @throws InvalidParameterException invalid luiIds or contextInfo
     * @throws MissingParameterException one or more missing parameters.
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
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
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByLuiAndType (@WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPRs by person and ATP
     *
     * Example Use Case: Can be used to get a list of registrations or instructor assignments for a person and a term
     * 
     * Note: the ATP is not stored on the LPR but on the Lui so the ATP specified is used
     * to select or filter the Lui ids that can appear on the LPRs that are returned.
     * 
     * @param personId  the person id
     * @param atpId     the id of the academic time period
     * @param contextInfo  Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByPersonForAtp(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of LPRs for a person and particular ATP by the type of
     * LPR.
     *
     * Example Use Case: Can be used to get a list of registrations for a person and a relation type
     * but making sure to exclude other types of relations the student may have 
     * during that term such as also being an teaching assistant for a course.
     *
     * @param personId  the person id
     * @param atpId     the id of the academic time period
     * @param lprTypeKey LUI person relation type key
     * @param contextInfo  Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of LUI Person Relation info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     *
     */
    public List<LprInfo> getLprsByPersonAndTypeForAtp(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

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
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByPersonAndLuiType(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the the LPRs for a person and atp and Lui type.
     * 
     * Example Use Case: Can be used to get the relations a person may have to a course offering
     * (which is a lui type) and a particular term (atpId)
     *
     * @param personId   person identifier
     * @param atpId academic time period identifier
     * @param luiTypeKey type of the LUI
     * @param contextInfo       Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return a List of Lui Person Relation Info
     * @throws DoesNotExistException     luiId not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(@WebParam(name = "personId") String personId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "luiTypeKey") String luiTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Validates the particular relation in a state for a Person and LUI
     *
     * @param validationType        Identifier of the extent of validation
     * @param luiId                 the identifier of the lui
     * @param personId              the identifier of the person
     * @param lprTypeKey       the key to the type of the relationship
     * @param lprInfo   lpr to be validated
     * @param contextInfo               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return list of validation results, list should be be zero length if no
     *         validation results are generated
     * @throws DoesNotExistException     lprInfo not found
     * @throws InvalidParameterException invalid lprInfo
     *                                   relationState
     * @throws MissingParameterException missing lprInfo
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validateLpr(@WebParam(name = "validationType") String validationType,
            @WebParam (name="luiId") String luiId,
            @WebParam (name="personId") String personId,
            @WebParam (name="lprTypeKey")  String lprTypeKey,
            @WebParam(name = "lprInfo") LprInfo lprInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches details of LUI Person Relation Ids
     *
     * @param criteria Criteria to be used for retrieval of multiple LUI Person
     *                 Relation identifiers
     * @param contextInfo  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForLprIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches details of LUI Person Relation by search criteria
     *
     * @param criteria Criteria to be used for retrieval of multiple LUI Person
     *                 Relation identifiers
     * @param contextInfo  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return Simple list of LUI Person Relation identifiers
     * @throws InvalidParameterException invalid relation criteria
     * @throws MissingParameterException missing relation criteria
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LprInfo> searchForLprs(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates relation between the specified Person and LUI
     *
     * @param personId                 Person Identifier
     * @param luiId                    LUI Identifier
     * @param lprTypeKey Type of LUI to Person Relation
     * @param lprInfo    Information required to create the LUI
     *                                 Person relation
     * @param contextInfo                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return Structure containing LUI Person relation identifiers
     * @throws DataValidationErrorException if lprInfo is not
     *                                      valid
     * @throws DoesNotExistException        personId, luiId, relationState,
     *                                      lprTypeKey does not exist
     * @throws InvalidParameterException    invalid personId, luiId, relationState,
     *                                      lprTypeKey, lprInfo
     * @throws MissingParameterException    missing personId, luiId, relationState,
     *                                      lprTypeKey, lprInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempt to update a read only attribute
     */
    public LprInfo createLpr(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "lprTypeKey") String lprTypeKey,
            @WebParam(name = "lprInfo") LprInfo lprInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException;

    /**
     * Creates multiple LPRs for one specified person. This is an all or
     * nothing transaction - any error will invalidate the entire transaction.
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
     * @throws PermissionDeniedException    authorization failure
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
     * @throws PermissionDeniedException    authorization failure
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
     * Update relations between Person and LUI
     *
     * @param lprId   Identifier for the LUI Person Relation
     * @param lprInfo Changed information about the LUI Person
     *                              Relation
     * @param contextInfo               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return Updated information about the LUI Person Relation
     * @throws DataValidationErrorException if lprInfo is not
     *                                      valid
     * @throws DoesNotExistException        lprId does not exist
     * @throws InvalidParameterException    invalid lprId,
     *                                      lprInfo
     * @throws MissingParameterException    missing lprId,
     *                                      lprInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempt to update a read only attribute
     * @throws VersionMismatchException     if optimistic lock version ind has
     *                                      changed
     */
    public LprInfo updateLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "lprInfo") LprInfo lprInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes relation between the specified Person and LUI
     *
     * @param lprId Identifier for the LUI Person Relation
     * @param contextInfo             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     lprId does not exist
     * @throws InvalidParameterException invalid lprId
     * @throws MissingParameterException missing lprId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteLpr(@WebParam(name = "lprId") String lprId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * This method creates a LPR transaction of the specified type
     * 
     * Validates the transaction generates a unique id for the request and 
     * persists it in the back-end.
     *
     * @param lprTransactionInfo LPR transaction info
     * @param lprTransactionType LPR transaction type
     * @param contextInfo context info
     * @throws DataValidationErrorException if LprTransactionInfo fields are not
     *                                      valid
     * @throws DoesNotExistException        LUI or Person doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionType, lprTransaction
     * @throws MissingParameterException    Missing fields on LprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransaction(@WebParam(name = "lprTransactionType") String lprTransactionType,
            @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param contextInfo the context information
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */
    public LprTransactionInfo createLprTransactionFromExisting(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * This method updates an LPR Transaction and all of it's items.
     *
     * @param lprTransactionId   The transaction identifier
     * @param lprTransactionInfo The updated Lpr Transaction
     * @param contextInfo
     * @throws DataValidationErrorException LprTransactionInfo is not valid
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId or
     *                                      lprTransactionInfo
     * @throws MissingParameterException    Missing lprTransactionId or
     *                                      lprTransactionInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     for when Optimistic Locking encounters a version mis-match.
     */
    public LprTransactionInfo updateLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "lprTransactionInfo") LprTransactionInfo lprTransactionInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Retrieves the LPR Transactions based on it's identifier.
     *
     * @param lprTransactionId The transaction identifier
     * @param contextInfo       the contextual information
     * @throws DoesNotExistException     lprTransactionId doesn't exist
     * @throws InvalidParameterException Invalid lprTransactionId
     * @throws MissingParameterException Missing lprTransactionId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public LprTransactionInfo getLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
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
     * @param personId The person identifier
     * @param luiId    The LUI id
     * @param contextInfo the context information
     * @throws DoesNotExistException     personId or luiId doesn't exist
     * @throws InvalidParameterException Invalid personId or luiId
     * @throws MissingParameterException Missing personId or luiId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(@WebParam(name = "personId") String personId,
            @WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;


    /**
     * Get lpr transactions for the specified list of transaction ids.
     *
     * @param lprTransactionIds the ids of the lpr transactions
     * @param contextInfo the context information
     * @throws DoesNotExistException     personId or luiId doesn't exist
     * @throws InvalidParameterException Invalid personId or luiId
     * @throws MissingParameterException Missing personId or luiId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionInfo> getLprTransactionsByIds(@WebParam(name = "lprTransactionIds") List<String> lprTransactionIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves LPR Transaction Items based on the resulting LPR.
     * 
     * Selects all transaction items that have this resulting lpr
     *
     * @param lprId   The resulting lpr
     * @param contextInfo the context info
     * @throws InvalidParameterException Invalid personId
     * @throws MissingParameterException Missing personId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
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
     * @throws PermissionDeniedException Authorization failure
     */
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(@WebParam(name = "luiId") String luiId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves transactions for the requesting person and the ATP.
     *
     * Unsubmitted transactions are those that have not been submitted (processed) yet. They can be found
     * by checking the state of transactions where state is "New" (see wiki).
     *
     * @param requestingPersonId The person identifier
     * @param atpId   The ATP Id
     * @param contextInfo the context info
     * @throws InvalidParameterException Invalid personId or atpId
     * @throws MissingParameterException Missing personId or atpId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     * @return
     */
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(
            @WebParam(name = "personId") String requestingPersonId,
            @WebParam(name = "atpId") String atpId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an lpr Transaction based on Id.
     *
     * @param lprTransactionId LPR Transaction identifier
     * @param contextInfo the context info
     * @return
     * @throws DoesNotExistException     lprTransactionId doesn't exist
     * @throws InvalidParameterException Invalid lprTransactionId
     * @throws MissingParameterException Missing lprTransactionId
     * @throws OperationFailedException  Unable to complete request
     * @throws PermissionDeniedException Authorization failure
     */
    public StatusInfo deleteLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Submits a LPR transaction - validates the input and based on the type of
     * transaction creates, updates, cancels or removes LPRs.
     *
     * @param lprTransactionId the id for the LPR transaction
     * @param contextInfo
     * @throws AlreadyExistsException       LPR is already present
     * @throws DataValidationErrorException Invalid lprTransaction
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException    Authorization failure
     */
    public LprTransactionInfo processLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate the LPR Transaction
     *
     * @param lprTransactionId the id of the transaction
     * @param contextInfo the context info
     * @return
     * @throws DataValidationErrorException Invalid lprTransaction
     * @throws DoesNotExistException        lprTransactionId doesn't exist
     * @throws InvalidParameterException    Invalid lprTransactionId
     * @throws MissingParameterException    Missing lprTransactionId
     * @throws OperationFailedException     Unable to complete request
     * @throws PermissionDeniedException    Authorization failure
     */
    public List<ValidationResultInfo> verifyLprTransaction(@WebParam(name = "lprTransactionId") String lprTransactionId,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * search for matching LPR transactions
     *
     * @param criteria
     * @param contextInfo
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<LprTransactionInfo> searchForLprTransactions(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Search for matching LPR transactions returning their ids
     *
     * @param criteria
     * @param contextInfo
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> searchForLprTransactionIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;


}