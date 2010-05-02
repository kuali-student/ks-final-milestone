/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

/**
 * 
 */
package org.kuali.student.brms.devgui.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.brms.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.brms.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.brms.devgui.client.service.DevelopersGuiService;
import org.kuali.student.brms.devgui.server.impl.DevelopersGuiServiceImpl;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImplGWT extends RemoteServiceServlet implements DevelopersGuiService {

    private static final long serialVersionUID = 822326113643828855L;

    private final DevelopersGuiService serviceImpl = (DevelopersGuiService) BeanFactory.getInstance().getBean("developersGuiService");

    public FactTypeInfo fetchFactType(String factTypeKey) throws Exception {
    	return serviceImpl.fetchFactType(factTypeKey);
    }
    
    public List<FactTypeInfo> fetchFactTypeList(List<String> factTypeKeys) throws Exception {
    	return serviceImpl.fetchFactTypeList(factTypeKeys);
    }

    public ExecutionResultDTO executeBusinessRuleTest(BusinessRuleInfo businessRule, Map<String, String> definitionTimeFacts, Map<String, String> executionTimeFacts) throws Exception {
    	return serviceImpl.executeBusinessRuleTest(businessRule, definitionTimeFacts, executionTimeFacts);
    }    
    
    public BusinessRuleInfo createBusinessRule(BusinessRuleInfo businessRuleInfo) throws Exception {
        return serviceImpl.createBusinessRule(businessRuleInfo);
    }

    public BusinessRuleInfo updateBusinessRule(String businessRuleId, BusinessRuleInfo businessRuleInfo) throws Exception {
        return serviceImpl.updateBusinessRule(businessRuleId, businessRuleInfo);
    }
    
    public BusinessRuleInfo updateBusinessRuleState(String businessRuleId, String brState) throws Exception {
        return serviceImpl.updateBusinessRuleState(businessRuleId,  brState);
    }

    public BusinessRuleInfo fetchDetailedBusinessRuleInfo(String ruleId) throws Exception {
        return serviceImpl.fetchDetailedBusinessRuleInfo(ruleId);
    }

    public BusinessRuleTypeInfo fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) throws Exception {
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
