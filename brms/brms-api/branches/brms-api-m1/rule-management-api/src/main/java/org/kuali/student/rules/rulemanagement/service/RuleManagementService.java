package org.kuali.student.rules.rulemanagement.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.management.InvalidApplicationException;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;

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
    public List<String> findAgendaTypes() throws OperationFailedException;

    /**
     * Retrieves the list of business rule types
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> findBusinessRuleTypes() throws OperationFailedException;

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
    public List<String> findBusinessRuleTypesByAgendaType(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the list of anchor types known by this service
     * 
     * @return list of anchor types
     * @throws OperationFailedException
     */
    @WebMethod
    public List<String> findAnchorTypes() throws OperationFailedException;

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
    public AgendaInfoDeterminationStructureDTO fetchAgendaInfoDeterminationStructure(@WebParam(name = "agendaTypeKey")
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
    public AgendaInfoDTO fetchAgendaInfo(@WebParam(name = "agendaTypeKey")
    String agendaTypeKey, @WebParam(name = "agendaInfoDeterminationStructure")
    AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method retieves the business rule type for given type key and anchor type
     * 
     * @param buisnessRuleTypeKey
     * @param AnchorTypeKey
     * @return
     */
    @WebMethod
    public BusinessRuleTypeDTO fetchBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoByAnchor(BusinessRuleAnchorDTO ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoByAnchorList(@WebParam(name = "businessRuleAnchorInfoList")
    List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

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
    public List<String> findAnchorsByAnchorType(@WebParam(name = "anchorTypeKey")
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
    public List<String> findBusinessRuleIdsByBusinessRuleType(@WebParam(name = "businessRuleTypeKey")
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
    public BusinessRuleInfoDTO fetchBusinessRuleInfo(@WebParam(name = "businessRuleId")
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
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(@WebParam(name = "businessRuleId")
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
    public String fetchBusinessRuleEnglish(@WebParam(name = "businessRuleId")
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
    public String createBusinessRule(@WebParam(name = "businessRuleInfo")
    BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public StatusDTO updateBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId, @WebParam(name = "businessRuleInfo")
    BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
    public StatusDTO deleteBusinessRule(@WebParam(name = "businessRuleId")
    String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException;
}
