package org.kuali.student.rules.lumgui.client.model;

import java.util.ArrayList;
import java.util.List;

public class StatementVO {

    public static enum Operator {
        AND, OR;
    }
    
    private List<RequirementComponentVO> requirementComponents;
    private List<StatementVO> statements;
    private Operator operator;
    private String id; // statement id
    
    public boolean isNested() {
        boolean result = false;
        if (statements != null && !statements.isEmpty()) {
            result = true;
        }
        return result;
    }

    public List<RequirementComponentVO> getRequirementComponents() {
        return requirementComponents;
    }

    public List<StatementVO> getStatements() {
        return statements;
    }

    public Operator getOperator() {
        return operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addRequirementComponent(RequirementComponentVO requirementComponent) {
        if (statements != null && !statements.isEmpty()) {
            throw new RuntimeException(
                    "A statment cannot have a requirement component and a " +
                    "statment at the same time");
        }
        requirementComponents = (requirementComponents == null)? 
                new ArrayList<RequirementComponentVO>(7) :
                    requirementComponents;
                requirementComponents.add(requirementComponent);
    }
    
    public void addStatement(StatementVO statement) {
        if (requirementComponents != null && !requirementComponents.isEmpty()) {
            throw new RuntimeException(
                    "A statment cannot have a requirement component and a " +
                    "statment at the same time");
        }
        statements = (statements == null)? new ArrayList<StatementVO>(7) :
            statements;
        statements.add(statement);
    }
    
    public void removeStatement(StatementVO statement) {
        if (statements != null) {
            statements.remove(statement);
            if (statements.isEmpty()) {
                operator = null;
            }
        }
    }

}
