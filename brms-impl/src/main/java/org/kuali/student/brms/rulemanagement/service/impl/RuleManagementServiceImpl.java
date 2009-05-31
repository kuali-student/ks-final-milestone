package org.kuali.student.brms.rulemanagement.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleAnchorInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.runtime.RuleManagement;
import org.kuali.student.brms.rulemanagement.service.RuleManagementService;
import org.kuali.student.brms.translators.drools.RuleSetTranslatorDroolsImpl;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.brms.rulemanagement.service.RuleManagementService", serviceName = "RuleManagementService", portName = "RuleManagementService", targetNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@Transactional
public class RuleManagementServiceImpl implements RuleManagementService {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);

    private RuleManagement ruleManagement;

    public void setRuleManagement(RuleManagement ruleManagement) {
		this.ruleManagement = ruleManagement;
	}

	@Override
    public List<String> getAgendaTypes() throws OperationFailedException {
		return this.ruleManagement.getAgendaTypes();
    }

    @Override
    public List<String> getBusinessRuleTypes() throws OperationFailedException {
    	return this.ruleManagement.getBusinessRuleTypes();
    }

    @Override
    public List<String> getBusinessRuleTypesByAgendaType(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleTypesByAgendaType(agendaTypeKey);
    }

    @Override
    public List<String> getAnchorTypes() throws OperationFailedException {
    	return this.ruleManagement.getAnchorTypes();
    }

    @Override
    public AgendaDeterminationInfo getAgendaInfoDeterminationStructure(String agendaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getAgendaInfoDeterminationStructure(agendaTypeKey);
    }

    @Override
    public AgendaInfo getAgendaInfo(String agendaTypeKey, AgendaDeterminationInfo agendaDeterminationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getAgendaInfo(agendaTypeKey, agendaDeterminationInfo);
    }

    @Override
    public BusinessRuleTypeInfo getBusinessRuleType(String businessRuleTypeKey, String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleType(businessRuleTypeKey, anchorTypeKey);
    }

    @Override
    public List<BusinessRuleInfo> getBusinessRuleByAnchorList(List<BusinessRuleAnchorInfo> businessRuleAnchorInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleByAnchorList(businessRuleAnchorInfoList);
    }

    @Override
    public List<BusinessRuleInfo> getBusinessRuleByAnchor(BusinessRuleAnchorInfo ruleAnchor) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleByAnchor(ruleAnchor);
    }

    @Override
    public List<String> getAnchorsByAnchorType(String anchorTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getAnchorsByAnchorType(anchorTypeKey);
    }

    @Override
    public List<String> getBusinessRuleIdsByBusinessRuleType(String businessRuleTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleIdsByBusinessRuleType(businessRuleTypeKey);
    }

    @Override
    public BusinessRuleInfo getBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleInfo(businessRuleId);
    }

    @Override
    public BusinessRuleInfo getDetailedBusinessRuleInfo(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getDetailedBusinessRuleInfo(businessRuleId);
    }

    @Override
    public String getBusinessRuleEnglish(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	return this.ruleManagement.getBusinessRuleEnglish(businessRuleId);
    }

    @Override
    public BusinessRuleInfo createBusinessRule(BusinessRuleInfo businessRuleInfo) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.ruleManagement.createBusinessRule(businessRuleInfo);
    }

    @Override
    public BusinessRuleInfo updateBusinessRule(String businessRuleId, BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
    	return this.ruleManagement.updateBusinessRule(businessRuleId, businessRuleInfo);
    }

    @Override
    public BusinessRuleInfo updateBusinessRuleState(String businessRuleId, String brState) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return this.ruleManagement.updateBusinessRuleState(businessRuleId, brState);
    }

    @Override
    public StatusInfo deleteBusinessRule(String businessRuleId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
    	return this.ruleManagement.deleteBusinessRule(businessRuleId);
    }

    @Override
    public BusinessRuleInfo createNewVersion(BusinessRuleInfo businessRuleInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DataValidationErrorException, OperationFailedException, PermissionDeniedException {
    	return this.ruleManagement.createNewVersion(businessRuleInfo);
    }
}
