package org.kuali.rice.krms.util;

import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
            errorMessages.add("must start with ( or condition");
            return false;
        }
        if ((tokenList.get(1).type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("parent compound proposition cannot be changed");
            return false;
        }
        int lastIndex = tokens.size() - 1;
        if ((tokens.get(lastIndex).type == ExpressionToken.EndParenthesis || tokens.get(lastIndex).type == ExpressionToken.Condition) == false) {
            errorMessages.add("must end with ) or condition");
            return false;
        }
        if (countToken(tokens, ExpressionToken.StartParenthesis) != countToken(tokens, ExpressionToken.EndParenthesis)) {
            errorMessages.add("() not in pair");
            return false;
        }
        if (!validateAndOr(errorMessages,tokens)){
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
            if (token.type == ExpressionToken.Condition) {
                //do nothing
            } else if (token.type == ExpressionToken.EndParenthesis) {
                ExpressionToken operator = operatorStack.pop();

                //Check if first type is a OR or a AND
                if (operator.type != ExpressionToken.StartParenthesis){

                    //Check if all other types are the same as the first type.
                    while (operatorStack.peek().type != ExpressionToken.StartParenthesis) {
                        ExpressionToken next = operatorStack.pop();
                        if (next.type != operator.type){
                            errorMessages.add("Operators within parenthesis must be the same type.");
                            return false;
                        }
                    }

                    operatorStack.pop();// pop the (
                }
            } else {
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
            errorMessages.add("only ) and condition could sit before and");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after and");
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
            errorMessages.add("only ) and condition could sit before or");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after or");
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
        if (prevToken != null && (prevToken.type == ExpressionToken.Condition) == false) {
            errorMessages.add("cannot alter compound proposition");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after (");
            validToken = false;
        }
        if (prevToken != null && (prevToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.Condition || prevToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("cannot alter compound proposition");
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
            errorMessages.add("only condition and ) could sit before )");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Or || nextToken.type == ExpressionToken.And || nextToken.type == ExpressionToken.EndParenthesis) == false) {
            errorMessages.add("only ), and, or could sit after )");
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
            errorMessages.add("only and, or could sit before condition");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Or || nextToken.type == ExpressionToken.And || nextToken.type == ExpressionToken.EndParenthesis || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("only (, ), and, or could sit after condition");
            validToken = false;
        }
        ExpressionToken conditionToken = tokenList.get(currentIndex);
        String conditionLetter = conditionToken.value;
        boolean validConditonLetter = false;
        if (propsAlpha != null) {
            for (String propAlpha : propsAlpha) {
                if (propAlpha != null &&
                        propAlpha.equalsIgnoreCase(conditionLetter)) {
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

    public PropositionEditor parseExpressionIntoRule(RuleEditor ruleEditor) {

        PropositionEditor oldEditor = (PropositionEditor) ruleEditor.getProposition();
        List<PropositionEditor> rcs = this.getPropositions(new ArrayList<PropositionEditor>(), oldEditor);

        List<ExpressionToken> rpnList = getRPN(tokenList);
        return ruleFromRPN(rpnList, rcs);
    }

    private List<PropositionEditor> getPropositions(List<PropositionEditor> propositions, PropositionEditor propositionEditor) {
        propositions.add(propositionEditor);
        if (propositionEditor.getCompoundComponents() != null) {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                this.getPropositions(propositions, child);
            }
        }
        return propositions;
    }

    /**
     * If higher push to stack, else pop till less than or equal, add to list push to stack if ( push to stack if ) pop to
     * list till (.
     * <p/>
     * http://en.wikipedia.org/wiki/Reverse_Polish_notation
     */
    private List<ExpressionToken> getRPN(List<ExpressionToken> nodeList) {
        List<ExpressionToken> rpnList = new ArrayList<ExpressionToken>();
        Stack<ExpressionToken> operatorStack = new Stack<ExpressionToken>();

        for (ExpressionToken token : nodeList) {
            if (token.type == ExpressionToken.Condition) {
                rpnList.add(token);
            } else if (token.type == ExpressionToken.EndParenthesis) {
                while (operatorStack.peek().type != ExpressionToken.StartParenthesis) {
                    rpnList.add(operatorStack.pop());
                }
                operatorStack.pop();// pop the (
            } else {
                operatorStack.push(token);
            }
        }

        //Add remaining operators to rpnlist
        while (operatorStack.isEmpty() == false){
            rpnList.add(operatorStack.pop());
        }

        return rpnList;
    }

    /**
     * Build the binary tree from list of tokens
     */
    private PropositionEditor ruleFromRPN(List<ExpressionToken> rpnList, List<PropositionEditor> rcs) {
        //if rule is empty
        if (rpnList.size() == 0) {
            return new PropositionEditor();
        }

        Stack<PropositionEditor> conditionStack = new Stack<PropositionEditor>();
        Stack<PropositionEditor> simpleProps = new Stack<PropositionEditor>();
        for (ExpressionToken token : rpnList) {
            if (token.type == ExpressionToken.Condition) {
                PropositionEditor rc = lookupPropositionEditor(rcs, token.value);
                if (rc.getPropositionTypeCode().equals("C")){
                    rc.setCompoundEditors(new ArrayList<PropositionEditor>());
                }
                conditionStack.push(rc);
            } else {
                if(simpleProps.empty()){
                    simpleProps.push(conditionStack.pop());
                }
                simpleProps.push(conditionStack.pop());
                if(conditionStack.peek().getPropositionTypeCode().equals("C")) {
                    PropositionEditor compound = conditionStack.pop();
                    if (token.type == ExpressionToken.And){
                        PropositionTreeUtil.setTypeForCompoundOpCode(compound, LogicalOperator.AND.getCode());
                    } else if (token.type == ExpressionToken.Or) {
                        PropositionTreeUtil.setTypeForCompoundOpCode(compound, LogicalOperator.OR.getCode());
                    }
                    while (!simpleProps.empty()) {
                        compound.getCompoundEditors().add(simpleProps.pop());
                    }
                    conditionStack.push(compound);
                }
            }
        }

        return conditionStack.pop();
    }

    private PropositionEditor lookupPropositionEditor(List<PropositionEditor> rcs, String key) {
        if (rcs != null) {
            for (PropositionEditor rc : rcs) {
                if (rc.getKey().equalsIgnoreCase(key)) {
                    return rc;
                }
            }
        }
        return null;
    }
}
