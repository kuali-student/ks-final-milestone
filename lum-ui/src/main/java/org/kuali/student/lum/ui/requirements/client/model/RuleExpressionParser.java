package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class RuleExpressionParser {

    public boolean validateExpression(List<String> errorMessages, String expression) {
        boolean valid = false;
        List<String> tokenValueList = getTokenValue(expression);
        List<Token> tokenList = getTokenList(tokenValueList);
        valid = doValidateExpression(errorMessages, tokenList);
        return valid;
    }
    
    private boolean doValidateExpression(List<String> errorMessages, List<Token> tokens) {
        boolean valid = true;
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
        for (int i = 1; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.type == Token.And) {
                checkAnd(errorMessages, tokens, i);
            } else if (token.type == Token.Or) {
                checkOr(errorMessages, tokens, i);
            } else if (token.type == Token.StartParenthesis) {
                checkStartParenthesis(errorMessages, tokens, i);
            } else if (token.type == Token.EndParenthesis) {
                checkEndParenthesis(errorMessages, tokens, i);
            } else if (token.type == Token.Condition) {
                checkCondition(errorMessages, tokens, i);
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

    private void checkAnd(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessages.add("only ) and condition could sit before and");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after and");
        }

    }

    private void checkOr(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessages.add("only ) and condition could sit before or");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after or");
        }
    }

    private void checkStartParenthesis(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {
            errorMessages.add("only and, or, ( could sit before (");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Condition || tokenList.get(currentIndex + 1).type == Token.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after (");
        }

    }

    private void checkEndParenthesis(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.Condition || tokenList.get(currentIndex - 1).type == Token.EndParenthesis) == false) {
            errorMessages.add("only condition and ) could sit before )");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            errorMessages.add("only ), and, or could sit after )");
        }

    }

    private void checkCondition(List<String> errorMessages, List<Token> tokenList, int currentIndex) {
        if ((tokenList.get(currentIndex - 1).type == Token.And || tokenList.get(currentIndex - 1).type == Token.Or || tokenList.get(currentIndex - 1).type == Token.StartParenthesis) == false) {
            errorMessages.add("only and, or could sit before condition");
        }
        if (currentIndex == tokenList.size() - 1) {
            return;
        }
        if ((tokenList.get(currentIndex + 1).type == Token.Or || tokenList.get(currentIndex + 1).type == Token.And || tokenList.get(currentIndex + 1).type == Token.EndParenthesis) == false) {
            errorMessages.add("only ), and, or could sit after condition");
        }

    }
    
    public Node parseExpressionIntoTree(String expression, List<ReqComponentVO> rcs) {
        Node tree = null;
        if (expression != null && expression.trim().length() > 0) {
            StatementVO parsedS = parseExpressionIntoStatementVO(expression, rcs);
            if (parsedS != null) {
                tree = parsedS.getTree(false);
            }
        }
        return tree;
    }
    
    public StatementVO parseExpressionIntoStatementVO(String expression,
            List<ReqComponentVO> rcs) {
        StatementVO parsedS = null;
        try {
            List<String> tokenValueList = getTokenValue(expression);
            List<Token> tokenList = getTokenList(tokenValueList);
            if (!doValidateExpression(new ArrayList<String>(), tokenList)) return null;
            List<Node<Token>> nodeList = toNodeList(tokenList);
            List<Node<Token>> rpnList = getRPN(nodeList);
            parsedS = statementVOFromRPN(rpnList, rcs);

            if (parsedS != null) {
                parsedS.simplify();
            }
        } catch (Exception error) {
            error.printStackTrace();
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
            List<ReqComponentVO> rcs) {
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
                LuStatementInfo luStatementInfo = new LuStatementInfo();
                luStatementInfo.setOperator(op);
                subS.setLuStatementInfo(luStatementInfo);
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
                        subS.addStatementVO(wrapReqComponent(op, (ReqComponentVO)left));
                    } else {
                        subS.addStatementVO((StatementVO)left);
                    }
                    if (right instanceof ReqComponentVO) {
                        subS.addStatementVO(wrapReqComponent(op, (ReqComponentVO)right));
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
                    (ReqComponentVO)conditionStack.pop().getUserObject());
        } else {
            statementVO = (StatementVO)conditionStack.pop().getUserObject();
        }

        return statementVO;
    }
    
    private StatementVO wrapReqComponent(StatementOperatorTypeKey op, ReqComponentVO rc) {
        StatementVO wrapS = new StatementVO();
        LuStatementInfo wrapLuStatementInfo = new LuStatementInfo();
        wrapLuStatementInfo.setOperator(op);
        wrapS.setLuStatementInfo(wrapLuStatementInfo);
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
