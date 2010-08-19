/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.common.ui.client.widgets.rules.Token;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;

import com.google.gwt.core.client.GWT;

public class RuleExpressionParser {
    
    private String expression;
    private List<Token> tokenList;
    
    public void setExpression(String expression) {
        this.expression = expression;
        List<String> tokenValueList = getTokenValue(expression);
        this.tokenList = getTokenList(tokenValueList);
    }
    
    public String getExpression() {
        return expression;
    }
    
    private void checkExpressionSet() {
        if (tokenList == null || expression == null) {
            throw new java.lang.IllegalStateException("setExpression must be called first");
        }
    }
    
    public boolean validateExpression(List<String> errorMessages, List<ReqComponentVO> validRCs) {
        boolean valid = false;
        checkExpressionSet();
        valid = doValidateExpression(errorMessages, tokenList, validRCs);
        return valid;
    }
    
    public void checkMissingRCs(List<ReqComponentVO> missingRCs, List<ReqComponentVO> rcs) {
        List<String> conditionValues = new ArrayList<String>();
        checkExpressionSet();
        for (int i = 0; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);
            if (token.type == Token.Condition) {
                conditionValues.add(token.value);
            }
        }
        if (rcs != null && !rcs.isEmpty()) {
            for (ReqComponentVO rc : rcs) {
                boolean foundId;
                foundId = false;
                if (conditionValues != null && !conditionValues.isEmpty()) {
                    for (String conditionValue : conditionValues) {
                        if (conditionValue != null && 
                                conditionValue.equalsIgnoreCase(
                                        rc.getGuiReferenceLabelId())) {
                            foundId = true;
                            break;
                        }
                    }
                }
                if (!foundId) {
                    missingRCs.add(rc);
                }
            }
        }
    }
    
    private boolean doValidateExpression(List<String> errorMessages, final List<Token> tokens,
            List<ReqComponentVO> validRCs) {
        boolean valid = true;
        List<String> seenConditonValues = new ArrayList<String>();
        // allow empty expression. ie. user wants the entire rule deleted.
        if (tokens.size() == 0) {
            return true;
        }
        // allow single conditons (example. A)
//        if (tokens.size() <= 2) {
//            return true;
//        }
        if ((tokens.get(0).type == Token.StartParenthesis 
                || tokens.get(0).type == Token.Condition) == false) {
            errorMessages.add("must start with ( or condition");
            return false;
        }
        int lastIndex = tokens.size() - 1;
        if ((tokens.get(lastIndex).type == Token.EndParenthesis || tokens.get(lastIndex).type == Token.Condition) == false) {
            errorMessages.add("must end with ) or condition");
            return false;
        }
        if (countToken(tokens, Token.StartParenthesis) != countToken(tokens, Token.EndParenthesis)) {
            errorMessages.add("() not in pair");
            return false;
        }
        // condition cannot duplicate
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.type == Token.And) {
                if (!checkAnd(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == Token.Or) {
                if (!checkOr(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == Token.StartParenthesis) {
                if (!checkStartParenthesis(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == Token.EndParenthesis) {
                if (!checkEndParenthesis(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == Token.Condition) {
                if (seenConditonValues.contains(token.value)) {
                    errorMessages.add("Condition " + token.value + " is duplicated.");
                    return false;
                } else {
                    seenConditonValues.add(token.value);
                }
                if (!checkCondition(errorMessages, tokens, i, validRCs)) {
                    return false;
                }
            }
        }
        return valid;
    }
    
    private int countToken(List<Token> tokenList, int type) {
        int count = 0;
        for (Token t : tokenList) {
            if (t.type == type) {
                count++;
            }
        }
        return count;
    }

    private boolean checkAnd(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        Token prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
            tokenList.get(currentIndex - 1);
        Token nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
            tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == Token.Condition || prevToken.type == Token.EndParenthesis) == false) {
            errorMessages.add("only ) and condition could sit before and");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == Token.Condition || nextToken.type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after and");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkOr(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        Token prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
            tokenList.get(currentIndex - 1);
        Token nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
            tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == Token.Condition || prevToken.type == Token.EndParenthesis) == false) {
            errorMessages.add("only ) and condition could sit before or");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == Token.Condition || nextToken.type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after or");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkStartParenthesis(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        Token prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
            tokenList.get(currentIndex - 1);
        Token nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
            tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == Token.And || prevToken.type == Token.Or || prevToken.type == Token.StartParenthesis) == false) {
            errorMessages.add("only and, or, ( could sit before (");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == Token.Condition || nextToken.type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after (");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkEndParenthesis(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        Token prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
            tokenList.get(currentIndex - 1);
        Token nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
            tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == Token.Condition || prevToken.type == Token.EndParenthesis) == false) {
            errorMessages.add("only condition and ) could sit before )");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == Token.Or || nextToken.type == Token.And || nextToken.type == Token.EndParenthesis) == false) {
            errorMessages.add("only ), and, or could sit after )");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkCondition(List<String> errorMessages, List<Token> tokenList, int currentIndex,
            List<ReqComponentVO> validRCs) {
        Token prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
            tokenList.get(currentIndex - 1);
        Token nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
            tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == Token.And || prevToken.type == Token.Or || prevToken.type == Token.StartParenthesis) == false) {
            errorMessages.add("only and, or could sit before condition");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == Token.Or || nextToken.type == Token.And || nextToken.type == Token.EndParenthesis) == false) {
            errorMessages.add("only ), and, or could sit after condition");
            validToken = false;
        }
        Token conditionToken = tokenList.get(currentIndex);
        String conditionLetter = conditionToken.value;
        boolean validConditonLetter = false;
        if (validRCs != null) {
            for (ReqComponentVO rc : validRCs) {
                if (rc.getGuiReferenceLabelId() != null &&
                        rc.getGuiReferenceLabelId().equalsIgnoreCase(conditionLetter)) {
                    validConditonLetter = true;
                }
            }
        }
        if (!validConditonLetter) {
            errorMessages.add(conditionLetter + " is not a valid conditon");
            validToken = false;
        }
        return validToken;
    }
    
    public Node parseExpressionIntoTree(String expression, StatementVO statementVO, String statementType) {
        Node tree = null;
        if (expression != null && expression.trim().length() > 0) {
            StatementVO parsedS = parseExpressionIntoStatementVO(expression, statementVO, statementType);
            if (parsedS != null) {
                tree = parsedS.getTree();
            }
        }
        return tree;
    }
    
    public StatementVO parseExpressionIntoStatementVO(String expression, StatementVO statementVO, String statementType) {
        StatementVO parsedS = null;

        List<ReqComponentVO> rcs = statementVO.getAllReqComponentVOs();
        try {
            List<String> tokenValueList = getTokenValue(expression);
            List<Token> tokenList = getTokenList(tokenValueList);
            if (!doValidateExpression(new ArrayList<String>(), tokenList, rcs)) return null;
            List<Node<Token>> nodeList = toNodeList(tokenList);
            List<Node<Token>> rpnList = getRPN(nodeList);
            parsedS = statementVOFromRPN(rpnList, rcs, statementType);

            if (parsedS != null) {
                parsedS.simplify();
            }
        } catch (Exception error) {
            GWT.log("Exception parsing",error);
            parsedS = null;
        }
        return parsedS;
    }
    
    private ReqComponentVO lookupReqComponent(List<ReqComponentVO> rcs, String guiKey) {
        ReqComponentVO result = null;
        if (rcs != null) {
            for (ReqComponentVO rc : rcs) {
                if (rc.getGuiReferenceLabelId() != null && rc.getGuiReferenceLabelId().equalsIgnoreCase(guiKey)) {
                    result = rc;
                    break;
                }
            }
        }
        return result;
    }
    
    /** Build the binary tree from list of tokens*/
    private StatementVO statementVOFromRPN(List<Node<Token>> rpnList,
            List<ReqComponentVO> rcs, String statementType) {
        StatementVO statementVO = null;
        
        
        Stack<Node<? extends Token>> conditionStack = new Stack<Node<? extends Token>>();
        for (Node<Token> node : rpnList) {
            if (node.getUserObject().type == Token.Condition) {
                ReqComponentVO rc = null;
                Node<ReqComponentVO> rcNode = null;
                rc = lookupReqComponent(rcs, node.getUserObject().value);
                if (rc != null) {
                    rcNode = new Node<ReqComponentVO>();
                    rcNode.setUserObject(rc);
                }
                conditionStack.push(rcNode);
            } else if (node.getUserObject().type == Token.And || node.getUserObject().type == Token.Or) {
                StatementVO subS = new StatementVO();
                StatementOperatorTypeKey op = null;
                if (node.getUserObject().type == Token.And) {
                    op = StatementOperatorTypeKey.AND;
                } else if (node.getUserObject().type == Token.Or) {
                    op = StatementOperatorTypeKey.OR;
                }
                StatementInfo statementInfo = new StatementInfo();
                statementInfo.setOperator(op);
                statementInfo.setType(statementType);
                subS.setStatementInfo(statementInfo);
                Token right = conditionStack.pop().getUserObject();
                Token left = conditionStack.pop().getUserObject();
                List<ReqComponentVO> nodeRcs = new ArrayList<ReqComponentVO>();
                if (left instanceof ReqComponentVO) {
                    nodeRcs.add((ReqComponentVO) left);
                }
                if (right instanceof ReqComponentVO) {
                    nodeRcs.add((ReqComponentVO) right);
                }
                // if both left and right nodes are rcs add them into
                // the list of rcs in subS
                // otherwise wrap the one that is a RC and add the
                // left and right tokens as statementVO into subS
                if (nodeRcs != null && nodeRcs.size() == 2) {
                    subS.addReqComponentVOs(nodeRcs);
                } else {
                    if (left instanceof ReqComponentVO) {
                        subS.addStatementVO(wrapReqComponent(op, (ReqComponentVO)left, statementType));
                    } else {
                        subS.addStatementVO((StatementVO)left);
                    }
                    if (right instanceof ReqComponentVO) {
                        subS.addStatementVO(wrapReqComponent(op, (ReqComponentVO)right, statementType));
                    } else {
                        subS.addStatementVO((StatementVO)right);
                    }
                }
                
                Node<StatementVO> nodeS = new Node<StatementVO>();
                nodeS.setUserObject(subS);
                conditionStack.push(nodeS);
            }
        }
        
        if (conditionStack.peek().getUserObject() instanceof ReqComponentVO) {
            statementVO = wrapReqComponent(StatementOperatorTypeKey.AND,
                    (ReqComponentVO)conditionStack.pop().getUserObject(), statementType);
        } else {
            statementVO = (StatementVO)conditionStack.pop().getUserObject();
        }
        statementVO.getStatementInfo().setType(statementType);

        return statementVO;
    }
    
    private StatementVO wrapReqComponent(StatementOperatorTypeKey op, ReqComponentVO rc, String statementType) {
        StatementVO wrapS = new StatementVO();
        StatementInfo wrapStatementInfo = new StatementInfo();
        wrapStatementInfo.setOperator(op);
        wrapStatementInfo.setType(statementType);
        wrapS.setStatementInfo(wrapStatementInfo);
        wrapS.addReqComponentVO(rc);
        return wrapS;
    }
    
    /**
     * If higher push to stack, else pop till less than or equal, add to list push to stack if ( push to stack if ) pop to
     * list till (.
     * 
     * http://en.wikipedia.org/wiki/Reverse_Polish_notation
     * 
     */
    private List<Node<Token>> getRPN(List<Node<Token>> nodeList) {
        List<Node<Token>> rpnList = new ArrayList<Node<Token>>();
        Stack<Node<Token>> operatorStack = new Stack<Node<Token>>();

        for (Node<Token> node : nodeList) {
            if (node.getUserObject().type == Token.Condition) {
                rpnList.add(node);

            } else if (node.getUserObject().type == Token.And) {
                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.StartParenthesis) {
                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.Or) {

                if (operatorStack.isEmpty() == false && operatorStack.peek().getUserObject().type == Token.And) {
                    do {
                        rpnList.add(operatorStack.pop());
                    } while (operatorStack.isEmpty() == false && operatorStack.peek().getUserObject().type == Token.And);
                }

                operatorStack.push(node);
            } else if (node.getUserObject().type == Token.EndParenthesis) {
                while (operatorStack.peek().getUserObject().type != Token.StartParenthesis) {
                    rpnList.add(operatorStack.pop());
                }
                operatorStack.pop();// pop the (
            }
        }
        if (operatorStack.isEmpty() == false) {
            do {
                rpnList.add(operatorStack.pop());
            } while (operatorStack.isEmpty() == false);
        }
        return rpnList;
    }
    
    private List<Node<Token>> toNodeList(List<Token> tokenList) {
        List<Node<Token>> nodeList = new ArrayList<Node<Token>>();
        for (Token token : tokenList) {
            Node<Token> node = new Node<Token>();
            node.setUserObject(token);
            nodeList.add(node);
        }
        return nodeList;
    }
    
    private List<Token> getTokenList(List<String> tokenValueList) {
        List<Token> tokenList = new ArrayList<Token>();
        for (String value : tokenValueList) {
            if (value.isEmpty()) {
                continue;
            }
            if ("(".equals(value)) {
                Token t = new Token();
                t.type = Token.StartParenthesis;
                tokenList.add(t);
            } else if (")".equals(value)) {
                Token t = new Token();
                t.type = Token.EndParenthesis;
                tokenList.add(t);

            } else if ("and".equals(value)) {
                Token t = new Token();
                t.type = Token.And;
                tokenList.add(t);

            } else if ("or".equals(value)) {
                Token t = new Token();
                t.type = Token.Or;
                tokenList.add(t);

            } else {
                Token t = new Token();
                t.type = Token.Condition;
                t.value = value;
                tokenList.add(t);

            }
        }
        return tokenList;
    }
    
    private List<String> getTokenValue(String expression) {
        expression = expression.toLowerCase();
        List<String> tokenValueList = new ArrayList<String>();
        StringBuffer tokenValue = new StringBuffer();
        for (int i = 0; i < expression.length(); i++) {

            char ch = expression.charAt(i);
            if (ch == ' ') {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuffer();
            } else if (ch == '(' || ch == ')') {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuffer();
                tokenValueList.add(String.valueOf(ch));
            } else {
                tokenValue.append(ch);
            }
        }
        tokenValueList.add(tokenValue.toString());
        return tokenValueList;
    }
        
}
