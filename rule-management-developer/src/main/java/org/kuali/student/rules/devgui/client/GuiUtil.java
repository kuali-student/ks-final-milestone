/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.devgui.client.model.BusinessRuleElement;
import org.kuali.student.rules.devgui.client.model.BusinessRuleProposition;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author zzraly
 */
public class GuiUtil {

    public static String assembleRuleFromComposition(String composition, Map<Integer, BusinessRuleElement> definedPropositions) {
        BusinessRuleElement elem;
        BusinessRuleProposition prop;
        String token;
        StringBuffer completeRule = new StringBuffer();
        int counter = 0;

        while (((token = getNextTokenFromComposition(composition)) != null) && (counter < 100)) {
            counter++;
            composition = composition.substring(composition.indexOf(token, 0) + token.length());
            System.out.println("Comp:" + token.substring(1));
            if (token.charAt(0) == 'P') {
                elem = definedPropositions.get(new Integer(token.substring(1)));
                if (elem != null) {
                    prop = elem.getRuleProposition();
                    completeRule.append(prop.getLeftHandSide() + " " + GuiUtil.getComparisonOperatorTypeSymbol(prop.getComparisonOperatorType()) + " " + prop.getRightHandSide() + " ");
                }
            } else {
                completeRule.append(token + " ");
            }
        }

        return completeRule.toString();
    }

    private static String getNextTokenFromComposition(String partialComposition) {
        String nextToken = "x";
        System.out.println("get next token:" + partialComposition);
        partialComposition = partialComposition.trim().toUpperCase();
        if (partialComposition.isEmpty()) {
            return null;
        }

        // check for brackets, OR, AND
        if ((partialComposition.charAt(0) == '(') || (partialComposition.charAt(0) == ')')) {
            nextToken = partialComposition.substring(0, 1);
            // partialComposition = (partialComposition.length() == 1 ? "" : partialComposition.substring(1));
        } else if (partialComposition.startsWith("OR")) {
            nextToken = partialComposition.substring(0, 2);
            // partialComposition = (partialComposition.length() == 2 ? "" : partialComposition.substring(2));
        } else if (partialComposition.startsWith("AND")) {
            nextToken = partialComposition.substring(0, 3);
            // partialComposition = (partialComposition.length() == 3 ? "" : partialComposition.substring(3));
        } else if (partialComposition.charAt(0) == 'P') {

            String[] tokens = partialComposition.split(" ");
            if (tokens[0].length() == 1) {
                // partialComposition = "";
                return null;
            }

            Integer propID;
            try {
                propID = new Integer(tokens[0].substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid Proposition ID: '" + tokens[0] + "'");
                return null;
            }

            nextToken = "P" + propID.toString();
            // partialComposition = (partialComposition.length() == (1 + propID.toString().length()) ? "" :
            // partialComposition.substring(1 + propID.toString().length()));
        } else {
            String[] tokens = partialComposition.split(" ");
            // partialComposition = (tokens.length == 1 ? "" : partialComposition.substring(tokens[0].length()));
            nextToken = tokens[0];

        }

        System.out.println("Next token:'" + nextToken + "'");
        return nextToken;
    }

    public static String validateRuleComposition(String text, Set<Integer> identifiers) {

        // we need at least one proposition
        if (text.trim().isEmpty()) {
            return "Enter at least one Proposition";
        }

        // validate rule composition
        String message = "Composition is valid."; // validateProposition(text);

        // check that all propositions are defined
        String[] tokens = text.split(" ");
        ArrayList<Integer> listedPropositionIds = new ArrayList<Integer>();
        for (String token : tokens) {
            token = token.trim();
            if (!token.isEmpty() && ((token.charAt(0) == 'P') || (token.charAt(0) == 'p'))) {
                if (token.length() == 1) {
                    return "Found invalid Proposition 'P'";
                }

                Integer propID;
                try {
                    propID = new Integer(token.substring(1));
                } catch (NumberFormatException e) {
                    return "Invalid Proposition ID: '" + token + "'";
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

        return message;
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
        EQUAL_TO("="), NOT_EQUAL_TO("<>"), GREATER_THAN(">"), LESS_THAN("<"), GREATER_THAN_OR_EQUAL_TO(">="), LESS_THAN_OR_EQUAL_TO("<=");

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
        INTERSECTION("Intersection (arg1, arg2)"), SUBSET("Subset (arg)"), SUM("Sum (arg)"), AVERAGE("Average (arg)");

        private final String symbol;

        YieldValueFunctionType(String symbol) {
            this.symbol = symbol;
        }

        public String symbol() {
            return symbol;
        }
    }

}
