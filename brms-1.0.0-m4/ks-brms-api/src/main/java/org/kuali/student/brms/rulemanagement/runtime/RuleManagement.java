package org.kuali.student.brms.rulemanagement.runtime;

import java.util.List;

import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;

public interface RuleManagement {

    /* Setup */
    /**
     * Retrieves the list of agenda types known by this service
     * 
     * @return list of agenda type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    public List<String> getAgendaTypes() throws OperationFailedException;

    /**
     * Retrieves the list of business rule types known by this service
     * 
     * @return list of business rule type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    public List<String> getBusinessRuleTypes() throws OperationFailedException;

    /**
     * Retrieves business rules types for the specified agenda type
     * 
     * @param agendaTypeKey
     *            type of agenda
     * @return list of business rule type keys
     * @throws DoesNotExistException
     *             AgendaType not found
     * @throws InvalidParameterException
     *             Invalid agendaType
     * @throws MissingParameterException
     *             Missing agendaType
     * @throws OperationFailedException
     *             Unable to complete request
     */
    public List<String> getBusinessRuleTypesByAgendaType(String agendaTypeKey) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of anchor types known by this service
     * 
     * @return list of anchor type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    public List<String> getAnchorTypes() throws OperationFailedException;

    /**
     * Retrieves the information needed to determine the agendaInfo.
     * 
     * @param agendaTypeKey
     * @return identifiers to be able to determine the agendaInfo (list of business rule types, anchor types, orchestration)
     *         for a given agendaTypeKey
     * @throws DoesNotExistException
     *             agendaContext not found
     * @throws InvalidParameterException
     *             invalid agendaContext
     * @throws MissingParameterException
     *             missing agendaContext
     * @throws OperationFailedException
     *             unable to complete request
     */
    public AgendaDeterminationInfo getAgendaInfoDeterminationStructure(String agendaTypeKey) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the information needed for the specified agenda type and agenda info determination structure. An agenda Info
     * contains a list of business rule types, their anchor types and orchestration of business rule types.
     * 
     * @param agendaTypeKey
     *            the agenda type
     * @param agendaInfoDeterminationStructure
     *            list of zero or more keys that are required to determine a specific agendaInfo for with a given
     *            agendaTypeKey
     * @return information about an agenda including output state set, list of business rules types, their anchor type keys,
     *         orchestration (XML with order, decision points, exit points)
     * @throws DoesNotExistException
     *             agendaType not found
     * @throws InvalidParameterException
     *             agendaType not found
     * @throws MissingParameterException
     *             missing agendaType
     * @throws OperationFailedException
     *             unable to complete request
     */
    public AgendaInfo getAgendaInfo(String agendaTypeKey, AgendaDeterminationInfo agendaDeterminationInfo) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the information associated with a business rule type (business rule type key, anchor type key, fact
     * structure list available for the rules of this type)
     * 
     * @param buisnessRuleTypeKey
     *            the business rule type key
     * @param AnchorTypeKey
     *            the anchor type key
     * @return information about the business rule type
     * @throws DoesNotExistException
     *             agendaType not found
     * @throws InvalidParameterException
     *             agendaType not found
     * @throws MissingParameterException
     *             missing agendaType
     * @throws OperationFailedException
     *             unable to complete request
     */
    public BusinessRuleTypeInfo getBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of specific business rules that belong to the given business rule type and anchor.
     * 
     * @param ruleAnchor
     *            business rule type, anchor type key and anchor value.
     * @return information about business rules including list of business rules types, their anchor type keys values, and
     *         fact structures (with the definition keys filled out for the business rule)
     * @throws DoesNotExistException
     *             business rule not found
     * @throws InvalidApplicationException
     *             invalid ruleAnchor
     * @throws MissingParameterException
     *             missing ruleAnchor
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<BusinessRuleInfo> getBusinessRuleByAnchor(BusinessRuleAnchorInfo ruleAnchor) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves a list of specific business rules that belong to the given business rule type and anchor.
     * 
     * @param list
     *            of business rule types, anchor type keys and anchor values
     * @return information about business rules including list of business rules types, their anchor type keys values, and
     *         fact structures (with the definition keys filled out for the business rule)
     * @throws DoesNotExistException
     *             business rule not found
     * @throws InvalidApplicationException
     *             invalid ruleAnchor
     * @throws MissingParameterException
     *             missing ruleAnchor
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<BusinessRuleInfo> getBusinessRuleByAnchorList(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves all identifiers for anchors of the specified type
     * 
     * @param anchorTypeKey
     *            type of anchor
     * @return list of the anchors matching the specified type
     * @throws DoesNotExistException
     *             anchorType not found
     * @throws InvalidParameterException
     *             invalid anchorType
     * @throws MissingParameterException
     *             missing anchorType
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<String> getAnchorsByAnchorType(String anchorTypeKey) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves all identifiers for business rules of the specified type
     * 
     * @param businessRuleTypeKey
     *            type of business rule
     * @return list of identifiers for business rules matching the specified type
     * @throws DoesNotExistException
     *             businessRuleType not found
     * @throws InvalidParameterException
     *             invalid businessRuleType
     * @throws MissingParameterException
     *             missing businessRuleType
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<String> getBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information about the business rule. Rule elements are not present in the response
     * 
     * @param businessRuleId
     *            the business rule id
     * @return information needed to execute the business rule
     * @throws DoesNotExistException
     *             businessRuleId not found
     * @throws InvalidParameterException
     *             invalid businessId
     * @throws MissingParameterException
     *             missing businessRuleId
     * @throws OperationFailedException
     *             unable to complete request
     */
    public BusinessRuleInfo getBusinessRuleInfo(String businessRuleId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves detailed information including the rule elements composing the business rules
     * 
     * @param businessRuleId
     *            the business rule id
     * @return information needed by the GUI to render the business rule and update it.
     * @throws DoesNotExistException
     *             businessRuleId not found
     * @throws InvalidParameterException
     *             invalid businessId
     * @throws MissingParameterException
     *             missing businessRuleId
     * @throws OperationFailedException
     *             unable to complete request
     */
    public BusinessRuleInfo getDetailedBusinessRuleInfo(String businessRuleId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves english translation of a business rule
     * 
     * @param businessRuleId
     *            the business rule id
     * @return english translation of a business rule
     * @throws DoesNotExistException
     *             businessRuleId not found
     * @throws InvalidParameterException
     *             invalid businessId
     * @throws MissingParameterException
     *             missing businessRuleId
     * @throws OperationFailedException
     *             unable to complete request
     */
    public String getBusinessRuleEnglish(String businessRuleId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a Business Rule of a given type
     * 
     * @param businessRuleInfo
     *            Information required to create the business rule
     * @return newly created business rule record
     * @throws AlreadyExistsException
     *             business rule already exists
     * @throws InvalidParameterException
     *             invalid businessRuleType, businessRuleInfo
     * @throws MissingParameterException
     *             missing businessRuleType, businessRuleInfo
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public BusinessRuleInfo createBusinessRule(BusinessRuleInfo businessRuleInfo) 
    	throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a business rule record
     * 
     * @param businessRuleId
     *            identifier for business rule to be updated
     * @param businessRuleInfo
     *            information needed to update a business rule record
     * @return updated business rule record
     * @throws DoesNotExistException
     *             businessRuleId not found
     * @throws InvalidParameterException
     *             invalid businessRuleId, businessRuleUpdateInfo
     * @throws MissingParameterException
     *             missing businessRuleId, businessRuleUpdateInfo
     * @throws ReadOnlyException
     *             attempted to update a read only attribute
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public BusinessRuleInfo updateBusinessRule(String businessRuleId, BusinessRuleInfo businessRuleInfo) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a business rule record's state. This is typically used to activate or retire a business rule
     * 
     * @param businessRuleId
     *            identifier for business rule
     * @param brState
     *            new state for the business rule. Value is expected to be constrained to those in the brState enumeration.
     * @return updated business rule
     * @throws DataValidationErrorException
     *             new state is not valid
     * @throws DoesNotExistException
     *             business rule does not exist
     * @throws InvalidParameterException
     *             invalid businessRuleId, brState
     * @throws MissingParameterException
     *             missing businessRuleId, brState
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public BusinessRuleInfo updateBusinessRuleState(String businessRuleId, String brState) 
    	throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a business rule record
     * 
     * @param businessRuleId
     *            identifier for business rule to be deleted
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException
     *             business rule does not exist
     * @throws InvalidParameterException
     *             invalid businessRuleId
     * @throws MissingParameterException
     *             missing businessRuleId
     * @throws DependentObjectsExistException
     *             One or more objects linked to this rule id
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteBusinessRule(String businessRuleId) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new version from the input business rule keeping the originalRuleId as that of the provided business rule.
     * The new version is created with DRAFT_IN_PRORGRESS state. New versions can be created only from ACTIVE or RETIRED
     * rules.
     * 
     * @param businessRuleInfo business rule
     * @return  new business rule cloned from the input with new businessRuleId
     * @throws DataValidationErrorException new state is not valid 
     * @throws DoesNotExistException business rule does not exist
     * @throws InvalidParameterException invalid businessRuleId, brState
     * @throws MissingParameterException missing businessRuleId, brState
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public BusinessRuleInfo createNewVersion(BusinessRuleInfo businessRuleInfo) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, OperationFailedException, PermissionDeniedException;;
}
