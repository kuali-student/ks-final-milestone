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
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;

@WebService(name = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleManagementService {

    /* Setup */
    /**
     * Retrieves the list of Agenda types
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getAgendaTypes() throws OperationFailedException;

    /**
     * Retrieves the list of business rule types
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getBusinessRuleTypes() throws OperationFailedException;

    /**
     * Retrieves the list of business rule types associated with a given agenda type
     * 
     * @param agendaTypeKey
     * @return list of business rule types
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getBusinessRuleTypesByAgendaType(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of anchor types known by this service
     * 
     * @return list of anchor types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getAnchorTypes() throws OperationFailedException;

    /* Fetch */

    /**
     * Retrieves the agenda info determination structure
     * 
     * @param agendaTypeKey
     * @return Map of keys that will uniquely identify the agenda info along with agenda type key
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public AgendaDeterminationInfo getAgendaInfoDeterminationStructure(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the agenda info for a given agenda type key and agenda info determination structure
     * 
     * @param agendaTypeKey
     * @param agendaInfoDeterminationStructure
     * @return Map of keys that will uniquely identify the agenda info along with agenda type key
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public AgendaInfo getAgendaInfo(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey, @WebParam(name = "agendaDeterminationInfo")
    AgendaDeterminationInfo agendaDeterminationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method retieves the business rule type for given type key and anchor type
     * 
     * @param buisnessRuleTypeKey
     * @param AnchorTypeKey
     * @return
     */
    @WebMethod
    public BusinessRuleTypeInfo getBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method retrieves a list of business rules associated with a given business rule type and anchor
     * 
     * @param ruleAnchor
     * @return
     * @throws DoesNotExistException
     * @throws InvalidApplicationException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public List<BusinessRuleInfo> getBusinessRuleByAnchor(BusinessRuleAnchorInfo ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of business rules associated with a given list of business rule type and anchor
     * 
     * @param list
     *            of business rule anchor info
     * @return list of business rule info
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public List<BusinessRuleInfo> getBusinessRuleByAnchorList(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of anchor values associated with a given anchor type key
     * 
     * @param anchor
     *            type key
     * @return list of anchor values
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getAnchorsByAnchorType(@WebParam(name = "anchorTypeKey")
    String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of business rules ids associated with a given business rule type
     * 
     * @param of
     *            business rule type key
     * @return list of business rule ids
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> getBusinessRuleIdsByBusinessRuleType(@WebParam(name = "businessRuleTypeKey")
    String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the business rule associated with a given business rule Id. This method will not populate the structure
     * information of the business rule
     * 
     * @param business
     *            rule Id
     * @return business rule info
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public BusinessRuleInfo getBusinessRuleInfo(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves all the details of the business rule associated with a given business rule Id. This method will populate the
     * structure information of the business rule
     * 
     * @param business
     *            rule Id
     * @return business rule info
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public BusinessRuleInfo getDetailedBusinessRuleInfo(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves all the english language representation of the business rule associated with a given business rule Id.
     * 
     * @param business
     *            rule Id
     * @return business rule info
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    @WebMethod
    public String getBusinessRuleEnglish(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /* Maintenance */
    /**
     * Creates a business rule record
     * 
     * @param businessRuleInfo
     * @return identifier for the newly created business rule
     * @throws AlreadyExistsException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @WebMethod
    public BusinessRuleInfo createBusinessRule(@WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a business rule record
     * 
     * @param businessRuleId
     * @param businessRuleInfo
     * @return status of the operation
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @WebMethod
    public BusinessRuleInfo updateBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId, @WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * 
     * This method should be used to update the state 
     * 
     * @param businessRuleId
     * @param brState
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    @WebMethod
    public BusinessRuleInfo updateBusinessRuleState(@WebParam(name = "businessRuleId")
    String businessRuleId, @WebParam(name = "brState")
    String brState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    
    /**
     * Deletes a business rule record
     * 
     * @param businessRuleId
     * @return status of the operation
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DependentObjectsExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @WebMethod
    public StatusInfo deleteBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * This method creates a new version of an existing rule
     * 
     * @param businessRuleInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DependentObjectsExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @WebMethod
    public BusinessRuleInfo createNewVersion(@WebParam(name = "businessRuleInfo")
    BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;;
}
