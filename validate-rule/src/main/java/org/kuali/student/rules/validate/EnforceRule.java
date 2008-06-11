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
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * This class is responsibile for extracting rule from the BRMS, Generating the required constraints and excecuting the rules
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
public class EnforceRule {

    @Autowired
    private FunctionalBusinessRuleManagementService metadata;

    @Autowired
    private RuleEngineRepository droolsRepository;

    public ValidationResult validateLuiPersonRelation(String personID, String luiID, String luiPersonRelationType,
            String relationState) {

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
        String functionString = rule.createAdjustedRuleFunctionString();
        System.out.println("Applying Business Rule:" + rule.getRuleIdentifier());

        // 4. Extract compiled rule from drools repository
        org.drools.rule.Package binPkg = (org.drools.rule.Package) droolsRepository.loadCompiledRuleSet(rule
                .getCompiledRuleID());
        List<Object> factList = new ArrayList<Object>();

        PropositionContainer props =  new PropositionContainer();
        CourseEnrollmentRequest request = new CourseEnrollmentRequest();
        request.setLuiIds(parseList("Math 100, Math 101, Math 102, Math 105, Art 55"));

        factList.add(props);


        // 5. Execute the compiled rule
        try {
            RuleBase rb = RuleBaseFactory.newRuleBase();
            rb.addPackage(binPkg);
            StatelessSession sess = rb.newStatelessSession();
            sess.execute(factList.toArray());

        } catch (Exception e) {
            System.out.println("Exception while executing rule:" + rule.getRuleIdentifier());
            e.printStackTrace(System.out);
            // Eating exception here. BAD BAD Code!
        }

        // 6. Process rule outcome
        if (props.getRuleResult()) {
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

    /**
     * This method generates a Set of String from a comma separated list of string elements
     * 
     * @param list
     * @return
     */
    private Set<String> parseList(String list) {
        return new HashSet<String>(Arrays.asList(list.split(",")));
    }

    /**
     * @return the metadata
     */
    public FunctionalBusinessRuleManagementService getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
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
     * @param droolsRepository
     *            the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }

}
