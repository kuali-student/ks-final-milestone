/**
 * 
 */
package org.kuali.student.rules.devgui.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.devgui.server.impl.DevelopersGuiServiceImpl;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImplGWT extends RemoteServiceServlet implements DevelopersGuiService {

    private static final long serialVersionUID = 822326113643828855L;

    private final DevelopersGuiService serviceImpl = (DevelopersGuiService) BeanFactory.getInstance().getBean("developersGuiService");

    public FactTypeInfoDTO fetchFactType(String factTypeKey) throws Exception {
    	return serviceImpl.fetchFactType(factTypeKey);
    }
    
    public List<FactTypeInfoDTO> fetchFactTypeList(List<String> factTypeKeys) throws Exception {
    	return serviceImpl.fetchFactTypeList(factTypeKeys);
    }

    public ExecutionResultDTO executeBusinessRuleTest(BusinessRuleInfoDTO businessRule, Map<String, String> definitionTimeFacts, Map<String, String> executionTimeFacts) throws Exception {
    	return serviceImpl.executeBusinessRuleTest(businessRule, definitionTimeFacts, executionTimeFacts);
    }    
    
    public BusinessRuleInfoDTO createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws Exception {
        return serviceImpl.createBusinessRule(businessRuleInfo);
    }

    public BusinessRuleInfoDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws Exception {
        return serviceImpl.updateBusinessRule(businessRuleId, businessRuleInfo);
    }
    
    public BusinessRuleInfoDTO updateBusinessRuleState(String businessRuleId, String brState) throws Exception {
        return serviceImpl.updateBusinessRuleState(businessRuleId,  brState);
    }

    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String ruleId) throws Exception {
        return serviceImpl.fetchDetailedBusinessRuleInfo(ruleId);
    }

    public BusinessRuleTypeInfoDTO fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) throws Exception {
        return serviceImpl.fetchBusinessRuleType(ruleTypeKey, anchorTypeKey);
    }

    public String testBusinessRule(String businessRuleId) throws Exception {
        return serviceImpl.testBusinessRule(businessRuleId);
    }

    public List<String> findAgendaTypes() throws Exception {
    	return serviceImpl.findAgendaTypes();
    }
    
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws Exception {
    	return serviceImpl.findBusinessRuleTypesByAgendaType(agendaTypeKey);
    }
    
    public List<RulesHierarchyInfo> fetchRulesHierarchyInfo() {
        return serviceImpl.fetchRulesHierarchyInfo();
    }

    public List<RuleTypesHierarchyInfo> fetchRuleTypesHierarchyInfo() {
        return serviceImpl.fetchRuleTypesHierarchyInfo();
    }    
    
    /**
     * @return the serviceImpl
     */
    public DevelopersGuiService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl
     *            the serviceImpl to set
     */
    public void setServiceImpl(DevelopersGuiServiceImpl serviceImpl) {
    // this.serviceImpl = serviceImpl;
    }
}
