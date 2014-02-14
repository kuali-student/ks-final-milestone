/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.rule.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.core.rule.dto.RuleInfo;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import javax.jws.WebParam;

/**
 * Rule Service provides access to the definition of one or more rules, i.e., the description of a rule in a BRMS (business rule
 * management system).
 */
@WebService(name = "RuleService", targetNamespace = RuleServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleService {

    /**
     * Retrieves a Rule by a Rule ID
     *
     * @param ruleId the Rule ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the Rule
     * @throws DoesNotExistException ruleId not found
     * @throws InvalidParameterException invalid ruleId or contextInfo
     * @throws MissingParameterException ruleId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public RuleInfo getRule(@WebParam(name = "ruleId") String ruleId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of rules by a list of Rule IDs
     *
     * @param ruleIds a list of Rule IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Rules
     * @throws DoesNotExistException ruleId not found
     * @throws InvalidParameterException invalid ruleId or contextInfo
     * @throws MissingParameterException ruleId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RuleInfo> getRulesByIds(@WebParam(name = "ruleIds") List<String> ruleIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Rule Ids by rule type.
     *
     * @param ruleTypeKey the ruleTypeKey to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Rule IDs
     * @throws DoesNotExistException ruleId not found
     * @throws InvalidParameterException invalid ruleId or contextInfo
     * @throws MissingParameterException ruleId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getRuleIdsByType(@WebParam(name = "ruleTypeKey") String ruleTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Rule Ids based on the criteria and returns a list of Rule identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Rule IDs
     * @throws DoesNotExistException ruleId not found
     * @throws InvalidParameterException invalid ruleId or contextInfo
     * @throws MissingParameterException ruleId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForRuleIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for rules based on the criteria and returns a list of rules which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Rules
     * @throws DoesNotExistException ruleId not found
     * @throws InvalidParameterException invalid ruleId or contextInfo
     * @throws MissingParameterException ruleId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<RuleInfo> searchForRules(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Rule. If an identifier is present for the Rule and a record is found for that identifier, the validation checks
     * if the Rule can be updated to the new values. If an identifier is not present or a record does not exist, the validation
     * checks if the rule with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param ruleTypeKey the identifier for the rule type to be validated
     * @param ruleInfo the rule to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException ruleInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, ruleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateRule(String validationTypeKey,
            @WebParam(name = "ruleTypeKey") String ruleTypeKey,
            @WebParam(name = "ruleInfo") RuleInfo ruleInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Rule. The Rule Id, Type, and Meta information may not be set in the supplied data.
     *
     * @param ruleTypeKey the identifier for the rule type to be validated
     * @param ruleInfo the rule to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new Rule
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException ruleInfo or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, ruleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public RuleInfo createRule(@WebParam(name = "ruleTypeKey") String ruleTypeKey, 
            @WebParam(name = "ruleInfo") RuleInfo ruleInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Rule. The Rule Id, Type, and Meta information may not be changed.
     *
     * @param ruleId the identifier for the Rule to be updated
     * @param ruleInfo the rule to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated Rule
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException ruleId is not found
     * @throws InvalidParameterException ruleId, ruleInfo or contextInfo is not valid
     * @throws MissingParameterException ruleId, ruleInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this rule since you fetched the version you are updating.
     */
    public RuleInfo updateRule(@WebParam(name = "ruleId") String ruleId, 
            @WebParam(name = "ruleInfo") RuleInfo ruleInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Rule.
     *
     * @param ruleId the identifier for the rule to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException ruleInfo is not found
     * @throws InvalidParameterException ruleInfo or contextInfo is not valid
     * @throws MissingParameterException ruleInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteRule(@WebParam(name = "ruleId") String ruleId, 
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
