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
package org.kuali.student.brms.devgui.server.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.devgui.client.GregorianCalendar;
import org.kuali.student.brms.devgui.client.GuiUtil;
import org.kuali.student.brms.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.brms.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.brms.devgui.client.model.RulesVersionInfo;
import org.kuali.student.brms.devgui.client.service.DevelopersGuiService;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.factfinder.service.FactFinderService;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.brms.ruleexecution.service.RuleExecutionService;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.service.RuleManagementService;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImpl implements DevelopersGuiService {

    RuleManagementService ruleManagementService;
    FactFinderService factFinderService;
    RuleExecutionService ruleExecutionService;
    
    /******************************************************************************************************************
     * 
     *                                                     FACTS 
     *
     *******************************************************************************************************************/    
 
    public FactTypeInfo fetchFactType(String factTypeKey) throws Exception {
    	FactTypeInfo factTypeInfo;
    	try {
    		factTypeInfo = factFinderService.fetchFactType(factTypeKey);
        } catch (Exception ex) {
            throw new Exception("Unable to fetch Fact Type using Fact Type Key: " + factTypeKey, ex); // TODO
        }
        return factTypeInfo;
    }
    
    public List<FactTypeInfo> fetchFactTypeList(List<String> factTypeKeys) throws Exception {
    	List<FactTypeInfo> factTypeInfoList = new ArrayList<FactTypeInfo>();
    	
		for (String factTypeKey : factTypeKeys) {
			try {
				factTypeInfoList.add(factFinderService.fetchFactType(factTypeKey));
	        } catch (Exception ex) {
	            throw new Exception("Unable to fetch Fact Type using Fact Type Key: " + factTypeKey, ex); // TODO
	        }
    	}
        return factTypeInfoList;
    }
    
    
    /******************************************************************************************************************
     * 
     *                                                     BUSINESS RULES 
     *
     *******************************************************************************************************************/                  

    public ExecutionResultDTO executeBusinessRuleTest(BusinessRuleInfo businessRule, Map<String, String> definitionTimeFacts, Map<String, String> executionTimeFacts) throws Exception {
    	ExecutionResultDTO executionResult;
    	String testFactValue = null;
    	
    	System.out.println("----> EXECUTING: rule id: " + businessRule.getId() + ", definition time facts: " + definitionTimeFacts);
    	
    	//populate all static and dynamic facts entered in the rule test tab
        for (RuleElementInfo elem : businessRule.getBusinessRuleElementList()) {
        	if (elem.getBusinessRuleElemnetTypeKey().equals(RuleElementType.PROPOSITION.getName()) == false) continue;
            System.out.println("EXECUTING: rule id: " + businessRule.getId() + ", element: " + elem.getName());        	
        	List<FactStructureInfo> factStructureList = elem.getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList();
        	List<FactStructureInfo> factStructureListAdjusted = new ArrayList<FactStructureInfo>();
        	int ix = 0;
        	      //assumption is that at least one fact is type of criteria and has to be stored first in the list
        	boolean criteriaAdded = false;
        	for (FactStructureInfo fact : factStructureList) {        	     	            	        	    
        		if (fact.isStaticFact()) {        		    
        		    testFactValue = definitionTimeFacts.get(fact.getFactStructureId());
        		    System.out.println("-- Added static fact ("+fact.getFactStructureId()+"): " + testFactValue);
        			fact.setStaticValue(testFactValue);    
        			if (factStructureList.size() > 1) {
        			    if (criteriaAdded) {
        			        factStructureListAdjusted.add(ix++, fact);
        			    } else {
                            criteriaAdded = true;
                            factStructureListAdjusted.add(0, fact);
                            if (ix == 0) ix++;
        			    }    			    
        			}
        		} else {        		           		    
                    Map<String, String> map = fact.getParamValueMap();
                    Map<String, String> mapCopy = new HashMap<String, String>(map);
                    boolean foundExecutionKey = false;
                    for (String key : mapCopy.keySet()) {
                        System.out.println("-- Added dynamic fact ("+fact.getFactStructureId()+"):: " + key + " - " + definitionTimeFacts.get(key));
                        if (executionTimeFacts.containsKey(key)) {
                            foundExecutionKey = true;
                            continue;  //we don't populate execution time facts in the rule structure
                        }
                        map.remove(key);
                        map.put(key, definitionTimeFacts.get(key));
                    }
                    if (factStructureList.size() > 1) {
                        if (criteriaAdded) {
                            factStructureListAdjusted.add(ix++, fact);
                        } else {
                            if (foundExecutionKey) {
                                if (ix == 0) ix++;
                                factStructureListAdjusted.add(ix++, fact); 
                            } else {
                                criteriaAdded = true;
                                factStructureListAdjusted.add(0, fact);
                                if (ix == 0) ix++;
                            }
                        }
                    }
        		}                
        	}
        	if (factStructureList.size() > 1) {
        	    elem.getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().setFactStructureList(factStructureListAdjusted);
        	}
        }           
        
        System.out.println("-- Added execution time facts: " + executionTimeFacts);
        
        //in order for rule to be executed, we need to set the effective/expiration dates around current date
        businessRule.setEffectiveDate(new Date());
        businessRule.setExpirationDate(GuiUtil.OMEGA_DATE);
        
        try {
            executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule, executionTimeFacts);  //TODO dynamic facts
        } catch (Exception ex) {
            throw new Exception("Unable to execute rule ID: " + businessRule.getId(), ex); // TODO
        }
        return executionResult;
    }     

    //returns business rule ID
	public BusinessRuleInfo createBusinessRule(BusinessRuleInfo businessRuleInfo) throws Exception {    	
        try {
            return ruleManagementService.createBusinessRule(businessRuleInfo);
        } catch (Exception ex) {
            throw new Exception("Unable to create business rule ID: " + businessRuleInfo.getId(), ex); // TODO
        }
    }

	
    public BusinessRuleInfo updateBusinessRule(String businessRuleId, BusinessRuleInfo businessRuleInfo) throws Exception {                        
        try {
            return ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
        } catch (Exception ex) {
        	throw new Exception("Unable to update business rule ID: " + businessRuleInfo.getId() + "\n" + ex.getMessage());
        }        
    }

    public BusinessRuleInfo updateBusinessRuleState(String businessRuleId, String brState) throws Exception {
        BusinessRuleInfo businessRuleInfo;
        
        try {
            businessRuleInfo = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
        } catch (Exception ex) {
            throw new Exception("Unable to get detailed business rule info", ex);
        }

        //if the rule was not compiled (e.g. this is a rule loaded from data beans, then first update the rule before updating its state
        if ((businessRuleInfo.getCompiledId() == null) || (businessRuleInfo.getCompiledId().length() == 0)) {
            updateBusinessRule(businessRuleId, businessRuleInfo);
        }
        
        try {
            return ruleManagementService.updateBusinessRuleState(businessRuleId, brState);
        } catch (Exception ex) {
            throw new Exception("Unable to update state of business rule ID: " + businessRuleId + "\n" + ex.getMessage());
        }        
    }    
    
    public BusinessRuleInfo fetchDetailedBusinessRuleInfo(String ruleId) throws Exception {

        BusinessRuleInfo businessRuleInfo;

        try {
            businessRuleInfo = ruleManagementService.fetchDetailedBusinessRuleInfo(ruleId);
        } catch (Exception ex) {
            throw new Exception("Unable to get detailed business rule info", ex);
        }

        return businessRuleInfo;
    }

    public BusinessRuleTypeInfo fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) throws Exception {

        BusinessRuleTypeInfo businessRuleType;

        try {
            businessRuleType = ruleManagementService.fetchBusinessRuleType(ruleTypeKey, anchorTypeKey);
        } catch (Exception ex) {
            throw new Exception("Unable to get agenda types", ex);
        }

        return businessRuleType;
    }

    public String testBusinessRule(String businessRuleId) {
        return "TEST result"; // TODO
    }
    
    public List<String> findAgendaTypes() throws Exception {
    	
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new Exception("Unable to find agenda types", ex); // TODO
        }
        
        return agendaTypes;
    }
     
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) throws Exception {
    	
        List<String> businessRuleTypes = new ArrayList<String>();
        try {
        	businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
        } catch (Exception ex) {
            throw new Exception("Unable to find Business Rule Types based on Agenda Key:" + agendaTypeKey, ex); // TODO
        }
        
        return businessRuleTypes;
    }    
    
    
    /******************************************************************************************************************
     * 
     *                                                     LOADING TREES 
     *
     *******************************************************************************************************************/
    
    // populate Business Rule Types tree
    public List<RuleTypesHierarchyInfo> fetchRuleTypesHierarchyInfo() {
        List<RuleTypesHierarchyInfo> ruleTypesInfo = new ArrayList<RuleTypesHierarchyInfo>();

        // 1. retrieve agendas
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get agenda types", ex); // TODO
        }

        // TODO show 'empty' node in the rule types tree if none exist?
        if (agendaTypes == null) {
            throw new RuntimeException("Unable to get agenda types for Rule Types Tree.");
        }

        // 2. for each agenda type, retrieve business rule types
        for (String agendaTypeKey : agendaTypes) {

            // 3. retrieve business rule types
            List<String> businessRuleTypes = new ArrayList<String>();
            try {
                System.out.println("DEBUG findRuleTypesHierarchyInfo(): " + agendaTypeKey);
                businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule types", ex); // TODO
            }

            // TODO show 'empty' node in the rule types tree if none exist?
            if (businessRuleTypes == null) {
                ruleTypesInfo.add(createTypesHierarchyInfoObject(agendaTypeKey, "", ""));
                System.out.println("DEBUG findRuleTypesHierarchyInfo(): no business rule types for Agenda Type " + agendaTypeKey);
                continue;
            }

            for (String businessRuleTypeKey : businessRuleTypes) {
                ruleTypesInfo.add(createTypesHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, "KUALI_COURSE"));  //TODO: remove hard-coded value
            }
        }

        return ruleTypesInfo;
    }

    private RuleTypesHierarchyInfo createTypesHierarchyInfoObject(String agendaType, String businessRuleType, String anchorTypeKey) {
        RuleTypesHierarchyInfo ruleTypeInfo = new RuleTypesHierarchyInfo();

        ruleTypeInfo.setAgendaType(agendaType);
        ruleTypeInfo.setBusinessRuleTypeKey(businessRuleType);
        ruleTypeInfo.setAnchorTypeKey(anchorTypeKey);

        return ruleTypeInfo;
    }

    // populate rules tree
    public List<RulesHierarchyInfo> fetchRulesHierarchyInfo() {
    	System.out.println("loading rules hierarchy");
        List<RulesHierarchyInfo> rulesInfo = new ArrayList<RulesHierarchyInfo>();

        // 1. retrieve agendas
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get Agenda Types", ex); // TODO
        }

        // TODO show 'empty' node in the rule types tree if none exist?
        if (agendaTypes == null) {
            throw new RuntimeException("Received zero Agenda Types for Rules Tree.");
        }

        // 2. for each agenda type, retrieve business rule types and business rules
        for (String agendaTypeKey : agendaTypes) {

            // 3. retrieve business rule types
            List<String> businessRuleTypes = new ArrayList<String>();
            try {
                businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule types", ex); // TODO
            }

            // TODO show 'empty' node in the rules tree if none exist?
            if (businessRuleTypes == null) {
                rulesInfo.add(createRulesHierarchyInfoObject(agendaTypeKey, "", "", "", "", "", "", null, null));
                continue;
            }

            // 4. find individual business rules
            List<String> businessRuleIds = new ArrayList<String>();
            for (String businessRuleTypeKey : businessRuleTypes) {

                try {
                    businessRuleIds = ruleManagementService.findBusinessRuleIdsByBusinessRuleType(businessRuleTypeKey);
                } catch (Exception ex) {
                    throw new RuntimeException("Unable to get business rule ids", ex); // TODO
                }

                // TODO show 'empty' node in the rules tree if none exist?
                if (businessRuleIds == null) {
                    rulesInfo.add(createRulesHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, "", "", "", "", "", null, null));
                    System.out.println("DEBUG findRulesHierarchyInfo(): no business rules for Business Rule Type: " + businessRuleTypeKey);
                    continue;
                }

                // 5. go through individual business rules
                BusinessRuleInfo businessRule;
                Map<String, RulesHierarchyInfo> versionGroups = new HashMap<String, RulesHierarchyInfo>();
                for (String businessRuleId : businessRuleIds) {
                    RulesVersionInfo rulesVersionInfo = null;
                    RulesHierarchyInfo versionGroup = null;
                    try {
                        businessRule = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
                    } catch (Exception ex) {
                        throw new RuntimeException("Unable to get business rule hame", ex); // TODO
                    }
                    rulesVersionInfo = createRulesVersionInfoObject(agendaTypeKey, 
                            businessRuleTypeKey, businessRuleId, businessRule.getOriginalRuleId(),
                            businessRule.getName(), businessRule.getAnchorValue(), 
                            businessRule.getState(),
                            businessRule.getEffectiveDate(),
                            businessRule.getExpirationDate());
                    versionGroup = versionGroups.get(rulesVersionInfo.getBusinessRuleOriginalId());
                    if (versionGroup == null) {
                        versionGroup = new RulesHierarchyInfo();
                    }
                    versionGroup.add(rulesVersionInfo);
                    versionGroups.put(rulesVersionInfo.getBusinessRuleOriginalId(), versionGroup);
                }
                // Adds the groups into rulesInfo
                rulesInfo.addAll(versionGroups.values());
            }

            System.out.println("DEBUG: rule info:" + rulesInfo.toString());
        } // next agenda type

        System.out.println("Finished loading rule info");
        return rulesInfo;
    }
    
    private RulesHierarchyInfo createRulesHierarchyInfoObject(
            String agendaType, 
            String businessRuleType, 
            String ruleId,
            String originalRuleId,
            String ruleName, 
            String anchor, 
            String status,
            Date effectiveDate,
            Date expiryDate) {
        RulesHierarchyInfo rulesHierarchyInfo = new RulesHierarchyInfo();
        rulesHierarchyInfo.add(createRulesVersionInfoObject(
                agendaType, businessRuleType, ruleId, originalRuleId,
                ruleName, anchor, status, effectiveDate, expiryDate));
        return rulesHierarchyInfo;
    }

    private RulesVersionInfo createRulesVersionInfoObject(
            String agendaType, 
            String businessRuleType, 
            String ruleId,
            String originalRuleId,
            String ruleName, 
            String anchor, 
            String status,
            Date effectiveDate,
            Date expirationDate) {
        RulesVersionInfo rulesVersionInfo = new RulesVersionInfo();

        rulesVersionInfo.setAgendaType(agendaType);
        rulesVersionInfo.setBusinessRuleType(businessRuleType);
        rulesVersionInfo.setBusinessRuleId(ruleId);
        rulesVersionInfo.setBusinessRuleOriginalId(originalRuleId);
        rulesVersionInfo.setBusinessRuleDisplayName(ruleName);
        rulesVersionInfo.setAnchor(anchor);
        rulesVersionInfo.setStatus(status);
        rulesVersionInfo.setEffectiveDate(effectiveDate);
        rulesVersionInfo.setExpirationDateDate(expirationDate);

        return rulesVersionInfo;
    }    
    
    /**
     * @return the ruleManagementService
     */
    public final RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }
    
    /******************************************************************************************************************
     * 
     *                                                     VARIOUS 
     *
     *******************************************************************************************************************/        
    

    
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         

    /**
     * @param ruleManagementService
     *            the ruleManagementService to set
     */
    public final void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }
    
    public FactFinderService getFactFinderService() {
		return factFinderService;
	}

	public void setFactFinderService(FactFinderService factFinderService) {
		this.factFinderService = factFinderService;
	}    

    public RuleExecutionService getRuleExecutionService() {
		return ruleExecutionService;
	}


	public void setRuleExecutionService(RuleExecutionService ruleExecutionService) {
		this.ruleExecutionService = ruleExecutionService;
	}
}