/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.util;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * This is a helper class that validates logical expressions and rebuild a tree based on a valid expression.
 *
 * @author Kuali Student Team
 */
public class RuleLogicExpressionParser {

    private String expression;
    private boolean isOtiose;

    private List<ExpressionToken> tokenList;

    private void checkExpressionSet() {
        if (tokenList == null || expression == null) {
            throw new java.lang.IllegalStateException("setExpression must be called first");
        }
    }

    public boolean validateExpression(List<String> errorMessages) {
        checkExpressionSet();
        return doValidateExpression(errorMessages, tokenList);
    }

    private boolean doValidateExpression(List<String> errorMessages, final List<ExpressionToken> tokens) {
        boolean valid = true;
        List<String> seenConditonValues = new ArrayList<String>();

        // allow empty expression. ie. user wants the entire rule deleted.
        if (tokens.size() > 0) {
            //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
            int firstToken = 0;
            int firstTokenType = tokens.get(firstToken).getType();
            if ((firstTokenType != ExpressionToken.PARENTHESIS_START)
                    && (firstTokenType != ExpressionToken.CONDITION)) {
                errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_LOGIC_EXPRESSION_START);
                return false;
            }
        } else {
            return true;
        }
        if (countToken(tokens, ExpressionToken.PARENTHESIS_START) != countToken(tokens, ExpressionToken.PARENTHESIS_END)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_PARENTHESIS_NOT_PAIR);
            return false;
        }
        if (!validateAndOr(errorMessages, tokens)) {
            return false;
        }
        // condition cannot duplicate
        for (int i = 0; i < tokens.size(); i++) {
            ExpressionToken token = tokens.get(i);

            ExpressionToken prevToken = null;
            ExpressionToken nextToken = null;
            if (tokens != null) {
                prevToken = i - 1 < 0 ? null : tokens.get(i - 1);
                nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);
            }

            if (token.getType() == ExpressionToken.OPERATOR_AND) {
                if (!checkAnd(errorMessages, prevToken, nextToken)) {
                    return false;
                }
            } else if (token.getType() == ExpressionToken.OPERATOR_OR) {
                if (!checkOr(errorMessages, prevToken, nextToken)) {
                    return false;
                }
            } else if (token.getType() == ExpressionToken.PARENTHESIS_START) {
                if (!checkStartParenthesis(errorMessages, nextToken)) {
                    return false;
                }
            } else if (token.getType() == ExpressionToken.PARENTHESIS_END) {
                if (!checkEndParenthesis(errorMessages, prevToken, nextToken)) {
                    return false;
                }
            } else if (token.getType() == ExpressionToken.CONDITION) {
                if (seenConditonValues.contains(token.getValue())) {
                    errorMessages.add("Condition " + token.getValue() + " is duplicated.");
                    return false;
                } else {
                    seenConditonValues.add(token.getValue());
                }
                if (!checkCondition(errorMessages, prevToken, nextToken)) {
                    return false;
                }
            }
        }
        return valid;
    }

    private boolean validateAndOr(List<String> errorMessages, List<ExpressionToken> nodeList) {
        Stack<ExpressionToken> operatorStack = new Stack<ExpressionToken>();

        for (ExpressionToken token : nodeList) {
            if (token.getType() == ExpressionToken.PARENTHESIS_END) {
                ExpressionToken operator = operatorStack.pop();

                //Check if first type is a OR or a AND
                if (operator.getType() != ExpressionToken.PARENTHESIS_START) {

                    //Check if all other types are the same as the first type.
                    while (operatorStack.peek().getType() != ExpressionToken.PARENTHESIS_START) {
                        ExpressionToken next = operatorStack.pop();
                        if (next.getType() != operator.getType()) {
                            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_OPERATOR_TYPE);
                            return false;
                        }
                    }

                    operatorStack.pop();// pop the (
                }
            } else if (token.getType() != ExpressionToken.CONDITION) {
                operatorStack.push(token);
            }
        } if (!operatorStack.isEmpty()) {
            ExpressionToken operator = operatorStack.pop();
            //Check if all other types are the same as the first type.
            while (!operatorStack.isEmpty()) {
                ExpressionToken next = operatorStack.pop();
                if (next.getType() != operator.getType()) {
                    errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_OPERATOR_TYPE);
                    return false;
                }
            }
        }

        return true;
    }

    private int countToken(List<ExpressionToken> tokenList, int type) {
        int count = 0;
        for (ExpressionToken t : tokenList) {
            if (t.getType() == type) {
                count++;
            }
        }
        return count;
    }

    private boolean checkAnd(List<String> errorMessages, ExpressionToken prevToken, ExpressionToken nextToken) {
        boolean validToken = true;
        if ((prevToken != null) && (prevToken.getType() != ExpressionToken.CONDITION) && (prevToken.getType() != ExpressionToken.PARENTHESIS_END)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_PRECEDE_OPERATOR);
            validToken = false;
        }
        if ((nextToken != null) && (nextToken.getType() != ExpressionToken.CONDITION) && (nextToken.getType() != ExpressionToken.PARENTHESIS_START)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_FOLLOW_OPERATOR);
            validToken = false;
        }
        return validToken;
    }

    private boolean checkOr(List<String> errorMessages, ExpressionToken prevToken, ExpressionToken nextToken) {
        boolean validToken = true;
        if ((prevToken != null) && (prevToken.getType() != ExpressionToken.CONDITION) && (prevToken.getType() != ExpressionToken.PARENTHESIS_END)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_PRECEDE_OR);
            validToken = false;
        }
        if ((nextToken != null) && (nextToken.getType() != ExpressionToken.CONDITION) && (nextToken.getType() != ExpressionToken.PARENTHESIS_START)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_FOLLOW_OR);
            validToken = false;
        }
        return validToken;
    }

    private boolean checkStartParenthesis(List<String> errorMessages, ExpressionToken nextToken) {
        boolean validToken = true;
        if ((nextToken != null) && (nextToken.getType() != ExpressionToken.CONDITION) && (nextToken.getType() != ExpressionToken.PARENTHESIS_START)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_FOLLOW_START_PARENTHESIS);
            validToken = false;
        }
        return validToken;
    }

    private boolean checkEndParenthesis(List<String> errorMessages, ExpressionToken prevToken, ExpressionToken nextToken) {
        boolean validToken = true;
        if ((prevToken != null) && (prevToken.getType() != ExpressionToken.CONDITION) && (prevToken.getType() == ExpressionToken.PARENTHESIS_END)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_PRECEDE_END_PARENTHESIS);
            validToken = false;
        }
        if ((nextToken != null) && (!ExpressionToken.isOperator(nextToken.getType())) && (nextToken.getType() != ExpressionToken.PARENTHESIS_END)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_FOLLOW_END_PARENTHESIS);
            validToken = false;
        }
        return validToken;
    }

    private boolean checkCondition(List<String> errorMessages, ExpressionToken prevToken, ExpressionToken nextToken) {
        boolean validToken = true;
        if ((prevToken != null) && (!ExpressionToken.isOperator(prevToken.getType())) && (prevToken.getType() != ExpressionToken.PARENTHESIS_START)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_PRECEDE_CONDITION);
            validToken = false;
        }
        if ((nextToken != null) && (!ExpressionToken.isOperator(nextToken.getType())) && (nextToken.getType() != ExpressionToken.PARENTHESIS_END) && (nextToken.getType() != ExpressionToken.PARENTHESIS_START)) {
            errorMessages.add(KRMSConstants.KRMS_MSG_ERROR_FOLLOW_CONDITION);
            validToken = false;
        }
        return validToken;
    }

    private List<ExpressionToken> getTokenList(List<String> tokenValueList) {
        List<ExpressionToken> returnList = new ArrayList<ExpressionToken>();
        for (String value : tokenValueList) {
            if (value.isEmpty()) {
                continue;
            }
            if ("(".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.setType(ExpressionToken.PARENTHESIS_START);
                returnList.add(t);
            } else if (")".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.setType(ExpressionToken.PARENTHESIS_END);
                returnList.add(t);

            } else if ("and".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.setType(ExpressionToken.OPERATOR_AND);
                returnList.add(t);

            } else if ("or".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.setType(ExpressionToken.OPERATOR_OR);
                returnList.add(t);

            } else {
                ExpressionToken t = new ExpressionToken();
                t.setType(ExpressionToken.CONDITION);
                t.setValue(value);
                returnList.add(t);

            }
        }
        return returnList;
    }

    private List<String> getTokenValue(String expression) {
        List<String> tokenValueList = new ArrayList<String>();
        StringBuilder tokenValue = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {

            char ch = expression.charAt(i);
            if (Character.isSpaceChar(ch)) {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuilder();
            } else if (ch == '(' || ch == ')') {
                tokenValueList.add(tokenValue.toString());
                tokenValue = new StringBuilder();
                tokenValueList.add(String.valueOf(ch));
            } else {
                tokenValue.append(ch);
            }
        }
        tokenValueList.add(tokenValue.toString());
        return tokenValueList;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        List<String> tokenValueList = getTokenValue(expression.toLowerCase());
        this.tokenList = getTokenList(tokenValueList);
    }

    public String getExpression() {
        return expression;
    }

    public List<ExpressionToken> getTokenList() {
        return tokenList;
    }

    /**
     * Method to rebuild the tree based on the set token list that was built from given expression.
     *
     * @param ruleEditor
     * @param viewHelper
     * @return PropositionEditor
     */
    public PropositionEditor parseExpressionIntoRule(RuleEditor ruleEditor, RuleViewHelperService viewHelper) {

        Stack<ExpressionToken> tokenStack = new Stack<ExpressionToken>();
        int index = 0;
        isOtiose = false;
        for (ExpressionToken token : tokenList) {
            if (isOtioseCompound(token, tokenList, index)) {
                index++;
                continue;
            }

            tokenStack.push(token);
            index++;
        }

        Map<String, PropositionEditor> simplePropositions = new LinkedHashMap<String, PropositionEditor>();
        Queue<PropositionEditor> compoundPropositions = new LinkedList<PropositionEditor>();
        this.setupPropositions(simplePropositions, compoundPropositions, ruleEditor.getPropositionEditor());

        return ruleFromStack(tokenStack, simplePropositions, compoundPropositions, ruleEditor, viewHelper);
    }

    private boolean isOtioseCompound(ExpressionToken token, List<ExpressionToken> tokenList, int index) {

        if (token.getType() == ExpressionToken.PARENTHESIS_END && isOtiose) {
            isOtiose = false;
            return true;
        }

        if (token.getType() == ExpressionToken.PARENTHESIS_START) {
            if (tokenList.size() > index + 2) {
                if (tokenList.get(index + 1).getType() == ExpressionToken.CONDITION && tokenList.get(index + 2).getType() == ExpressionToken.PARENTHESIS_END) {
                    isOtiose = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This is a recursive method that loop thru the proposition tree to add all simple proposition in a map so that
     * they can easily be retrieved by their keys when we rebuild the proposition tree.
     * <p/>
     * It also creates a queue with all the existing compound propositions so that the root proposition could be
     * retrieved first.
     *
     * @param simplePropositions
     * @param compoundPropositions
     * @param propositionEditor
     */
    private void setupPropositions(Map<String, PropositionEditor> simplePropositions, Queue<PropositionEditor> compoundPropositions, PropositionEditor propositionEditor) {
        if (propositionEditor.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            simplePropositions.put(propositionEditor.getKey(), propositionEditor);
        } else {
            compoundPropositions.add(propositionEditor);
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                this.setupPropositions(simplePropositions, compoundPropositions, child);
            }
            propositionEditor.setCompoundEditors(new ArrayList<PropositionEditor>());
        }

    }

    /**
     * Build the proposition tree from the token stack in reverse order. If the token is a end parenthesis,
     * recursively call this method again to resolve the inner group (compound proposition). When the next token
     * is a start parenthesis, return the group.     *
     *
     * @param tokenStack
     * @param simplePropositions
     * @param compoundPropositions
     * @param rule
     * @return
     */
    public PropositionEditor ruleFromStack(Stack<ExpressionToken> tokenStack, Map<String, PropositionEditor> simplePropositions,
                                           Queue<PropositionEditor> compoundPropositions, RuleEditor rule, RuleViewHelperService viewHelper) {

        //Get the first compound from the queue, if nothing left create new one.
        PropositionEditor currentCompound = null;
        if (compoundPropositions.peek() == null) {
            currentCompound = new PropositionEditor();
            currentCompound.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
            currentCompound.setRuleId(rule.getId());
            currentCompound.setKey((String) rule.getCompoundKeys().next());
            currentCompound.setCompoundEditors(new ArrayList<PropositionEditor>());
        } else {
            currentCompound = compoundPropositions.remove();
        }

        //Loop thru tokens and recreate the proposition tree.
        while (!tokenStack.empty()) {
            ExpressionToken token = tokenStack.pop();
            if (token.getType() == ExpressionToken.PARENTHESIS_END) {
                PropositionEditor compound = ruleFromStack(tokenStack, simplePropositions, compoundPropositions, rule, viewHelper);
                currentCompound.getCompoundEditors().add(0, compound);
            } else if (token.getType() == ExpressionToken.PARENTHESIS_START) {
                return currentCompound;
            } else if (token.getType() == ExpressionToken.CONDITION) {
                currentCompound.getCompoundEditors().add(0, simplePropositions.remove(token.getValue().toUpperCase()));
            } else if (token.getType() == ExpressionToken.OPERATOR_AND) {
                viewHelper.setTypeForCompoundOpCode(currentCompound, LogicalOperator.AND.getCode());
            } else if (token.getType() == ExpressionToken.OPERATOR_OR) {
                viewHelper.setTypeForCompoundOpCode(currentCompound, LogicalOperator.OR.getCode());
            }
        }

        return currentCompound;
    }

}
