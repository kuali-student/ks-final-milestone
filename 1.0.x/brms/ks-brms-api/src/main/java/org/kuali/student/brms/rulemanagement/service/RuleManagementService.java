package org.kuali.student.brms.rulemanagement.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.management.InvalidApplicationException;

import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.runtime.RuleManagement;
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
/**
 * <b>IMPORTANT:</b> This service contract is currently under development. If you are planning to implement the Kuali Student System or parts thereof, <b>please do not consider this service to be final!</b> Consult this page for status before making any plans that rely on specific implementations of these services.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Description"></a>Description</h3>
 * 
 * <p>The Business Rules Management Service (BRMS) supports the maintenance, storage and retrieval of agendas and business rules. The BRMS also provides natural language expressions of rules and execution results. The specific execution of the rules within an agenda, executeAgenda, is currently implemented as an API.</p>
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-Assumptions"></a>Assumptions</h3>
 * 
 * <p>The design of this service considers the following assumptions:</p>
 * <ul>
 * 	<li>Authoring of rules will require additional operations TBD</li>
 * 	<li>Orchestration of rules within an agenda is TBD</li>
 * 
 * 	<li>Each business rule relates to a single executable rule.</li>
 * 	<li>Yield Value Functions (YVF) provide basic computations as part of rules logic.</li>
 * 	<li>Facts provide specific information including the procesing of data needed to evaluate a particular rule.</li>
 * 	<li>Business Rule Type has a constraint, represented by the YVF + the Fact that it consumes that ensures valid logic. For example, some values, such as financial aid awarded to date, may be summed while others, such as Visa types, cannot.</li>
 * </ul>
 * 
 * 
 * <h3><a name="KSDOC-ServiceDescriptions-ConceptsandTerminology"></a>Concepts and Terminology</h3>
 * 
 * <p>Agendas provide the container for specifying various business rules related to a particular process. For example, as part of checking to see if a student can enroll in a course, there may be course prerequisite, program GPA requirements, and fee checks.</p>
 * <ul>
 * 	<li>The <b>Agenda Type</b> (for example, StudentEnrollsInCourse) specifies the set of Business Rule Types that apply to a business process requiring a decision</li>
 * 	<li>The <b>Agenda Determination Structure</b> is the information required to determine the specific agenda, including anchors or other identifiers that can be used to identify the specific business rules that apply in a specific situation</li>
 * 	<li>An <b>Agenda</b>, which is determined dynamically, is the specific instance of an Agenda Type, based on the Business Rule Types for the Agenda Type and the specific anchors provided</li>
 * 
 * </ul>
 * 
 * 
 * <p>Business Rules represent the logic used as part of evaluation various business decisions. There can be multiple business rules associated with each agenda.</p>
 * <ul>
 * 	<li>The <b>Business Rule Type</b> is a categorization of specific rules that can be used as part of validating a process (for example, pre-requisites or fee calculations). The same Business Rule Type may apply to multiple agenda types. A Business Rule Type contains one anchor type (for example: course, program, Biology Major, graduate student).</li>
 * 	<li>A <b>Business Rule</b> is an individual rule of a single type defined in the BRMS (for example, the pre-requisites for Math 301). Rules can have multiple parts with AND-OR logic connectors and parenthesis. A Business Rule contains on anchor (for example, MATH 301).</li>
 * 
 * </ul>
 * 
 * 
 * <p>Anchors represent the entity that the business rule is attached to. The specification of the anchor as part of defining the business rule facilitates specific rule identification when a business process is evaluated. Each business rule has one and only one anchor.</p>
 * <ul>
 * 	<li>The <b>Anchor Type</b> is the type of entity to which the business rule is attached (course, program, Biology Major, graduate student). There is one anchor type for each business rule type</li>
 * 	<li>The <b>Anchor</b> for a rule is the entity type instance to which the rule is attached (for example, MATH 301).</li>
 * 
 * </ul>
 * 
 * 
 * <p>The Output from the execution of an agenda of business rules includes the result of each rule as well as an explanation of the execution results for the entire agenda.</p>
 * <ul>
 * 	<li><b>Output State</b> is the specific result of a rule execution (examples: True, False, 15, orange)</li>
 * 	<li><b>Output State Set</b> is the set of possible output states, (examples: true/false, number within a range, red/yellow/green) for each Agenda Type and Business Rule Type</li>
 * 	<li><b>Display Output Structure</b> provides the explanation of agenda processing and execution</li>
 * 
 * </ul>
 * 
 * 
 * <p>Facts refer to both the criteria or arguments of the rule and the facts that are compared as part of evaluating the rule. The actual retrieval of facts is managed through the Fact Finding Service.. The information stored in the BRMS is enough to retrieve the facts.</p>
 * <ul>
 * 	<li>The <b>Fact Type Key</b> is the type of entity to which the associated ID refers (for example, person, course)</li>
 * 	<li>The <b>Fact Structure Id</b> is the specific key needed by the Fact Finding Service to retrieve the data.</li>
 * 
 * </ul>

 * 
 * @author Kuali Student Team
 *
 */
@WebService(name = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleManagementService extends RuleManagement {

    /* Setup */
    /**
     * Retrieves the list of agenda types known by this service
     * 
     * @return list of agenda type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    @WebMethod
    public List<String> getAgendaTypes() throws OperationFailedException;

    /**
     * Retrieves the list of business rule types known by this service
     * 
     * @return list of business rule type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    @WebMethod
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
    @WebMethod
    public List<String> getBusinessRuleTypesByAgendaType(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of anchor types known by this service
     * 
     * @return list of anchor type keys
     * @throws OperationFailedException
     *             Unable to complete request
     */
    @WebMethod
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
    @WebMethod
    public AgendaDeterminationInfo getAgendaInfoDeterminationStructure(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public AgendaInfo getAgendaInfo(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey, @WebParam(name = "agendaDeterminationInfo")
    AgendaDeterminationInfo agendaDeterminationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public BusinessRuleTypeInfo getBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public List<BusinessRuleInfo> getBusinessRuleByAnchor(BusinessRuleAnchorInfo ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public List<BusinessRuleInfo> getBusinessRuleByAnchorList(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public List<String> getAnchorsByAnchorType(@WebParam(name = "anchorTypeKey")
    String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public List<String> getBusinessRuleIdsByBusinessRuleType(@WebParam(name = "businessRuleTypeKey")
    String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public BusinessRuleInfo getBusinessRuleInfo(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public BusinessRuleInfo getDetailedBusinessRuleInfo(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public String getBusinessRuleEnglish(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    @WebMethod
    public BusinessRuleInfo createBusinessRule(@WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    @WebMethod
    public BusinessRuleInfo updateBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId, @WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
    @WebMethod
    public BusinessRuleInfo updateBusinessRuleState(@WebParam(name = "businessRuleId")
    String businessRuleId, @WebParam(name = "brState")
    String brState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    @WebMethod
    public StatusInfo deleteBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

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
    @WebMethod
    public BusinessRuleInfo createNewVersion(@WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, OperationFailedException, PermissionDeniedException;;
}
