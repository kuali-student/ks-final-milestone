/**
 * Copyright 2010 The Kuali Foundation
 *
 *  Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.lui.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.service.TypeService;
import org.kuali.student.common.util.constants.LuiServiceConstants;
import org.kuali.student.datadictionary.service.DataDictionaryService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;


/**
 * Learning Unit Insntance (LUI) Service
 *
 * Managages the creation of Instances of the cannonical Learning unit.  An instance is
 * associated with a particular time period during which is is offered.
 *
 * This includes course and section offerings as well as program offerings
 * 
 * Version: 1.0 (Dev)
 *
 * @Author Tom
 * @Since Wed Mar 2 15:18:59 EST 2011
 */

@WebService(name = "LuiService", targetNamespace = LuiServiceConstants.LUI_NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LuiService extends DataDictionaryService, TypeService {


    /**
     * Retrieves information about a LUI.
     *
     * @param luiId   identifier of the LUI
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return information about a LUI
     * @throws DoesNotExistException     lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     */

    public LuiInfo getLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves information about LUIs from a list of Ids.
     *
     * @param luiIdList List of LUI identifiers
     * @param context   Context information containing the principalId
     *                  and locale information about the caller of service
     *                  operation
     * @return information about a list of LUIs
     * @throws DoesNotExistException     One or more luis not found
     * @throws InvalidParameterException One or more invalid luiIds
     * @throws MissingParameterException missing luiIdList
     * @throws OperationFailedException  unable to complete request
     */

    public List<LuiInfo> getLuisByIdList(@WebParam(name = "luiIdList") List<String> luiIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of LUIs for the specified CLU and period.
     *
     * @param cluId   identifier of the CLU
     * @param atpKey  identifier for the academic time period
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of LUI information
     * @throws DoesNotExistException     clu, atp not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException  unable to complete request
     */

    public List<LuiInfo> getLuisInAtpByCluId(@WebParam(name = "cluId") String cluId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of LUI ids for the specified CLU.
     *
     * @param cluId   identifier of the CLU
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of LUI identifiers
     * @throws DoesNotExistException     clu not found
     * @throws InvalidParameterException invalid cluId
     * @throws MissingParameterException missing cluId
     * @throws OperationFailedException  unable to complete request
     */

    public List<String> getLuiIdsByCluId(@WebParam(name = "cluId") String cluId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of LUI ids for the specified CLU and Time period.
     *
     * @param cluId   identifier of the CLU
     * @param atpKey  identifier for the academic time period
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of LUI identifiers
     * @throws DoesNotExistException     clu, atp not found
     * @throws InvalidParameterException invalid cluId, atpKey
     * @throws MissingParameterException missing cluId, atpKey
     * @throws OperationFailedException  unable to complete request
     */

    public List<String> getLuiIdsInAtpByCluId(@WebParam(name = "cluId") String cluId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of LUI information for the LUIs related to
     * the specified LUI Id with a certain LU to LU relation type.
     * <p/>
     * (getRelatedLuisByLuiId from the other direction)
     *
     * @param relatedLuiId     identifier of the LUI
     * @param luLuRelationTypeKey the LU to LU relation type
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return list of LUI information, empty list if none
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationTypeKey
     * @throws MissingParameterException missing relatedLuiId, luLuRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     */
    public List<LuiInfo> getLuisByRelation(@WebParam(name = "relatedLuiId") String relatedLuiId,
      @WebParam(name = "luLuRelationTypeKey") String luLuRelationTypeKey,
      @WebParam(name = "context") ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of LUI Ids for the specified related LUI Id
     * and LU to LU relation type (getRelatedLuiIdsByLuiId from the
     * other direction).
     *
     * @param relatedLuiId     identifier of the LUI
     * @param luLuRelationTypeKey the LU to LU relation type
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return list of LUI identifiers, empty list of none found
     * @throws InvalidParameterException invalid relatedLuiId, luLuRelationTypeKey
     * @throws MissingParameterException missing relatedLuiId, luLuRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     */
    public List<String> getLuiIdsByRelation(@WebParam(name = "relatedLuiId") String relatedLuiId, 
      @WebParam(name = "luLuRelationTypeKey") String luLuRelationTypeKey,
      @WebParam(name = "context") ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of related LUI information for the specified
     * LUI Id and LU to LU relation type (getLuisByRelation from the
     * other direction).
     *
     * @param luiId            identifier of the LUI
     * @param luLuRelationTypeKey the LU to LU relation type
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return list of LUI information, empty list if none found
     * @throws InvalidParameterException invalid luiId, luLuRelationTypeKey
     * @throws MissingParameterException missing luiId, luLuRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     */

    public List<LuiInfo> getRelatedLuisByLuiId(@WebParam(name = "luiId") String luiId,
      @WebParam(name = "luLuRelationTypeKey") String luLuRelationTypeKey,
      @WebParam(name = "context") ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of related LUI Ids for the specified LUI Id
     * and LU to LU relation type. (getLuiIdsByRelation from the other
     * direction).
     *
     * @param luiId            identifier of the LUI
     * @param luLuRelationTypeKey the LU to LU relation type
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return list of LUI identifier, empty list if none found
     * @throws InvalidParameterException invalid luiId, luLuRelationTypeKey
     * @throws MissingParameterException missing luiId, luLuRelationTypeKey
     * @throws OperationFailedException  unable to complete request
     */

    public List<String> getRelatedLuiIdsByLuiId(@WebParam(name = "luiId") String luiId,
      @WebParam(name = "luLuRelationTypeKey") String luLuRelationTypeKey,
      @WebParam(name = "context") ContextInfo context)
      throws InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the relationship information between LUIs given a
     * specific relation instance.
     *
     * @param luiLuiRelationId identifier of LUI to LUI relatio
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return information on the relation between two LUIs
     * @throws DoesNotExistException     luiLuiRelation not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException  unable to complete request
     */

    public LuiLuiRelationInfo getLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Retrieves the list of relationship information for the
     * specified LUI.
     *
     * @param luiId   identifier of the LUI
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return list of LUI to LUI relation information
     * @throws DoesNotExistException     lui not found
     * @throws InvalidParameterException invalid luiId
     * @throws MissingParameterException missing luiId
     * @throws OperationFailedException  unable to complete request
     */

    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Validates a LUI. Depending on the value of validationType, this
     * validation could be limited to tests on just the current object
     * and its directly contained sub-objects or expanded to perform
     * all tests related to this object. If an identifier is present
     * for the LUI (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the LUI can be shifted to the new values. If an identifier is
     * not present or a record cannot be found for the identifier, it
     * is assumed that the record does not exist and as such, the
     * checks performed will be much shallower, typically mimicking
     * those performed by setting the validationType to the current
     * object.
     *
     * @param validationType identifier of the extent of validation
     * @param luiInfo        LUI information to be tested.
     * @param context        Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, luiInfo
     * @throws MissingParameterException missing validationTypeKey, luiInfo
     * @throws OperationFailedException  unable to complete request
     */

    public List<ValidationResultInfo> validateLui(@WebParam(name = "validationType") String validationType,
      @WebParam(name = "luiInfo") LuiInfo luiInfo,
      @WebParam(name = "context") ContextInfo context)
      throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Creates a new LUI.
     *
     * @param cluId   identifier of the CLU for the LUI being created
     * @param atpKey  identifier of the academic time period for the
     *                LUI being created
     * @param luiInfo information about the LUI being created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the created LUI information
     * @throws AlreadyExistsException       LUI already exists
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        clu, atp not found
     * @throws InvalidParameterException    invalid cluId, atpKey, luiInfo
     * @throws MissingParameterException    missing cluId, atpKey, luiInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */

    public LuiInfo createLui(@WebParam(name = "cluId") String cluId,
      @WebParam(name = "atpKey") String atpKey,
      @WebParam(name = "luiInfo") LuiInfo luiInfo,
      @WebParam(name = "context") ContextInfo context)
      throws AlreadyExistsException,
      DataValidationErrorException,
      DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException;


    /**
     * Updates an existing LUI.
     *
     * @param luiId   identifier for the LUI to be updated
     * @param luiInfo updated information about the LUI
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the updated LUI information
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        lui not found
     * @throws InvalidParameterException    invalid luiId, luiInfo
     * @throws MissingParameterException    missing luiId, luiInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an
     *                                      out of date version.
     */

    public LuiInfo updateLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "luiInfo") LuiInfo luiInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;


    /**
     * Deletes a LUI record.
     *
     * @param luiId   identifier for the LUI to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation
     * @throws DependentObjectsExistException delete would leave
     *                                        orphaned objects or violate integrity constraints
     * @throws DoesNotExistException          lui not found
     * @throws InvalidParameterException      invalid luiId
     * @throws MissingParameterException      missing luiId
     * @throws OperationFailedException       unable to complete request
     * @throws PermissionDeniedException      authorization failure
     */

    public StatusInfo deleteLui(@WebParam(name = "luiId") String luiId, @WebParam(name = "context") ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Updates the state of the LUI.
     *
     * @param luiId   identifier for the LUI to be updated
     * @param luState New state for LUI. Value is expected to be
     *                constrained to those in the luState enumeration.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the updated LUI information
     * @throws DataValidationErrorException New state not valid for
     *                                      existing state of LUI
     * @throws DoesNotExistException        lui, luState not found
     * @throws InvalidParameterException    invalid luiId, luState
     * @throws MissingParameterException    missing luiId, luState
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     */

    public LuiInfo updateLuiState(@WebParam(name = "luiId") String luiId, @WebParam(name = "luState") String luState, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Validates a relationship between LUIs. Depending on the value
     * of validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the relationship (and/or one of its
     * contained sub-objects) and a record is found for that
     * identifier, the validation checks if the relationship can be
     * shifted to the new values. If an identifier is not present or a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object.
     *
     * @param validationType     identifier of the extent of validation
     * @param luiLuiRelationInfo LUI to LUI relationship information
     *                           to be tested.
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     *                                   luiLuiRelationInfo
     * @throws MissingParameterException missing validationTypeKey,
     *                                   luiLuiRelationInfo
     * @throws OperationFailedException  unable to complete request
     */

    public List<ValidationResultInfo> validateLuiLuiRelation(@WebParam(name = "validationType") String validationType,
      @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo,
      @WebParam(name = "context") ContextInfo context)
      throws DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException;


    /**
     * Create a relationship between two LUIs.
     *
     * @param luiId              identifier of the first LUI in the relationship
     * @param relatedLuiId       identifier of the second LUI in the
     *                           relationship to be related to
     * @param luLuRelationTypeKey   the LU to LU relationship type of the
     *                           relationship
     * @param luiLuiRelationInfo information about the relationship
     *                           between the two LUIs
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return the created LUI to LUI relation information
     * @throws AlreadyExistsException        relationship already exists
     * @throws CircularRelationshipException luiId equals relatedLuiId
     * @throws DataValidationErrorException  One or more values invalid
     *                                       for this operation
     * @throws DoesNotExistException         luiId, relatedLuiId,
     *                                       luLuRelationTypeKey not found
     * @throws InvalidParameterException     invalid luiIds,
     *                                       luLuRelationTypeKey, luiLuiRelationInfo
     * @throws MissingParameterException     missing luiIds,
     *                                       luLuRelationTypeKey, luiLuiRelationInfo
     * @throws OperationFailedException      unable to complete request
     * @throws PermissionDeniedException     authorization failure
     */

    public LuiLuiRelationInfo createLuiLuiRelation(@WebParam(name = "luiId") String luiId,
      @WebParam(name = "relatedLuiId") String relatedLuiId,
      @WebParam(name = "luLuRelationTypeKey") String luLuRelationTypeKey,
      @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo,
      @WebParam(name = "context") ContextInfo context)
      throws AlreadyExistsException,
      CircularRelationshipException,
      DataValidationErrorException,
      DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException;


    /**
     * Updates a relationship between two LUIs.
     *
     * @param luiLuiRelationId   identifier of the LUI to LUI relation to update
     * @param luiLuiRelationInfo changed information about the
     *                           relationship between the two LUIs
     * @param context            Context information containing the principalId
     *                           and locale information about the caller of service
     *                           operation
     * @return the update LUI to LUI relation information
     * @throws DataValidationErrorException One or more values invalid
     *                                      for this operation
     * @throws DoesNotExistException        luiLuiRelation not found
     * @throws InvalidParameterException    invalid luiLuiRelationId,
     *                                      luiLuiRelationInfo
     * @throws MissingParameterException    missing luiLuiRelationId,
     *                                      luiLuiRelationInfo
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws VersionMismatchException     The action was attempted on an
     *                                      out of date version.
     */

    public LuiLuiRelationInfo updateLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId,
      @WebParam(name = "luiLuiRelationInfo") LuiLuiRelationInfo luiLuiRelationInfo,
      @WebParam(name = "context") ContextInfo context)
      throws DataValidationErrorException,
      DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException,
      VersionMismatchException;


    /**
     * Deletes a relationship between two LUIs.
     *
     * @param luiLuiRelationId identifier of the LUI to LUI relation
     *                         to delete
     * @param context          Context information containing the principalId
     *                         and locale information about the caller of service
     *                         operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException     luiLuiRelation not found
     * @throws InvalidParameterException invalid luiLuiRelationId
     * @throws MissingParameterException missing luiLuiRelationId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */

    public StatusInfo deleteLuiLuiRelation(@WebParam(name = "luiLuiRelationId") String luiLuiRelationId,
      @WebParam(name = "context") ContextInfo context)
      throws DoesNotExistException,
      InvalidParameterException,
      MissingParameterException,
      OperationFailedException,
      PermissionDeniedException;
}
