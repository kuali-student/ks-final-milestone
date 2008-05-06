package org.kuali.student.rules.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatelessSession;
import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.drools.DroolsTestUtil;
import org.kuali.student.rules.BRMSCore.entity.FunctionalBusinessRule;
import org.kuali.student.rules.BRMSCore.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.util.Constraint;
import org.kuali.student.rules.util.ConstraintStrategy;
import org.kuali.student.rules.util.Propositions;
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

    @Autowired
    private RuleEngineRepository droolsRepository;

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

        System.out.println("\n\n");
        
        // 2. get function string for a given business rule
        String functionString = metadata.createAdjustedRuleFunctionString(rule);
        System.out.println("Applying Business Rule:" + rule.getRuleIdentifier()  );
        
        // 3. Build constraints (lot of hardcoding here)
        List<Constraint> constraintList = buildConstraint(ruleID);
        Propositions props = new Propositions();
        Propositions.init(functionString);
        Propositions.setProposition(FunctionalBusinessRuleManagementService.VALIDATION_OUTCOME, false);
        
        // 4. Extract compiled rule from drools repository
        org.drools.rule.Package binPkg = (org.drools.rule.Package) droolsRepository.loadCompiledRuleSet(rule.getCompiledRuleID());
        List<Object> factList = new ArrayList<Object>();
        
        factList.add(props);
        
        for(Constraint constraint : constraintList) {
            factList.add(constraint);
        }
        
        // 5. Execute the compiled rule
        try {
            RuleBase rb = RuleBaseFactory.newRuleBase();
            rb.addPackage( binPkg );
            StatelessSession sess = rb.newStatelessSession();
            sess.execute( factList.toArray() );
            
        } catch (Exception e) {
            System.out.println("Exception while executing rule:" + rule.getRuleIdentifier());
            e.printStackTrace(System.out);
            // Eating exception here. BAD BAD Code!
        }
        
        // 6. Process rule outcome
        if((Boolean) Propositions.getProposition(FunctionalBusinessRuleManagementService.VALIDATION_OUTCOME)) {
            result.setSuccess(true);
            result.setRuleMessage(rule.getSuccessMessage());   
            System.out.println(rule.getSuccessMessage());
        } else {
            result.setRuleMessage(rule.getFailureMessage());
            System.out.println(rule.getFailureMessage());
        }
        
        System.out.println("\n\n");
                
        return result;
    }

    
    private java.util.List<Constraint> buildConstraint(String ruleID) {
        
        
        List<Constraint> constraintList = new ArrayList<Constraint>();
        
        CourseEnrollmentRequest request = new CourseEnrollmentRequest();
                
        Set<String> lr = null;
        
        int id = Integer.valueOf(ruleID);
        switch(id) {
            case 1:
                 lr = parseList("Math 100, Math 101, Math 110");

                request.setLuiIds(parseList("Math 100, Math 101, Math 102, Math 105, Art 55"));
                 
                ConstraintStrategy constraintStrategy1A = new SubsetConstraint<String>("A",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LuiIds", lr, 2);

                Constraint constraint1A = new Constraint(constraintStrategy1A); 
                constraint1A.setRequest(request);
                
                constraintList.add( constraint1A );
                
                lr = parseList("Math 100, Math 101, Math 102, Math 105, Art 55");
                
                ConstraintStrategy constraintStrategy1B = new SubsetConstraint<String>("B",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LuiIds", lr, 5);

                Constraint constraint1B = new Constraint(constraintStrategy1B);
                constraint1B.setRequest(request);
                constraintList.add(constraint1B);
                
                break;
            case 2:
                lr = parseList("Math 100, Math 101, Math 110");

                request.setLuiIds(parseList("Math 100, Math 201, Math 102, Math 105, Art 55"));
                 
                ConstraintStrategy constraintStrategy2A = new SubsetConstraint<String>("A",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LuiIds", lr, 2);

                Constraint constraint2A = new Constraint(constraintStrategy2A); 
                constraint2A.setRequest(request);
                
                constraintList.add( constraint2A );
                
                lr = parseList("Math 100, Math 201, Math 102, Math 105, Art 55");
                
                ConstraintStrategy constraintStrategy2B = new SubsetConstraint<String>("B",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LuiIds", lr, 5);

                Constraint constraint2B = new Constraint(constraintStrategy2B);
                constraint2B.setRequest(request);
                constraintList.add(constraint2B);
                break;
            case 3:
                lr = parseList("Math 100, Math 101, Math 110");
                
                ConstraintStrategy constraintStrategy3A = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LearningResults", lr, 2);                
                request.setLuiIds(parseList("Math 100, Math 101, Math 102, Math 105, Art 55"));
                break;
            case 4:
                lr = parseList("Math 100, Math 101, Math 110");

                ConstraintStrategy constraintStrategy4A = new SubsetConstraint<String>("A constraintID from DB",
                        "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                        "LearningResults", lr, 2);           
                request.setLearningResults(new ArrayList<Number>(Arrays.asList((new Number[]{1, 2, 3}))));
                break;
             default:
                 lr = parseList("Math 100, Math 101, Math 110");

                 ConstraintStrategy constraintStrategyA = new SubsetConstraint<String>("A constraintID from DB",
                         "org.kuali.student.rules.validate.CourseEnrollmentRequest",
                         "LearningResults", lr, 2);                
                 request.setLearningResults(new ArrayList<Number>(Arrays.asList((new Number[]{1, 2, 3}))));
                 break;
        }
        
        return constraintList;
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


    /**
     * @return the droolsRepository
     */
    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }


    /**
     * @param droolsRepository the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }    

    
}
