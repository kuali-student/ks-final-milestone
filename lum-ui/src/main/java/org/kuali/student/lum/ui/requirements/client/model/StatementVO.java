package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
    
    public void printTree(Node node) {        
        int level = 0;
        ReqComponentVO content;
//        level++;
        
        if (node == null) {
            System.out.println("null node found");
            return;
        }
        
        level = node.getDistance(node);
        if (node.getUserObject() != null) {
            Token token = (Token) node.getUserObject();
            //content = (ReqComponentVO) token.value;
            System.out.println("Node level " + level + ", content: " + token.value);
        }
        else System.out.println("Node user object null, level: " + level);
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                Token token = (Token) child.getUserObject();
                content = (ReqComponentVO) child.getUserObject();
                System.out.println("Node level " + child.getDistance(child) + ", content: " + content);
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
            statementVO.type = Token.And;
            statementVO.value = "and";
            node.setUserObject(statementVO);
        } else if (statementVO.getLuStatementInfo() != null &&
                statementVO.getLuStatementInfo().getOperator() ==
                    StatementOperatorTypeKey.OR) {
            statementVO.type = Token.Or;
            statementVO.value = "or";
            node.setUserObject(statementVO);
        }
    }
    
    /**
     * Gets the immediate parent statement of reqComponentVO
     * Example: (a and b) or (c and d) or (e)
     *     Where the statements are enclosed in brackets.  So in this example
     *     there are 3 statements.  Namely (a and b), (c and d), and (e).  
     *     There are 5 requirement components a, b, c, d, and e. If
     *     requirement component b is passed in as a parameter, then (a and b)
     *     is returned.  If e is passed in, then (e) is returned.  If d is
     *     is passed in, then (c and d) is returned.
     * @param reqComponentVO
     * @return
     */
    public StatementVO getEnclosingStatementVO(ReqComponentVO reqComponentVO) {
        StatementVO result = null;
        result = doGetEnclosingStatementVO(this, reqComponentVO);
        return result;
    }
    
    private StatementVO doGetEnclosingStatementVO(StatementVO statementVO, 
            ReqComponentVO reqComponentVO) {
        StatementVO result = null;
        List<StatementVO> statementVOs =
            (statementVO == null)? null : statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs =
            (statementVO == null)? null : statementVO.getReqComponentVOs();
        
        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO subStatementVO : statementVOs) {
                result = doGetEnclosingStatementVO(subStatementVO, reqComponentVO);
                // found the enclosing statement exit
                if (result != null) {
                    break;
                }
            }
        } else if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            for (ReqComponentVO leafReqComponentVO : reqComponentVOs) {
                if (leafReqComponentVO == reqComponentVO) {
                    result = this;
                    break;
                }
            }
        }
        return result;
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
    
    public void shiftReqComponent(String shiftType, 
            final ReqComponentVO reqComponentVO) {
        int reqComponentIndex = 0;
        if (reqComponentVOs != null && reqComponentVOs.size() > 1) {
            for (ReqComponentVO currReqComponentVO :
                reqComponentVOs) {
                if (shiftType != null && shiftType.equals("RIGHT")) {
                    if (currReqComponentVO == reqComponentVO &&
                            reqComponentIndex + 1 < reqComponentVOs.size()) {
                        Collections.swap(reqComponentVOs, reqComponentIndex,
                                reqComponentIndex + 1);
                        break;
                    }
                } else if (shiftType != null && shiftType.equals("LEFT")) {
                    if (currReqComponentVO == reqComponentVO &&
                            reqComponentIndex > 0) {
                        Collections.swap(reqComponentVOs, reqComponentIndex,
                                reqComponentIndex - 1);
                        break;
                    }
                }
                reqComponentIndex++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(value);
        return sbResult.toString();
    }      
}
