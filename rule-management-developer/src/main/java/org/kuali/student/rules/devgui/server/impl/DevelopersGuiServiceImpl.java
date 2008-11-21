/**
 * 
 */
package org.kuali.student.rules.devgui.server.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImpl implements DevelopersGuiService {

    RuleManagementService ruleManagementService;
    FactFinderService factFinderService;
    
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
    
    
    /******************************************************************************************************************
     * 
     *                                                     BUSINESS RULES 
     *
     *******************************************************************************************************************/    
     
    
	private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }       
    
    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) {

        String new_rule_id = null;

        //TODO temporary fix
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("");
     
        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        brInfoDTO.setBusinessRuleId("111111");
        brInfoDTO.setName("Test Rule Name 1111");
        brInfoDTO.setDescription("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setBusinessRuleTypeKey(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue("TEST");
        brInfoDTO.setStatus(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

		Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
		brInfoDTO.setEffectiveStartTime(effectiveStartTime);
		brInfoDTO.setEffectiveEndTime(effectiveEndTime);
        
        try {
            new_rule_id = ruleManagementService.createBusinessRule(brInfoDTO);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getBusinessRuleId(), ex); // TODO
        }
        return new_rule_id;
    }

    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) {
        StatusDTO rule_update_status = null;

        //TODO temporary fix
        businessRuleInfo.setEffectiveStartTime(new Date());
        businessRuleInfo.setEffectiveEndTime(new Date());
        businessRuleInfo.setDescription("Test Description");
        businessRuleInfo.setName("Test Name");
        
        try {
            rule_update_status = ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getBusinessRuleId(), ex); // TODO
        }
        return rule_update_status;
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

    public BusinessRuleTypeDTO fetchBusinessRuleType(String ruleTypeKey, String anchorTypeKey) {

        BusinessRuleTypeDTO businessRuleType;

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
    public List<RuleTypesHierarchyInfo> findRuleTypesHierarchyInfo() {
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
    public List<RulesHierarchyInfo> findRulesHierarchyInfo() {
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
                System.out.println("DEBUG findRulesHierarchyInfo(): " + agendaTypeKey);
                businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule types", ex); // TODO
            }

            // TODO show 'empty' node in the rules tree if none exist?
            if (businessRuleTypes == null) {
                rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, "", "", "", ""));
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
                    rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, "", "", ""));
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

                    rulesInfo.add(createHierarchyInfoObject(agendaTypeKey, businessRuleTypeKey, businessRuleId, businessRule.getName(), businessRule.getAnchorValue()));
                }
            }

            System.out.println("DEBUG: rule info:" + rulesInfo.toString());
        } // next agenda type

        System.out.println("Finished loading rule info");
        return rulesInfo;
    }

    private RulesHierarchyInfo createHierarchyInfoObject(String agendaType, String businessRuleType, String ruleId, String ruleName, String anchor) {
        RulesHierarchyInfo ruleInfo = new RulesHierarchyInfo();

        ruleInfo.setAgendaType(agendaType);
        ruleInfo.setBusinessRuleType(businessRuleType);
        ruleInfo.setBusinessRuleId(ruleId);
        ruleInfo.setBusinessRuleName(ruleName);
        ruleInfo.setAnchor(anchor);

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
}