/**
 * 
 */
package org.kuali.student.rules.devgui.server.devguiservice.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.rules.devgui.client.DevGuiService;
import org.kuali.student.rules.devgui.client.model.BusinessRuleElement;
import org.kuali.student.rules.devgui.client.model.BusinessRuleInfo;
import org.kuali.student.rules.devgui.client.model.BusinessRuleProposition;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;

/**
 * @author zzraly
 */
public class DevGuiServiceImpl implements DevGuiService {

    // retrieve all available Agenda Type Keys
    public List<String> findAgendaTypes() {

        // TODO: call findAgendaTypes() using BRMS Service

        String[] agendaTypeKeys = {"kuali.studentEnrollsInCourse", "kuali.studentDropsCourse", "kuali.instructorIsAssignedToCourse"};
        List<String> result = new ArrayList<String>();
        for (String agenaTypeKey : agendaTypeKeys) {
            result.add(agenaTypeKey);
        }
        return result;
    }

    // retrieve all available set of determination structure keys based on given Agenda Type
    public List<String> findDeterminationKeysByAgendaType(String businessRuleType) {

        // TODO: call ?() using BRMS Service

        String[] determinationStructure1 = {"Enrolled"};
        String[] determinationStructure2 = {"Dropped"};
        String[] determinationStructure3 = {"Assigned"};

        if (businessRuleType == "kuali.studentEnrollsInCourse") {
            return Arrays.asList(determinationStructure1);
        } else if (businessRuleType == "kuali.studentDropsCourse") {
            return Arrays.asList(determinationStructure2);
        } else if (businessRuleType == "kuali.instructorIsAssignedToCourse") {
            return Arrays.asList(determinationStructure3);
        }

        return null;
    }

    // retrieve all available Business Rule Types based on given set of determination keys
    public List<String> findBusinessRuleTypesByDeterminationKeySet(String determinationKeys) {

        // TODO: call ?() using BRMS Service

        String[] businessRuleTypes1 = {"kuali.coursePrerequisite", "kuali.coursCorequisite", "kuali.academicProgramRequirement", "kuali.gpaMinimum"};
        String[] businessRuleTypes2 = {"kuali.courseFinancials"};
        String[] businessRuleTypes3 = {"kuali.teachingQualifications", "kuali.courseRequirements"};

        if (determinationKeys == "Enrolled") {
            return Arrays.asList(businessRuleTypes1);
        } else if (determinationKeys == "Dropped") {
            return Arrays.asList(businessRuleTypes2);
        } else if (determinationKeys == "Assigned") {
            return Arrays.asList(businessRuleTypes3);
        }

        return null;
    }

    // retrieve all available Business Rule Types based on given set of determination keys
    /*   public List<String> findBusinessRuleTypesByDeterminationKeySet(String determinationKeys) {

           // TODO: call ?() using BRMS Service

           String[] businessRuleTypes1 = {"kuali.coursePrerequisite", "kuali.coursCorequisite", "kuali.academicProgramRequirement", "kuali.gpaMinimum"};
           String[] businessRuleTypes2 = {"kuali.courseFinancials"};
           String[] businessRuleTypes3 = {"kuali.teachingQualifications", "kuali.courseRequirements"};

           if (determinationKeys == "Enrolled") {
               return Arrays.asList(businessRuleTypes1);
           } else if (determinationKeys == "Dropped") {
               return Arrays.asList(businessRuleTypes2);
           } else if (determinationKeys == "Assigned") {
               return Arrays.asList(businessRuleTypes3);
           }

           return null;
       }  */

    public List<RulesHierarchyInfo> findRulesHierarchyInfo() {
        List<RulesHierarchyInfo> rulesInfo = new ArrayList<RulesHierarchyInfo>();

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

        return rulesInfo;
    }

    public BusinessRuleInfo fetchDetailedBusinessRuleInfo(String ruleId) {

        // TODO: call BRMS service fetchDetailedBusinessRuleInfo()
        // if ruleId = 1 ...

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

        return ruleInfo;
    }

    public List<BusinessRuleInfo> findBusinessRules() {

        List<BusinessRuleInfo> rules = new ArrayList<BusinessRuleInfo>();

        BusinessRuleInfo rule = new BusinessRuleInfo();
        rule.setId("1");
        rule.setName("CHEM 100 course prerequisites");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule.setAnchorTypeKey("kuali.lui.course.id");
        rule.setAnchorValue("CHEM 100");
        rules.add(rule);

        BusinessRuleInfo rule5 = new BusinessRuleInfo();
        rule5.setId("1");
        rule5.setName("CHEM 100 course co-requisites");
        rule5.setDescription("Corequsite courses required in order to enroll in CHEM 100.");
        rule5.setSuccessMessage("Test success message");
        rule5.setFailureMessage("Test failure message");
        rule5.setBusinessRuleTypeKey("kuali.courseCorequisite");
        rule5.setAnchorTypeKey("kuali.lui.course.id");
        rule5.setAnchorValue("CHEM 100");
        rules.add(rule5);

        BusinessRuleInfo rule2 = new BusinessRuleInfo();
        rule2.setId("2");
        rule2.setName("CHEM 200 course prerequisites");
        rule2.setDescription("Prerequsite courses required in order to enroll in CHEM 200.");
        rule2.setSuccessMessage("Test success message");
        rule2.setFailureMessage("Test failure message");
        rule2.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule2.setAnchorTypeKey("kuali.lui.course.id");
        rule2.setAnchorValue("CHEM 200");
        rules.add(rule2);

        BusinessRuleInfo rule3 = new BusinessRuleInfo();
        rule3.setId("3");
        rule3.setName("CHEM 100 course prerequisites");
        rule3.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule3.setSuccessMessage("Test success message");
        rule3.setFailureMessage("Test failure message");
        rule3.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule3.setAnchorTypeKey("kuali.lui.course.id");
        rule3.setAnchorValue("CHEM 100");
        rules.add(rule3);

        BusinessRuleInfo rule4 = new BusinessRuleInfo();
        rule4.setId("4");
        rule4.setName("CHEM 200 course prerequisites");
        rule4.setDescription("Prerequsite courses required in order to enroll in CHEM 200.");
        rule4.setSuccessMessage("Test success message");
        rule4.setFailureMessage("Test failure message");
        rule4.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule4.setAnchorTypeKey("kuali.lui.course.id");
        rule4.setAnchorValue("CHEM 200");
        rules.add(rule4);

        return rules;
    }
}