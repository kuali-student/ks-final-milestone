/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author zzraly
 */
public class GuiUtil {

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
            return "Warning: Propositions defined but not used: " + missingProps;
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
