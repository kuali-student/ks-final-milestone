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

import org.jacorb.idl.runtime.token;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;

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

    private List<ExpressionToken> tokenList;

    private void checkExpressionSet() {
        if (tokenList == null || expression == null) {
            throw new java.lang.IllegalStateException("setExpression must be called first");
        }
    }

    public void checkMissingRCs(List<PropositionEditor> missingProps, List<PropositionEditor> props) {
        checkExpressionSet();

        List<String> conditionValues = new ArrayList<String>();
        for (ExpressionToken token : tokenList) {
            if (token.type == ExpressionToken.Condition) {
                conditionValues.add(token.value);
            }
        }

        if (props != null && props.isEmpty()) {
            for (PropositionEditor p : props) {
                boolean foundId = false;
                if (!conditionValues.isEmpty()) {
                    for (String conditionValue : conditionValues) {
                        if (conditionValue != null && conditionValue.equalsIgnoreCase(p.getCompoundOpCode())) {
                            foundId = true;
                            break;
                        }
                    }
                }
                if (!foundId) {
                    missingProps.add(p);
                }
            }
        }
    }

    public boolean validateExpression(List<String> errorMessages, List<String> propsAlpha) {
        checkExpressionSet();
        return doValidateExpression(errorMessages, tokenList, propsAlpha);
    }

    private boolean doValidateExpression(List<String> errorMessages, final List<ExpressionToken> tokens, List<String> propsAlpha) {
        boolean valid = true;
        List<String> seenConditonValues = new ArrayList<String>();
        // allow empty expression. ie. user wants the entire rule deleted.
        if (tokens.size() == 0) {
            return true;
        }

        if ((tokens.get(0).type == ExpressionToken.StartParenthesis
                || tokens.get(0).type == ExpressionToken.Condition) == false) {
            errorMessages.add("error.krms.logic.expression.start");
            return false;
        }
        if (countToken(tokens, ExpressionToken.StartParenthesis) != countToken(tokens, ExpressionToken.EndParenthesis)) {
            errorMessages.add("error.krms.logic.parenthesis.notpair");
            return false;
        }
        if (!validateAndOr(errorMessages, tokens)) {
            return false;
        }
        // condition cannot duplicate
        for (int i = 0; i < tokens.size(); i++) {
            ExpressionToken token = tokens.get(i);
            if (token.type == ExpressionToken.And) {
                if (!checkAnd(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == ExpressionToken.Or) {
                if (!checkOr(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == ExpressionToken.StartParenthesis) {
                if (!checkStartParenthesis(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == ExpressionToken.EndParenthesis) {
                if (!checkEndParenthesis(errorMessages, tokens, i)) {
                    return false;
                }
            } else if (token.type == ExpressionToken.Condition) {
                if (seenConditonValues.contains(token.value)) {
                    errorMessages.add("Condition " + token.value + " is duplicated.");
                    return false;
                } else {
                    seenConditonValues.add(token.value);
                }
                if (!checkCondition(errorMessages, tokens, i, propsAlpha)) {
                    return false;
                }
            }
        }
        return valid;
    }

    private boolean validateAndOr(List<String> errorMessages, List<ExpressionToken> nodeList) {
        Stack<ExpressionToken> operatorStack = new Stack<ExpressionToken>();

        for (ExpressionToken token : nodeList) {
            if (token.type == ExpressionToken.EndParenthesis) {
                ExpressionToken operator = operatorStack.pop();

                //Check if first type is a OR or a AND
                if (operator.type != ExpressionToken.StartParenthesis) {

                    //Check if all other types are the same as the first type.
                    while (operatorStack.peek().type != ExpressionToken.StartParenthesis) {
                        ExpressionToken next = operatorStack.pop();
                        if (next.type != operator.type) {
                            errorMessages.add("error.krms.logic.operator.type");
                            return false;
                        }
                    }

                    operatorStack.pop();// pop the (
                }
            } else if (token.type != ExpressionToken.Condition) {
                operatorStack.push(token);
            }
        }

        return true;
    }

    private int countToken(List<ExpressionToken> tokenList, int type) {
        int count = 0;
        for (ExpressionToken t : tokenList) {
            if (t.type == type) {
                count++;
            }
        }
        return count;
    }

    private boolean checkAnd(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0) ? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size()) ? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == ExpressionToken.Condition || prevToken.type == ExpressionToken.EndParenthesis) == false) {
            errorMessages.add("error.krms.logic.precede.operator");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("error.krms.logic.follow.operator");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkOr(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0) ? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size()) ? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == ExpressionToken.Condition || prevToken.type == ExpressionToken.EndParenthesis) == false) {
            errorMessages.add("error.krms.logic.precede.or");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("error.krms.logic.follow.or");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkStartParenthesis(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0) ? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size()) ? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("error.krms.logic.follow.start.parenthesis");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkEndParenthesis(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0) ? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size()) ? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == ExpressionToken.Condition || prevToken.type == ExpressionToken.EndParenthesis) == false) {
            errorMessages.add("error.krms.logic.precede.end.parenthesis");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Or || nextToken.type == ExpressionToken.And || nextToken.type == ExpressionToken.EndParenthesis) == false) {
            errorMessages.add("error.krms.logic.follow.end.parenthesis");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkCondition(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex,
                                   List<String> propsAlpha) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0) ? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size()) ? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == ExpressionToken.And || prevToken.type == ExpressionToken.Or || prevToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("error.krms.logic.precede.condition");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Or || nextToken.type == ExpressionToken.And || nextToken.type == ExpressionToken.EndParenthesis || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("error.krms.logic.follow.condition");
            validToken = false;
        }
        return validToken;
    }

    private List<ExpressionToken> getTokenList(List<String> tokenValueList) {
        List<ExpressionToken> tokenList = new ArrayList<ExpressionToken>();
        for (String value : tokenValueList) {
            if (value.isEmpty()) {
                continue;
            }
            if ("(".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.type = ExpressionToken.StartParenthesis;
                tokenList.add(t);
            } else if (")".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.type = ExpressionToken.EndParenthesis;
                tokenList.add(t);

            } else if ("and".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.type = ExpressionToken.And;
                tokenList.add(t);

            } else if ("or".equals(value)) {
                ExpressionToken t = new ExpressionToken();
                t.type = ExpressionToken.Or;
                tokenList.add(t);

            } else {
                ExpressionToken t = new ExpressionToken();
                t.type = ExpressionToken.Condition;
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
            if (Character.isSpaceChar(ch)) {
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

    public void setExpression(String expression) {
        this.expression = expression;
        List<String> tokenValueList = getTokenValue(expression);
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
     * @return
     */
    public PropositionEditor parseExpressionIntoRule(RuleEditor ruleEditor) {

        Stack<ExpressionToken> tokenStack = new Stack<ExpressionToken>();
        for (ExpressionToken token : tokenList) {
            tokenStack.push(token);
        }

        Map<String, PropositionEditor> simplePropositions = new LinkedHashMap<String, PropositionEditor>();
        Queue<PropositionEditor> compoundPropositions = new LinkedList<PropositionEditor>();
        this.setupPropositions(simplePropositions, compoundPropositions, ruleEditor.getPropositionEditor());

        return ruleFromStack(tokenStack, simplePropositions, compoundPropositions, ruleEditor);
    }

    /**
     * This is a recursive method that loop thru the proposition tree to add all simple proposition in a map so that
     * they can easily be retrieved by their keys when we rebuild the proposition tree.
     *
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
                                         Queue<PropositionEditor> compoundPropositions, RuleEditor rule) {

        //Get the first compound from the queue, if nothing left create new one.
        PropositionEditor currentCompound = null;
        if(compoundPropositions.peek()==null){
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
            if(token.type == ExpressionToken.EndParenthesis){
                PropositionEditor compound = ruleFromStack(tokenStack, simplePropositions, compoundPropositions, rule);
                currentCompound.getCompoundEditors().add(0,compound);
            } else if (token.type == ExpressionToken.StartParenthesis) {
                return currentCompound;
            } else if (token.type == ExpressionToken.Condition){
                currentCompound.getCompoundEditors().add(0,simplePropositions.remove(token.value.toUpperCase()));
            } else if (token.type == ExpressionToken.And){
                PropositionTreeUtil.setTypeForCompoundOpCode(currentCompound, LogicalOperator.AND.getCode());
            } else if (token.type == ExpressionToken.Or){
                PropositionTreeUtil.setTypeForCompoundOpCode(currentCompound, LogicalOperator.OR.getCode());
            }
        }

        return currentCompound;
    }

}
