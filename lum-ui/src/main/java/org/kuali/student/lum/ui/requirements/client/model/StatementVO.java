package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.lu.dto.LuStatementInfo;

public class StatementVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private LuStatementInfo luStatementInfo;
    private List<ReqComponentVO> reqComponentVOs;
    private List<StatementVO> statementVOs; 
    
    public StatementVO() {
        init();
    }
    
    private void init() {
        reqComponentVOs = new ArrayList<ReqComponentVO>();
        statementVOs = new ArrayList<StatementVO>();
    }
    
    public StatementVO(LuStatementInfo luStatementInfo) {
        init();
        setLuStatementInfo(luStatementInfo);
    }

    public LuStatementInfo getLuStatementInfo() {
        return luStatementInfo;
    }

    public void setLuStatementInfo(LuStatementInfo luStatementInfo) {
        this.luStatementInfo = luStatementInfo;
    }
    
    public Node getTree() {
        Node node = new Node(this);
        addChildrenNodes(node, this);
        return node;
    }
    
    private void addChildrenNodes(Node node, StatementVO statementVO) {
        if (statementVOs != null) {
            for (StatementVO childStatementVO : statementVOs) {
                node.addNode(new Node(childStatementVO));
                addChildrenNodes(node, childStatementVO);
            }
        }
        if (reqComponentVOs != null) {
            for (ReqComponentVO childReqComponentVO : reqComponentVOs) {
                node.addNode(new Node(childReqComponentVO));
            }
        }
    }

    public void addStatementVO(StatementVO statementVO) {
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            throw new java.lang.IllegalArgumentException(
                    "A statement cannot contain both Requirement " +
                    "component and statement");
        }
        statementVOs.add(statementVO);
    }
    
    public void removeStatementVO(StatementVO statementVO) {
        statementVOs.remove(statementVO);
    }
    
    public void addReqComponentVO(ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            throw new java.lang.IllegalArgumentException(
                    "A statement cannot contain both Requirement " +
                    "component and statement");
        }
        reqComponentVOs.add(reqComponentVO);
    }

    public void removeReqComponentVO(ReqComponentVO reqComponentVO) {
        reqComponentVOs.remove(reqComponentVO);
    }
    
    public void clearStatementAndReqComponents() {
        if (statementVOs != null) {
            statementVOs.clear();
        }
        if (reqComponentVOs != null) {
            reqComponentVOs.clear();
        }
    }

    @Override
    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(luStatementInfo.getDesc());
        return sbResult.toString();
    }
       
}
