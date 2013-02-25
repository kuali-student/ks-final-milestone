package org.kuali.student.enrollment.class1.krms.util;

import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;

import java.util.ArrayList;
import java.util.List;

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

    private boolean doValidateExpression(List<String> errorMessages, final List<ExpressionToken> tokens,  List<String> propsAlpha) {
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
        int lastIndex = tokens.size() - 1;
        if ((tokens.get(lastIndex).type == ExpressionToken.EndParenthesis || tokens.get(lastIndex).type == ExpressionToken.Condition) == false) {
            errorMessages.add("must end with ) or condition");
            return false;
        }
        if (countToken(tokens, ExpressionToken.StartParenthesis) != countToken(tokens, ExpressionToken.EndParenthesis)) {
            errorMessages.add("() not in pair");
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
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
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
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
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
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
                tokenList.get(currentIndex + 1);
        boolean validToken = true;
        if (prevToken != null && (prevToken.type == ExpressionToken.And || prevToken.type == ExpressionToken.Or || prevToken.type == ExpressionToken.StartParenthesis || prevToken.type == ExpressionToken.Condition) == false) {
            errorMessages.add("only and, or, ( could sit before (");
            validToken = false;
        }
        if (nextToken != null && (nextToken.type == ExpressionToken.Condition || nextToken.type == ExpressionToken.StartParenthesis) == false) {
            errorMessages.add("only ( and condition could sit after (");
            validToken = false;
        }
        return validToken;
    }

    private boolean checkEndParenthesis(List<String> errorMessages, List<ExpressionToken> tokenList, int currentIndex) {
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
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
        ExpressionToken prevToken = (tokenList == null || currentIndex - 1 < 0)? null :
                tokenList.get(currentIndex - 1);
        ExpressionToken nextToken = (tokenList == null || currentIndex + 1 >= tokenList.size())? null :
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
}
