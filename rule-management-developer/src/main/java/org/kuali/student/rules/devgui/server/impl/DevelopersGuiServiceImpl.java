/**
 * 
 */
package org.kuali.student.rules.devgui.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.rules.devgui.client.model.RuleTypesHierarchyInfo;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.StatusDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImpl implements DevelopersGuiService {

    RuleManagementService ruleManagementService;

    public String createBusinessRule(BusinessRuleInfoDTO businessRuleInfo) {

        String new_rule_id = null;

        try {
            new_rule_id = ruleManagementService.createBusinessRule(businessRuleInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getBusinessRuleId(), ex); // TODO
        }
        return new_rule_id;
    }

    public StatusDTO updateBusinessRule(String businessRuleId, BusinessRuleInfoDTO businessRuleInfo) {
        StatusDTO rule_update_status = null;

        try {
            rule_update_status = ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create business rule ID: " + businessRuleInfo.getBusinessRuleId(), ex); // TODO
        }
        return rule_update_status;
    }

    // populate Business Rule Types tree
    public List<RuleTypesHierarchyInfo> findRuleTypesHierarchyInfo() {
        List<RuleTypesHierarchyInfo> ruleTypesInfo = new ArrayList<RuleTypesHierarchyInfo>();
        RuleTypesHierarchyInfo ruleTypeInfo;

        // 1. retrieve agendas
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get agenda types", ex); // TODO
        }

        // 2. for each agenda type, retrieve business rule types
        for (String agendaTypeKey : agendaTypes) {

            ruleTypeInfo = new RuleTypesHierarchyInfo();
            ruleTypeInfo.setAgendaType(agendaTypeKey);

            // 3. retrieve business rule types
            List<String> businessRuleTypes = new ArrayList<String>();
            try {
                // businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
                businessRuleTypes.add("Business Rule Type 123"); // WORKAROUND
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule types", ex); // TODO
            }

            List<String> businessRuleIds = new ArrayList<String>();
            for (String businessRuleTypeKey : businessRuleTypes) {

                ruleTypeInfo.setBusinessRuleTypeKey(businessRuleTypeKey);
                ruleTypesInfo.add(ruleTypeInfo);
            }
        }

        return ruleTypesInfo;
    }

    // populate rules tree
    public List<RulesHierarchyInfo> findRulesHierarchyInfo() {
        List<RulesHierarchyInfo> rulesInfo = new ArrayList<RulesHierarchyInfo>();
        RulesHierarchyInfo ruleInfoA;

        // 1. retrieve agendas
        List<String> agendaTypes = new ArrayList<String>();
        try {
            agendaTypes = ruleManagementService.findAgendaTypes();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get agenda types", ex); // TODO
        }

        // TODO: throw new RuntimeException("Test Exception", new Exception().initCause(new OutOfMemoryError()));

        // 2. for each agenda type, retrieve business rule types and business rules
        for (String agendaTypeKey : agendaTypes) {

            ruleInfoA = new RulesHierarchyInfo();
            ruleInfoA.setAgendaType(agendaTypeKey);

            // 3. retrieve business rule types
            List<String> businessRuleTypes = new ArrayList<String>();
            try {
                businessRuleTypes = ruleManagementService.findBusinessRuleTypesByAgendaType(agendaTypeKey);
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule types", ex); // TODO
            }

            // 4. find individual business rules
            /*
            List<String> businessRuleIds = new ArrayList<String>();
            for (String businessRuleTypeKey : businessRuleTypes) {

                ruleInfoA.setBusinessRuleType(businessRuleTypeKey);

                try {
                    businessRuleIds = ruleManagementService.findBusinessRuleIdsByBusinessRuleType(businessRuleTypeKey);
                } catch (Exception ex) {
                    throw new RuntimeException("Unable to get business rule ids", ex); // TODO
                }

                // 5. go through individual business rules
                String businessRuleName;
                for (String businessRuleId : businessRuleIds) {

                    try {
                        businessRuleName = ruleManagementService.fetchDetailBusinessRuleInfo(businessRuleId).getName();
                    } catch (Exception ex) {
                        throw new RuntimeException("Unable to get business rule hame", ex); // TODO
                    }

                    ruleInfoA.setBusinessRuleId(businessRuleId);
                    ruleInfoA.setBusinessRuleName(businessRuleName);
                }
            } */

            // WORKAROUND BEFORE KAMAL FINISHES HIS Database and services
            BusinessRuleInfoDTO businessRuleInfo;
            try {
                businessRuleInfo = ruleManagementService.fetchBusinessRuleInfo("123");
            } catch (Exception ex) {
                throw new RuntimeException("Unable to get business rule hame", ex); // TODO
            }

            ruleInfoA.setBusinessRuleId(businessRuleInfo.getBusinessRuleId());
            ruleInfoA.setBusinessRuleName(businessRuleInfo.getName());
            ruleInfoA.setBusinessRuleType(businessRuleInfo.getBusinessRuleTypeKey());
            ruleInfoA.setAnchor(businessRuleInfo.getAnchorValue());

            rulesInfo.add(ruleInfoA);
            System.out.println("DEBUG: rule info:" + rulesInfo.toString());
        } // next agenda type

        /*
                RulesHierarchyInfo ruleInfo1 = new RulesHierarchyInfo();
                ruleInfo1.setBusinessRuleId("1");
                ruleInfo1.setBusinessRuleName("CHEM 100 course prerequisites");
                ruleInfo1.setBusinessRuleType("kuali.coursePrerequisite");
                ruleInfo1.setAnchor("CHEM 100");
                ruleInfo1.setAgendaType("kuali.studentEnrollsInCourse");
                rulesInfo.add(ruleInfo1);

                RulesHierarchyInfo ruleInfo2 = new RulesHierarchyInfo();
                ruleInfo2.setBusinessRuleId("1");
                ruleInfo2.setBusinessRuleName("CHEM 100 course co-requisites");
                ruleInfo2.setBusinessRuleType("kuali.courseCorequisite");
                ruleInfo2.setAnchor("CHEM 100");
                ruleInfo2.setAgendaType("kuali.studentEnrollsInCourse");
                rulesInfo.add(ruleInfo2);

                RulesHierarchyInfo ruleInfo3 = new RulesHierarchyInfo();
                ruleInfo3.setBusinessRuleId("2");
                ruleInfo3.setBusinessRuleName("CHEM 200 course prerequisites");
                ruleInfo3.setBusinessRuleType("kuali.coursePrerequisite");
                ruleInfo3.setAnchor("CHEM 200");
                ruleInfo3.setAgendaType("kuali.studentEnrollsInCourse");
                rulesInfo.add(ruleInfo3);

                RulesHierarchyInfo ruleInfo4 = new RulesHierarchyInfo();
                ruleInfo4.setBusinessRuleId("3");
                ruleInfo4.setBusinessRuleName("CHEM 100 course prerequisites");
                ruleInfo4.setBusinessRuleType("kuali.coursePrerequisite");
                ruleInfo4.setAnchor("CHEM 100");
                ruleInfo4.setAgendaType("kuali.studentDropsCourse");
                rulesInfo.add(ruleInfo4);

                RulesHierarchyInfo ruleInfo5 = new RulesHierarchyInfo();
                ruleInfo5.setBusinessRuleId("4");
                ruleInfo5.setBusinessRuleName("CHEM 200 course prerequisites");
                ruleInfo5.setBusinessRuleType("kuali.coursePrerequisite");
                ruleInfo5.setAnchor("CHEM 200");
                ruleInfo5.setAgendaType("kuali.studentDropsCourse");
                rulesInfo.add(ruleInfo5);
        */
        return rulesInfo;
    }

    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String ruleId) {

        BusinessRuleInfoDTO businessRuleInfo;

        try {
            businessRuleInfo = ruleManagementService.fetchBusinessRuleInfo(ruleId); // TODO detailed buss rule info
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get agenda types", ex); // TODO
        }

        // TODO: call BRMS service fetchDetailedBusinessRuleInfo()
        // if ruleId = 1 ...
        /*
                BusinessRuleProposition prop = new BusinessRuleProposition();
                prop.setName("Credit Check");
                prop.setDescription("Credit Intersection Check");
                prop.setLeftHandSide("INTERSECTION");
                prop.setComparisonOperatorType("LESS_THAN");
                prop.setRightHandSide("12.0");
                prop.setComparisonDataType("kuali.number");

                BusinessRuleProposition prop2 = new BusinessRuleProposition();
                prop2.setName("Credit Check 2");
                prop2.setDescription("Credit Intersection Check 2");
                prop2.setLeftHandSide("SUBSET");
                prop2.setComparisonOperatorType("EQUAL_TO");
                prop2.setRightHandSide("6.0");
                prop2.setComparisonDataType("kuali.number");

                BusinessRuleProposition prop3 = new BusinessRuleProposition();
                prop3.setName("Credit Check 3");
                prop3.setDescription("Credit Intersection Check 3");
                prop3.setLeftHandSide("SUM");
                prop3.setComparisonOperatorType("GREATER_THAN");
                prop3.setRightHandSide("3.0");
                prop3.setComparisonDataType("kuali.number");

                BusinessRuleElement elem = new BusinessRuleElement();
                elem.setName("left bracket");
                elem.setOperation("(");
                elem.setOrdinalPosition(0);

                BusinessRuleElement elem0 = new BusinessRuleElement();
                elem0.setName("Pre-req I");
                elem0.setDescription("Pre req check for Math 101");
                elem0.setOperation("PROPOSITION");
                elem0.setOrdinalPosition(1);
                elem0.setRuleProposition(prop);

                BusinessRuleElement elem1 = new BusinessRuleElement();
                elem1.setName("left bracket");
                elem1.setOperation("AND");
                elem1.setOrdinalPosition(2);

                BusinessRuleElement elem2 = new BusinessRuleElement();
                elem2.setName("Pre-req I");
                elem2.setDescription("Pre req check for Math 101");
                elem2.setOperation("PROPOSITION");
                elem2.setOrdinalPosition(3);
                elem2.setRuleProposition(prop2);

                BusinessRuleElement elem3 = new BusinessRuleElement();
                elem3.setName("left bracket");
                elem3.setOperation(")");
                elem3.setOrdinalPosition(4);

                BusinessRuleElement elem4 = new BusinessRuleElement();
                elem4.setName("left bracket");
                elem4.setOperation("OR");
                elem4.setOrdinalPosition(5);

                BusinessRuleElement elem5 = new BusinessRuleElement();
                elem5.setName("Pre-req I");
                elem5.setDescription("Pre req check for Math 101");
                elem5.setOperation("PROPOSITION");
                elem5.setOrdinalPosition(6);
                elem5.setRuleProposition(prop3);

                List<BusinessRuleElement> elemList = new ArrayList<BusinessRuleElement>();
                elemList.add(elem);
                elemList.add(elem0);
                elemList.add(elem1);
                elemList.add(elem2);
                elemList.add(elem3);
                elemList.add(elem4);
                elemList.add(elem5);

                BusinessRuleInfo ruleInfo = new BusinessRuleInfo();
                ruleInfo.setId("1");
                ruleInfo.setName("CHEM 100 course prerequisites");
                ruleInfo.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
                ruleInfo.setSuccessMessage("Test success message");
                ruleInfo.setFailureMessage("Test failure message");
                ruleInfo.setBusinessRuleTypeKey("kuali.coursePrerequisite");
                ruleInfo.setAnchorTypeKey("kuali.lui.course.id");
                ruleInfo.setAnchorValue("CHEM 100");
                ruleInfo.setStatus("ACTIVE");
                ruleInfo.setCreateTime("today");
                ruleInfo.setCreateUserId("Zdenek");
                ruleInfo.setEffectiveStartTime("2007");
                ruleInfo.setEffectiveEndTime("2009");
                ruleInfo.setUpdateTime("2008");
                ruleInfo.setUpdateUserId("Len");
                ruleInfo.setRuleElementList(elemList);
        */
        return businessRuleInfo;
    }

    public BusinessRuleTypeDTO fetchBusinessRuleTypeInfo(String ruleTypeKey) {

        // TODO call Kamal's service once he is done
        BusinessRuleTypeDTO ruleType = new BusinessRuleTypeDTO();
        ruleType.setAnchorTypeKey("kuali.student.course");
        ruleType.setBussinessRuleTypeKey("kuali.student.enrollCourses");
        List<FactStructureDTO> factStructures = null;
        ruleType.setFactStructureList(factStructures);

        return ruleType;
    }

    /**
     * @return the ruleManagementService
     */
    public final RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    /**
     * @param ruleManagementService
     *            the ruleManagementService to set
     */
    public final void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }
}