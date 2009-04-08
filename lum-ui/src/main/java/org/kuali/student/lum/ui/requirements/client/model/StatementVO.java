package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class StatementVO extends Token implements Serializable {

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
//        Node testNode = new Node();
//        testNode.setUserObject(Token.createAndToken());
//        
//        Node testSubNode = new Node();
//        testSubNode.setUserObject(Token.createOrToken());
//        
//        Node childNode1 = new Node();
//        childNode1.setUserObject("Child Node 1");
//        testSubNode.addNode(childNode1);
//        
//        Node childNode2 = new Node();
//        childNode2.setUserObject("Child Node 2");
//        testSubNode.addNode(childNode2);
//        
//        Node childNode4 = new Node();
//        childNode4.setUserObject("Child Node 4");
//        testSubNode.addNode(childNode4);
//
//        testNode.addNode(testSubNode);
//        Node childNode3 = new Node();
//        childNode3.setUserObject("Child Node 3");
//        testNode.addNode(childNode3);
//
//        return testNode;
        
        Node node = new Node();
        addChildrenNodes(node, this);
        return node;
    }
    
    private void setOperatorNode(Node node, StatementVO statementVO) {
        if (statementVO.getLuStatementInfo() != null &&
                statementVO.getLuStatementInfo().getOperator() ==
                    StatementOperatorTypeKey.AND) {
            node.setUserObject(Token.createAndToken());
        } else if (statementVO.getLuStatementInfo() != null &&
                statementVO.getLuStatementInfo().getOperator() ==
                    StatementOperatorTypeKey.OR) {
            node.setUserObject(Token.createOrToken());
        }
    }
    
    private void addChildrenNodes(Node node, StatementVO statementVO) {
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();
        if (statementVOs != null) {
//            node.setUserObject(statementVO);
            setOperatorNode(node, statementVO);
            for (int i = 0, statementVOCount = statementVOs.size();
                i < statementVOCount; i++) {
                StatementVO childStatementVO = statementVOs.get(i);
                Node childNode = new Node();
                node.addNode(childNode);
//                if (childStatementVO.getStatementVOs() == null ||
//                        childStatementVO.getStatementVOs().isEmpty()) {
//                    setOperatorNode(childNode, childStatementVO);
//                }
                addChildrenNodes(childNode, childStatementVO);
            }
        }
        if (reqComponentVOs != null) {
            if (reqComponentVOs != null) {
                for (int rcIndex = 0, rcCount = reqComponentVOs.size();
                rcIndex < rcCount; rcIndex++) {
                    ReqComponentVO childReqComponentVO = reqComponentVOs.get(rcIndex);
                    if (rcCount > 1) {
                        node.addNode(new Node(childReqComponentVO));
                    } else {
                        node.setUserObject(childReqComponentVO);
                    }
                }
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
    
    public List<ReqComponentVO> getReqComponentVOs() {
        return reqComponentVOs;
    }

    public List<StatementVO> getStatementVOs() {
        return statementVOs;
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
