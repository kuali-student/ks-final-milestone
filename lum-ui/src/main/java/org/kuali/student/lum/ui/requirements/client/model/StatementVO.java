package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.NodeWidget;
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
    
    public StatementVO(LuStatementInfo luStatementInfo) {
        init();
        setLuStatementInfo(luStatementInfo);
    }    
    
    private void init() {
        reqComponentVOs = new ArrayList<ReqComponentVO>();
        statementVOs = new ArrayList<StatementVO>();
    }
    
    public Node getTree() {        
        Node node = new Node();
        addChildrenNodes(node, this);
        //printTree(node);
        return node;
    }
    
    private void printTree(Node node) {        
        int level = 0;
        ReqComponentVO content;
        level++;
        
        if (node == null) {
            System.out.println("null node found");
            return;
        }
        
        if (node.getUserObject() != null) {
            Token token = (Token) node.getUserObject();
            //content = (ReqComponentVO) token.value;
            System.out.println("Node level " + level + ", content: " + token.value);
        }
        else System.out.println("Node user object null, level: " + level);
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                Token token = (Token) node.getUserObject();
                content = (ReqComponentVO) node.getUserObject();
                System.out.println("Node level " + level++ + ", content: " + token.value);
            } else {
                printTree(child);
            }
        }
    }
       
    private void addChildrenNodes(Node node, StatementVO statementVO) {
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();
        
        if (statementVOs != null) {
//            node.setUserObject(statementVO);
            setOperatorNode(node, statementVO);
            for (int i = 0; i < statementVOs.size(); i++) {
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
            //System.out.println("VO size: " + reqComponentVOs.size());
            for (int rcIndex = 0, rcCount = reqComponentVOs.size(); rcIndex < rcCount; rcIndex++) {
                ReqComponentVO childReqComponentVO = reqComponentVOs.get(rcIndex);
                if (rcCount > 1) {
                    //System.out.println("TESTING 00---> " + childReqComponentVO.getReqComponentInfo().getDesc() + " ### " + childReqComponentVO.getReqComponentInfo().getReqCompField().size());
                    node.addNode(new Node(childReqComponentVO));
                } else {
                    //System.out.println("TESTING 0---> " + childReqComponentVO.getReqComponentInfo().getReqCompField().size());
                    node.setUserObject(childReqComponentVO);
                }
            }
        }        
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
    
    public void addStatementVO(StatementVO statementVO) {
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            throw new java.lang.IllegalArgumentException(
                    "A statement cannot contain both Requirement " +
                    "component and statement");
        }
        statementVOs.add(statementVO);
    }
    
    public void addReqComponentVO(ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            throw new java.lang.IllegalArgumentException(
                    "A statement cannot contain both Requirement " +
                    "component and statement");
        }
        reqComponentVOs.add(reqComponentVO);
    }    
    
    public void removeStatementVO(StatementVO statementVO) {
        statementVOs.remove(statementVO);
    }   

    public void removeReqComponentVO(ReqComponentVO reqComponentVO) {
        reqComponentVOs.remove(reqComponentVO);
    }
    
    public LuStatementInfo getLuStatementInfo() {
        return luStatementInfo;
    }

    public void setLuStatementInfo(LuStatementInfo luStatementInfo) {
        this.luStatementInfo = luStatementInfo;
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
