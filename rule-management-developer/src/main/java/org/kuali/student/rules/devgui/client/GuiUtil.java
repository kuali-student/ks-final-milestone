/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author zzraly
 */
public class GuiUtil {

    /*
     * Creates a string of text that represents the complete rule, including details on each proposition (left, operator and right hand side)
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */
    public static String assembleRuleFromComposition(String composition, Map<Integer, RuleElementDTO> definedPropositions) {
        RuleElementDTO elem;
        RulePropositionDTO prop;
        String token;
        StringBuffer completeRule = new StringBuffer();
        int counter = 0;

        while (((token = getNextTokenFromComposition(composition)) != null) && (counter < 100)) {
            counter++;
            // System.out.println("Comp Token read:" + token);
            composition = composition.substring(composition.toUpperCase().indexOf(token, 0) + token.length());
            if (token.charAt(0) == 'P') {
                elem = definedPropositions.get(new Integer(token.substring(1)));
                if (elem != null) {
                    prop = elem.getRuleProposition();
                    completeRule.append(prop.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType() + " " + GuiUtil.getComparisonOperatorTypeSymbol(prop.getComparisonOperatorType()) + " " + prop.getRightHandSide().getExpectedValue() + " ");
                }
            } else {
                completeRule.append(token + " ");
            }
        }

        return completeRule.toString().trim();
    }

    /*
     * Retrieves next token from rule composition e.g. Px, (, ), OR, AND
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */
    private static String getNextTokenFromComposition(String partialComposition) {
        String nextToken = "?";
        // System.out.println("get next token:" + partialComposition);
        partialComposition = partialComposition.trim().toUpperCase();
        if (partialComposition.isEmpty()) {
            return null;
        }

        // check for brackets, OR, AND
        if ((partialComposition.charAt(0) == '(') || (partialComposition.charAt(0) == ')')) {
            nextToken = partialComposition.substring(0, 1);
        } else if (partialComposition.startsWith("OR")) {
            nextToken = partialComposition.substring(0, 2);
        } else if (partialComposition.startsWith("AND")) {
            nextToken = partialComposition.substring(0, 3);
        } else if (partialComposition.charAt(0) == 'P') {

            String[] tokens = partialComposition.substring(1).split("[^0-9]");
            if (tokens[0].isEmpty()) {
                return null;
            }

            // get Proposition ID
            Integer propID;
            try {
                propID = new Integer(tokens[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Proposition ID: '" + tokens[0] + "'");
                return null;
            }

            nextToken = "P" + propID.toString();
        } else {
            String[] tokens = partialComposition.split(" ");
            nextToken = tokens[0];

        }

        // System.out.println("Next token:'" + nextToken + "'");
        return nextToken;
    }

    // TODO ? Replace with antler?
    public static String validateRuleComposition(String text, Set<Integer> identifiers) {

        String message;

        // we need at least one proposition
        if (text.trim().isEmpty()) {
            return "Enter at least one Proposition";
        }

        // validate rule composition
        try {
            message = validateCompositionFormat(text, false);
        } catch (Exception e) {
            return e.getMessage();
        }

        // check that all propositions are defined
        int propID;
        String[] tokens = text.split(" ");
        ArrayList<Integer> listedPropositionIds = new ArrayList<Integer>();
        for (String token : tokens) {
            token = token.trim();
            // System.out.println("Validate Token read:" + token);
            if (!token.isEmpty() && ((token.charAt(0) == 'P') || (token.charAt(0) == 'p'))) {

                try {
                    propID = getPropositionID(token);
                } catch (Exception e) {
                    return e.getMessage();
                }

                listedPropositionIds.add(propID);

                if (!identifiers.contains(propID)) {
                    return "Unknown Proposition: " + token;
                }
            }
        }

        // check that all defined propositions were used; if not, issue a warning
        StringBuffer missingProps = new StringBuffer();
        for (Integer id : identifiers) {
            if (!listedPropositionIds.contains(id)) {
                missingProps.append("P" + id + " ");
            }
        }

        if (missingProps.length() > 0) {
            return "Warning: Unused Propositions - " + missingProps;
        }

        // check that all elements are valid i.e. OR, AND etc.
        // TODO

        return (message.isEmpty() ? "Composition valid." : message);
    }

    // call when parsing Proposition e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.
    private static String validateCompositionFormat(String text, boolean findClosingBracket) throws Exception {
        // TODO our own exception here?

        String nextToken;
        boolean logicalOperatorFound = false;

        // first we expect P1 or (P1
        while ((nextToken = getNextTokenFromComposition(text)) != null) {

            text = text.trim();

            // first handle any opening bracket
            if (nextToken.equals("(")) {
                text = text.substring(1).trim();
                // we expect Proposition P1 or '(P1'
                text = validateCompositionFormat(text, true).trim();
                if (text.isEmpty()) {
                    throw new Exception("Expected ')' but found nothing.");
                }
                if (text.charAt(0) != ')') {
                    throw new Exception("Expected ')' but found '" + firstFiveChars(text) + "...'");
                }
                text = text.substring(1); // remove the closing bracket
                logicalOperatorFound = false;

            } else { // now we expect Px

                if (text.startsWith("AND")) {
                    throw new Exception("Expected Px but found AND: " + firstFiveChars(text) + "...'");
                } else if (text.startsWith("OR")) {
                    throw new Exception("Expected Px but found OR: " + firstFiveChars(text) + "...'");
                }

                // (after brackets) handle Proposition or combination of Propositions
                int propID;
                try {
                    propID = getPropositionID(nextToken); // ensure we can get valid Proposition ID
                } catch (Exception e) {
                    throw new Exception("Found invalid proposition ID: " + nextToken);
                }
                text = text.substring(new Integer(propID).toString().length() + 1).trim();
            }

            text = text.trim();

            // next we expect one of the following: end of the text, OR, AND, )
            if (text.isEmpty()) {
                return "";
            } else if (text.charAt(0) == ')') {
                if (findClosingBracket) {
                    return text; // process closing bracket in the caller
                }
                throw new Exception("Found ')' without opening bracket.");
            } else if (!text.startsWith("AND") && !text.startsWith("OR")) {
                throw new Exception("Expected AND or OR but found '" + firstFiveChars(text) + "...'");
            }

            // we found another logical operator but we don't want P1 AND P2 OR P3 but rather (P1 AND P2) OR P3
            if (logicalOperatorFound) {
                throw new Exception("Found ambiguous proposition composition. Please add brackets: '" + firstFiveChars(text) + "...'");
            }

            logicalOperatorFound = true;
            text = text.substring((text.startsWith("AND") ? 3 : 2)).trim();

            // now we expect again P1 or (P1 so loop
        }

        throw new Exception("Expected 'Px' or '(Px' but found nothing.");
    }

    /*
     * Retrieve up to first five characters of a String
     */
    private static String firstFiveChars(String text) {
        if (text.isEmpty())
            return "";
        int maxLen = (text.length() > 5 ? 5 : text.length());
        return text.substring(0, maxLen);
    }

    // expects the first token to be proposition in format 'Px' e.g. P1
    private static int getPropositionID(String text) throws Exception { // TODO our own exception here?

        String[] tokens = text.trim().toUpperCase().split(" ");
        if (tokens[0].isEmpty() || (tokens[0].charAt(0) != 'P')) {
            throw new Exception("Missing Proposition");
        }

        if (tokens[0].length() == 1) {
            throw new Exception("Proposition is missing ID.");
        }

        Integer propID;
        try {
            propID = new Integer(tokens[0].substring(1));
        } catch (NumberFormatException e) {
            throw new Exception("Invalid Proposition ID: '" + tokens[0].substring(1) + "'");
        }

        return propID;
    }

    public static int getListBoxIndexByName(ListBox listBox, String itemText) {
        for (int i = 0; i < listBox.getItemCount(); i++) {
            if (listBox.getValue(i).trim().equals(itemText.trim())) {
                return i;
            }
        }
        return 0;
    }

    public static void populateComparisonOperatorList(ListBox opList) {
        for (ComparisonOperator op : ComparisonOperator.values()) {
            opList.addItem("     " + op.symbol(), op.name());
        }
    }

    public static String getComparisonOperatorTypeSymbol(String comparisonOperatorTypeText) {
        for (ComparisonOperator op : ComparisonOperator.values()) {
            if (op.toString().equals(comparisonOperatorTypeText)) {
                return op.symbol();
            }
        }
        return "";
    }

    public enum ComparisonOperator {
        EMPTY(" "), EQUAL_TO("="), NOT_EQUAL_TO("<>"), GREATER_THAN(">"), LESS_THAN("<"), GREATER_THAN_OR_EQUAL_TO(">="), LESS_THAN_OR_EQUAL_TO("<=");

        private final String symbol;

        ComparisonOperator(String symbol) {
            this.symbol = symbol;
        }

        public String symbol() {
            return symbol;
        }
    }

    public static void populateYVFList(ListBox yvfList) {
        for (YieldValueFunctionType yvf : YieldValueFunctionType.values()) {
            yvfList.addItem("     " + yvf.symbol(), yvf.name()); // String.valueOf(yvf.ordinal()));
        }
    }

    public static String getYVFSymbol(String yvfTypeText) {
        for (YieldValueFunctionType yvf : YieldValueFunctionType.values()) {
            if (yvf.toString().equals(yvfTypeText)) {
                return yvf.symbol();
            }
        }
        return "";
    }

    public enum YieldValueFunctionType {
        EMPTY("   "), INTERSECTION("Intersection (arg1, arg2)"), SUBSET("Subset (arg)"), SUM("Sum (arg)"), AVERAGE("Average (arg)");

        private final String symbol;

        YieldValueFunctionType(String symbol) {
            this.symbol = symbol;
        }

        public String symbol() {
            return symbol;
        }
    }

}
