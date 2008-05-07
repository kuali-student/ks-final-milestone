package org.kuali.student.rules.BRMSCore.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import org.kuali.student.rules.BRMSCore.dao.FunctionalBusinessRuleDAO;
import org.kuali.student.rules.BRMSCore.entity.FunctionalBusinessRule;
import org.kuali.student.rules.BRMSCore.entity.RuleElement;
import org.kuali.student.rules.BRMSCore.entity.RuleElementType;
import org.kuali.student.rules.BRMSCore.entity.RuleProposition;
import org.kuali.student.rules.brms.parser.GenerateRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * An interface to BRMS Meta Data that allows to store and retrieve a business rule
 * 
 * @author Zdenek Zraly (zdenek.zraly@ub.ca)
 */
@Repository
@Transactional
public class FunctionalBusinessRuleManagementService {

    private static final String RULE_PACKAGE = "org.kuali.student.rules.enrollment";
    private static final String RULESET_DESC = "Enrollment Rules";

    public static final String VALIDATION_OUTCOME = "validationResultOutcome";

    public static final char INITIAL_PROPOSITION_PLACEHOLDER = 'A';

    @Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;

    /**
     * Generates a function string from a functional business rule based on RuleElementType classification.
     * 
     * @param rule
     *            Functional business rule used to create its function string representation
     * @return Returns function string e.g. "(A OR B) AND C"
     */
    public String createRuleFunctionString(FunctionalBusinessRule rule) {

        Collection<RuleElement> ruleElements = rule.getRuleElements();

        StringBuilder functionString = new StringBuilder();
        char proposition = INITIAL_PROPOSITION_PLACEHOLDER; // each proposition is represented as a letter

        // step through rule elements and create a function string
        for (RuleElement ruleElement : ruleElements) {
            switch (ruleElement.getOperation()) {
                case AND_TYPE:
                    functionString.append(" " + RuleElementType.AND_TYPE.getName() + " ");
                    break;
                case LPAREN_TYPE:
                    functionString.append(RuleElementType.LPAREN_TYPE.getName());
                    break;
                case NOT_TYPE:
                    functionString.append(RuleElementType.NOT_TYPE.getName());
                    break;
                case OR_TYPE:
                    functionString.append(" " + RuleElementType.OR_TYPE.getName() + " ");
                    break;
                case PROPOSITION_TYPE:
                    functionString.append(proposition);
                    proposition++;
                    break;
                case RPAREN_TYPE:
                    functionString.append(RuleElementType.RPAREN_TYPE.getName());
                    break;
                case XOR_TYPE:
                    functionString.append(RuleElementType.XOR_TYPE.getName());
                    break;
                default:
                    functionString.append("(unknown)");
            }
        }
        return functionString.toString().trim();
    }

    public String createAdjustedRuleFunctionString(FunctionalBusinessRule rule) {
        String functionString = createRuleFunctionString(rule);
        functionString = functionString.replace("AND", "*");
        functionString = functionString.replace("OR", "+");
        return functionString;
    }

    /**
     * Generates a HashMap of <unique alphabet character, proposition> pair from a functional business rule.
     * 
     * @param rule
     *            Functional business rule used to generate HashMap
     * @return Returns HashMap
     */
    public HashMap<String, RuleProposition> getRulePropositions(FunctionalBusinessRule rule) {

        HashMap<String, RuleProposition> propositions = new HashMap<String, RuleProposition>();
        Collection<RuleElement> ruleElements = rule.getRuleElements();

        char key = INITIAL_PROPOSITION_PLACEHOLDER;
        for (RuleElement ruleElement : ruleElements) {
            if (ruleElement.getOperation() == RuleElementType.PROPOSITION_TYPE) {
                propositions.put(String.valueOf(key), ruleElement.getRuleProposition());
                key++;
            }
        }
        return propositions;
    }

    /**
     * This method builds the drools rule set container associated with the functional business rule.
     * 
     * @param rule
     * @return
     * @throws Exception
     */
    public GenerateRuleSet buildRuleSet(FunctionalBusinessRule rule) throws Exception {
        GenerateRuleSet grs = new GenerateRuleSet(createAdjustedRuleFunctionString(rule));

        grs.setRuleSetName(RULE_PACKAGE + rule.getRuleIdentifier());
        grs.setRuleSetDescription(RULESET_DESC);
        grs.setRuleName(rule.getRuleIdentifier());
        grs.setRuleDescription(rule.getDescription()); // Rule description cannot be empty
        grs.setRuleCategory(null);
        grs.setRuleAttributes(null);

        Hashtable<String, String> funcConstraintsMap = new Hashtable<String, String>();

        HashMap<String, RuleProposition> propositions = getRulePropositions(rule);

        Set<String> labels = propositions.keySet();

        for (String label : labels) {
            funcConstraintsMap.put(label, label);
        }

        grs.setLhsFuncConstraintMap(funcConstraintsMap);
        grs.setRuleOutcome("Propositions.setProposition(\"" + VALIDATION_OUTCOME + "\",true);");

        grs.parse();

        return grs;
    }

    /**
     * Retrieves a functional business rule from database based on Rule ID.
     * 
     * @param ruleID
     *            Functional business rule ID.
     * @return Returns functional business rule with Rule ID
     */
    public FunctionalBusinessRule getFunctionalBusinessRule(String ruleID) {

        FunctionalBusinessRule rule = businessRuleDAO.lookupBusinessRuleID(ruleID);

        // force Hibernate to load the RuleElements collection as well
        rule.getRuleElements().size();

        return rule;
    }

    /**
     * @return the businessRuleDAO
     */
    public final FunctionalBusinessRuleDAO getBusinessRuleDAO() {
        return businessRuleDAO;
    }

    /**
     * @param businessRuleDAO
     *            the businessRuleDAO to set
     */
    public final void setBusinessRuleDAO(FunctionalBusinessRuleDAO businessRuleDAO) {
        this.businessRuleDAO = businessRuleDAO;
    }
}
