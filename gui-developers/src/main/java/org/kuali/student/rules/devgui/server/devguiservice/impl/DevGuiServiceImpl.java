/**
 * 
 */
package org.kuali.student.rules.devgui.server.devguiservice.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.rules.devgui.client.DevGuiService;
import org.kuali.student.rules.devgui.client.model.BusinessRule;

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

    public List<BusinessRule> findBusinessRules() {

        List<BusinessRule> rules = new ArrayList<BusinessRule>();

        BusinessRule rule = new BusinessRule();
        rule.setId("1");
        rule.setName("CHEM 100 course prerequisites");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule.setAnchorTypeKey("kuali.lui.course.id");
        rule.setAnchor("CHEM 100");
        rule.setAgendaType("kuali.studentEnrollsInCourse");
        rules.add(rule);

        BusinessRule rule5 = new BusinessRule();
        rule5.setId("1");
        rule5.setName("CHEM 100 course co-requisites");
        rule5.setDescription("Corequsite courses required in order to enroll in CHEM 100.");
        rule5.setSuccessMessage("Test success message");
        rule5.setFailureMessage("Test failure message");
        rule5.setBusinessRuleTypeKey("kuali.courseCorequisite");
        rule5.setAnchorTypeKey("kuali.lui.course.id");
        rule5.setAnchor("CHEM 100");
        rule5.setAgendaType("kuali.studentEnrollsInCourse");
        rules.add(rule5);

        BusinessRule rule2 = new BusinessRule();
        rule2.setId("2");
        rule2.setName("CHEM 200 course prerequisites");
        rule2.setDescription("Prerequsite courses required in order to enroll in CHEM 200.");
        rule2.setSuccessMessage("Test success message");
        rule2.setFailureMessage("Test failure message");
        rule2.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule2.setAnchorTypeKey("kuali.lui.course.id");
        rule2.setAnchor("CHEM 200");
        rule2.setAgendaType("kuali.studentEnrollsInCourse");
        rules.add(rule2);

        BusinessRule rule3 = new BusinessRule();
        rule3.setId("3");
        rule3.setName("CHEM 100 course prerequisites");
        rule3.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule3.setSuccessMessage("Test success message");
        rule3.setFailureMessage("Test failure message");
        rule3.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule3.setAnchorTypeKey("kuali.lui.course.id");
        rule3.setAnchor("CHEM 100");
        rule3.setAgendaType("kuali.studentDropsCourse");
        rules.add(rule3);

        BusinessRule rule4 = new BusinessRule();
        rule4.setId("4");
        rule4.setName("CHEM 200 course prerequisites");
        rule4.setDescription("Prerequsite courses required in order to enroll in CHEM 200.");
        rule4.setSuccessMessage("Test success message");
        rule4.setFailureMessage("Test failure message");
        rule4.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule4.setAnchorTypeKey("kuali.lui.course.id");
        rule4.setAnchor("CHEM 200");
        rule4.setAgendaType("kuali.studentDropsCourse");
        rules.add(rule4);

        return rules;
    }
}