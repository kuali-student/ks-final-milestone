package org.kuali.student.rules.internal.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulesmanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulesmanagement.dto.RulePropositionDTO;

public class BusinessRuleUtil {

    public static final String PROPOSITION_PREFIX = "P";
    
    /**
     * Generates a function string from a business rule DTO
     * 
     * @param rule 
     *          business rule whose functional string representation is required 
     */
    public static String createFunctionalString(BusinessRuleInfoDTO rule) {

        Collection<RuleElementDTO> ruleElements = rule.getRuleElementList();

        int counter = 1;
        StringBuilder functionString = new StringBuilder();
 
        // step through rule elements and create a function string
        for (RuleElementDTO ruleElement : ruleElements) {
            switch ( RuleElementType.valueOf(ruleElement.getOperation()) ) {
                case AND:
                    functionString.append(" " + RuleElementType.AND.getName() + " ");
                    break;
                case LPAREN:
                    functionString.append(RuleElementType.LPAREN.getName());
                    break;
                case NOT:
                    functionString.append(RuleElementType.NOT.getName());
                    break;
                case OR:
                    functionString.append(" " + RuleElementType.OR.getName() + " ");
                    break;
                case PROPOSITION:
                    functionString.append(PROPOSITION_PREFIX + String.valueOf(counter));
                    counter++;
                    break;
                case RPAREN:
                    functionString.append(RuleElementType.RPAREN.getName());
                    break;
                default:
                    functionString.append("(unknown)");
            }
        }
        return functionString.toString().trim();
    }

    /**
     * 
     * This method returns the functional string with AND as * and OR as +
     * 
     * @param rule
     * @return
     */
    public static String createAdjustedRuleFunctionString(BusinessRuleInfoDTO rule) {
        String functionString = createFunctionalString(rule);
        functionString = functionString.replace("AND", "*");
        functionString = functionString.replace("OR", "+");
        return functionString;
    }

    /**
     * This method generates a map of proposition name with proposition for a given business rule
     * 
     * @param rule
     *            business rule used to generate map
     * @return returns map of name and proposition
     */
    public static Map<String, RulePropositionDTO> getRulePropositions(BusinessRuleInfoDTO rule ) {

        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        Collection<RuleElementDTO> ruleElements = rule.getRuleElementList();

        int counter = 1;
        for (RuleElementDTO ruleElement : ruleElements) {
            if ( RuleElementType.valueOf( ruleElement.getOperation() ) == RuleElementType.PROPOSITION) {
                propositionMap.put(PROPOSITION_PREFIX + String.valueOf(counter), ruleElement.getRuleProposition());
                counter++;
            }
        }
        return propositionMap;
    }
}
