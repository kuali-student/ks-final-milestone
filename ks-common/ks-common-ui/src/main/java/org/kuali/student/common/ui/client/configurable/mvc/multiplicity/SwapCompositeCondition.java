package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;

public class SwapCompositeCondition {
    private String conditionId;
    private CompositeConditionOperator op;
    private List<SwapCondition> childrenConditions;
    private List<SwapCompositeCondition> childrenCompositeConditions;
    public SwapCompositeCondition(CompositeConditionOperator op) {
        setOp(op);
    }
    public CompositeConditionOperator getOp() {
        return op;
    }
    public void setOp(CompositeConditionOperator op) {
        this.op = op;
    }
    public List<SwapCondition> getChildrenConditions() {
        if (childrenConditions == null) {
            childrenConditions = new ArrayList<SwapCondition>();
        }
        return childrenConditions;
    }
    public void setChildrenConditions(List<SwapCondition> childrenConditions) {
        this.childrenConditions = childrenConditions;
    }
    public List<SwapCompositeCondition> getChildrenCompositeConditions() {
        return childrenCompositeConditions;
    }
    public void setChildrenCompositeConditions(List<SwapCompositeCondition> childrenCompositeConditions) {
        this.childrenCompositeConditions = childrenCompositeConditions;
    }
    public boolean evaluate(GroupSection section, Map<String, String> helperFieldKeys) {
        boolean result = false;
        boolean allSubCompositeConditionsMet = true;
        boolean allSubConditionsMet = true;
        if (childrenCompositeConditions != null && !childrenCompositeConditions.isEmpty()) {
            for (SwapCompositeCondition childCompositeCondition : childrenCompositeConditions) {
                boolean subCompositeConditionMet = childCompositeCondition.evaluate(section, helperFieldKeys);
                if (op == CompositeConditionOperator.OR) {
                    // as soon as I find one sub condition that meets criteria the entire condition is met for OR
                    if (subCompositeConditionMet) {
                        allSubCompositeConditionsMet = true;
                        break;
                    }
                } else if (op == CompositeConditionOperator.AND) {
                    // the entire condition is false once I find at least one sub condition that fails the criteria
                    if (!subCompositeConditionMet) {
                        allSubCompositeConditionsMet = false;
                        break;
                    }
                }
            }
        }
        if (childrenConditions != null && !childrenConditions.isEmpty()) {
            for (SwapCondition childCondition : childrenConditions) {
                boolean subConditionMet = childCondition.evaluate(section, helperFieldKeys);
                if (op == CompositeConditionOperator.OR) {
                    // as soon as I find one sub condition that meets criteria the entire condition is met for OR
                    if (subConditionMet) {
                        allSubConditionsMet = true;
                        break;
                    }
                } else if (op == CompositeConditionOperator.AND) {
                    // the entire condition is false once I find at least one sub condition that fails the criteria
                    if (!subConditionMet) {
                        allSubConditionsMet = false;
                        break;
                    }
                }
            }
        }
        if (op == CompositeConditionOperator.OR) {
            result = allSubCompositeConditionsMet || allSubConditionsMet;
        } else if (op == CompositeConditionOperator.AND) {
            result = allSubCompositeConditionsMet && allSubConditionsMet;
        }
        return result;
    }
    public String getConditionId() {
        return conditionId;
    }
    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
    
}
