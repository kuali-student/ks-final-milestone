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
package org.kuali.student.brms.devgui.client.service;

import java.util.List;
import java.util.Map;

import org.kuali.student.brms.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.brms.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResult;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author zzraly
 */
public interface DevelopersGuiService extends RemoteService {
    /**
     * URI for the log service servlet.
     */
    public static final String SERVICE_URI = "/DevelopersGuiService";

    /**
     * Utility class used to create instance of the asynchronous service interface.
     */
    public static class Util {
        /**
         * Creates an instance of the DevGuiServiceAsync interface using module base URL and the SERVICE_URI constant
         * 
         * @return instance of LogServiceAsync
         */
        public static DevelopersGuiServiceAsync getInstance() {
            DevelopersGuiServiceAsync instance = (DevelopersGuiServiceAsync) GWT.create(DevelopersGuiService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
    public FactTypeInfoDTO fetchFactType(String factTypeKey) throws Exception;

    public List<FactTypeInfoDTO> fetchFactTypeList(List<String> factTypeKeys) throws Exception;
    
    public ExecutionResultDTO executeBusinessRuleTest(BusinessRuleInfoDTO businessRule, Map<String, String> definitionTimeFacts, Map<String, String> executionTimeFacts) throws Exception;    
    
    public BusinessRuleInfoDTO createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) throws Exception;

    public BusinessRuleInfoDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws Exception;

    public BusinessRuleInfoDTO updateBusinessRuleState(String businessRuleId, String brState) throws Exception;
    
    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String ruleId) throws Exception;

    public BusinessRuleTypeInfoDTO fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) throws Exception;

    public String testBusinessRule(String businessRuleId) throws Exception;
    
    public List<String> findAgendaTypes() throws Exception;
    
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws Exception;
    
    public List<RulesHierarchyInfo> fetchRulesHierarchyInfo();

    public List<RuleTypesHierarchyInfo> fetchRuleTypesHierarchyInfo();    
}
