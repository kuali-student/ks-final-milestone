package org.kuali.student.rules.validate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.kuali.student.rules.BRMSCore.entity.FunctionalBusinessRule;
import org.kuali.student.rules.BRMSCore.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.util.Constraint;
import org.kuali.student.rules.util.ConstraintStrategy;
import org.kuali.student.rules.util.SubsetConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * 
 * This class is responsibile for extracting rule from the BRMS,
 * Generating the required constraints and excecuting the rules
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class EnforceRule {

    @Autowired
    private FunctionalBusinessRuleManagementService metadata;

    public ValidationResult validateLuiPersonRelation(String personID, String luiID, String luiPersonRelationType, String relationState) {
        
        ValidationResult result = new ValidationResult();
        
        /*
         * Currently map relationType to ruleId (until further clarification from service team or
         * implementation of agenda
         */
        String ruleID = luiPersonRelationType;
        
        
        FunctionalBusinessRule rule = null;
        
        try {
            rule = metadata.getFunctionalBusinessRule(ruleID);
        } catch (DataAccessException dae) {
            System.err.println("Functional rule: " + ruleID + " not present in the database!");
            throw dae;
        }

        // 2. get function string for a given business rule
        String functionString = metadata.createRuleFunctionString(rule);
        System.out.println("Function Str:" + functionString );
        
        Constraint constraint = buildConstraint(ruleID);
     
        
        return result;
    }

    
    private Constraint buildConstraint(String ruleID) {
        
        Constraint constraint = null;
        MockCourseEnrollmentRequest request = null;
        
        ConstraintStrategy constraintStrategy = null;
        Set<String> lr = null;
        
        int id = Integer.valueOf(ruleID);
        switch(id) {
            case 1:
                 lr = parseList("Math 100, Math 101, Math 110");

                constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.MockCourseEnrollmentRequest",
                        "LuiIds", lr, 2);
                
                request.setLuiIds(Arrays.asList("Math 100, Math 101, Math 102, Math 105, Art 55".split(",")));
                break;
            case 2:
                lr = parseList("Math 100, Math 101, Math 110");

                constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.MockCourseEnrollmentRequest",
                        "LuiIds", lr, 2);                
                break;
            case 3:
                lr = parseList("Math 100, Math 101, Math 110");
                
                constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.MockCourseEnrollmentRequest",
                        "LearningResults", lr, 2);                
                break;
            case 4:
                lr = parseList("Math 100, Math 101, Math 110");

                constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.MockCourseEnrollmentRequest",
                        "LearningResults", lr, 2);                
                break;
             default:
                 lr = parseList("Math 100, Math 101, Math 110");

                 constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                         "org.kuali.student.rules.validate.MockCourseEnrollmentRequest",
                         "LearningResults", lr, 2);                
                 break;
        }

        constraint = new Constraint(constraintStrategy);
        constraint.setRequest(request);
        
        return constraint;
    }
        
    /**
     * 
     * This method generates a Set of String from a comma separated list of string elements
     * 
     * @param list
     * @return
     */
    private Set<String> parseList(String list) {
        return new HashSet<String>( Arrays.asList(list.split(",")) );
    }

    /**
     * @return the metadata
     */
    public FunctionalBusinessRuleManagementService getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(FunctionalBusinessRuleManagementService metadata) {
        this.metadata = metadata;
    }    
    
}
