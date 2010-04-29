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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface DevelopersGuiServiceAsync {

	public void fetchFactType(String factTypeKey, AsyncCallback<FactTypeInfoDTO> asyncCallback);
	
	public void fetchFactTypeList(List<String> factTypeKeys, AsyncCallback<List<FactTypeInfoDTO>> asyncCallback);
	
    public void executeBusinessRuleTest(BusinessRuleInfoDTO businessRule, Map<String, String> definitionTimeFacts, Map<String, String> executionTimeFacts, AsyncCallback<ExecutionResultDTO> callback);	
	
    public void createBusinessRule(BusinessRuleInfoDTO businessRuleInfo, AsyncCallback<BusinessRuleInfoDTO> callback);

    public void updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo, AsyncCallback<BusinessRuleInfoDTO> callback);

    public void updateBusinessRuleState(String businessRuleId, String brState, AsyncCallback<BusinessRuleInfoDTO> callback);
    
    public void fetchDetailedBusinessRuleInfo(String ruleId, AsyncCallback<BusinessRuleInfoDTO> callback);

    public void fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey, AsyncCallback<BusinessRuleTypeInfoDTO> callback);

    public void testBusinessRule(String businessRuleId, AsyncCallback<String> callback);
    
    public void findAgendaTypes(AsyncCallback<List<String>> callback);
    
    public void findBusinessRuleTypesByAgendaType(String agendaTypeKey, AsyncCallback<List<String>> callback);
    
    public void fetchRulesHierarchyInfo(AsyncCallback<List<RulesHierarchyInfo>> callback);

    public void fetchRuleTypesHierarchyInfo(AsyncCallback<List<RuleTypesHierarchyInfo>> callback);    
}
