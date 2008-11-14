/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author zzraly
 */
public class GuiUtil {

    public static final String COMPOSITION_IS_VALID_MESSAGE = "Composition is valid";
    public static final char PROPOSITION_PREFIX = 'P';

    /*
     * Creates a string of text that represents the complete rule, including details on each proposition (left, operator and right hand side)
     * 
     * ASSUMPTION: Rule Composition string is valid (was validated)
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */
    public static String assembleRuleFromComposition(String composition, Map<Integer, RulePropositionDTO> definedPropositions) {
        RuleElementDTO elem;
        RulePropositionDTO prop;
        String token;
        StringBuffer completeRule = new StringBuffer();
        int counter = 0;

        if ((definedPropositions == null) || (definedPropositions.isEmpty())) {
            return "";
        }

        try {
            while (((token = getNextTokenFromComposition(composition)) != null) && (counter < 100)) {
                counter++;
                // System.out.println("Comp Token read:" + token);
                composition = composition.substring(composition.toUpperCase().indexOf(token, 0) + token.length());
                if (token.charAt(0) == PROPOSITION_PREFIX) {
                    prop = definedPropositions.get(new Integer(token.substring(1)));

                    // check that the proposition defined in Rule Composition exists (this should not happen since Rule
                    // Composition should be verified
                    // before calling this method.
                    if (prop == null) {
                        // TODO report error
                        continue;
                    }

                    completeRule.append(prop.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType() + " " + GuiUtil.getComparisonOperatorTypeSymbol(prop.getComparisonOperatorType()) + " " + prop.getRightHandSide().getExpectedValue() + " ");
                } else {
                    completeRule.append(token + " ");
                }
            }
        } catch (IllegalRuleFormatException e) {
            // TODO: log into screen log text box
            return "Error: Cannot parse rule composition (" + e.getMessage() + ")";
        }

        return completeRule.toString().trim();
    }

    /*
     * 
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */
    public static List<RuleElementDTO> createRuleElementsFromComposition(String composition, Map<Integer, RulePropositionDTO> definedPropositions) throws IllegalRuleFormatException {

        String token;
        int counter = 0;

        List<RuleElementDTO> elemList = new ArrayList<RuleElementDTO>();

        // try {
        while (((token = getNextTokenFromComposition(composition)) != null) && (counter < 100)) {
            counter++;
            // System.out.println("Comp Token read:" + token);
            RuleElementDTO ruleElem = new RuleElementDTO();
            ruleElem.setOrdinalPosition(counter);
            composition = composition.substring(composition.toUpperCase().indexOf(token, 0) + token.length());
            if (token.charAt(0) == PROPOSITION_PREFIX) {
                ruleElem.setOperation("PROPOSITION");
                ruleElem.setRuleProposition(definedPropositions.get(new Integer(token.substring(1))));
            } else {
                ruleElem.setOperation(token);
            }
            elemList.add(ruleElem);
        }
        // } catch (IllegalRuleFormatException e) {
        // This should not happen as rule suppose to be checked before calling this function
        // TODO: log into screen log text box

        // }

        return elemList;
    }

    /*
     * Retrieves next token from rule composition e.g. Px, (, ), OR, AND
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types or NULL if no token found
     * @throws OperationFailedException
     * 
     */
    private static String getNextTokenFromComposition(String partialComposition) throws IllegalRuleFormatException {
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
        } else if (partialComposition.charAt(0) == PROPOSITION_PREFIX) {
            nextToken = PROPOSITION_PREFIX + Integer.toString(getPropositionID(partialComposition));
        } else {
            String[] tokens = partialComposition.split(" ");
            nextToken = tokens[0];

        }

        // System.out.println("Next token:'" + nextToken + "'");
        return nextToken;
    }

    /*
     * Validates rule composed of propositions e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.
     * 
     * @param text - rule text
     * @param identifiers - currently defined identifiers
     * @return validation message or COMPOSITION_IS_VALID_MESSAGE is rule is valid
     * @throws TBD
     * 
     */
    public static String validateRuleComposition(String text, Set<Integer> identifiers) { // TODO with Antler?

        String message = COMPOSITION_IS_VALID_MESSAGE;

        System.out.println("HERE in validate rule composition");

        // we need at least one proposition
        if (text.trim().isEmpty()) {
            return "Enter at least one Proposition";
        }

        // validate rule composition
        try {
            message = validateCompositionFormat(text, false);
        } catch (IllegalRuleFormatException e) {
            return e.getMessage();
        }

        // check that all propositions are defined
        int propID;
        String[] tokens = text.split("[ ()]");
        ArrayList<Integer> listedPropositionIds = new ArrayList<Integer>();
        for (String token : tokens) {
            token = token.trim();
            // System.out.println("Validate Token read:" + token);
            if (!token.isEmpty() && ((token.charAt(0) == PROPOSITION_PREFIX) || (token.charAt(0) == PROPOSITION_PREFIX))) {

                try {
                    propID = getPropositionID(token);
                } catch (IllegalRuleFormatException e) {
                    // TODO
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
                missingProps.append(PROPOSITION_PREFIX + "" + id + " ");
            }
        }

        if (missingProps.length() > 0) {
            return "Warning: Unused Propositions - " + missingProps;
        }

        // check that all elements are valid i.e. OR, AND etc.
        // TODO

        return (message.trim().isEmpty() ? COMPOSITION_IS_VALID_MESSAGE : message);
    }

    /*
     * Recursive parsing of a rule composed of propositions e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.
     * 
     * @param text - rule text
     * @param findClosingBracket - whether the recusion occured where we entered in bracket parsing mode
     * @return rule text left to be parsed
     * @throws IllegalRuleFormatException     
     */
    private static String validateCompositionFormat(String text, boolean findClosingBracket) throws IllegalRuleFormatException {
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
                    throw new IllegalRuleFormatException("Expected ')' but found nothing.");
                }
                if (text.charAt(0) != ')') {
                    throw new IllegalRuleFormatException("Expected ')' but found '" + text.charAt(0) + "' in '" + firstFiveChars(text) + "...'");
                }
                text = text.substring(1); // remove the closing bracket
                logicalOperatorFound = false;

            } else { // now we expect Px

                if (text.startsWith("AND")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' but found 'AND' in '" + firstFiveChars(text) + "...'");
                } else if (text.startsWith("OR")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' but found 'OR' in '" + firstFiveChars(text) + "...'");
                } else if (text.startsWith(")")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' or '(" + PROPOSITION_PREFIX + "x' but found ')' in '" + firstFiveChars(text) + "...'");
                }

                // (after brackets) handle Proposition or combination of Propositions
                int propID = getPropositionID(nextToken); // ensure we can get valid Proposition ID
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
                throw new IllegalRuleFormatException("Found ')' without opening bracket.");
            } else if (!text.startsWith("AND") && !text.startsWith("OR")) {
                throw new IllegalRuleFormatException("Expected 'AND' or 'OR' but found '" + firstFiveChars(text) + "...'");
            }

            // we found another logical operator but we don't want P1 AND P2 OR P3 but rather (P1 AND P2) OR P3
            if (logicalOperatorFound) {
                throw new IllegalRuleFormatException("Found ambiguous proposition composition with multiple logical operators not grouped by brackets. Please add brackets: '" + firstFiveChars(text) + "...'");
            }

            logicalOperatorFound = true;
            text = text.substring((text.startsWith("AND") ? 3 : 2)).trim();

            // now we expect again P1 or (P1 or P1) so loop
        }

        throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' or '(" + PROPOSITION_PREFIX + "x' but found nothing.");
    }

    /*
     * Retrieve up to first five characters of a String
     * 
     * @param text to retrieve chars from
     * @return up to the first five characters 
     * 
     */
    private static String firstFiveChars(String text) {
        if (text.isEmpty())
            return "";
        int maxLen = (text.length() > 5 ? 5 : text.length());
        return text.substring(0, maxLen);
    }

    /*
     * Get Proposition ID from a text that starts with proposition in format 'Px' or 'px' e.g. P1
     * 
     * @param text - rule text
     * @param identifiers - currently defined identifiers
     * @return ID 
     * @throws IllegalRuleFormatException if format of the proposition ID not valid
     * 
     */
    private static int getPropositionID(String text) throws IllegalRuleFormatException { // TODO our own exception here?

        if (text == null) {
            throw new IllegalRuleFormatException("Proposition ID is null.");
        }

        text = text.trim().toUpperCase();
        if (text.isEmpty() || (text.charAt(0) != PROPOSITION_PREFIX)) {
            throw new IllegalRuleFormatException("Invalid Proposition format. Expected '" + PROPOSITION_PREFIX + "' but found: '" + text + "'");
        }

        String[] tokens = text.split("[^0-9]");
        if ((tokens.length == 1)) {
            throw new IllegalRuleFormatException("Proposition is missing ID number: '" + text + "'");
        }

        Integer propID;
        try {
            propID = new Integer(tokens[1]);
        } catch (NumberFormatException e) {
            throw new IllegalRuleFormatException("Invalid Proposition ID: '" + tokens[1] + "'");
        }

        return propID;
    }

    /****************************************************************************************************************
     *                                 LIST BOX HELPERS
     ***************************************************************************************************************/
    
    public static String getListBoxSelectedValue(ListBox listBox) {
    	int ix = listBox.getSelectedIndex();    	
    	return (ix == -1 ? "" : listBox.getItemText(ix));    	
    }
    
    public static int getListBoxIndexByName(ListBox listBox, String itemText) {
        for (int i = 0; i < listBox.getItemCount(); i++) {
            if (listBox.getValue(i).trim().equals(itemText.trim())) {
                return i;
            }
        }
        return 0;
    }

    //if itemText is empty then un-select any selected item the list box
    public static void setListBoxByItemName(ListBox listBox, String itemText) {  
   	    	
    	if (itemText.isEmpty()) {
    		if (listBox.getSelectedIndex() != -1) {
    			listBox.setItemSelected(listBox.getSelectedIndex(), false);
    		}
    		return;
    	}
    	
    	int itemIx = getListBoxIndexByName(listBox, itemText);
    	
    	System.out.println("Util: " + itemIx + "  " + itemText);
    	
    	listBox.setSelectedIndex(itemIx);
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
