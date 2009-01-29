/**
 * 
 */
package org.kuali.student.rules.devgui.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.devgui.client.GuiUtil;
import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

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
 
    public FactTypeInfoDTO fetchFactType(String factTypeKey) {
    	FactTypeInfoDTO factTypeInfo;
    	try {
    		factTypeInfo = factFinderService.fetchFactType(factTypeKey);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to fetch Fact Type using Fact Type Key: " + factTypeKey, ex); // TODO
        }
        return factTypeInfo;
    }
    
    public List<FactTypeInfoDTO> fetchFactTypeList(List<String> factTypeKeys) {
    	List<FactTypeInfoDTO> factTypeInfoList = new ArrayList<FactTypeInfoDTO>();
    	
		for (String factTypeKey : factTypeKeys) {
			try {
				factTypeInfoList.add(factFinderService.fetchFactType(factTypeKey));
	        } catch (Exception ex) {
	            throw new RuntimeException("Unable to fetch Fact Type using Fact Type Key: " + factTypeKey, ex); // TODO
	        }
    	}
        return factTypeInfoList;
    }
    
    
    /******************************************************************************************************************
     * 
     *                                                     BUSINESS RULES 
     *
     *******************************************************************************************************************/                  

    public ExecutionResultDTO executeBusinessRuleTest(BusinessRuleInfoDTO businessRule, Map<String, String> facts) {
    	ExecutionResultDTO executionResult;
    	FactStructureDTO dynamicTestFacts = new FactStructureDTO();
    	dynamicTestFacts.setParamValueMap(new HashMap<String, String>());
    	String testFactValue = null;
    	
    	System.out.println("----> EXECUTING: rule id: " + businessRule.getId() + ", facts: " + facts);
    	
    	//populate all static and dynamic facts entered in the rule test tab
        for (RuleElementDTO elem : businessRule.getBusinessRuleElementList()) {
        	if (elem.getBusinessRuleElemnetTypeKey().equals(RuleElementType.PROPOSITION.getName()) == false) continue;
            System.out.println("EXECUTING: rule id: " + businessRule.getId() + ", element: " + elem.getName());        	
        	List<FactStructureDTO> factStructureList = elem.getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList();
        	for (FactStructureDTO fact : factStructureList) {        	     	            	    
        	    //System.out.println("key: " + fact.getResultColumnKeyTranslations());        	    
        		if (fact.isStaticFact()) {
        		    testFactValue = facts.get(fact.getFactStructureId());
        		    System.out.println("-- Added static fact: " + testFactValue);
        			fact.setStaticValue(testFactValue);        			
        		} else {
                    Map<String, String> map = fact.getParamValueMap();
                    for (String key : map.keySet()) {        		    
                        testFactValue = facts.get(key);
                        //map.remove(key);
                        dynamicTestFacts.getParamValueMap().put(key, testFactValue);
                        System.out.println("-- Added dynamic fact: " + key + " - " + testFactValue);
                    }
        		}                
        	}
        }            
        
        try {
            executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule, dynamicTestFacts.getParamValueMap());  //TODO dynamic facts
        } catch (Exception ex) {
            throw new RuntimeException("Unable to execute rule ID: " + businessRule.getId(), ex); // TODO
        }
        return executionResult;
    }     

    //returns business rule ID
	public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) {
    	
    	String newRuleId = null;
    	
        try {
            newRuleId = ruleManagementService.createBusinessRule(businessRuleInfo).getId();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getId(), ex); // TODO
        }
        return newRuleId;
    }

	
    public BusinessRuleInfoDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) throws Exception {
        
    	String ruleUpdateStatus = null;
                
        try {
            return ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
        } catch (Exception ex) {
        	throw new Exception("Unable to update business rule: " + ex.getMessage());
            //throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getId(), ex); // TODO
        }        
    }

    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String ruleId) {

        BusinessRuleInfoDTO businessRuleInfo;

        try {
            businessRuleInfo = ruleManagementService.fetchDetailedBusinessRuleInfo(ruleId);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get detailed business rule info", ex);
        }

        return businessRuleInfo;
    }

    public BusinessRuleTypeInfoDTO fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) {

        BusinessRuleTypeInfoDTO businessRuleType;

        try {
            businessRuleType = ruleManagementService.fetchBusinessRuleType(ruleTypeKey, anchorTypeKey);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get agenda types", ex);
        }

        return businessRuleType;
    }

    public String testBusinessRule(String businessRuleId) {
        return "TEST result"; // TODO
    }
    
    public List<String> findAgendaTypes() {
    	
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to find agenda types", ex); // TODO
        }
        
        return agendaTypes;
    }
     
    public List<String> findBusinessRuleTypesByAgendaType(String agendaTypeKey) {
    	
        List<String> businessRuleTypes = new ArrayList<String>();
        try {
        	businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to find Business Rule Types based on Agenda Key:" + agendaTypeKey, ex); // TODO
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
                rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, "", "", "", "", ""));
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
                    rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, "", "", "", ""));
                    System.out.println("DEBUG findRulesHierarchyInfo(): no business rules for Business Rule Type: " + businessRuleTypeKey);
                    continue;
                }

                // 5. go through individual business rules
                BusinessRuleInfoDTO businessRule;
                for (String businessRuleId : businessRuleIds) {

                    try {
                        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
                    } catch (Exception ex) {
                        throw new RuntimeException("Unable to get business rule hame", ex); // TODO
                    }

                    rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, businessRuleId, businessRule.getName(),
                    				businessRule.getAnchorValue(), businessRule.getState()));
                }
            }

            System.out.println("DEBUG: rule info:" + rulesInfo.toString());
        } // next agenda type

        System.out.println("Finished loading rule info");
        return rulesInfo;
    }

    private RulesHierarchyInfo createHierarchyInfoObject(String agendaType, String businessRuleType, String ruleId, String ruleName, String anchor, String status) {
        RulesHierarchyInfo ruleInfo = new RulesHierarchyInfo();

        ruleInfo.setAgendaType(agendaType);
        ruleInfo.setBusinessRuleType(businessRuleType);
        ruleInfo.setBusinessRuleId(ruleId);
        ruleInfo.setBusinessRuleDisplayName(ruleName);
        ruleInfo.setAnchor(anchor);
        ruleInfo.setStatus(status);

        return ruleInfo;
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