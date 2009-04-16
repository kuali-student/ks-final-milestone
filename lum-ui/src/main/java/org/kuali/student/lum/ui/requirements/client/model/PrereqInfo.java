package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.ExpressionNode;
import org.kuali.student.common.ui.client.widgets.table.ExpressionParser;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;

public class PrereqInfo implements Idable {

    private String cluId;
    private StatementVO statementVO;
    private String rationale;
    private String naturalLanguage;

    
    @Override
    public String getId() {
        return cluId;
    }
    @Override
    public void setId(String id) {
        this.cluId = id;
    }
    public String getCluId() {
        return cluId;
    }
    public void setCluId(String cluId) {
        this.cluId = cluId;
    }
    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }
    public String getNaturalLanguage() {
        return naturalLanguage;
    }
    public void setNaturalLanguage(String naturalLanguage) {
        this.naturalLanguage = naturalLanguage;
    }
    
    public StatementVO getStatementVO() {
        return statementVO;
    }
    public void setStatementVO(StatementVO statementVO) {
        this.statementVO = statementVO;
    }
    public Node getStatementTree() {
//        Node<Token> n = p.parse("(a or b) and c or d and f");
//        return n;
        Node tree = null;
        if (statementVO != null) {
            tree = statementVO.getTree();
        }
        return tree;
    }
    
    
//    public Node getTree(
//            String statementId,
//            List<LuStatementInfo> statements,
//            List<ReqComponentInfo> reqComponentInfos) {
//        LuStatementInfo rootStatement = null;
//        StatementVO rootStatementVO = null;
//        Node node = null;
//        for (LuStatementInfo statement : statements) {
//            if (statementId != null && statement.getId().equals(statementId)) {
//                rootStatement = statement;
//            }
//        }
//        if (rootStatement == null) {
//            // return empty tree if there is no root statement
//            return null;
//        }
//        rootStatementVO = new StatementVO(rootStatement);
//        node = new Node(rootStatementVO);
//        addChildren(node, statements, reqComponentInfos, rootStatement);
//        return node;
//    }
//    
//    private void addChildren(Node node, List<LuStatementInfo> statements,
//            List<ReqComponentInfo> reqComponents, LuStatementInfo statement) {
//        List<LuStatementInfo> childStatements = findChildStatements(statements, statement);
//        List<ReqComponentInfo> childReqComponents = findChildReqComponents(
//                reqComponents, statement);
//        if (childStatements != null && !childStatements.isEmpty()) {
//            for (LuStatementInfo childStatement : childStatements) {
//                addChildren(node, statements, reqComponents, childStatement);
//            }
//        } else if (childReqComponents != null && !childReqComponents.isEmpty()) {
//            for (ReqComponentInfo reqComponentInfo : childReqComponents) {
//                Node reqComponentNode = new Node(reqComponentInfo);
//                node.addNode(reqComponentNode);
//            }
//        }
//    }
    
    
    /**
     * Finds statements within the first parameter <code>statements</code> with
     * ids that are equal to the child statementIds of the second parameter 
     * @param statements
     * @param parentStatement
     * @return
     */
//    private List<LuStatementInfo> findChildStatements(List<LuStatementInfo> statements,
//            LuStatementInfo parentStatement) {
//        List<String> childStatementIds = parentStatement.getLuStatementIds();
//        List<LuStatementInfo> childStatements = new ArrayList<LuStatementInfo>();
//        if (statements != null) {
//            for (LuStatementInfo statement : statements) {
//                if (childStatementIds.contains(statement.getId())) {
//                    childStatements.add(statement);
//                }
//            }
//        }
//        return childStatements;
//    }
    
    /**
     * Finds ReqComponentInfos within the first parameter <code>reqComponents</code> with
     * ids that are equal to the child reqComponentIds of the second parameter 
     * @param reqComponents
     * @param parentStatement
     * @return
     */
//    private List<ReqComponentInfo> findChildReqComponents(List<ReqComponentInfo> reqComponents,
//            LuStatementInfo parentStatement) {
//        List<String> childReqComponentIds = parentStatement.getReqComponentIds();
//        List<ReqComponentInfo> childReqComponents = new ArrayList<ReqComponentInfo>();
//        if (reqComponents != null) {
//            for (ReqComponentInfo reqComponentInfo : reqComponents) {
//                if (childReqComponentIds.contains(reqComponentInfo.getId())) {
//                    childReqComponents.add(reqComponentInfo);
//                }
//            }
//        }
//        return childReqComponents;
//    }
//    public List<LuStatementInfo> getStatements() {
//        return statements;
//    }
//    public void setStatements(List<LuStatementInfo> statements) {
//        this.statements = statements;
//    }
//    public List<ReqComponentInfo> getReqComponentInfos() {
//        return reqComponentInfos;
//    }
//    public void setReqComponentInfos(List<ReqComponentInfo> reqComponentInfos) {
//        this.reqComponentInfos = reqComponentInfos;
//    }   
}
