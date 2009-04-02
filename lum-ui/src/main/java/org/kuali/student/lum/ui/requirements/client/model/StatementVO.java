package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class StatementVO {

    private LuStatementInfo luStatementInfo;
    private List<RequirementComponentVO> requirementComponents;
    private List<StatementVO> statements;
    
    public StatementVO() {
        init();
    }
    
    private void init() {
        luStatementInfo = new LuStatementInfo();
    }
    
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

    public StatementOperatorTypeKey getOperator() {
        return luStatementInfo.getOperator();
    }
    
    public void setOperator(StatementOperatorTypeKey operator) {
        this.luStatementInfo.setOperator(operator);
    }
    
    public String getId() {
        return luStatementInfo.getId();
    }

    public void setId(String id) {
        luStatementInfo.setId(id);
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
        if (!luStatementInfo.getReqComponentIds().contains(
                requirementComponent.getId())) {
            luStatementInfo.getReqComponentIds().add(requirementComponent.getId());
        }
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
        if (!luStatementInfo.getLuStatementIds().contains(
                statement.getId())) {
            luStatementInfo.getLuStatementIds().add(statement.getId());
        }
    }
    
    public void removeStatement(StatementVO statement) {
        if (statements != null) {
            statements.remove(statement);
        }
        luStatementInfo.getLuStatementIds().remove(statement.getId());
    }

    public LuStatementInfo getLuStatementInfo() {
        return luStatementInfo;
    }

    public void setLuStatementInfo(LuStatementInfo luStatementInfo) {
        this.luStatementInfo = luStatementInfo;
    }

}
