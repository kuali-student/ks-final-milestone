/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.population.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;


import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;

/**
 * Population Service Description and Assumptions.
 *
 * This service supports the management of people sets.
 *
 * Version: 1.0 (Dev)
 *
 * @Author tom
 * @Since Mon Nov 21 14:22:34 EDT 2011
 */

@WebService(name = "PopulationService", serviceName = "PopulationService", portName = "PopulationService", targetNamespace = PopulationServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface PopulationService {

    /** 
     * Tests if a Person is a member of a Population.
     *
     * @param personId a unique Id of a Person
     * @param populationKey a unique Id of a Population
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return true if the person is a member of the Population, 
     *         false otherwise
     * @throws DoesNotExistException populationKey not found
     * @throws InvalidParameterException invalid personId,
     *         populationKey, or contextInfo
     * @throws MissingParameterException missing personId,
     *         populationKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Boolean isMember(@WebParam(name = "personId") String personId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets a list of people Ids who qualify as a member of a
     * Population. This method is intended for auditing purposes in
     * setting up and testing Population rules.
     *
     * @param populationKey a unique Id of a Population
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Person Ids
     * @throws DoesNotExistException populationKey not found
     * @throws InvalidParameterException invalid populationKey or
     *         contextInfo
     * @throws MissingParameterException missing populationKey or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getMembers(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a Population.
     *
     * @param populationKey a unique Id of a Population
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a Population
     * @throws DoesNotExistException populationKey not found
     * @throws InvalidParameterException invalid populationKey or contextInfo
     * @throws MissingParameterException missing populationKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public PopulationInfo getPopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Populations corresponding to the given list
     * of Population ids.
     *
     * @param populationKeys list of Populationss to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Population ids of the given type
     * @throws DoesNotExistException an populationKey in list not found
     * @throws InvalidParameterException invalid populationKey or contextInfo
     * @throws MissingParameterException missing populationKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PopulationInfo> getPopulationsByIds(@WebParam(name = "populationKeys") List<String> populationKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Population ids of the specified type.
     *
     * @param populationTypeId a Population type to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Population ids
     * @throws InvalidParameterException invalid populationTypeId or
     *         contextInfo
     * @throws MissingParameterException missing populationTypeId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getPopulationKeysByType(@WebParam(name = "populationTypeId") String populationTypeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Populations in which the given
     * PopulationRule is applied.
     *
     * @param populationRuleId a PopulationRule Id
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of Populations
     * @throws InvalidParameterException invalid populationRuleId or
     *         contextInfo
     * @throws MissingParameterException missing populationRuleId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PopulationInfo> getPopulationsForPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Populations based on the criteria and returns a list
     * of Population identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of Population Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForPopulationKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Populations based on the criteria and returns a list of
     * Populations which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of Populations
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PopulationInfo> searchForPopulations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Population. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained sub-objects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the Population and a record is found for that
     * identifier, the validation checks if the Population can be shifted
     * to the new values. If a record cannot be found for the
     * identifier, it is assumed that the record does not exist and as
     * such, the checks performed will be much shallower, typically
     * mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the
     * standard validation as the caller provides the identifier in
     * the create statement instead of the server assigning an
     * identifier.
     *
     * @param validationTypeId the identifier of the extent of validation
     * @param populationInfo the Population information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeId not found
     * @throws InvalidParameterException invalid validationTypeId,
     *         populationInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         populationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validatePopulation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new Population.
     *
     * @param populationInfo the details of Population to be created
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the Population just created
     * @throws AlreadyExistsException the Population being created
     *         already exists
     * @throws DataValidationErrorException one or more values invalid
     *         for this operation
     * @throws InvalidParameterException invalid populationInfo or
     *         contextInfo
     * @throws MissingParameterException missing populationInfo or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public PopulationInfo createPopulation(@WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing Population.
     *
     * @param populationKey the Id of Population to be updated
     * @param populationInfo the details of updates to Population being updated
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the details of Population just updated
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException populationKey not found
     * @throws InvalidParameterException invalid populationKey,
     *         populationInfo, or contextInfo
     * @throws MissingParameterException missing populationKey,
     *         populationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an out 
     *         of date version.
     */
    public PopulationInfo updatePopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing Population.
     *
     * @param populationKey the Id of the Population to be deleted
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException populationKey not found
     * @throws InvalidParameterException invalid populationKey or contextInfo
     * @throws MissingParameterException missing populationKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deletePopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a PopulationRule.
     *
     * @param populationRuleId a unique Id of a PopulationRule
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a PopulationRule
     * @throws DoesNotExistException populationRuleId not found
     * @throws InvalidParameterException invalid populationRuleId or
     *         contextInfo
     * @throws MissingParameterException missing populationRuleId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public PopulationRuleInfo getPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of PopulationRules corresponding to the
     * given list of PopulationRule Ids.
     *
     * @param populationRuleIds list of PopulationRules to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of PopulationRule Ids of the given type
     * @throws DoesNotExistException an populationRuleId in list not found
     * @throws InvalidParameterException invalid populationRuleId or 
     *         contextInfo
     * @throws MissingParameterException missing populationRuleId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PopulationRuleInfo> getPopulationRulesByIds(@WebParam(name = "populationRuleIds") List<String> populationRuleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of PopulationRule Ids of the specified type.
     *
     * @param populationTypeKey a PopulationRule type to be retrieved
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a list of PopulationRule Ids
     * @throws InvalidParameterException invalid populationTypeKey or
     *         contextInfo
     * @throws MissingParameterException missing populationTypeKey or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getPopulationRuleIdsByType(@WebParam(name = "populationTypeKey") String populationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of PopulationRules in which the given
     * Population is related.
     *
     * @param populationid a Population id
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return a PopulationRules
     * @throws DoesNotExistException populationKey is not found
     * @throws InvalidParameterException invalid populationid or contextInfo
     * @throws MissingParameterException missing populationKey or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public PopulationRuleInfo getPopulationRuleForPopulation(@WebParam(name = "populationid") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for PopulationRules based on the criteria and
     * returns a list of PopulationRule identifiers which match the
     * search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of PopulationRule Ids
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForPopulationRuleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for PopulationRules based on the criteria and
     * returns a list of PopulationRules which match the search
     * criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return list of PopulationRules
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException missing criteria or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<PopulationRuleInfo> searchForPopulationRules(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a PopulationRule. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the PopulationRule and a record is
     * found for that identifier, the validation checks if the
     * PopulationRule can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting
     * the validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     *
     * @param validationTypeKey the identifier of the extent of validation
     * @param populationInfo the PopulationRule information to be tested
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey,
     *         populationInfo, or contextInfo
     * @throws MissingParameterException missing validationTypeKey,
     *         populationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ValidationResultInfo> validatePopulationRule(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationRuleInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a new PopulationRule.
     *
     * @param populationRuleInfo the details of PopulationRule to be created
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the PopulationRule just created
     * @throws AlreadyExistsException the PopulationRule being
     *         created already exists
     * @throws DataValidationErrorException one or more values invalid
     *         for this operation
     * @throws InvalidParameterException invalid populationInfo or
     *         contextInfo
     * @throws MissingParameterException missing populationInfo or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     */
    public PopulationRuleInfo createPopulationRule(@WebParam(name = "populationRuleInfo") PopulationRuleInfo populationRuleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /** 
     * Updates an existing PopulationRule.
     *
     * @param populationRuleId the Id of PopulationRule to be updated
     * @param populationInfo the details of updates to PopulationRule
     *        being updated
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return the details of PopulationRule just updated
     * @throws DataValidationErrorException One or more values invalid 
     *         for this operation
     * @throws DoesNotExistException populationRuleId not found
     * @throws InvalidParameterException invalid populationRuleId,
     *         populationInfo, or contextInfo
     * @throws MissingParameterException missing populationRuleId,
     *         populationInfo, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws VersionMismatchException The action was attempted on an out 
     *         of date version.
     */
    public PopulationRuleInfo updatePopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /** 
     * Deletes an existing PopulationRule.
     *
     * @param populationRuleId the Id of the PopulationRule to be deleted
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException populationRuleId not found
     * @throws InvalidParameterException invalid populationRuleId or
     *         contextInfo
     * @throws MissingParameterException missing populationRuleId or
     *         contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deletePopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Applies PopulationRule to a Population. If the Population
     * already has a rule, it is replaced with the specified one.
     *
     * @param populationRuleId a unique identifier for a PopulationRule
     * @param populationKey a unique identifier for a Population
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return status
     * @throws DoesNotExistException populationKey or
     *         populationRuleId not found
     * @throws InvalidParameterException invalid populationRuleId,
     *         populationKey, or contextInfo
     * @throws MissingParameterException missing populationRuleId,
     *         populationKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo applyPopulationRuleToPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes Population from a PopulationRule.
     *
     * @param populationRuleId a unique identifier for a PopulationRule
     * @param populationKey a unique identifier for a Population
     * @param contextInfo Context information containing the
     *        principalId and locale information about the caller of
     *        service operation
     * @return status
     * @throws DoesNotExistException populationRuleId or
     *         populationKey not found or unrelated
     * @throws InvalidParameterException invalid populationRuleId,
     *         populationKey, or contextInfo
     * @throws MissingParameterException missing populationRuleId,
     *         populationKey, or contextInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removePopulationRuleFromPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
