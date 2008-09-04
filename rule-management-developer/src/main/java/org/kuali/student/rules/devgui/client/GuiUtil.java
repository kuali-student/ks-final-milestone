/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author zzraly
 */
public class GuiUtil {

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
