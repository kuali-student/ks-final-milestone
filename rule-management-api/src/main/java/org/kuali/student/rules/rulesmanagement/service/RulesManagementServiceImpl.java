package org.kuali.student.rules.rulesmanagement.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDeterminationStructureDTO;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleAnchorDTO;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulesmanagement.dto.StatusDTO;
@WebService(endpointInterface = "org.kuali.student.rules.rulesmanagement.service.RulesManagementService", 
        serviceName = "RulesManagementService", 
        portName = "RulesManagementService", 
        targetNamespace = "http://student.kuali.org/wsdl/brms/RulesManagement")
public class RulesManagementServiceImpl implements RulesManagementService {

    @Override
    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusDTO deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AgendaInfoDTO fetchAgendaInfo(String agendaTypeKey, AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public AgendaInfoDeterminationStructureDTO fetchAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String fetchBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public BusinessRuleInfoDTO fetchBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<BusinessRuleInfoDTO> fetchBusinessRuleInfoList(List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findAgendaTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findAnchorTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findBusinessRuleTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
